package backend.taskweaver.domain.task.entity;

import backend.taskweaver.domain.BaseEntity;
import backend.taskweaver.domain.files.entity.Files;
import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.project.entity.Project;
import backend.taskweaver.domain.task.entity.enums.TaskStateName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "task")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "color")
    private Long emojiId;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private TaskStateName taskState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

    @Builder.Default
    @OneToMany(mappedBy = "parentTask")
    private List<Task> children = new ArrayList<>();

    @OneToMany(mappedBy = "task")
    private List<Files> files = new ArrayList<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskMember> taskMembers = new ArrayList<>();
    public List<Member> getMembers() {
        return taskMembers.stream()
                .map(TaskMember::getMember)
                .collect(Collectors.toList());
    }

}
