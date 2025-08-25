package emqu.GestorTareas.Repository;
import emqu.GestorTareas.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepo extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);

}
