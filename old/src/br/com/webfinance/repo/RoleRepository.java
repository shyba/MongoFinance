package br.com.webfinance.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.webfinance.model.Role;


public interface RoleRepository extends MongoRepository<Role, String> {
}
