package database.application.development.repository.impl;

import database.application.development.model.domain.Product;
import database.application.development.repository.ProductDao;
import database.application.development.repository.configuration.ORMConfig;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.sql.RowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinohorvat on 30/10/2017.
 */
@Slf4j
@Repository
public class ProductDaoImpl extends ORMConfig implements ProductDao {
    @Autowired
    public ProductDaoImpl(){
        super();
    }

    @Override
    public Product getProductById(int productId) {
        Session session = this.getSession();
        Product product = null;
        Transaction transaction = session.beginTransaction();
        product = session.get(Product.class, productId);
        if(product == null) throw new EmptyResultDataAccessException(1);
        transaction.commit();
        session.close();

        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(product);
        transaction.commit();
        session.close();

        return getProductById(product.getId());
    }

    @Override
    public Product createProduct(Product product) {
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        int newEntityId = (int) session.save(product);
        transaction.commit();
        session.close();

        return getProductById(newEntityId);
    }

    @Override
    public List<Product> getAllProducts() {
        Session session = this.getSession();

        String hql = "FROM product WHERE productStatus != 'DELETED'";
        List<Product> products = session.createQuery(hql).list();

        session.close();
        return products;
    }

    @Override
    public void deleteProduct(Product product) {
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(product);
        transaction.commit();
        session.close();
    }
}



