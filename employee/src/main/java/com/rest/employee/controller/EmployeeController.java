package com.rest.employee.controller;

import com.rest.employee.entities.Department;
import com.rest.employee.entities.Employee;
import com.rest.employee.services.DepartmentService;
import com.rest.employee.services.EmpolyeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    private EmpolyeeService empolyeeService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees(){
        List<Employee> list = empolyeeService.getEmployees();
        if(list==null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if(list.size()==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        Employee emp = null;
        emp = empolyeeService.getEmployeeById(employee.getId());
        if(emp!=null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        else {
            emp = empolyeeService.addEmployee(employee);
            if(emp==null){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
                return ResponseEntity.ok(employee);
        }
    }

    @PutMapping("/employees/{empId}/department/{id}")
    public ResponseEntity<Employee> updateEmployeeToDepartment(@PathVariable("id") int id,@PathVariable("empId") int empId){
        Employee emp = empolyeeService.getEmployeeById(empId);
        Department dep = departmentService.getDepartmentById(id);
        if(emp==null || dep==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        emp.setDepartment(dep);
        emp = empolyeeService.putEmployee(emp);
        return ResponseEntity.ok(emp);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id){
        Employee employee = empolyeeService.getEmployeeById(id);
        if(employee == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/employees/department/{id}")
    public ResponseEntity<List<Employee>> getEmployeeByDepartment(@PathVariable("id") int department_id){
        Department department = departmentService.getDepartmentById(department_id);
        if(department==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Employee> list = empolyeeService.getEmployeeByDepartment(department);
        return ResponseEntity.of(Optional.of(list));
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> putEmployeeById(@PathVariable("id") int id,@RequestBody Employee employee){
        if(id!=employee.getId()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Department department = departmentService.getDepartmentById(employee.getDepartment().getId());
        if(department==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if(department.getDepartmentName().equals(employee.getDepartment().getDepartmentName()) && department.getId()==employee.getDepartment().getId()){
            Employee emp = empolyeeService.putEmployee(employee);
            if(emp==null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }else
                return ResponseEntity.ok(emp);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable("id") int id){
        Employee emp = empolyeeService.deleteEmployeeById(id);
        if(emp==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(emp);
    }
    @DeleteMapping("/employees/department/{id}")
    public ResponseEntity<Employee> deleteEmployeeByDepartmentId(@PathVariable("id") int id){
        boolean bool = empolyeeService.deleteEmployeeByDepartmentId(id);
        if(!bool){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(null);
    }
}
