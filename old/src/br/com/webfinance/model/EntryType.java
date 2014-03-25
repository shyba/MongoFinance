package br.com.webfinance.model;


public enum EntryType{
	CREDIT("Crédito"),DEBIT("Débito");
	private String label;
	private EntryType(String label) {
		this.label=label;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}