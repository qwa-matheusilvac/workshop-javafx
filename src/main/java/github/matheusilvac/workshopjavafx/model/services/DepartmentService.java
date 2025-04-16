package github.matheusilvac.workshopjavafx.model.services;

import github.matheusilvac.workshopjavafx.model.dao.DaoFactory;
import github.matheusilvac.workshopjavafx.model.dao.DepartmentDao;
import github.matheusilvac.workshopjavafx.model.persistence.entity.Department;

import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll(){
      return dao.findAll();
    };

    public void saveOrUpdate(Department department) {
        if (department.getId() == null) {
            dao.insert(department);
        } else {
            dao.update(department);
        }
    }

    public void remove(Department department) {
        if (department.getId() == null) {
            throw new IllegalArgumentException("Department id is null");
        } else {
            dao.deleteById(department.getId());
        }
    }
}
