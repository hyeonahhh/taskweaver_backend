package backend.taskweaver.domain.task.service;


import backend.taskweaver.domain.task.dto.TaskRequest;
import backend.taskweaver.domain.task.dto.TaskResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


public interface TaskService {

    // 우선 팀 생성자 필드로만 추가
    public TaskResponse.taskCreateOrUpdateResult createTask(TaskRequest.taskCreate request, List<MultipartFile> multipartFiles, Long user, Long projectId) throws ParseException, IOException;

    public TaskResponse.taskDeleteResult deleteTask(Long user, Long taskId);

    public TaskResponse.taskChangeStateResult changeTaskState(TaskRequest.taskStateChange request, Long user, Long taskId);

    public TaskResponse.taskCreateOrUpdateResult changeTask(TaskRequest.taskChange request, List<MultipartFile> multipartFiles, Long user, Long taskId) throws IOException;

    public List<TaskResponse.taskInquiryResult> getTaskList(Long teamId, Long projectId);
    
    public List<TaskResponse.todoInquiryResult> getTodoList(Long user, TaskRequest.todoList request) throws ParseException;
}
