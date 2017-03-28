package hr.asc.appic.mapping;

public interface Mapper<Pojo, Model> {

	Pojo modelToPojo(Model model);
	
	Model pojoToModel(Pojo pojo);
}
