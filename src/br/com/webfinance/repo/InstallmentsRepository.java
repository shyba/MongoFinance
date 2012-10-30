package br.com.webfinance.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.webfinance.model.InstallmentFinancialEntry;


@Repository
public interface InstallmentsRepository extends PagingAndSortingRepository<InstallmentFinancialEntry,String>{

	List<InstallmentFinancialEntry> findAll();

	
}
