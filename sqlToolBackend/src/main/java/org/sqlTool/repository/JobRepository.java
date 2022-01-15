package org.sqlTool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sqlTool.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {

}
