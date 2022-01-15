package org.sqlTool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.sqlTool.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  @Query("select coalesce(max(e.employeeId), 0) from Employee e")
  long findMaxId();
}
