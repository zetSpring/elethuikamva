package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.Project;
import elethu.ikamva.exception.ProjectException;
import elethu.ikamva.repositories.ProjectRepository;
import elethu.ikamva.services.ProjectService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void saveOrUpdateProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void deleteProject(Project project) {
        if (isProjectActive(project))
            saveOrUpdateProject(project);
        else
            throw new ProjectException("Project: " + project.getName() + " could not found for deletion");
    }

    @Override
    public Project findProjectById(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);

        if (!projectOptional.isPresent()) {
            throw new ProjectException("Project: " + id + " could not found");
        }

        return projectOptional.get();
    }

    @Override
    public Project findProjectByName(String name) {
        Optional<Project> projectOptional = projectRepository.findProjectByName(name);
        if (!projectOptional.isPresent()) {
            throw new ProjectException("Project: " + name + " could not found");
        }

        return projectOptional.get();
    }

    @Override
    public Set<Project> findAllProject() {
        Set<Project> projects = new HashSet<>();

        projectRepository.findAll().iterator().forEachRemaining(projects::add);

        return projects;
    }

    @Override
    public Boolean isProjectActive(Project project) {
        return project.getId() != null && project.getEndDate() == null;
    }
}
