package ru.academits.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.academits.model.Contact;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ContactDaoImpl extends GenericDaoImpl<Contact, Long> implements ContactDao {

    public ContactDaoImpl() {
        super(Contact.class);
    }

    @Override
    public List<Contact> getAllContacts() {
        return findAll();
    }

    @Override
    public List<Contact> findByPhone(String phone) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> cq = cb.createQuery(clazz);

        Root<Contact> root = cq.from(clazz);

        cq.where(cb.equal(root.get("phone"), phone));

        CriteriaQuery<Contact> select = cq.select(root);
        TypedQuery<Contact> q = entityManager.createQuery(select);

        return q.getResultList();
    }

    public List<Contact> filter(String filterString) {
        return null;
    }

//    @Transactional
//    @Override
//    public void remove(List<Contact> contacts) {
//        for (Contact contact : contacts) {
//            Query query = entityManager.createQuery("DELETE FROM Contact c WHERE c.id = :id");
//            query.setParameter("id", contact.getId());
//            query.executeUpdate();
//        }
//
//        entityManager.flush();
//    }
}