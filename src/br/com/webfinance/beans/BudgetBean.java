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
import br.com.webfinance.model.InstallmentFinancialEntry;
import br.com.webfinance.repo.CategoryRepository;
import br.com.webfinance.repo.FinancialEntriesRepository;
import br.com.webfinance.repo.InstallmentsRepository;

@Controller
@Scope("session")
public class BudgetBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6613432872033960457L;
	Logger logger = Logger.getLogger(getClass());

	private List<FinancialEntry> financialEntries;


	@Autowired
	FinancialEntriesRepository financialEntriesRepository;
	@Autowired
	InstallmentsRepository installmentsRepository;

	@Autowired
	CategoryRepository categoryRepository;

	public BudgetBean() {

	}



	public List<FinancialEntry> getFinancialEntries() {
		financialEntries=new ArrayList<FinancialEntry>(financialEntriesRepository.findAll());
		for(InstallmentFinancialEntry entry : installmentsRepository.findAll()){
			FinancialEntry fin = new FinancialEntry(entry.getName(),entry.getTotalValue(),entry.getEntryType());
			fin.setDescription(entry.getDescription());
			fin.setMaturityDay(entry.getMaturityDay());
			fin.setValue(entry.getValue());
			fin.setClosed(entry.isClosed());
			financialEntries.add(fin);
			
			
		}

		return financialEntries;
	}


	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

}
