package ru.academits.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.academits.dao.ContactDaoImpl;
import ru.academits.model.Contact;
import ru.academits.model.ContactValidation;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class ContactService {
    private final ContactValidation contactValidation = new ContactValidation();
    private final ContactDaoImpl contactDao;
    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);


    public ContactService(ContactDaoImpl contactDao) {
        this.contactDao = contactDao;
    }

    private boolean isExistContactWithPhone(String phone) {
        List<Contact> contactList = contactDao.getAllContacts();
        for (Contact contact : contactList) {
            if (contact.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    private ContactValidation validateContact(List<Contact> contacts, boolean toDelete) {
        contactValidation.setValid(true);

        for (Contact contact : contacts) {
            if (contact.getFirstName().isEmpty()) {
                contactValidation.setValid(false);
                contactValidation.setError("Поле Имя должно быть заполнено.");

                return contactValidation;
            }

            if (contact.getLastName().isEmpty()) {
                contactValidation.setValid(false);
                contactValidation.setError("Поле Фамилия должно быть заполнено.");

                return contactValidation;
            }

            if (contact.getPhone().isEmpty()) {
                contactValidation.setValid(false);
                contactValidation.setError("Поле Телефон должно быть заполнено.");

                return contactValidation;
            }

            if (isExistContactWithPhone(contact.getPhone()) && !toDelete) {
                contactValidation.setValid(false);
                contactValidation.setError("Номер телефона не должен дублировать другие номера в телефонной книге.");

                return contactValidation;
            }

            if (toDelete && !isExistContactWithPhone(contact.getPhone())) {
                contactValidation.setValid(false);
                contactValidation.setError("Такого контакта нет в телефонной книге.");

                return contactValidation;
            }
        }

        return contactValidation;
    }

    //added logs
    public ContactValidation addContact(List<Contact> contacts) {
        ContactValidation contactValidation = validateContact(contacts, false);

        for (Contact contact : contacts) {
            if (contactValidation.isValid()) {
                contactDao.add(contact);
                logger.info("Contact service: contact added");
            }
        }

        return contactValidation;
    }

    public List<Contact> getAllContacts() {
        logger.info("Contact service: all contacts received");

        return contactDao.getAllContacts();
    }

    public ContactValidation deleteContacts(List<Contact> contacts) {
        ContactValidation contactValidation = validateContact(contacts, true);

        if (contactValidation.isValid()) {
            logger.info("Contact service: contacts deleted");
            contactDao.delete(contacts);
        }

        return contactValidation;
    }

    public void deleteRandomContacts() {
        List<Contact> allContacts = getAllContacts();
        Random random = new Random();
        int randomIndex;

        if (!allContacts.isEmpty()) {
            randomIndex = random.nextInt(allContacts.size());
            deleteContacts(Collections.singletonList(allContacts.get(randomIndex)));
        }
    }

    public List<Contact> getContactsByFilter(String filterString) {
        logger.info("Contact service: contacts filtered");

        return contactDao.filter(filterString);
    }
}