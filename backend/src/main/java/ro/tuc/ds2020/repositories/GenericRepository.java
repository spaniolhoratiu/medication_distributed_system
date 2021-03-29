package ro.tuc.ds2020.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class GenericRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public <E> E insert(E entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    public <E> E update(E entity) {
        return em.merge(entity);
    }

    @Transactional
    public void delete(Object entity) {
        em.remove(entity);
    }

}