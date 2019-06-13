package elethu.ikamva.repositories;

import elethu.ikamva.domain.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Optional<Project> findProjectByName(String name);
}
