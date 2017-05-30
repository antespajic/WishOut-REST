package hr.asc.appic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

@Configuration
public class ElasticSearchConfiguration {

	@Bean
	public JestClient jestClient() {
		String connectionUrl = "https://9wsqhj425m:nb88u3md0l@wishout-staging-9440164663.eu-west-1.bonsaisearch.net";
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig
		       .Builder(connectionUrl)
		       .multiThreaded(true)
		       .build());
		JestClient client = factory.getObject();
		return client;
	}
	
	@Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new JestElasticsearchTemplate(jestClient());
    }
}
