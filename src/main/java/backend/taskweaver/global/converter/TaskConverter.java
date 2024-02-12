package backend.taskweaver.global.converter;

import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.task.dto.TaskRequest;
import backend.taskweaver.domain.task.dto.TaskResponse;
import backend.taskweaver.domain.task.entity.Task;
import backend.taskweaver.domain.task.entity.TaskMember;
import backend.taskweaver.domain.task.entity.enums.TaskStateName;
import backend.taskweaver.domain.team.dto.TeamRequest;
import backend.taskweaver.domain.team.dto.TeamResponse;
import backend.taskweaver.domain.team.entity.Team;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskConverter {

    public static Task toTask(TaskRequest.taskCreate request, Project project, TaskStateName taskStateName) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return Task.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .startDate(formatter.parse(request.getStartDate()))
                .endDate(formatter.parse(request.getEndDate()))
                .color(request.getColor())
                .project(project)
                .taskState(taskStateName)
                .build();
    }


    public static Task toTask(TaskRequest.taskCreate request, Project project, Task parent, TaskStateName taskStateName) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return Task.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .startDate(formatter.parse(request.getStartDate()))
                .endDate(formatter.parse(request.getEndDate()))
                .color(request.getColor())
                .parentTask(parent)
                .project(project)
                .taskState(taskStateName)
                .build();
    }



    public static TaskResponse.taskCreateResult toCreateResponse(Task task, List<TaskMember> taskMembers) {
        List<TaskResponse.taskMemberResult> taskMemberResults = new ArrayList<>();
        for (TaskMember taskMember : taskMembers) {
            taskMemberResults.add(new TaskResponse.taskMemberResult(taskMember.getMember().getId(), taskMember.getMember().getImageUrl(), taskMember.getMember().getNickname()));
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (task.getParentTask() == null) {
            return TaskResponse.taskCreateResult.builder()
                    .id(task.getId())
                    .title(task.getTitle())
                    .content(task.getContent())
                    .startDate(formatter.format(task.getStartDate()))
                    .endDate(formatter.format(task.getStartDate()))
                    .taskMember(taskMemberResults)
                    .taskStateName(task.getTaskState().getValue())
                    .build();
        } else {
            return TaskResponse.taskCreateResult.builder()
                    .id(task.getId())
                    .title(task.getTitle())
                    .content(task.getContent())
                    .startDate(formatter.format(task.getStartDate()))
                    .endDate(formatter.format(task.getStartDate()))
                    .taskMember(taskMemberResults)
                    .taskStateName(task.getTaskState().getValue())
                    .parentTaskId(task.getParentTask().getId())
                    .build();
        }

    }



}
