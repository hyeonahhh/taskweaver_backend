package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.entity.enums.LoginType;
import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.project.entity.enums.ProjectStateName;
import backend.taskweaver.domain.project.repository.ProjectRepository;
import backend.taskweaver.domain.team.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("프로젝트 서비스 통합 테스트")
public class ProjectServiceImplTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectRepository projectRepository;

    @Test
    @DisplayName("프로젝트 등록 성공 테스트")
    void createProject() {
        // given
        Team team = Team.builder().name("name").description("description").build();
        entityManager.persist(team);
        Member member = Member.builder().email("email").password("password").nickname("nickname").loginType(LoginType.DEFAULT).build();
        entityManager.persist(member);

        ProjectRequest request = new ProjectRequest("name", "description", member.getId(), Arrays.asList(member.getId()));

        // when
        ProjectResponse response = projectService.createProject(request, team.getId());

        // then
        assertThat(response.name()).isEqualTo("name");
        assertThat(response.description()).isEqualTo("description");
    }


    @Test
    @DisplayName("프로젝트 멤버 등록 성공 테스트")
    void createProjectMember() {}

    @Test
    @DisplayName("프로젝트 수정 성공 테스트")
    void updateProject() {
        // given
        // team
        Team team = Team.builder().name("name").description("description").build();
        entityManager.persist(team);
        // member
        Member member = Member.builder().email("email").password("password").nickname("nickname").loginType(LoginType.DEFAULT).build();
        entityManager.persist(member);

        // project
        Project project = Project.builder().name("name").description("description").team(team).projectStateName(ProjectStateName.ON_PROGRESS).build();
        project.setManager(member.getId(), member.getNickname());
        projectRepository.save(project);


        // 프로젝트 request
        ProjectRequest request = new ProjectRequest("new name", "new description", member.getId(), Arrays.asList(member.getId()));

        // when
        projectService.updateProject(project.getId(), request, member.getId());

        // then
        assertThat(project.getName()).isEqualTo("new name");
        // 멤버도 건드려야함
    }


}
