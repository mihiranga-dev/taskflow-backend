package com.mihiranga.taskflowbackend.service;

import com.mihiranga.taskflowbackend.model.Task;
import com.mihiranga.taskflowbackend.model.User;
import com.mihiranga.taskflowbackend.repository.TaskRepository;
import com.mihiranga.taskflowbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public Task createTask(Task task) {
        User currentUser = getCurrentUser();
        task.setUser(currentUser);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        User currentUser = getCurrentUser();
        return taskRepository.findByUser(currentUser);
    }


    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        User currentUser = getCurrentUser();

        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You do not have permission to delete this task");
        }
        taskRepository.deleteById(id);
    }


    public Task updateTask(Long id, Task taskDetails) {
        System.out.println("--- UPDATE TASK REQUEST RECEIVED ---");

        Task task = getTaskById(id);
        User currentUser = getCurrentUser();

        System.out.println("Task Owner ID: " + task.getUser().getId());
        System.out.println("Current User ID: " + currentUser.getId());

        // Security Check
        if (!task.getUser().getId().equals(currentUser.getId())) {
            System.out.println("!!! PERMISSION DENIED !!!");
            throw new RuntimeException("You do not have permission to update this task");
        }

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());

        System.out.println("--- UPDATE SUCCESSFUL ---");
        return taskRepository.save(task);
    }
}
