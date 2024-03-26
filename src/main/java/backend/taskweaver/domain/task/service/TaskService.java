package backend.taskweaver.domain.task.service;


import backend.taskweaver.domain.task.dto.TaskRequest;
import backend.taskweaver.domain.task.dto.TaskResponse;

import java.text.ParseException;


public interface TaskService {

    // 우선 팀 생성자 필드로만 추가
    public TaskResponse.taskCreateOrUpdateResult createTask(TaskRequest.taskCreate request, Long user, Long projectId) throws ParseException;

    public TaskResponse.taskDeleteResult deleteTask(Long user, Long taskId);

    public TaskResponse.taskChangeStateResult changeTaskState(TaskRequest.taskStateChange request, Long user, Long taskId);

    public TaskResponse.taskCreateOrUpdateResult changeTask(TaskRequest.taskChange request, Long user, Long taskId);
}
