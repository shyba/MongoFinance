package br.com.webfinance.beans;

import static br.com.webfinance.util.PageFlowConstants.INSTALLMENTS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import br.com.webfinance.model.Category;
import br.com.webfinance.model.EntryType;
import br.com.webfinance.model.InstallmentFinancialEntry;
import br.com.webfinance.model.UserAccount;
import br.com.webfinance.repo.CategoryRepository;
import br.com.webfinance.repo.InstallmentsRepository;

@Controller
@Scope("session")
public class InstallmentFinancialEntryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6613432872033960457L;
	Logger logger = Logger.getLogger(getClass());
	private InstallmentFinancialEntry financialEntry;
	private InstallmentFinancialEntry editingFinancialEntry;
	private List<InstallmentFinancialEntry> financialEntries;
	private String registerType = "credit";
	private int newInstallments;

	@Autowired
	InstallmentsRepository installmentsRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	BudgetBean budgetBean;
	
	

	public InstallmentFinancialEntryBean() {
		resetFinancialEntry();
	}
	
	public void init(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    if (!facesContext.isPostback() && !facesContext.isValidationFailed()) {
	    	Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	    	String id = params.get("id");
	    	if(id!=null && !id.trim().isEmpty())
	    		financialEntry = installmentsRepository.findOne(id);
	    	else
	    		resetFinancialEntry();
	    }
		 
	}

	private void resetFinancialEntry() {
		financialEntry = new InstallmentFinancialEntry("Nome", 0, EntryType.CREDIT);
		financialEntry.setDescription("Descrição");
		Calendar cal = Calendar.getInstance();
		financialEntry.setMaturityDay(cal.get(Calendar.DAY_OF_MONTH));

	}

	public String register() {
		if (financialEntry.getValue() > financialEntry.getTotalValue())
			financialEntry.setClosed(true);
		financialEntry.setBudget(budgetBean.getSelectedBudget());
		if (financialEntry != null && financialEntry.isValid())
			installmentsRepository.save(financialEntry);
		FacesMessage msg = new FacesMessage("Lançamento salvo!",
				financialEntry.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		resetFinancialEntry();
		reloadEntries();
		return INSTALLMENTS;
	}

	public EntryType[] getEntryTypes() {
		return EntryType.values();
	}

	private void sendMessage(String message) {
		FacesMessage msg = new FacesMessage(message, financialEntry.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onEdit(RowEditEvent event) {
		InstallmentFinancialEntry edited = (InstallmentFinancialEntry) event.getObject();
		if(edited.getInitialDate()==null){
			sendMessage("Data inicial deve ser informada!");
			return;
		}

		if(newInstallments>0 && edited.getFinalDate()==null){
			if(edited.getInitialDate()!=null){
				Date finalDate = new Date(new DateTime(edited.getInitialDate()).plusMonths(newInstallments-1).getMillis());
				edited.setFinalDate(finalDate);
			}else{
				sendMessage("Preencha apenas uma das formas - Parcelas ou Data final");
				return;
			}
		}else if(newInstallments==0 && edited.getFinalDate()!=null){
			if(edited.getFinalDate().getTime()<edited.getInitialDate().getTime()){
				sendMessage("Data final deve ser maior que inicial.");
				return;
			}
		}else{
			sendMessage("Número de parcelas ou data final deve ser informado!!");
			return;
		}

		if (edited.getValue() > edited.getTotalValue()){
			edited.setClosed(true);
			sendMessage("Fechado automaticamente por ter excedido o valor total!");
		}
		if(edited.isClosed()){
			DateTime ini = new DateTime(edited.getInitialDate());
			int between = edited.getInstallments();
			for(int x=1;x<between;x++){
				edited.getPaymentDates().add(new Date(ini.plusMonths(1).getMillis()));
			}
			sendMessage("Foram adicionados os meses entre a data inicial e a data final como pagos!");
		}
		if (edited != null && edited.isValid()) {
			installmentsRepository.save(edited);
			sendMessage("Lançamento Editado!");
		} else {
			FacesContext.getCurrentInstance().validationFailed();
			sendMessage("Edição Inválida!");
			reloadEntries();
		}

	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edição cancelada",
				((InstallmentFinancialEntry) event.getObject()).getName());
		reloadEntries();

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<InstallmentFinancialEntry> getFinancialEntries() {
		reloadEntries();
		return financialEntries;
	}

	public void reloadEntries() {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getDetails();
		financialEntries = new ArrayList<InstallmentFinancialEntry>();
		financialEntries.addAll(installmentsRepository.findByBudget(budgetBean.getSelectedBudget()));
	}

	public InstallmentFinancialEntry getFinancialEntry() {
		return financialEntry;
	}

	public void setFinancialEntry(InstallmentFinancialEntry financialEntry) {
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

	public InstallmentFinancialEntry getEditingFinancialEntry() {
		return editingFinancialEntry;
	}

	public void setEditingFinancialEntry(
			InstallmentFinancialEntry editingFinancialEntry) {
		this.editingFinancialEntry = editingFinancialEntry;
	}
	
	public void removeEntry(ActionEvent event)  
	{  
	   UIParameter parameter = (UIParameter) event.getComponent().findComponent("itemId");  
	   String itemId = parameter.getValue().toString();
	   installmentsRepository.delete(itemId);
	}
	
	public void toggleClose(ActionEvent event)  
	{  
	   UIParameter parameter = (UIParameter) event.getComponent().findComponent("itemId");  
	   String itemId = parameter.getValue().toString();
	   InstallmentFinancialEntry entry = installmentsRepository.findOne(itemId);
	   if(!entry.isClosed()){
		   entry.setValue(entry.getTotalValue());
	   }
	   entry.setClosed(!entry.isClosed());
	   installmentsRepository.save(entry);
	}

}
