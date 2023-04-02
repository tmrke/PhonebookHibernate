package ru.academits.dao;

import ru.academits.model.Contact;

import java.util.List;

public interface ContactDao extends GenericDao<Contact, Long> {
    List<Contact> getAllContacts();

    List<Contact> findByPhone(String phone);

    void remove(List<Contact> contacts);
}
