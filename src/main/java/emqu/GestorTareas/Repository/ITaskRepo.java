package emqu.GestorTareas.Repository;
import emqu.GestorTareas.model.Tasks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITaskRepo extends JpaRepository<Tasks, Integer> {



}
