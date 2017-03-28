package hr.asc.appic.test;
import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {

	List<Customer> findByFirstName(String firstName);

	List<Customer> findByLastName(String lastName);

}