package com.rest.employee;

import com.rest.employee.Dao.DepartmentRepository;
import com.rest.employee.Dao.EmployeeRepository;
import com.rest.employee.entities.Department;
import com.rest.employee.services.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DepartmentServiceTest {


    @Autowired
    DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    Department D0 = new Department(0,null);
    Department D1 = new Department(1,"backend");
    Department D2 = new Department(2,"frontend");
    Department D3 = new Department(3,"Management");


    @Test
    public void getDepartmentTest(){
        List<Department> departments = Stream.of(D0,D1,D2,D3).collect(Collectors.toList());
        Mockito.when(departmentRepository.findAll()).thenReturn(departments);
        List<Department> dep = departmentService.getDepartment();
        assertEquals(dep,departments);
    }

//    @Test
//    public void getDepartmentByIdTest(){
//        Mockito.when(departmentRepository.findById(this.D1.getId())).thenReturn(Optional.of(this.D1));
//        Department dept = departmentService.getDepartmentById(this.D1.getId());
//        assertEquals(dept,this.D1);
//    }

}
