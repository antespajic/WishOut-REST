package hr.asc.appic.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import hr.asc.appic.elasticsearch.model.StoryElasticModel;

public interface StoryElasticRepository extends ElasticsearchRepository<StoryElasticModel, String>{

}
