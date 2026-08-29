package com.synechisveltiosi.apis.app365.campaign;

import com.synechisveltiosi.apis.app365.campaign.entity.Task;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Alfredo Martinez <martin3zra@gmail.com> on 10/5/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskRequest {

    @NotBlank(message = "Task name is required")
    @Pattern(regexp = "^[a-zA-Z0-9 -]+$", message = "Task name can only contains alphanumeric characters.")
    @Size(min = 6, max = 100, message = "Task name should be 6 to 100 characters. ")
    @JsonProperty("name")
    private String name;

    public Task mapToTask() {

        Task task = new Task();

        task.setName(getName());
        return task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
