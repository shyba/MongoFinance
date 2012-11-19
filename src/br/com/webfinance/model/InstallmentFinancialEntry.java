package br.com.webfinance.model;

import java.util.Date;
import java.util.HashSet;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class InstallmentFinancialEntry {
	@Id
	private ObjectId _id;
	private String name;
	private String description;
	@Reference
	private Category category;
	private int maturityDay;
	private HashSet<Date> paymentDates = new HashSet<Date>();
	private double value;
	private double interestValue;
	private double totalValue;

	private EntryType entryType;
	private boolean closed;

	private Date initialDate;
	private Date finalDate;


	public InstallmentFinancialEntry(String name, double totalValue,
			EntryType entryType) {
		this.name = name;
		this.totalValue = totalValue;
		this.entryType = entryType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + _id.hashCode();
		result = prime * result
				+ ((entryType == null) ? 0 : entryType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof InstallmentFinancialEntry))
			return false;
		InstallmentFinancialEntry other = (InstallmentFinancialEntry) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (!_id.equals(other._id))
			return false;
		if (entryType == null) {
			if (other.entryType != null)
				return false;
		} else if (!entryType.equals(other.entryType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public EntryType getEntryType() {
		return entryType;
	}

	public void setEntryType(EntryType entryType) {
		this.entryType = entryType;
	}

	@Override
	public String toString() {
		return name + " - " + description;
	}

	public int getMaturityDay() {
		return maturityDay;
	}

	public void setMaturityDay(int maturityDay) {
		this.maturityDay = maturityDay;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public boolean isValid() {
		boolean valid = true;
		valid = name != null && name.length() > 0;
		valid = valid
				&& ((closed && totalValue <= value) || (!closed && value <= totalValue));
		return valid;
	}

	public HashSet<Date> getPaymentDates() {
		return paymentDates;
	}

	public void setPaymentDates(HashSet<Date> paymentDates) {
		this.paymentDates = paymentDates;
	}

	public double getInterestValue() {
		return interestValue;
	}

	public void setInterestValue(double interestValue) {
		this.interestValue = interestValue;
	}

	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}

	public int getInstallments() {
		return Months.monthsBetween(new DateTime(finalDate),
				new DateTime(initialDate)).getMonths();
	}
	
	public int getPaidInstallments() {
		return paymentDates.size();
	}
	
	public boolean paidMonth(){
		for(Date date:paymentDates){
			if(new DateTime(date).getMonthOfYear()==new DateTime().getMonthOfYear())
				return true;
		}
		return false;
	}

}
