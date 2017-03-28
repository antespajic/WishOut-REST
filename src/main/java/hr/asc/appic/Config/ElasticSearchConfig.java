package hr.asc.appic.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

@Configuration
public class ElasticSearchConfig {

	@Bean
	public JestClient jestClient() {
		String connectionUrl = "https://h778jl0r52:v01of7aoxf@first-cluster-2619062181.eu-west-1.bonsaisearch.net";
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig
		       .Builder(connectionUrl)
		       .multiThreaded(true)
		       .build());
		JestClient client = factory.getObject();
		return client;
	}
}
