package com.rest.employee.services;


import com.rest.employee.Dao.DepartmentRepository;
import com.rest.employee.Dao.EmployeeRepository;
import com.rest.employee.entities.Department;
import com.rest.employee.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmpolyeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentService departmentService;

    public List<Employee> getEmployees(){
        List<Employee> employees = null;
        try{
            employees = (List<Employee>) employeeRepository.findAll();
            return employees;
        }catch (Exception e){
            return null;
        }
    }

    public Employee addEmployee(Employee employee){
        Department department = departmentService.getDepartmentById(0);
        if(department==null){
            department = new Department();
            department.setId(0);
            departmentService.addDepartment(department);
        }
        employee.setDepartment(department);
        Employee E1 = employeeRepository.save(employee);
        return E1;
    }


    public  Employee addEmployeeToDepartment(Employee employee){
        employeeRepository.save(employee);
        return employee;
    }

    public Employee getEmployeeById(int id){
        Employee employee = null;
        try{
            employee = employeeRepository.findById(id).stream().findFirst().get();
        }catch (Exception e){
            return null;
        }
        return employee;
    }

    public List<Employee> getEmployeeByDepartment(Department department){
        List<Employee> list = null;
        try{
            list = employeeRepository.findByDepartment(department);
        }catch (Exception e) {
            return null;
        }
        return list;
    }

    public List<Employee> getEmployeeByDepartmentId(int id){
        List<Employee> list = null;
        try{
            list = employeeRepository.findByDepartmentId(id);
        }catch (Exception e) {
            return null;
        }
        return list;
    }

    public Employee putEmployee(Employee employee){
        Employee emp = null;
        try{
            emp = employeeRepository.save(employee);
            return emp;
        }catch (Exception e){
            return null;
        }
    }

    public Employee deleteEmployeeById(int id){
        Employee emp = getEmployeeById(id);
        if(emp==null){
            return null;
        }
        employeeRepository.deleteById(id);
        return emp;
    }

    @Transactional
    public Boolean deleteEmployeeByDepartmentId(int id){
        employeeRepository.deleteByDepartmentId(id);
        return true;
    }

}
