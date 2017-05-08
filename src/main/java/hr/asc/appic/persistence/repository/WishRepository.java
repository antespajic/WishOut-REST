package hr.asc.appic.persistence.repository;

import java.util.Collection;

import hr.asc.appic.persistence.model.Wish;

public interface WishRepository extends AsyncRepository<Wish> {
	
	Collection<Wish> findByIdIn(Collection<String> ids);
}
