package database.application.development.repository.hst.impl;

import database.application.development.model.history.HstBranch;
import database.application.development.repository.configuration.ORMConfig;
import database.application.development.repository.hst.HstBranchDao;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

/**
 * Created by dinohorvat on 30/10/2017.
 */
@Slf4j
@Repository
public class HstBranchDaoImpl extends ORMConfig implements HstBranchDao {
    @Autowired
    public HstBranchDaoImpl(){
        super();
    }

    @Override
    public HstBranch getHstBranchById(int hstBranchId, Session session) {
        HstBranch hstBranch = null;
        hstBranch = session.get(HstBranch.class, hstBranchId);
        if(hstBranch == null) throw new EmptyResultDataAccessException(1);

        return hstBranch;
    }

    @Override
    public HstBranch updateHstBranch(HstBranch hstBranch) {
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(hstBranch);
        transaction.commit();
        session.close();

        return getHstBranchById(hstBranch.getId(), session);
    }

    @Override
    public HstBranch createHstBranch(HstBranch hstBranch, Session session) {
        int newEntityId = (int) session.save(hstBranch);

        return getHstBranchById(newEntityId, session);
    }

    @Override
    public void deleteHstBranch(HstBranch hstBranch) {
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(hstBranch);
        transaction.commit();
        session.close();
    }
}
