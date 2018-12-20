package com.jira.demo.service;

import com.jira.demo.dto.ReportDto;
import com.jira.demo.dto.TaskCreateDto;
import com.jira.demo.dto.TaskUpdateRequest;
import com.jira.demo.model.*;
import com.jira.demo.repository.ReportRepository;
import com.jira.demo.repository.TaskRepository;
import com.jira.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Сервис для задач
 */
@Service
@Transactional
public class TaskService {

    /**
     * Репозиторий задач
     */
    private TaskRepository taskRepository;

    /**
     * Репозиторий пользователей
     */
    private UserRepository userRepository;

    /**
     * Репозиторий репортов
     */
    private ReportRepository reportRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ReportRepository reportRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
    }

    public Page<Task> findByPage(Pageable pageable) {
        Page<Task> all = taskRepository.findAll(pageable);
        return all;
    }

    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(
                        String.format("Cant find Task by id : [%s]", id)
                )
        );
    }

    /**
     * Создание задачи
     */
    public Task createTask(TaskCreateDto dto, Long userId) {
        return taskRepository.save(generateTask(dto, userId));
    }

    /**
     * Изменения статуса задачи
     */
    public void changeStatus(Long taskId, TaskStatus status) {
        checkArgument(taskRepository.existsById(taskId), "Cant find Task by id : {}", taskId);
        Task task = taskRepository.findById(taskId).get();
        task.setStatus(status);
        taskRepository.save(task);
    }

    /**
     * Проверка доступа пользователя к задаче
     */
    public void checkNotForbiden(Long userId, Long taskId) {
        checkArgument(userRepository.existsById(userId), "No User found with id : {}", userId);
        User user = userRepository.findById(userId).get();
//        boolean match = user.getTasks().stream().anyMatch(task -> checkTaskReqursive(task, taskId));
//        boolean match1 = user.getTaskList().stream().anyMatch(task -> checkTaskReqursive(task, taskId));
//        if (!match && !match1) {
//            throw new AccessDeniedException(String.format("No access for task id : [%s]", taskId));
//        }
        if (user.getTaskList().stream().noneMatch(task -> task.getId().equals(taskId))) {
            throw new AccessDeniedException(String.format("No access for task id : [%s]", taskId));
        }
    }

    /**
     * Создание репорта
     */
    public Report reportTask(String author, ReportDto dto) {
        Report report = new Report();
        Optional<User> userOptional = userRepository.findByUsername(author);
        checkArgument(userOptional.isPresent(), "Can not find User by username : {}", author);
        User user = userOptional.get();
        report.setAuthor(user);
        Optional<Task> taskOptional = taskRepository.findById(dto.getTaskId());
        checkArgument(taskOptional.isPresent(), "Can not find Task by id : {}", dto.getTaskId());
        Task task = taskOptional.get();
        report.setTask(task);
        report.setDescription(dto.getDescription());
        report.setEstimate(dto.getEstimate());
        report.setReportedfrom(dto.getFrom());
        return reportRepository.save(report);
    }

    public Task createSubTask(TaskCreateDto dto, Long userId, Long parentTaskId) {
        checkArgument(taskRepository.existsById(parentTaskId),
                "Can not find parent task by id : {}", parentTaskId);
        Task parent = taskRepository.findById(parentTaskId).get();
        Task task = generateTask(dto, userId);
        Task save = taskRepository.save(task);
        parent.getSubTasksList().add(new SubTask(save.getId(), save.getName()));
        return taskRepository.save(parent);
    }

    private Task generateTask(TaskCreateDto dto, Long userId) {
        Task task = new Task();
        User author = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException(
                        String.format("Cant find User by id : [%s]", userId)
                )
        );
        task.setAuthor(author);
        task.setDescription(dto.getDescription());
        task.setEstimate(dto.getEstimate());
        task.setProfile(dto.getProfile());
        task.setName(dto.getName());
        task.setStatus(TaskStatus.CREATED);
        return task;
    }

    /**
     * Рекурсивный обход всех задач
     */
    private boolean checkTaskReqursive(Task task, Long taskId) {
        if (task.getId().equals(taskId)) {
            return true;
        } else {
            for (SubTask subTask : task.getSubTasksList()) {
                Optional<Task> subTaskOption = taskRepository.findById(subTask.getId());
                if (!subTaskOption.isPresent()) {
                    task.getSubTasksList().remove(subTask);
                    taskRepository.save(task);
                }
                checkTaskReqursive(subTaskOption.get(), taskId);
            }
        }
        return false;
    }

    public void updateDescr(TaskUpdateRequest request, Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}
