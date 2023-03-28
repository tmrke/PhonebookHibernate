package ru.academits.dao;

import org.springframework.stereotype.Repository;
import ru.academits.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ContactDao {
    private final List<Contact> contactList = new ArrayList<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    public ContactDao() {

    }

    private int getNewId() {
        return idSequence.addAndGet(1);
    }

    public List<Contact> getAllContacts() {
        return contactList;
    }

    public void add(Contact contact) {
        contactList.add(contact);
    }

    public void delete(List<Contact> toDeleteContacts) {
        contactList.removeAll(toDeleteContacts);
    }

    public List<Contact> filter(String filterString){
        List<Contact> contactsByFilter = new ArrayList<>();

        for (Contact contact: contactList){
            if (contact.getFirstName().toLowerCase().contains(filterString)
                    || contact.getLastName().toLowerCase().contains(filterString)
                    || contact.getPhone().toLowerCase().contains(filterString)) {
                contactsByFilter.add(contact);
            }
        }

        return contactsByFilter;
    }
}