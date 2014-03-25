package br.com.webfinance.beans;
import static br.com.webfinance.util.PageFlowConstants.CONTACTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.webfinance.model.Contact;
import br.com.webfinance.repo.ContactRepository;
@Controller
@Scope("session")
public class ContactDataTable { 
			

	  
	    private List<Contact> contacts = new ArrayList<Contact>();  
	    private Contact contact;
	    Logger logger = Logger.getLogger(getClass());
	    
		ContactRepository contactRepository;
	  	  
	    @Autowired
	    public ContactDataTable(ContactRepository contactRepository) {  
	    	this.contactRepository=contactRepository;
	    	reload();
//		    contacts = new ArrayList<Contact>();  
		
//		    contacts.addAll(contactRepository.findAll());
		}
	    
		
		public void init(){
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    if (!facesContext.isPostback() && !facesContext.isValidationFailed()) {
		    	Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		    	String id = params.get("id");
		    	if(id!=null && !id.trim().isEmpty())
		    		contact = contactRepository.findOne(id);
		    	else
		    		this.contact = new Contact("Nome do contato", "email@exemplo.com");
		    }
			 
		}


	    private void reload(){
	    	contacts.clear();
	    	contacts.addAll(contactRepository.findAll());
	    }
	    
		public List<Contact> getContacts() {
			reload();
			return contacts;
		}

		public void setContacts(List<Contact> contacts) {
			this.contacts = contacts;
		}

		public void onEdit(RowEditEvent event) {
			Contact edited = (Contact) event.getObject();
			logger.debug(edited.getMobileNumber());
			logger.debug(edited.getHomeNumber());
			
			contactRepository.save(edited);
			reload();
			FacesMessage msg = new FacesMessage("Contato Editado!",
					edited.getName());

			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
	      
		
		public Contact getContact() {
			if(contact==null)this.contact=new Contact("Nome do contato", "email@exemplo.com");
			return contact;
		}
		
		 public String register()
		  {
			 System.out.println("Saving... Count:"+contactRepository.count());
		   //store data in DB
		   System.out.println(this.contact);
		   contactRepository.save(contact);
		   System.out.println("Saved! Count:"+contactRepository.count());
		   this.contact=new Contact("Nome do contato", "email@exemplo.com");
		   reload();
		   return CONTACTS;//go to welcome.xhtml
		  }


		public void setContact(Contact contact) {
			this.contact = contact;
		}
		
		public void removeEntry(ActionEvent event)  
		{  
		   UIParameter parameter = (UIParameter) event.getComponent().findComponent("itemId");  
		   String itemId = parameter.getValue().toString();
		   contactRepository.delete(itemId);
		}
	
}
