package com.alcideswenner.app_wildfly.CRM;

import com.alcideswenner.app_wildfly.entities.Contato;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class CRMService {

    @PersistenceContext
    private EntityManager em;

    public List<Contato> getAllContacts() {
        return em.createQuery("SELECT c FROM Contato c", Contato.class ).getResultList();
    }

    public Optional<Contato> findContact(Long contactId) {
        return Optional.ofNullable(em.find(Contato.class, contactId));
    }

    public Contato addContact(Contato ce) {
        em.persist( ce );
        return ce;
    }

    public void deleteContact(Long contactId) {
        em.remove( em.find(Contato.class, contactId) );
    }

    public void updateContact(Contato ce) { em.merge(ce); }
}