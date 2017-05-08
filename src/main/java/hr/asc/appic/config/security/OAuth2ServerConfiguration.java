package hr.asc.appic.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2ServerConfiguration {

	private static final String RESOURCE_ID = "restservice";

	@Configuration
    @EnableResourceServer
	protected static class ResourceServerConfiguration extends
			ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources
				.resourceId(RESOURCE_ID);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http
					.authorizeRequests()
						.antMatchers("/**").permitAll()
						.and()
						.csrf().disable();
		}

	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends
			AuthorizationServerConfigurerAdapter {

		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManagerBean;

		@Autowired
		private CustomUserDetailsService userDetailsService;

		@Autowired
		private PasswordEncoder passwordEncoder;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints
				.exceptionTranslator(loggingExceptionTranslator())
				.tokenStore(tokenStore())
				.authenticationManager(this.authenticationManagerBean)
				.userDetailsService(userDetailsService)
				.tokenEnhancer(tokenEnhancer());
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients
				.inMemory()
					.withClient("clientapp")
						.authorizedGrantTypes("password", "refresh_token")
						.scopes("read", "write")
						.resourceIds(RESOURCE_ID)
						.secret("123456");
		}

		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setSupportRefreshToken(true);
			tokenServices.setTokenStore(tokenStore());
			return tokenServices;
		}

		@Bean
		public TokenStore tokenStore() {
			return new InMemoryTokenStore();
		}

		@Bean
		public TokenEnhancer tokenEnhancer() {
			return new CustomTokenEnhancer();
		}

		@Bean
		public WebResponseExceptionTranslator loggingExceptionTranslator() {
			return new DefaultWebResponseExceptionTranslator() {
				@Override
				public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
					// This is the line that prints the stack trace to the log. You can customise this to format the trace etc if you like
					e.printStackTrace();

					// Carry on handling the exception
					ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
					HttpHeaders headers = new HttpHeaders();
					headers.setAll(responseEntity.getHeaders().toSingleValueMap());
					OAuth2Exception excBody = responseEntity.getBody();
					return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
				}
			};
		}
	}
}