package com.synechisveltiosi.apis.app365.campaign.service;

import com.synechisveltiosi.apis.app365.campaign.TaskNotFoundException;
import com.synechisveltiosi.apis.app365.campaign.entity.Task;
import com.synechisveltiosi.apis.app365.campaign.repository.TaskRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<Task> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Task id should not be null or 0");

        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Task id should not be null or blank");

        return taskRepository.findByTaskId(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task save(Task task) {

        return taskRepository.save(task);
    }

    @Override
    public void delete(String taskId) {

        Optional<Task> task = findById(taskId);

        if (!task.isPresent()) {
            throw new TaskNotFoundException();
        }

        taskRepository.delete(task.get());
    }
}
