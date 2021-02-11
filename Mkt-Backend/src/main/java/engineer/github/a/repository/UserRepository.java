package engineer.github.a.repository;

import java.util.List;

import engineer.github.a.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	@Query("select u from User u where u.username like %:username%")
	List<User> getByUsername(@Param("username") String username);

	User findByUsername(String username);

	@Query("select u from User u where u.email like %:email%")
	List<User> getByEmail(@Param("email") String email);
}
