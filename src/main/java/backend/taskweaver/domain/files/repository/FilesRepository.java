package backend.taskweaver.domain.files.repository;

import backend.taskweaver.domain.files.entity.Files;
import backend.taskweaver.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilesRepository extends JpaRepository<Files, Long> {
    void deleteFilesById(Long id);
    List<Files> findAllByTask(Task task);
}
