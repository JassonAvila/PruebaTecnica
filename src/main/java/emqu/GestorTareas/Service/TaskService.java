package emqu.GestorTareas.Service;

import emqu.GestorTareas.Repository.ITaskRepo;
import emqu.GestorTareas.Repository.IUserRepo;
import emqu.GestorTareas.model.Tasks;
import emqu.GestorTareas.model.Users;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskService  {

    @Autowired
    private ITaskRepo taskRepository;
    private IUserRepo userRepository;

    public TaskService(ITaskRepo taskRepo, IUserRepo userRepo) {
        this.taskRepository = taskRepo;
        this.userRepository = userRepo;
    }

    public List<Tasks> getAllTasks() {
       return taskRepository.findAll();
    }

    public Optional<Tasks> getTaskById(Integer id){
        return taskRepository.findById(id);

    }
    public Tasks createTask(Tasks task) {
        return taskRepository.save(task);

    }

//    public Tasks updateTask(Integer id, Tasks taskDetails, Set<Integer> userIds) {
//        Tasks task = taskRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Task not found"));
//
//        task.setTitulo(taskDetails.getTitulo());
//        task.setDescripcion(taskDetails.getDescripcion());
//        task.setFecha(taskDetails.getFecha());
//        task.setEstado(task.getEstado());
//
//        Set<Users> users = userIds.stream()
//                .map(userRepository::findById)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toSet());
//        return taskRepository.save(task);
//
//    }
    public Tasks save(Tasks task) {
        return taskRepository.save(task);
    }


    public void  deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }
}
