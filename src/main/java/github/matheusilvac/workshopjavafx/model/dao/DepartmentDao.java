package github.matheusilvac.workshopjavafx.model.dao;

import github.matheusilvac.workshopjavafx.model.persistence.entity.Department;

import java.util.List;

public interface DepartmentDao {

    void insert(Department obj);
    void update(Department obj);
    void deleteById(Integer id);
    Department findById(Integer id);
    List<Department> findAll();
}
