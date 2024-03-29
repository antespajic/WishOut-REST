package hr.asc.appic.config.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.repository.UserRepository;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

	@Autowired UserRepository userRepository;
	
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    	String email = (String) authentication.getPrincipal();
        User user = userRepository.findByEmail(email);
        
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("id", user.getId());
        additionalInfo.put("email", user.getEmail());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }
}