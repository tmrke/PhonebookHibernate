package ru.academits.phonebook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    public PhoneBookController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "getAllContacts", method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @RequestMapping(value = "addContact", method = RequestMethod.POST)
    @ResponseBody
    public ContactValidation addContact(@RequestBody List<Contact> contacts) {
        return contactService.addContact(contacts);
    }

    @RequestMapping(value = "deleteContacts", method = RequestMethod.POST)
    @ResponseBody
    public ContactValidation deleteContacts(@RequestBody List<Contact> contacts) {
        return contactService.deleteContacts(contacts);
    }

    @RequestMapping(value = "filterContacts", method = RequestMethod.POST)
    @ResponseBody
    public List<Contact> getContactsByFilter(@RequestParam("filterString") String filterSting) {
        return contactService.getContactsByFilter(filterSting);
    }
}


