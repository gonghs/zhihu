package ice.maple.springbootrest.dao;

import ice.maple.springbootrest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
