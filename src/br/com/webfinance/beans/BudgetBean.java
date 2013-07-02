package br.com.webfinance.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.webfinance.model.Budget;
import br.com.webfinance.model.UserAccount;
import br.com.webfinance.repo.BudgetRepository;
import br.com.webfinance.repo.UserAccountRepository;

import static br.com.webfinance.util.PageFlowConstants.BUDGETFORM; 

@Controller
@Scope("session")
public class BudgetBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6613432872033960457L;
	Logger logger = Logger.getLogger(getClass());

	private List<Budget> userBudgets;
	private Budget selectedBudget;
	private Budget registerBudget;


	@Autowired
	BudgetRepository budgetRepository;
	@Autowired
	UserAccountRepository userAccountRepository;
	@Autowired
	LoginBean loginBean;
	


	public BudgetBean() {
		
	}
	
	public void init(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    if (!facesContext.isPostback() && !facesContext.isValidationFailed()) {
	    	Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	    	String id = params.get("id");
	    	if(id!=null && !id.trim().isEmpty())
	    		registerBudget = budgetRepository.findOne(new ObjectId(id));
	    	else{
	    		this.registerBudget = new Budget();
	    		this.registerBudget.setName("Novo");
	    	}
	    }
		 
	}

	
	public String register() {
		UserAccount user = loginBean.getUser();
		user = userAccountRepository.findOne(user.getId());
		for(Budget budget: getUserBudgets()){
			if(budget.getName().equals(registerBudget.getName())){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Este nome de orçamento já existe!",
						registerBudget.getName());
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return BUDGETFORM;
			}
		}
		if(registerBudget.get_id()==null){
			budgetRepository.save(registerBudget);
			user.getBudgets().add(registerBudget);
		}else{
			budgetRepository.save(registerBudget);
		}
		userAccountRepository.save(user);
		userBudgets = getUserBudgets();
		FacesMessage msg = new FacesMessage("Budget salvo!",
				registerBudget.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		registerBudget = new Budget();
		registerBudget.setName("Novo");
		loginBean.setUser(user);
		selectedBudget = budgetRepository.findOne(selectedBudget.get_id());
		return BUDGETFORM;
	}

	public List<Budget> getUserBudgets() {
		UserAccount user = loginBean.getUser();
		checkDefaultBudget(user);
		userBudgets = user.getBudgets();
		return userBudgets;
	}


	private void checkDefaultBudget(UserAccount user) {
		user = userAccountRepository.findOne(user.getId());
		if(user.getBudgets()==null)user.setBudgets(new ArrayList<Budget>());
		if(user.getBudgets().size()==0){
			Budget defaultBudget = new Budget();
			defaultBudget.setName("Principal");
			budgetRepository.save(defaultBudget);
			user.getBudgets().add(defaultBudget);
			user = userAccountRepository.save(user);
		}
		loginBean.setUser(user);
	}



	public void setUserBudgets(List<Budget> userBudgets) {
		this.userBudgets = userBudgets;
	}



	public Budget getRegisterBudget() {
		return registerBudget;
	}


	public void setRegisterBudget(Budget registerBudget) {
		this.registerBudget = registerBudget;
	}


	public Budget getSelectedBudget() {
		if(selectedBudget==null){
			loginBean.getUser();
			checkDefaultBudget(loginBean.getUser());
			selectedBudget = loginBean.getUser().getBudgets().get(0);
		}
		return selectedBudget;
	}


	public void setSelectedBudget(Budget selectedBudget) {
		this.selectedBudget = selectedBudget;
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Orçamento selecionado!",
				selectedBudget.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void removeBudget(ActionEvent event)  
	{  
	   UIParameter parameter = (UIParameter) event.getComponent().findComponent("itemId");  
	   String itemId = parameter.getValue().toString();
	   Budget rm = budgetRepository.findOne(new ObjectId(itemId));
	   UserAccount user = userAccountRepository.findOne(loginBean.getUser().getId());
	   user.getBudgets().remove(rm);
	   userAccountRepository.save(user);
	   loginBean.setUser(user);
	   budgetRepository.delete(rm); 
	   userBudgets = getUserBudgets();
	}
	

}
