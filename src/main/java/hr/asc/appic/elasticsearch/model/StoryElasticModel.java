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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "story-index")
public class StoryElasticModel {

	@Id	private String id;
	private String description;
	private List<String> pictures;
	@Field(type = FieldType.Nested) private UserLightViewModel creator;
	@Field(type = FieldType.Nested) private UserLightViewModel sponsor;
	@Field(type = FieldType.Date) private String created;

}
