package br.com.webfinance.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.webfinance.model.Budget;
import br.com.webfinance.model.InstallmentFinancialEntry;
import br.com.webfinance.model.UserAccount;


@Repository
public interface InstallmentsRepository extends PagingAndSortingRepository<InstallmentFinancialEntry,String>{

	List<InstallmentFinancialEntry> findAll();
	List<InstallmentFinancialEntry> findByBudget(Budget budget);

	
}
