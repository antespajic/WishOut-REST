package hr.asc.appic.elasticsearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import hr.asc.appic.elasticsearch.model.WishElasticModel;

public interface WishElasticRepository extends ElasticsearchRepository<WishElasticModel, String> {

	Page<WishElasticModel> findByState(Integer state, Pageable pageable);
}
