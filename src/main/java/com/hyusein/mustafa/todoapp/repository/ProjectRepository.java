package com.hyusein.mustafa.todoapp.repository;

import com.hyusein.mustafa.todoapp.model.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
}
