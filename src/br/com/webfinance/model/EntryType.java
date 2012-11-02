package br.com.webfinance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class EntryType {

	public static EntryType DEBT = new EntryType(0);
	public static EntryType CREDIT = new EntryType(1);
	
	@Id
	private int value;

	public EntryType(int value) {
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
		if (!(obj instanceof EntryType))
			return false;
		EntryType other = (EntryType) obj;
		if (value != other.value)
			return false;
		return true;
	}
	
	
	
}
