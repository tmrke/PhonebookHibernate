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
        Contact contact1 = new Contact();
        Contact contact2 = new Contact();
        Contact contact3 = new Contact();

        contact1.setId(getNewId());
        contact1.setFirstName("Иван");
        contact1.setLastName("Иванов");
        contact1.setPhone("9123456701");

        contact2.setId(getNewId());
        contact2.setFirstName("Марина");
        contact2.setLastName("Маринина");
        contact2.setPhone("9123456702");

        contact3.setId(getNewId());
        contact3.setFirstName("Марина");
        contact3.setLastName("Иванова");
        contact3.setPhone("9123456703");

        contactList.add(contact1);
        contactList.add(contact2);
        contactList.add(contact3);
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