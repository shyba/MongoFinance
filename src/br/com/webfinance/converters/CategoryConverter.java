package br.com.webfinance.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.webfinance.model.Category;
import br.com.webfinance.repo.CategoryRepository;



@Component(value = "categoryConverter")
public class CategoryConverter implements Converter {

	
	private CategoryRepository categoryRepository;

	@Autowired
	public CategoryConverter(CategoryRepository categoryRepository){
  
		this.categoryRepository=categoryRepository;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value.trim().equals("")) {
			return null;
		} else {
			for (Category c : categoryRepository.findBySuperCategory(null)) {
				if (value.equals(c.getName())) {
					return c;
				}
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if(value instanceof Category){
			return ((Category) value).getName();
		}
		return null;
	}

	public CategoryRepository getCategoryRepository() {
		return categoryRepository;
	}

	
	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

}
