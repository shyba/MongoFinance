package br.com.webfinance.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.webfinance.model.Category;
import br.com.webfinance.repo.CategoryRepository;

@Controller
@Scope("session")
public class CategoryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6613432872033960457L;
	private Category category;
	private List<Category> superCategories;
	private List<Category> categories;
	private List<String> superCategoriesNames;
	private String itemSuperCategory;

	CategoryRepository categoryRepository;

	@Autowired
	public CategoryBean(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
		reloadCategories();
	}
	
	public void init(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    if (!facesContext.isPostback() && !facesContext.isValidationFailed()) {
	    	Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	    	String id = params.get("id");
	    	if(id!=null && !id.trim().isEmpty())
	    		category = categoryRepository.findOne(id);
	    	else
	    		this.category = new Category("Nome da Categoria");
	    }
		 
	}

	public Category getCategory() {
		if (category == null)
			this.category = new Category("Nome da Categoria");
		itemSuperCategory = null;
		return category;
	}

	public String register() {
		if (category.get_id()==null && categoryRepository.findByName(category.getName()).size() > 0) {
			sendMessage("Este nome já existe!");
			return "categories";
		}
		if (getItemSuperCategory() != null
				&& getItemSuperCategory().length() > 0)
			category.setSuperCategory(categoryRepository.findByName(
					getItemSuperCategory()).get(0));
		else
			category.setSuperCategory(null);
		categoryRepository.save(category);
		sendMessage("Categoria salva!");

		this.category = new Category("Nome da Categoria");
		itemSuperCategory = null;
		reloadCategories();
		return "categories";
	}

	private void sendMessage(String message) {
		FacesMessage msg = new FacesMessage(message, category.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getSuperCategories() {
		return categoryRepository.findBySuperCategory(null);
	}

	public String getItemSuperCategory() {
		return itemSuperCategory;
	}

	public List<Category> getCategories() {
		reloadCategories();
		return categories;
	}

	public void reloadCategories() {
		categories = new ArrayList<Category>();
		for (Category superCat : getSuperCategories()) {
			categories.add(superCat);
			categories.addAll(categoryRepository.findBySuperCategory(superCat));
		}
	}

	public void setItemSuperCategory(String itemSuperCategory) {
		this.itemSuperCategory = itemSuperCategory;
	}

	public List<String> getSuperCategoriesNames() {
		superCategoriesNames = new ArrayList<String>();
		for (Category c : getSuperCategories())
			superCategoriesNames.add(c.getName());
		return superCategoriesNames;
	}

	public void onEdit(RowEditEvent event) {
		Category edited = (Category) event.getObject();
		if (edited.getSuperCategory() != null
				&& edited.getName().equals(edited.getSuperCategory().getName())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Super categoria não pode ser a mesma!", edited.getName());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			edited.setSuperCategory(null);
			return;
		}
		categoryRepository.save(edited);
		reloadCategories();
		FacesMessage msg = new FacesMessage("Categoria Editada",
				edited.getName());

		FacesContext.getCurrentInstance().addMessage(null, msg);
		reloadCategories();

	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edição cancelada",
				((Category) event.getObject()).getName());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public CategoryRepository getCategoryRepository() {
		return categoryRepository;
	}

	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public void removeEntry(ActionEvent event)  
	{  
	   UIParameter parameter = (UIParameter) event.getComponent().findComponent("itemId");  
	   String itemId = parameter.getValue().toString();
	   Category rm = categoryRepository.findOne(itemId);
	   if(rm!=null)
	   for(Category cat:categoryRepository.findBySuperCategory(rm)){
		   categoryRepository.delete(cat);		   
	   }
	   categoryRepository.delete(itemId);
	}

}
