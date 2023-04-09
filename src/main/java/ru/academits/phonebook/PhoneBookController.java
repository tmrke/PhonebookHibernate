package ru.academits.phonebook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.academits.converter.ContactDtoToContactConverter;
import ru.academits.converter.ContactToContactDtoConverter;
import ru.academits.dto.ContactDto;
import ru.academits.model.Contact;
import ru.academits.model.ContactValidation;
import ru.academits.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Controller
@RequestMapping("/phoneBook/rpc/api/v1")
public class PhoneBookController {
    private final ContactService contactService;
    private final ContactDtoToContactConverter contactDtoToContactConverter;
    private final ContactToContactDtoConverter contactToContactDtoConverter;

    public PhoneBookController(ContactService contactService, ContactDtoToContactConverter contactDtoToContactConverter, ContactToContactDtoConverter contactToContactDtoConverter) {
        this.contactService = contactService;
        this.contactDtoToContactConverter = contactDtoToContactConverter;
        this.contactToContactDtoConverter = contactToContactDtoConverter;
    }

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    //add logs
    @RequestMapping(value = "getAllContacts", method = RequestMethod.GET)
    @ResponseBody
    public List<ContactDto> getAllContacts() {
        logger.info("Phonebook controller: called method \"getAllContacts\"");

        return contactToContactDtoConverter.convert(contactService.getAllContacts());
    }

    @RequestMapping(value = "addContact", method = RequestMethod.POST)
    @ResponseBody
    public ContactValidation addContact(@RequestBody List<ContactDto> contacts) {
        logger.info("Phonebook controller: called method \"addContact\"");

        return contactService.addContact(contactDtoToContactConverter.convert(contacts));
//        return contactService.addContact(contacts);
    }

    @RequestMapping(value = "deleteContacts", method = RequestMethod.POST)
    @ResponseBody
    public void deleteContacts(@RequestBody List<Long> contactsId) {
        logger.info("Phonebook controller: called method \"deleteContacts\"");
        contactService.deleteContacts(contactsId);
    }

    @RequestMapping(value = "filterContacts", method = RequestMethod.POST)
    @ResponseBody
    public List<Contact> getContactsByFilter(@RequestParam("filterString") String filterSting) {
        logger.info("Phonebook controller: called method \"filterContacts\"");

        return contactService.getContactsByFilter(filterSting);
    }
}


