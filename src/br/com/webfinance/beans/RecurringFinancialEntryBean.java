package br.com.webfinance.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.webfinance.model.Category;
import br.com.webfinance.model.EntryType;
import br.com.webfinance.model.InstallmentFinancialEntry;
import br.com.webfinance.model.RecurrentFinancialEntry;
import br.com.webfinance.model.RecurrentType;
import br.com.webfinance.repo.CategoryRepository;
import br.com.webfinance.repo.RecurrentRepository;

@Controller
@Scope("session")
public class RecurringFinancialEntryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6613432872033960457L;
	Logger logger = Logger.getLogger(getClass());
	private RecurrentFinancialEntry financialEntry;
	private List<RecurrentFinancialEntry> financialEntries;
	private String registerType = "credit";
	private int newInstallments;

	@Autowired
	RecurrentRepository recurrentRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	BudgetBean budgetBean;
	
	

	public RecurringFinancialEntryBean() {
		resetFinancialEntry();
	}
	
	public void init(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    if (!facesContext.isPostback() && !facesContext.isValidationFailed()) {
	    	Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	    	String id = params.get("id");
	    	if(id!=null && !id.trim().isEmpty())
	    		financialEntry = recurrentRepository.findOne(id);
	    	else
	    		resetFinancialEntry();
	    }
		 
	}

	private void resetFinancialEntry() {
		financialEntry = new RecurrentFinancialEntry("Nome", 14, EntryType.CREDIT, RecurrentType.MONTHLY);
		financialEntry.setDescription("Descrição");
		Calendar cal = Calendar.getInstance();
		financialEntry.setMaturityDay(cal.get(Calendar.DAY_OF_MONTH));

	}

	public String register() {
		if (financialEntry != null && financialEntry.isValid()){
			financialEntry.setBudget(budgetBean.getSelectedBudget());			
			recurrentRepository.save(financialEntry);
			FacesMessage msg = new FacesMessage("Lançamento salvo!",
					financialEntry.getName());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("Dados inválidos!",
					financialEntry.getName());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "recurringForm";
		}
		resetFinancialEntry();
		reloadEntries();
		return "recurring";
	}

	public EntryType[] getEntryTypes() {
		return EntryType.values();
	}
	
	public RecurrentType[] getRecurrentTypes() {
		return RecurrentType.values();
	}

	private void sendMessage(String message) {
		FacesMessage msg = new FacesMessage(message, financialEntry.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}


	public List<RecurrentFinancialEntry> getFinancialEntries() {
		reloadEntries();
		return financialEntries;
	}

	public void reloadEntries() {
		financialEntries = new ArrayList<RecurrentFinancialEntry>();
		financialEntries.addAll(recurrentRepository.findByBudget(budgetBean.getSelectedBudget()));
	}

	public RecurrentFinancialEntry getFinancialEntry() {
		return financialEntry;
	}

	public void setFinancialEntry(RecurrentFinancialEntry financialEntry) {
		this.financialEntry = financialEntry;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

	public int getNewInstallments() {
		return newInstallments;
	}

	public void setNewInstallments(int newInstallments) {
		this.newInstallments = newInstallments;
	}
	
	public void removeEntry(ActionEvent event)  
	{  
	   UIParameter parameter = (UIParameter) event.getComponent().findComponent("itemId");  
	   String itemId = parameter.getValue().toString();
	   recurrentRepository.delete(itemId);
	}
	
	public void toggleClose(ActionEvent event)  
	{  
	   UIParameter parameter = (UIParameter) event.getComponent().findComponent("itemId");  
	   String itemId = parameter.getValue().toString();
	   RecurrentFinancialEntry entry = recurrentRepository.findOne(itemId);
	   entry.setClosed(!entry.isClosed());
	   recurrentRepository.save(entry);
	}


}
