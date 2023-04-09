package ru.academits.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.academits.dao.ContactDao;
import ru.academits.model.Contact;
import ru.academits.model.ContactValidation;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class ContactService {
    private final ContactValidation contactValidation = new ContactValidation();
    private final ContactDao contactDao;
    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);


    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    private boolean isExistContactWithPhone(String phone) {
        List<Contact> contactList = contactDao.findByPhone(phone);
        return !contactList.isEmpty();
    }

    private ContactValidation validateContact(List<Contact> contacts) {
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

            if (isExistContactWithPhone(contact.getPhone())) {
                contactValidation.setValid(false);
                contactValidation.setError("Номер телефона не должен дублировать другие номера в телефонной книге.");

                return contactValidation;
            }

            if (isExistContactWithPhone(contact.getPhone())) {
                contactValidation.setValid(false);
                contactValidation.setError("Такого контакта нет в телефонной книге.");

                return contactValidation;
            }
        }

        return contactValidation;
    }

    //added logs
    public ContactValidation addContact(List<Contact> contacts) {
        ContactValidation contactValidation = validateContact(contacts);
        contacts.forEach(contactDao::create);
        logger.info("Contact service: contact added");


        return contactValidation;
    }

    public List<Contact> getAllContacts() {
        logger.info("Contact service: all contacts received");

        return contactDao.getAllContacts();
    }

    public void deleteContacts(List<Long> contactsId) {
        contactsId.forEach(contactDao::remove);
        logger.info("Contact service: contacts deleted");
    }

    public void deleteRandomContacts() {
        List<Contact> allContacts = getAllContacts();
        Random random = new Random();
        int randomIndex;

        if (!allContacts.isEmpty()) {
            randomIndex = random.nextInt(allContacts.size());
            deleteContacts(Stream.of(allContacts.get(randomIndex)).map(Contact::getId).toList());
        }
    }

    public List<Contact> getContactsByFilter(String filterString) {
        logger.info("Contact service: contacts filtered");

        return contactDao.findByFilterString(filterString);

    }
}