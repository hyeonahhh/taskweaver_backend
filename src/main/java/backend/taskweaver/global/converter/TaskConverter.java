package backend.taskweaver.global.converter;

import backend.taskweaver.domain.files.entity.Files;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.task.dto.TaskRequest;
import backend.taskweaver.domain.task.dto.TaskResponse;
import backend.taskweaver.domain.task.entity.Task;
import backend.taskweaver.domain.task.entity.TaskMember;
import backend.taskweaver.domain.task.entity.enums.TaskStateName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskConverter {

    public static Task toTask(TaskRequest.taskCreate request, Project project, TaskStateName taskStateName) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return Task.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .startDate(formatter.parse(request.getStartDate()))
                .endDate(formatter.parse(request.getEndDate()))
                .project(project)
                .taskState(taskStateName)
                .emojiId(request.getEmojiId())
                .build();
    }


    public static Task toTask(TaskRequest.taskCreate request, Project project, Task parent, TaskStateName taskStateName) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return Task.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .startDate(formatter.parse(request.getStartDate()))
                .endDate(formatter.parse(request.getEndDate()))
                .parentTask(parent)
                .project(project)
                .taskState(taskStateName)
                .emojiId(request.getEmojiId())
                .build();
    }



    public static TaskResponse.taskCreateOrUpdateResult toCreateResponse(Task task, List<TaskMember> taskMembers, List<Files> files) {
        List<TaskResponse.taskMemberResult> taskMemberResults = new ArrayList<>();
        List<TaskResponse.fileResult> fileResults = new ArrayList<>();
        for (TaskMember taskMember : taskMembers) {
            taskMemberResults.add(new TaskResponse.taskMemberResult(taskMember.getMember().getId(), taskMember.getMember().getImageUrl(), taskMember.getMember().getNickname()));
        }
        for (Files file : files) {
            fileResults.add(new TaskResponse.fileResult(file.getId(), file.getOriginalName(), file.getImageUrl(), file.getTask().getId()));
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (task.getParentTask() == null) {
            return TaskResponse.taskCreateOrUpdateResult.builder()
                    .id(task.getId())
                    .title(task.getTitle())
                    .content(task.getContent())
                    .startDate(formatter.format(task.getStartDate()))
                    .endDate(formatter.format(task.getStartDate()))
                    .taskMember(taskMemberResults)
                    .files(fileResults)
                    .taskState(task.getTaskState().getValue())
                    .emojiId(task.getEmojiId())
                    .build();
        } else {
            return TaskResponse.taskCreateOrUpdateResult.builder()
                    .id(task.getId())
                    .title(task.getTitle())
                    .content(task.getContent())
                    .startDate(formatter.format(task.getStartDate()))
                    .endDate(formatter.format(task.getStartDate()))
                    .taskMember(taskMemberResults)
                    .files(fileResults)
                    .taskState(task.getTaskState().getValue())
                    .parentTaskId(task.getParentTask().getId())
                    .emojiId(task.getEmojiId())
                    .build();
        }

    }



}
