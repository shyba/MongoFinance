package br.com.webfinance.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.webfinance.model.Category;
import br.com.webfinance.model.EntryType;
import br.com.webfinance.model.FinancialEntry;
import br.com.webfinance.repo.CategoryRepository;
import br.com.webfinance.repo.FinancialEntriesRepository;

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
	private String registerType="credit";
	
	@Autowired
	FinancialEntriesRepository financialEntriesRepository;
	@Autowired
	CategoryRepository categoryRepository;
	
	public FinancialEntryBean(){
		logger.error("Hello World");
		financialEntry = new FinancialEntry("Nome", 0, EntryType.CREDIT);
		financialEntry.setDescription("Descrição");
		Calendar cal = Calendar.getInstance();
		financialEntry.setMaturityDay(cal.get(Calendar.DAY_OF_MONTH));
		financialEntry.setMaturityMonth(cal.get(Calendar.MONTH)+1);
		financialEntry.setMaturityYear(cal.get(Calendar.YEAR));
		financialEntry.setPaymentDate(cal.getTime());
	}

	public String register() {
		logger.debug(financialEntry.getName());
		logger.debug(financialEntry.getDescription());
		logger.debug(financialEntry.getEntryType());
		logger.debug(financialEntry.getCategory());
		logger.debug(financialEntry.getMaturityDay());
		logger.debug(financialEntry.getMaturityMonth());
		logger.debug(financialEntry.getMaturityYear());
		logger.debug(financialEntry.getPaymentDate());
		logger.debug(financialEntry.getValue());
		logger.debug(financialEntry.getTotalValue());
		logger.debug(financialEntry.isClosed());
//		System.out.println("Saving... Count:" + categoryRepository.count());
//		// store data in DB
//		if (categoryRepository.findByName(category.getName()).size() > 0) {
//			sendMessage("Este nome já existe!");
//			return "categories";
//		}
//		System.out.println(this.category);
//		if (getItemSuperCategory() != null
//				&& getItemSuperCategory().length() > 0)
//			category.setSuperCategory(categoryRepository.findByName(
//					getItemSuperCategory()).get(0));
//		else
//			category.setSuperCategory(null);
//		categoryRepository.save(category);
//		sendMessage("Categoria salva!");
//		System.out.println("Saved! Count:" + categoryRepository.count());
		return "entries";
	}

	private void sendMessage(String message) {
		FacesMessage msg = new FacesMessage(message, financialEntry.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onEdit(RowEditEvent event) {
//		Category edited = (Category) event.getObject();
//		Category original = categoryRepository.findByName(edited.getName()).get(0);
//		
//		Category superCat=null;
//		if(edited.getSuperCategory().getName().length()>0)
//			superCat = categoryRepository.findByName(edited.getSuperCategory().getName()).get(0);
//		original.setName(edited.getName());
//		original.setSuperCategory(superCat);
//		categoryRepository.save(original);
		sendMessage("Lançamento Editado!");
	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edição cancelada",
				((Category) event.getObject()).getName());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<FinancialEntry> getFinancialEntries() {
		if(financialEntries==null || financialEntries.size()==0){
			reloadEntries();
		}
		return financialEntries;
	}
	
	public void reloadEntries(){
		financialEntries = new ArrayList<FinancialEntry>();
		financialEntries.addAll(financialEntriesRepository.findAll());
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
		return categoryRepository.findAll();
	}

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("log4j.xml")); 
		new FinancialEntryBean();
	}
	

}
