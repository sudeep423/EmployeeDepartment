package com.rest.employee.Dao;

import com.rest.employee.entities.Department;
import com.rest.employee.entities.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface EmployeeRepository extends CrudRepository<Employee,Integer> {
    public List<Employee> findByDepartment(Department department);

    public void deleteByDepartmentId(int id);

    public List<Employee> findByDepartmentId(int id);
}
