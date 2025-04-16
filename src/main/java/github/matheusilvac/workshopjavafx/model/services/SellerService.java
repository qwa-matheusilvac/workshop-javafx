package github.matheusilvac.workshopjavafx.model.services;

import github.matheusilvac.workshopjavafx.model.dao.DaoFactory;
import github.matheusilvac.workshopjavafx.model.dao.SellerDao;
import github.matheusilvac.workshopjavafx.model.persistence.entity.Seller;

import java.util.List;

public class SellerService {

    private SellerDao dao = DaoFactory.createSellerDao();

    public List<Seller> findAll(){
      return dao.findAll();
    };

    public void saveOrUpdate(Seller Seller) {
        if (Seller.getId() == null) {
            dao.insert(Seller);
        } else {
            dao.update(Seller);
        }
    }

    public void remove(Seller Seller) {
        if (Seller.getId() == null) {
            throw new IllegalArgumentException("Seller id is null");
        } else {
            dao.deleteById(Seller.getId());
        }
    }
}
