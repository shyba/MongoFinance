package br.com.webfinance.model;

import java.util.Date;

import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class InstallmentFinancialEntry extends FinancialEntry{
	private int installments;
	private int initialDay;
	private int initialMonth;
	private int initialYear;
	private int finalDay;
	private int finalMonth;
	private int finalYear;

	
	
	
	public InstallmentFinancialEntry(String name, Date maturityDate,
			double totalValue, EntryType entryType) {
		super(name, totalValue, entryType);
	}



	public int getInstallments() {
		return installments;
	}



	public void setInstallments(int installments) {
		this.installments = installments;
	}



	public int getInitialDay() {
		return initialDay;
	}



	public void setInitialDay(int initialDay) {
		this.initialDay = initialDay;
	}



	public int getInitialMonth() {
		return initialMonth;
	}



	public void setInitialMonth(int initialMonth) {
		this.initialMonth = initialMonth;
	}



	public int getInitialYear() {
		return initialYear;
	}



	public void setInitialYear(int initialYear) {
		this.initialYear = initialYear;
	}




	public int getFinalDay() {
		return finalDay;
	}



	public void setFinalDay(int finalDay) {
		this.finalDay = finalDay;
	}



	public int getFinalMonth() {
		return finalMonth;
	}



	public void setFinalMonth(int finalMonth) {
		this.finalMonth = finalMonth;
	}



	public int getFinalYear() {
		return finalYear;
	}



	public void setFinalYear(int finalYear) {
		this.finalYear = finalYear;
	}

	
}
