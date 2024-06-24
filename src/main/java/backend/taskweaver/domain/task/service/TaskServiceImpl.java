package backend.taskweaver.domain.task.service;

import backend.taskweaver.domain.files.entity.Files;
import backend.taskweaver.domain.files.repository.FilesRepository;
import backend.taskweaver.domain.files.service.S3Service;
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
import backend.taskweaver.global.code.ErrorCode;
import backend.taskweaver.global.converter.TaskConverter;
import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final TaskMemberRepository taskMemberRepository;
    private final S3Service s3Service;
    private final FilesRepository filesRepository;
    private final EntityManager em;

    public TaskResponse.taskCreateOrUpdateResult createTask(TaskRequest.taskCreate request, List<MultipartFile> multipartFiles, Long user, Long projectId) throws ParseException, IOException {
        Task task;
        List<Files> filesList = new ArrayList<>();
        if (request.getParentTaskId() == null) {
            task = taskRepository.save(TaskConverter.toTask(request, projectRepository.findById(projectId).get(), TaskStateName.BEFORE));
        } else {
            task = taskRepository.save(TaskConverter.toTask(request, projectRepository.findById(projectId).get(), taskRepository.findById(request.getParentTaskId()).get(), TaskStateName.BEFORE));
        }
        for (MultipartFile file : multipartFiles) {
            Files newFile = s3Service.saveFile(file);
            newFile.setTask(task);
            filesList.add(filesRepository.save(newFile));
        }

        List<TaskMember> taskMembers = new ArrayList<>();
        for (Long id : request.getMembers()) {
            Member member = memberRepository.findById(id).get();
            taskMembers.add(taskMemberRepository.save(new TaskMember(task, member)));
        }

        return TaskConverter.toCreateResponse(task, taskMembers, filesList);
    }


    public TaskResponse.taskDeleteResult deleteTask(Long user, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TASK_NOT_FOUND));
        Member member = memberRepository.findById(user)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));
        TaskMember taskMember = taskMemberRepository.findByMemberAndTask(member, task)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TASK_MEMBER_NOT_FOUND));
        TaskResponse.taskDeleteResult taskDeleteResult = new TaskResponse.taskDeleteResult(task.getId());
        taskRepository.delete(task);
        return taskDeleteResult;
    }

    public TaskResponse.taskChangeStateResult changeTaskState(TaskRequest.taskStateChange request, Long user, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TASK_NOT_FOUND));
        Member member = memberRepository.findById(user)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));
        TaskMember taskMember = taskMemberRepository.findByMemberAndTask(member, task)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TASK_MEMBER_NOT_FOUND));
        task.setTaskState(TaskStateName.find(request.getTaskState()));
        task = taskRepository.save(task);
        return new TaskResponse.taskChangeStateResult(task.getId(), task.getTaskState().getValue());
    }

    @Transactional
    public TaskResponse.taskCreateOrUpdateResult changeTask(TaskRequest.taskChange request, List<MultipartFile> multipartFiles, Long user, Long taskId) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<Files> filesList = new ArrayList<>();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TASK_NOT_FOUND));
        Member member = memberRepository.findById(user)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));
        TaskMember taskMember = taskMemberRepository.findByMemberAndTask(member, task)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.TASK_MEMBER_NOT_FOUND));
        List<TaskMember> taskMembers = taskMemberRepository.findAllByTask(task);
        if (request.getTaskTitle() != null && !request.getTaskTitle().equals("")) {
            task.setTitle(request.getTaskTitle());
        }
        if (request.getTaskContent() != null && !request.getTaskContent().equals("")) {
            task.setContent(request.getTaskContent());
        }
        if (request.getEndDate() != null && !request.getEndDate().equals("")) {
            task.setTitle(request.getEndDate());
        }
        if (request.getEmojiId() != null && !request.getEndDate().equals("")) {
            task.setEmojiId(request.getEmojiId());
        }
        if (request.getMembers() != null && !request.getMembers().isEmpty()) {
            taskMemberRepository.deleteAllByTask(task);
            for (Long id : request.getMembers()) {
                Member m = memberRepository.findById(id).get();
                taskMembers.add(taskMemberRepository.save(new TaskMember(task, m)));
            }
        }
        if (request.getDeleteFiles() != null && !request.getDeleteFiles().isEmpty()) {
            System.out.println(request.getDeleteFiles());
            for (Long id : request.getDeleteFiles()) {
                System.out.println(id + "삭제하고 싶은 파일 ID");
                filesRepository.deleteFilesById(id);
            }
            em.flush();
        }
        for (MultipartFile file : multipartFiles) {
            Files newFile = s3Service.saveFile(file);
            newFile.setTask(task);
            filesRepository.save(newFile);
            //filesList.add(filesRepository.save(newFile));
        }
        filesList.addAll(filesRepository.findAllByTask(task));
        return TaskConverter.toCreateResponse(task, taskMembers, filesList);
    }
}
