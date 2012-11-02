package br.com.webfinance.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RecurrentFinancialEntry extends FinancialEntry{
	private RecurrentType recurrentType;
	private Date firstDate;
	
	
	
	public RecurrentFinancialEntry(String name, Date maturityDate,
			double totalValue, EntryType entryType, RecurrentType recurrentType) {
		super(name, totalValue, entryType);
		this.recurrentType=recurrentType;
	}



	public RecurrentType getRecurrentType() {
		return recurrentType;
	}



	public void setRecurrentType(RecurrentType recurrentType) {
		this.recurrentType = recurrentType;
	}



	public Date getFirstDate() {
		return firstDate;
	}



	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}


}
