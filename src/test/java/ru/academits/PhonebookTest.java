package ru.academits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.academits.dao.ContactDao;
import ru.academits.dao.ContactDaoImpl;
import ru.academits.model.Contact;
import ru.academits.model.ContactValidation;
import ru.academits.service.ContactService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhonebookTest {            //added test
    private final ContactDao contactDao = new ContactDaoImpl();
    private final ContactService contactService = new ContactService(contactDao);
    private final Contact contact1 = new Contact();
    private final Contact contact2 = new Contact();
    private final Contact contact3 = new Contact();

    @BeforeEach
    public void createContact() {
        contact1.setFirstName("Иван");
        contact1.setLastName("Иванов");
        contact1.setPhone("9123456701");

        contact2.setFirstName("Марина");
        contact2.setLastName("Маринина");
        contact2.setPhone("9123456702");

        contact3.setFirstName("Марина");
        contact3.setLastName("Иванова");
        contact3.setPhone("9123456703");
    }

    @Test
    public void testContact() {
        ContactValidation contactValidation = contactService.addContact(Collections.singletonList(contact1));
        assertTrue(contactValidation.isValid());

        contactValidation = contactService.addContact(Collections.singletonList(contact2));
        assertTrue(contactValidation.isValid());

        contactValidation = contactService.addContact(Collections.singletonList(contact3));
        assertTrue(contactValidation.isValid());
    }
}