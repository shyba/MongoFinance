package br.com.webfinance.beans;


import static br.com.webfinance.util.PageFlowConstants.INDEX;
import static br.com.webfinance.util.PageFlowConstants.INSTALLMENTS;
import static br.com.webfinance.util.PageFlowConstants.REGISTER;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.webfinance.model.UserAccount;
import br.com.webfinance.repo.UserAccountRepository;
import br.com.webfinance.service.UserService;
import br.com.webfinance.util.Messages;



@ManagedBean
@SessionScoped
@Component
public class LoginBean  implements Serializable {

        private static final long serialVersionUID = 2868742783741899100L;
        private UserAccount user = new UserAccount();
        private UserAccount newUser = new UserAccount();

        private String userName;
        
        private String password;

        

        @Resource(name = "authenticationManager")
        private AuthenticationManager am;
        @Autowired
        UserAccountRepository userAccountRepository;
        @Autowired
        UserService userService;
        
    public LoginBean() {

    }

    //ActionEvent actionEvent
        public String login() throws java.io.IOException {
                try {
            Authentication request = new UsernamePasswordAuthenticationToken(this.getUserName(), getPassword());
            Authentication result = am.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            user = userAccountRepository.findByUsername(this.getUserName());
            System.out.println("Login Success! .. ");
            return INSTALLMENTS;
        } catch (AuthenticationException ex) {
                System.out.println("Login Failed");
                FacesContext.getCurrentInstance().addMessage("formLogin", new FacesMessage(FacesMessage.SEVERITY_WARN,"Falha!", Messages.getString("LoginBean.msgLoginFailed")));   //$NON-NLS-1$ //$NON-NLS-3$
             
            return INDEX;
        }
        }
        
        private boolean isValid(String item){
        	return item!=null && item.length()>3;
        }
        
        public String register(){
        	if(isValid(newUser.getFirstname()) && isValid(newUser.getLastname()) 
        			&& isValid(newUser.getUsername()) && isValid(newUser.getPassword())){
        		String hashed = new MessageDigestPasswordEncoder("SHA-256").encodePassword(newUser.getPassword(), null); //$NON-NLS-1$
        		newUser.setPassword(hashed);
        		newUser.addRole(userService.getRole("ROLE_USER"));
        		userService.create(newUser);
        		newUser = new UserAccount();
        		return INDEX;
        	}
        	return REGISTER;
        }

        public String logout() {
                //System.out.println("LoginBean.logout()....");
        		user = null;
                SecurityContextHolder.getContext().setAuthentication(null);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                                .clear();
                return INDEX;
        }
        
        public String getLogoutHidden() {
        	user = null;
                //System.out.println("LoginBean.getLogoutHidden()....");
                SecurityContextHolder.getContext().setAuthentication(null);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                                .clear();
                return INDEX;
        }
        
        public void setLogoutHidden(String logoutHidden) {
        }
        

        public String getUserName() {
                return userName;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public UserAccount getUser() {
                return user;
        }

        public void setUser(UserAccount user) {
                this.user = user;
        }

		public UserAccount getNewUser() {
			return newUser;
		}

		public void setNewUser(UserAccount newUser) {
			this.newUser = newUser;
		}


		public void saveDays() {
			this.user = userAccountRepository.save(this.user);
			
		}
}
