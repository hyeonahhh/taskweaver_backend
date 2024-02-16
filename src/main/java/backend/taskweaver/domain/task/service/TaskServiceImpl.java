package backend.taskweaver.domain.task.service;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.domain.project.repository.ProjectRepository;
import backend.taskweaver.domain.task.dto.TaskRequest;
import backend.taskweaver.domain.task.dto.TaskResponse;
import backend.taskweaver.domain.task.entity.Task;
import backend.taskweaver.domain.task.entity.TaskMember;
import backend.taskweaver.domain.task.entity.enums.TaskStateName;
import backend.taskweaver.domain.task.repository.TaskMemberRepository;
import backend.taskweaver.domain.task.repository.TaskRepository;
import backend.taskweaver.domain.team.dto.TeamRequest;
import backend.taskweaver.domain.team.dto.TeamResponse;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.repository.TeamRepository;
import backend.taskweaver.domain.team.service.TeamService;
import backend.taskweaver.global.converter.TaskConverter;
import backend.taskweaver.global.converter.TeamConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final TaskMemberRepository taskMemberRepository;

    public TaskResponse.taskCreateResult createTask(TaskRequest.taskCreate request, Long user, Long projectId) throws ParseException {
        Task task;
        if (request.getParentTaskId() == null) {
            task = taskRepository.save(TaskConverter.toTask(request, projectRepository.findById(projectId).get(), TaskStateName.BEFORE));
        } else {
            task = taskRepository.save(TaskConverter.toTask(request, projectRepository.findById(projectId).get(), taskRepository.findById(request.getParentTaskId()).get(), TaskStateName.BEFORE));
        }

        List<TaskMember> taskMembers = new ArrayList<>();
        for (Long id : request.getMembers()) {
            Member member = memberRepository.findById(id).get();
            taskMembers.add(taskMemberRepository.save(new TaskMember(task, member)));
        }

        return TaskConverter.toCreateResponse(task, taskMembers);
    }
}
