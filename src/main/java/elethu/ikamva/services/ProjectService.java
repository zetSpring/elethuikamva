package elethu.ikamva.services;

import elethu.ikamva.domain.Project;

import java.util.Set;

public interface ProjectService {
    void saveOrUpdateProject(Project project);

    void deleteProject(Project project);

    Project findProjectById(Long id);

    Project findProjectByName(String name);

    Set<Project> findAllProject();

    Boolean isProjectActive(Project project);
}
