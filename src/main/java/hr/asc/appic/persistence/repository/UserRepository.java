package hr.asc.appic.persistence.repository;

import java.util.Collection;

import hr.asc.appic.persistence.model.User;

public interface UserRepository extends AsyncRepository<User> {

	User findByEmail(String email);
	// TODO: only for testing, delete afterwards
	Collection<User> findAll();
}
