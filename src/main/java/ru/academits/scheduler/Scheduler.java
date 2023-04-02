package ru.academits.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import ru.academits.service.ContactService;

@Repository
public class Scheduler {
    private final ContactService contactService;

    public Scheduler(ContactService contactService) {
        this.contactService = contactService;
    }

    //deletes random contact every five second
//    @Scheduled(fixedRate = 5000)
//    public void deleteRandomContacts() {
//        contactService.deleteRandomContacts();
//    }

}
