package org.sqlTool.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.sqlTool.model.Department;
import org.sqlTool.model.Employee;
import org.sqlTool.model.Job;
import org.sqlTool.model.JobHistory;
import org.sqlTool.repository.EmployeeRepository;
import org.sqlTool.repository.JobHistoryRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.transaction.Transactional.TxType.REQUIRES_NEW;

@RequiredArgsConstructor
@Slf4j
@Service
public class DataCreationService {

  private final EmployeeRepository employeeRepository;
  private final JobHistoryRepository jobHistoryRepository;

  @Transactional(REQUIRES_NEW)
  public long create10kEmployee(Department department, Job job, Employee manager) {

    long maxId = employeeRepository.findMaxId();

    List<Employee> employeeList = new ArrayList<>();
    List<JobHistory> jobHistoryList = new ArrayList<>();

    for (int i = 1; i <= 10000; i++) {

      String uuidStr = UUID.randomUUID().toString();
      long id = maxId + i;

      Employee employee =
          Employee.builder().employeeId(id)
              .firstName("first"+uuidStr.substring(0, 4) + id)
              .lastName("last"+uuidStr.substring(4, 8) + id)
              .email(id + uuidStr.substring(8, 16) + "@dummy.com")
              .phoneNumber("0025" + id)
              .hireDate(LocalDate.now())
              .salary(Float.valueOf(id))
              .commissionPct(Float.valueOf(0))
              .job(job)
              .manager(manager)
              .build();

      JobHistory jobHistory =
          JobHistory.builder()
              .historyId(id-35)
              .employee(employee)
              .startDate(LocalDate.now().minusYears(1)).job(job)
              .endDate(LocalDate.now().plusYears(1))
              .department(department)
              .build();

      employeeList.add(employee);
      jobHistoryList.add(jobHistory);
    }

    employeeRepository.saveAllAndFlush(employeeList);
    jobHistoryRepository.saveAllAndFlush(jobHistoryList);

    return 10000;
  }
}
