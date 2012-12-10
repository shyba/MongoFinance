package br.com.webfinance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


public enum RecurrentType {
	DAILY("Diário"),MONTHLY("Mensal"),SEMESTRIAL("Semestral"),YEARLY("Anual");
	
	private String label;
	
	private RecurrentType(String label){
		this.label=label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}

