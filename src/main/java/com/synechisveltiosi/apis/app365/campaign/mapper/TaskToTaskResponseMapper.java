package com.synechisveltiosi.apis.app365.campaign.mapper;

import com.synechisveltiosi.apis.app365.campaign.dto.TaskResponse;
import com.synechisveltiosi.apis.app365.campaign.entity.Task;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskResponseMapper extends AbstractMapper<Task, TaskResponse> {

    @Override
    public TaskResponse map(Task task) {
        return TaskMapper.INSTANCE.from(task);
    }
}
