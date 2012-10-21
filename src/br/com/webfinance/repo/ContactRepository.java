package br.com.webfinance.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.webfinance.model.Contact;


@Repository
public interface ContactRepository extends PagingAndSortingRepository<Contact,String>{

	List<Contact> findAll();
	List<Contact> findByName(String name);
	
}