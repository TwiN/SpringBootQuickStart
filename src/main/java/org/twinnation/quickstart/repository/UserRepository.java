package org.twinnation.quickstart.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.twinnation.quickstart.bean.User;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	List<User> findAll();
	User findByUsername(String username);
	User findById(Long id);
	
	
}
