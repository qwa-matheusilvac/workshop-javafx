package github.matheusilvac.workshopjavafx.model.dao;

import github.matheusilvac.workshopjavafx.db.DB;
import github.matheusilvac.workshopjavafx.model.dao.impl.DepartmentDaoJDBC;
import github.matheusilvac.workshopjavafx.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
