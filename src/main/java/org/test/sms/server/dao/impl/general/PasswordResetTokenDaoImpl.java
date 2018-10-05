package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.PasswordResetToken;
import org.test.sms.server.dao.interfaces.general.PasswordResetTokenDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PasswordResetTokenDaoImpl implements PasswordResetTokenDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public PasswordResetToken saveToken(PasswordResetToken token) {
        em.persist(token);
        return token;
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return em.createQuery("FROM PasswordResetToken p WHERE p.token = :token", PasswordResetToken.class)
                .setParameter("token", token)
                .getSingleResult();
    }
}