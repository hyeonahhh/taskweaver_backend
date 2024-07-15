package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.entity.enums.ProjectStateName;
import backend.taskweaver.domain.project.repository.ProjectRepository;
import backend.taskweaver.domain.team.entity.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplUnitTest {

    @InjectMocks // 테스트 대상 클래스의 인스턴스를 생성한다. 해당 클래스에서 사용하는 종속성을 주입하는 역할
    ProjectServiceImpl projectService;

    @Mock // ProjectService에서 사용하는 ProjectRepository 종속성이 @Mock으로 만들어진다.
    ProjectRepository projectRepository;

    @Test
    @DisplayName("프로젝트 등록 성공 테스트")
    void createProject() {
        // given
        ProjectRequest request = new ProjectRequest("name", "description", 1L,  Arrays.asList(1L, 2L));
        Project project = Project.builder().name("name").description("description").team(Team.builder().description("description").name("name").build()).projectStateName(ProjectStateName.ON_PROGRESS).build();
        Mockito.when(projectRepository.save(any(Project.class))).thenReturn(project);
        // when
        ProjectResponse response = projectService.createProject(request, 1L);

        // then
        verify(projectRepository, times(1)).save(any(Project.class));

    }

    @Test
    void updateProject() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getOne() {
    }

    @Test
    void getAllProjectMembers() {
    }

    @Test
    void delete() {
    }

    @Test
    void updateState() {
    }
}