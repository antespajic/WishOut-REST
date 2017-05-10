package hr.asc.appic.config.security;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.repository.UserRepository;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

	@Autowired PasswordEncoder passwordEncoder;
	
	@Autowired UserRepository userRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Map<Object,Object> map = (Map<Object,Object>)authentication.getDetails();
		String username = (String)map.get("username");
		String pw = authentication.getCredentials().toString();
		
		User user = userRepository.findByEmail(username);
		
		if (user == null) {
	        throw new BadCredentialsException("1000");
	    }
		
		if (!passwordEncoder.matches(pw, user.getPassword())) {
	        throw new BadCredentialsException("1000");
	    }
		
	    return new UsernamePasswordAuthenticationToken(username, pw, 
	    		Collections.emptyList());

	}

}
