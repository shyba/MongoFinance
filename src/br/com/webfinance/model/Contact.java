package br.com.webfinance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Document(collection="contacts")
public class Contact {

	


	public Contact(String name, String mail) {
		Assert.isTrue(StringUtils.hasText(name), "O contato tem que ter um nome!");
		Assert.isTrue(StringUtils.hasText(mail), "O contato tem que ter um email!");
		this.name = name;
		this.mail = mail;
	}
	@Id
	private String name;
	private String relationship;
	private String mail;
	private String mobileNumber;
	private String homeNumber;
	private String city;
	private String state;
	


	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getRelationship() {
		return relationship;
	}



	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}



	public String getMail() {
		return mail;
	}



	public void setMail(String mail) {
		this.mail = mail;
	}



	public String getMobileNumber() {
		return mobileNumber;
	}



	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}



	public String getHomeNumber() {
		return homeNumber;
	}



	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Contact))
			return false;
		Contact other = (Contact) obj;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return name+" - "+mail;
	}
	
	
}

