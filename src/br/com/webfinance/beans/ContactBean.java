package br.com.webfinance.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.webfinance.model.Contact;
import br.com.webfinance.repo.ContactRepository;

//@ManagedBean(name="contactBean", eager=true)
//@SessionScoped
@Controller
@Scope("session")
public class ContactBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6613432872033960457L;
	private Contact contact;
	
	@Autowired
	ContactRepository contactRepository;

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
	   return "contacts";//go to welcome.xhtml
	  }


	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
    public void onEdit(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage("Contato Editado", ((Contact) event.getObject()).getName());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
      
    public void onCancel(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage("Edição cancelada", ((Contact) event.getObject()).getName());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    } 

}
