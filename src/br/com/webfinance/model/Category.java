package br.com.webfinance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Category {
	@Id
	private String name;
	private Category superCategory;
	public Category() {
	
	}

	public Category(String name, Category superCategory) {
		super();
		this.name = name;
		this.superCategory = superCategory;
	}
	public Category(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getSuperCategory() {
		return superCategory;
	}
	public void setSuperCategory(Category superCategory) {
		this.superCategory = superCategory;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((superCategory == null) ? 0 : superCategory.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Category))
			return false;
		Category other = (Category) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (superCategory == null) {
			if (other.superCategory != null)
				return false;
		} else if (!superCategory.equals(other.superCategory))
			return false;
		return true;
	}
	
	
	
}
