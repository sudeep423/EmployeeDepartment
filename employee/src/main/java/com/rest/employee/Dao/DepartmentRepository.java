package com.rest.employee.Dao;

import com.rest.employee.entities.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department,Integer> {
    public Department findDepartmentByDepartmentNameIgnoreCase(String departmentName);
}
