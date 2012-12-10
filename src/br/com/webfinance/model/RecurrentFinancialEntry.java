package br.com.webfinance.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RecurrentFinancialEntry {
	@Id
	private ObjectId _id;
	private String name;
	private String description;
	@Reference
	private Category category;
	private int maturityDay;
	private double value;


	private EntryType entryType;
	private boolean closed;
	private RecurrentType recurrentType;
	private Date firstDate;
	
	@DBRef
	private UserAccount user;
	@DBRef
	private Budget budget;
	
	
	
	public RecurrentFinancialEntry(String name, int maturityDay, EntryType entryType, RecurrentType recurrentType) {
		this.name = name;
		this.entryType = entryType;
		this.recurrentType=recurrentType;
		this.maturityDay = maturityDay;
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



	public ObjectId get_id() {
		return _id;
	}



	public void set_id(ObjectId _id) {
		this._id = _id;
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



	public int getMaturityDay() {
		return maturityDay;
	}



	public void setMaturityDay(int maturityDay) {
		this.maturityDay = maturityDay;
	}



	public Budget getBudget() {
		return budget;
	}



	public void setBudget(Budget budget) {
		this.budget = budget;
	}



	public double getValue() {
		return value;
	}



	public void setValue(double value) {
		this.value = value;
	}



	public EntryType getEntryType() {
		return entryType;
	}



	public void setEntryType(EntryType entryType) {
		this.entryType = entryType;
	}



	public boolean isClosed() {
		return closed;
	}



	public void setClosed(boolean closed) {
		this.closed = closed;
	}



	public UserAccount getUser() {
		return user;
	}



	public void setUser(UserAccount user) {
		this.user = user;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RecurrentFinancialEntry))
			return false;
		RecurrentFinancialEntry other = (RecurrentFinancialEntry) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		return true;
	}
	
	public boolean isValid(){
		return value>0&&maturityDay>0&&maturityDay<31&&recurrentType!=null&&entryType!=null;
	}



}
