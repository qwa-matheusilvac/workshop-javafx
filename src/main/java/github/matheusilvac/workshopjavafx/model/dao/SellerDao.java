package github.matheusilvac.workshopjavafx.model.dao;

import github.matheusilvac.workshopjavafx.model.persistence.entity.Department;
import github.matheusilvac.workshopjavafx.model.persistence.entity.Seller;

import java.util.List;

public interface SellerDao {
    void insert(Seller obj);
    void update(Seller obj);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Department department);
}
