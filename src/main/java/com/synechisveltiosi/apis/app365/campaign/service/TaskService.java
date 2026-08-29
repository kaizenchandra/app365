package com.synechisveltiosi.apis.app365.campaign.service;

import com.synechisveltiosi.apis.app365.campaign.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> findById(Long id);

    Optional<Task> findById(String id);

    List<Task> findAll();

    Task save(Task task);

    void delete(String taskId);
}
