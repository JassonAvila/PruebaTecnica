package emqu.GestorTareas.Controller;

import emqu.GestorTareas.Repository.ITaskRepo;
import emqu.GestorTareas.Service.TaskService;
import emqu.GestorTareas.model.Users;
import emqu.GestorTareas.Repository.IUserRepo;
import emqu.GestorTareas.model.Tasks;
import org.hibernate.query.sqm.mutation.internal.TableKeyExpressionCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.* ;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private  TaskService taskService;
    @Autowired
    private IUserRepo userRepository;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Tasks> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Tasks getTaskById(@PathVariable Integer id){
        return taskService.getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @PostMapping
    public Tasks createTasks(@RequestBody Map<String, Object> body){

        Tasks task = new Tasks();

        task.setTitulo((String) body.get("titulo"));
        task.setDescripcion((String) body.get("descripcion"));
        task.setEstado((String) body.get("estado"));

        task.setFecha(LocalDate.parse((String) body.get("fecha")));

        if (body.containsKey("user")) {
            Object userObj = body.get("user");
            if (userObj == null) {
                // explicit null -> create unassigned
                task.setUser(null);
            } else if (userObj instanceof Map<?, ?> uMap) {
                Object idObj = uMap.get("id");
                if (idObj != null) {
                    Integer userId = (idObj instanceof Number) ? ((Number) idObj).intValue() : Integer.valueOf(idObj.toString());
                    Users owner = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found: id=" + userId));
                    task.setUser(owner);
                } else {
                    throw new RuntimeException("user.id is required if 'user' is provided");
                }
            } else {
                throw new RuntimeException("'user' must be an object like {\"id\": 1} or null");
            }
        }

        return taskService.createTask(task);

    }

    @PutMapping("/{id}")
    public Tasks updateTask(@PathVariable Integer id, @RequestBody Map<String, Object> body){
        Tasks existing = taskService.getTaskById(id)
                        .orElseThrow(() -> new RuntimeException("Task not found"));
        existing.setTitulo((String) body.get("titulo"));
        existing.setDescripcion((String) body.get("descripcion"));
        existing.setEstado((String) body.get("estado"));

        if (body.containsKey("fecha")) {
            Object f = body.get("fecha");
            existing.setFecha(f == null || f.toString().isBlank()
                    ? null
                    : LocalDate.parse(LocalDate.parse(f.toString()).toString()));
        }

        if (body.containsKey("user")) {
            Object userObj = body.get("user");
            if (userObj == null) {
                existing.setUser(null); // unassign
            } else if (userObj instanceof Map<?, ?> uMap) {
                Object idObj = uMap.get("id");
                if (idObj == null) {
                    throw new RuntimeException("user.id is required when providing 'user'");
                }
                Integer userId = (idObj instanceof Number) ? ((Number) idObj).intValue() : Integer.valueOf(idObj.toString());
                Users owner = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found: id=" + userId));
                existing.setUser(owner);
            } else {
                throw new RuntimeException("'user' must be an object like {\"id\": 1} or null");
            }
        }

        return taskService.save(existing);


    }

    @DeleteMapping("/{id}")
    public  void deleteTask(@PathVariable Integer id){
        taskService.deleteTask(id);
    }


}
