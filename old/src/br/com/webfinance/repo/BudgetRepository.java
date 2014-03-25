package br.com.webfinance.repo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.webfinance.model.Budget;

@Repository
public interface BudgetRepository extends
		PagingAndSortingRepository<Budget, ObjectId> {

	List<Budget> findAll();

}
