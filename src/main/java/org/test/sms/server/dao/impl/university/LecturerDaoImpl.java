package org.test.sms.server.dao.impl.university;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.university.Lecturer;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.impl.general.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.LecturerDao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class LecturerDaoImpl extends AbstractDaoImpl<Lecturer> implements LecturerDao {

    public boolean exists(String personalNumber) {
        TypedQuery<Lecturer> query = em.createQuery("SELECT new Lecturer(id) FROM Lecturer WHERE UPPER(personalNumber) = :personalNumber", Lecturer.class);
        query.setParameter("personalNumber", personalNumber);

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public Optional<Lecturer> getByUserId(long userId) {
        TypedQuery<Lecturer> query = em.createQuery("FROM Lecturer WHERE user.id = :userId", Lecturer.class);
        query.setParameter("userId", userId);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}