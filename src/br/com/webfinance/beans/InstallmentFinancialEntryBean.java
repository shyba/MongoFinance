package br.com.webfinance.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.webfinance.model.Category;
import br.com.webfinance.model.EntryType;
import br.com.webfinance.model.FinancialEntry;
import br.com.webfinance.model.InstallmentFinancialEntry;
import br.com.webfinance.repo.CategoryRepository;
import br.com.webfinance.repo.FinancialEntriesRepository;
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
	
	

	public InstallmentFinancialEntryBean() {
		resetFinancialEntry();
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
		if (financialEntry != null && financialEntry.isValid())
			installmentsRepository.save(financialEntry);
		FacesMessage msg = new FacesMessage("Lançamento salvo!",
				financialEntry.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		resetFinancialEntry();
		reloadEntries();
		return "installments";
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
		if (financialEntries == null || financialEntries.size() == 0) {
			reloadEntries();
		}
		return financialEntries;
	}

	public void reloadEntries() {
		financialEntries = new ArrayList<InstallmentFinancialEntry>();
		financialEntries.addAll(installmentsRepository.findAll());
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

}
