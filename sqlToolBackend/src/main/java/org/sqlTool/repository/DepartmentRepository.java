package org.sqlTool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sqlTool.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
