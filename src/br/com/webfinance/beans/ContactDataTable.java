package br.com.webfinance.beans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.webfinance.model.Contact;
import br.com.webfinance.repo.ContactRepository;


@Controller
@Scope("session")
public class ContactDataTable { 


	  
	    private List<Contact> contacts;  
	    
	    @Autowired
		ContactRepository contactRepository;
	  	  

	    public ContactDataTable() {  
//		    contacts = new ArrayList<Contact>();  
		
//		    contacts.addAll(contactRepository.findAll());
		}



		public List<Contact> getContacts() {
			contacts=contactRepository.findAll();
			return contacts;
		}


		public void setContacts(List<Contact> contacts) {
			this.contacts = contacts;
		}


		public void onEdit(RowEditEvent event) {  
//	        FacesMessage msg = new FacesMessage("Car Edited", ((Car) event.getObject()).getModel());  
	  
//	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	    }  
	      
	    public void onCancel(RowEditEvent event) {  
//	        FacesMessage msg = new FacesMessage("Car Cancelled", ((Car) event.getObject()).getModel());  
	  
//	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	    }  
	
}
