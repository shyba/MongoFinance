package br.com.webfinance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.webfinance.model.Role;
import br.com.webfinance.model.UserAccount;
import br.com.webfinance.repo.RoleRepository;
import br.com.webfinance.repo.UserAccountRepository;



@Service
public class UserService {

	@Autowired
	private UserAccountRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public Role getRole(String role) {
		return roleRepository.findOne(role);
	}

	public boolean create(UserAccount user) {
		Assert.isNull(user.getId());
//		user.setId(UUID.randomUUID().toString().replace("-", ""));
		// duplicate username
		if (userRepository.findByUsername(user.getUsername()) != null) {
			return false;
		}
		user.setEnabled(true);
		userRepository.save(user);
		return true;
	}

	public void save(UserAccount user) {
		Assert.notNull(user.getId());
		userRepository.save(user);
	}

	public void delete(UserAccount user) {
		Assert.notNull(user.getId());
		userRepository.delete(user);
	}

	public UserAccount getByUsernameAndPassword(String username, String password) {
		List<UserAccount> users = userRepository.findByUsernameAndPasswordQuery(username, password);
		if (users.size() == 1) {
			return users.get(0);
		}
		return null;
	}

	public UserAccount getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
