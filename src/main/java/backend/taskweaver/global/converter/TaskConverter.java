package backend.taskweaver.global.converter;

import backend.taskweaver.domain.files.entity.Files;
import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.task.dto.TaskRequest;
import backend.taskweaver.domain.task.dto.TaskResponse;
import backend.taskweaver.domain.task.entity.Task;
import backend.taskweaver.domain.task.entity.TaskMember;
import backend.taskweaver.domain.task.entity.enums.TaskStateName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public static TaskResponse.taskMemberResult toTaskMemberResult(Member member) {
        return TaskResponse.taskMemberResult.builder()
                .id(member.getId())
                .imageUrl(member.getImageUrl())
                .nickname(member.getNickname())
                .build();
    }
    public static TaskResponse.taskInquiryResult toTaskInquiryResult(Task task) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<TaskResponse.childTaskInquiryResult> children = task.getChildren().stream()
                .map(TaskConverter::toChildTaskInquiryResult)
                .collect(Collectors.toList());
        TaskResponse.taskStatusCountResult statusCountResult = TaskConverter.getTaskStatusCount(task.getChildren());
        return TaskResponse.taskInquiryResult.builder()
                .id(task.getId())
                .title(task.getTitle())
                .emojiId(task.getEmojiId())
                .endDate(formatter.format(task.getEndDate()))
                .members(task.getMembers().stream().map(TaskConverter::toTaskMemberResult).collect(Collectors.toList()))
                .children(children)
                .childTaskStatusCount(statusCountResult)
                .build();
    }

    public static TaskResponse.childTaskInquiryResult toChildTaskInquiryResult(Task task) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return TaskResponse.childTaskInquiryResult.builder()
                .id(task.getId())
                .title(task.getTitle())
                .emojiId(task.getEmojiId())
                .endDate(formatter.format(task.getEndDate()))
                .members(task.getMembers().stream().map(TaskConverter::toTaskMemberResult).collect(Collectors.toList()))
                .build();

    }

    private static TaskResponse.taskStatusCountResult getTaskStatusCount(List<Task> children) {
        int before = 0;
        int onProgress = 0;
        int complete = 0;

        for (Task child : children) {
            switch (child.getTaskState()) {
                case BEFORE:
                    before++;
                    break;
                case ON_PROGRESS:
                    onProgress++;
                    break;
                case COMPLETE:
                    complete++;
                    break;
                default:
                    break;
            }
        }

        return TaskResponse.taskStatusCountResult.builder()
                .before(before)
                .onProgress(onProgress)
                .complete(complete)
                .build();
    }

    public static TaskResponse.todoInquiryResult toTodoInquiryResult(List<Task> tasks, Date endDate) {
        if (tasks.isEmpty()) return null;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Long teamId = tasks.get(0).getProject().getTeam().getId();
        String teamName = tasks.get(0).getProject().getTeam().getName();
        Long projectId = tasks.get(0).getProject().getId();
        String projectName = tasks.get(0).getProject().getName();

        List<TaskResponse.todoTaskResult> taskResults = tasks.stream()
                .map(task -> TaskResponse.todoTaskResult.builder()
                        .taskId(task.getId())
                        .title(task.getTitle())
                        .content(task.getContent())
                        .build())
                .collect(Collectors.toList());

        return TaskResponse.todoInquiryResult.builder()
                .endDate(formatter.format(endDate))
                .teamId(teamId)
                .teamName(teamName)
                .projectId(projectId)
                .projectName(projectName)
                .tasks(taskResults)
                .build();
    }

}
