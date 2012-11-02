package br.com.webfinance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RecurrentType {

	public static RecurrentType DAILY = new RecurrentType(0);
	public static RecurrentType MONTHLY = new RecurrentType(1);
	public static RecurrentType SEMESTRIAL = new RecurrentType(2);
	public static RecurrentType YEARLY = new RecurrentType(3);
	
	@Id
	private int value;

	public RecurrentType(int value) {
		super();
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RecurrentType))
			return false;
		RecurrentType other = (RecurrentType) obj;
		if (value != other.value)
			return false;
		return true;
	}
	
	
	
}
