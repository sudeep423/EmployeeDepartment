package com.rest.employee.services;

import com.rest.employee.Dao.DepartmentRepository;
import com.rest.employee.entities.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"Department"})
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getDepartment(){
        return (List<Department>)departmentRepository.findAll();
    }

    public Department addDepartment(Department department){
        Department department1 = departmentRepository.save(department);
        return department;
    }

    @Cacheable(key = "#id", value="Department")
    public Department getDepartmentById( int id){
        Department department = null;
        try{
            department = departmentRepository.findById(id).stream().findFirst().get();
        }catch (Exception e){
            return null;
        }
        return department;
    }

    public Department getDepartmentByDepartmentNameIgnoreCase(String departmentName){
        Department department = null;
        try{
            department = departmentRepository.findDepartmentByDepartmentNameIgnoreCase(departmentName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return department;
    }

    @CachePut(key="#id",value="Department")
    public Department updateDepartment(Department department,int id){
        Department department1 = departmentRepository.save(department);
        return department1;
    }

    @CacheEvict(key="#id",value="Department")
    public void deleteDepartmentById(int id){
        departmentRepository.delete(getDepartmentById(id));
        return;
    }
}
