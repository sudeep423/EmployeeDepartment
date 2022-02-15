package com.rest.employee;

import com.rest.employee.Dao.EmployeeRepository;
import com.rest.employee.entities.Department;
import com.rest.employee.entities.Employee;
import com.rest.employee.services.DepartmentService;
import com.rest.employee.services.EmpolyeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@SpringBootTest
public class EmployeeServiceTest {

    @Test
    public void normalTest(){
        assertEquals(true,true);
    }
    @Autowired
    private EmpolyeeService empolyeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private DepartmentService departmentService;

    Department D0 = new Department(0,null);
    Department D1 = new Department(1,"backend");
    Department D2 = new Department(2,"frontend");
    Department D3 = new Department(3,"Management");

    Employee employee = new Employee(1,"Sumit",this.D1);

    @Test
    public  void getEmployeeTest(){

        List<Employee> employees = Stream.of(
                new Employee(1,"Sumit",D1),
                new Employee(2,"Sudeep",D2),
                new Employee(3,"Suresh",D3),
                new Employee(4,"pinky",D1),
                new Employee(5,"Seema",D1),
                new Employee(6,"Indu",D2)
        ).collect(Collectors.toList());

        Mockito.when(employeeRepository.findAll()).thenReturn(
                employees
        );

        List<Employee> emps = empolyeeService.getEmployees();

        assertEquals(emps,employees);
    }

    @Test
    public void addEmployeeTest(){
        Employee employee = new Employee(1,"Sumit",D1);
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        Employee emp = empolyeeService.addEmployee(employee);
        assertEquals(emp,employee);
    }

    @Test
    public void getEmployeeByIdTest(){
        Mockito.when(employeeRepository.findById(this.employee.getId())).thenReturn(Optional.of(this.employee));
        Employee emp = empolyeeService.getEmployeeById(this.employee.getId());

        assertEquals(emp,this.employee);
    }

    @Test
    public void updateEmployeeByIdTest(){
        Mockito.when(employeeRepository.save(this.employee)).thenReturn(this.employee);
        Employee emp = empolyeeService.putEmployee(employee);
        assertEquals(emp,this.employee);
    }

    @Test
    public void DeleteEmployeeByIdTest(){
        Mockito.when(employeeRepository.findById(this.employee.getId())).thenReturn(Optional.of(employee));
        empolyeeService.deleteEmployeeById(this.employee.getId());
        Mockito.verify(employeeRepository,times(1)).deleteById(this.employee.getId());
    }

}
