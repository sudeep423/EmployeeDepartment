package com.rest.employee.controller;

import com.rest.employee.entities.Department;
import com.rest.employee.services.DepartmentService;
import com.rest.employee.services.EmpolyeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmpolyeeService empolyeeService;

    @GetMapping("/department")
    public ResponseEntity<List<Department>> getDepartment(){
        return ResponseEntity.of(Optional.of(departmentService.getDepartment()));
    }

    @PostMapping("/department")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department){
        Department department1 = null;
        department1 = departmentService.getDepartmentById(department.getId());
        if(department1!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        department1 = departmentService.getDepartmentByDepartmentNameIgnoreCase(department.getDepartmentName());
        if(department1!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.of(Optional.of(departmentService.addDepartment(department)));
    }

    @GetMapping("department/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") int id){
        Department department = null;
        department = departmentService.getDepartmentById(id);
        if(department == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(department);
    }

    @PutMapping("/department/{id}")
    public ResponseEntity<Department> updateDepartmentById(@PathVariable("id") int id , @RequestBody Department department){
        if(id!=department.getId()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Department department1 = departmentService.getDepartmentById(id);
        if(department1==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        department1 = departmentService.updateDepartment(department,id);
        return ResponseEntity.ok(department1);
    }

    @DeleteMapping("/department/{id}")
    public ResponseEntity<Void> DeleteDepartmentById(@PathVariable("id") int id){
        Department department = departmentService.getDepartmentById(id);
        if(department==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        empolyeeService.deleteEmployeeByDepartmentId(id);
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
