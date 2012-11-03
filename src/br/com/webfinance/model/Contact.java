package br.com.webfinance.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Document(collection="contacts")
public class Contact {

	


	public Contact(String name, String mail) {

		this.name = name;
		this.mail = mail;
	}
	
	@Id
    private ObjectId _id;
	private String name;
	private String mail;
	private String mobileNumber;
	private String homeNumber;




	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
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



	public ObjectId get_id() {
		return _id;
	}



	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	
	
}

