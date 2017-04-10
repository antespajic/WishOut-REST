package hr.asc.appic.elasticsearch.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import hr.asc.appic.controller.model.UserLightViewModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName="wish-index", shards = 1, createIndex = true)
public class WishElasticModel {

	@Id private String id;
	@Field(type = FieldType.Nested) private UserLightViewModel creator;
	private String title;
	private String description;
	private List<String> categories;
	@Field(type = FieldType.Date) private String created;
	private Integer upvoteCount;
	private Integer state;
	
}
