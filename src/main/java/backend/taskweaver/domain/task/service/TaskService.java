package backend.taskweaver.domain.task.service;


import backend.taskweaver.domain.task.dto.TaskRequest;
import backend.taskweaver.domain.task.dto.TaskResponse;
import backend.taskweaver.domain.team.dto.TeamRequest;
import backend.taskweaver.domain.team.dto.TeamResponse;

import java.text.ParseException;


public interface TaskService {

    // 우선 팀 생성자 필드로만 추가
    public TaskResponse.taskCreateResult createTask(TaskRequest.taskCreate request, Long user, Long projectId) throws ParseException;
}
