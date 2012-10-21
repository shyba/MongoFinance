package br.com.webfinance.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

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

	@Autowired
	CategoryRepository categoryRepository;

	public Category getCategory() {
		if (category == null)
			this.category = new Category("Nome da Categoria");
		itemSuperCategory = null;
		return category;
	}

	public String register() {
		System.out.println("Saving... Count:" + categoryRepository.count());
		// store data in DB
		if (categoryRepository.findByName(category.getName()).size() > 0) {
			sendMessage("Este nome já existe!");
			return "categories";
		}
		System.out.println(this.category);
		if (getItemSuperCategory() != null
				&& getItemSuperCategory().length() > 0)
			category.setSuperCategory(categoryRepository.findByName(
					getItemSuperCategory()).get(0));
		else
			category.setSuperCategory(null);
		categoryRepository.save(category);
		sendMessage("Categoria salva!");
		System.out.println("Saved! Count:" + categoryRepository.count());
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
		categories = new ArrayList<Category>();
		for (Category superCat : getSuperCategories()) {
			categories.add(superCat);
			categories.addAll(categoryRepository.findBySuperCategory(superCat));
		}
		return categories;
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
		Category original = categoryRepository.findByName(edited.getName()).get(0);
		
		Category superCat=null;
		if(edited.getSuperCategory().getName().length()>0)
			superCat = categoryRepository.findByName(edited.getSuperCategory().getName()).get(0);
		original.setName(edited.getName());
		original.setSuperCategory(superCat);
		categoryRepository.save(original);
		FacesMessage msg = new FacesMessage("Categoria Editada",
				original.getName());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edição cancelada",
				((Category) event.getObject()).getName());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
