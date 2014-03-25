package br.com.webfinance.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.webfinance.model.Budget;
import br.com.webfinance.model.RecurrentFinancialEntry;
import br.com.webfinance.model.UserAccount;


@Repository
public interface RecurrentRepository extends PagingAndSortingRepository<RecurrentFinancialEntry,String>{

	List<RecurrentFinancialEntry> findAll();
	List<RecurrentFinancialEntry> findByBudget(Budget budget);
	
}
