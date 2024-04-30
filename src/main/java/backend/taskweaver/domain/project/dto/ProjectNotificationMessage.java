package backend.taskweaver.domain.project.dto;

public class ProjectNotificationMessage {
    private final String teamName;
    private final String projectName;
    private final String managerName;

    public ProjectNotificationMessage(String teamName, String projectName, String managerName) {
        this.teamName = teamName;
        this.projectName = projectName;
        this.managerName = managerName;
    }

    public String getCreateMessage() {
        return this.teamName + "에 " + this.projectName + " 생성되었습니다." + "프로젝트 담당자는 " + this.managerName + " 입니다.";
    }
}