package org.sqlTool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.sqlTool.model.Department;
import org.sqlTool.model.Employee;
import org.sqlTool.model.Job;
import org.sqlTool.repository.DepartmentRepository;
import org.sqlTool.repository.EmployeeRepository;
import org.sqlTool.repository.JobRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@Service
public class PrepareDataService {

  private final EmployeeRepository employeeRepository;
  private final DepartmentRepository departmentRepository;
  private final JobRepository jobRepository;
  private final DataCreationService dataCreationService;

  @Transactional
  public long createTestData() {
    log.info(" start creating Test Data ");

    long count = employeeRepository.count();
    if (count > 10000) {
      log.warn("ignore create request because employee already has {} records", count);
      return 0;
    }

    List<Department> departmentList = departmentRepository.findAll();
    List<Job> jobList = jobRepository.findAll();
    List<Employee> employeeList = employeeRepository.findAll();

    long createdNumber = 0;
    for (int i = 1; i <= 20; i++) {
      Random random = new Random();
      Department department = departmentList.get(random.nextInt(departmentList.size()));
      Job job = jobList.get(random.nextInt(jobList.size()));
      Employee employee = employeeList.get(random.nextInt(employeeList.size()));

      long kEmployee = dataCreationService.create10kEmployee(department, job, employee);
      createdNumber += kEmployee;
      log.info("created rows = {}", createdNumber);
    }

    return createdNumber;
  }

}
