package br.com.webfinance.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import br.com.webfinance.model.Budget;
import br.com.webfinance.model.Category;
import br.com.webfinance.model.EntryType;
import br.com.webfinance.model.FinancialEntry;
import br.com.webfinance.model.UserAccount;
import br.com.webfinance.repo.CategoryRepository;
import br.com.webfinance.repo.FinancialEntriesRepository;

import static br.com.webfinance.util.PageFlowConstants.ENTRIES;

@Controller
@Scope("session")
public class FinancialEntryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6613432872033960457L;
	Logger logger = Logger.getLogger(getClass());
	private FinancialEntry financialEntry;
	private List<FinancialEntry> financialEntries;
	private String registerType = "credit";

	@Autowired
	FinancialEntriesRepository financialEntriesRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	LoginBean loginBean;
	@Autowired
	BudgetBean budgetBean;
	
	
	public FinancialEntryBean() {
		resetFinancialEntry();
	}

	private void resetFinancialEntry() {
		financialEntry = new FinancialEntry("Nome", 0, EntryType.CREDIT);
		financialEntry.setDescription("Descrição");
		Calendar cal = Calendar.getInstance();
		financialEntry.setMaturityDay(cal.get(Calendar.DAY_OF_MONTH));
		financialEntry.setMaturityMonth(cal.get(Calendar.MONTH) + 1);
		financialEntry.setMaturityYear(cal.get(Calendar.YEAR));
		financialEntry.setPaymentDate(cal.getTime());

	}
	
	public void init(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    if (!facesContext.isPostback() && !facesContext.isValidationFailed()) {
	    	Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	    	String id = params.get("id");
	    	if(id!=null && !id.trim().isEmpty())
	    		financialEntry = financialEntriesRepository.findOne(id);
	    	else
	    		resetFinancialEntry();
	    }
		 
	}

	public String register() {
		if (financialEntry.getValue() > financialEntry.getTotalValue())
			financialEntry.setClosed(true);
		financialEntry.setBudget(budgetBean.getSelectedBudget());
		if (financialEntry != null && financialEntry.isValid())
			financialEntriesRepository.save(financialEntry);
		FacesMessage msg = new FacesMessage("Lançamento salvo!",
				financialEntry.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		resetFinancialEntry();
		reloadEntries();
		return ENTRIES;
	}

	public EntryType[] getEntryTypes() {
		return EntryType.values();
	}

	private void sendMessage(String message) {
		FacesMessage msg = new FacesMessage(message, financialEntry.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onEdit(RowEditEvent event) {
		FinancialEntry edited = (FinancialEntry) event.getObject();

		// Category original =
		// categoryRepository.findByName(edited.getName()).get(0);
		//
		// Category superCat=null;
		// if(edited.getSuperCategory().getName().length()>0)
		// superCat =
		// categoryRepository.findByName(edited.getSuperCategory().getName()).get(0);
		// original.setName(edited.getName());
		// original.setSuperCategory(superCat);
		if (edited.getValue() > edited.getTotalValue()){
			edited.setClosed(true);
			sendMessage("Fechado automaticamente por ter excedido o valor total!");
		}
		if (edited != null && edited.isValid()) {
			financialEntriesRepository.save(edited);
			sendMessage("Lançamento Editado!");
		} else {
			FacesContext.getCurrentInstance().validationFailed();
			sendMessage("Edição Inválida!");
			reloadEntries();
		}

	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edição cancelada",
				((FinancialEntry) event.getObject()).getName());
		reloadEntries();

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<FinancialEntry> getFinancialEntries() {

		reloadEntries();
		return financialEntries;
	}

	public void reloadEntries() {	
		
		financialEntries = new ArrayList<FinancialEntry>();
		financialEntries.addAll(financialEntriesRepository.findByBudget(budgetBean.getSelectedBudget()));
	}

	public FinancialEntry getFinancialEntry() {
		return financialEntry;
	}

	public void setFinancialEntry(FinancialEntry financialEntry) {
		this.financialEntry = financialEntry;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public List<Category> getCategories() {
		return categoryRepository.findByUser(loginBean.getUser());
	}
	
	public void removeEntry(ActionEvent event)  
	{  
	   UIParameter parameter = (UIParameter) event.getComponent().findComponent("itemId");  
	   String itemId = parameter.getValue().toString();
	   financialEntriesRepository.delete(itemId);
	}
	
	public void toggleClose(ActionEvent event)  
	{  
	   UIParameter parameter = (UIParameter) event.getComponent().findComponent("itemId");  
	   String itemId = parameter.getValue().toString();
	   FinancialEntry entry = financialEntriesRepository.findOne(itemId);
	   if(!entry.isClosed()){
		   entry.setValue(entry.getTotalValue());
	   }
	   entry.setClosed(!entry.isClosed());
	   financialEntriesRepository.save(entry);
	}

}
