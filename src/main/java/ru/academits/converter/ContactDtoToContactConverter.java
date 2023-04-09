package ru.academits.converter;

import org.springframework.stereotype.Service;
import ru.academits.dto.ContactDto;
import ru.academits.model.Contact;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactDtoToContactConverter extends AbstractConverter<ContactDto, Contact> {
    @Override
    public Contact convert(ContactDto source) {
        Contact c = new Contact();

        c.setId(source.getId());
        c.setFirstName(source.getFirstName());
        c.setLastName(source.getLastName());
        c.setPhone(source.getPhone());

        return c;
    }

    @Override
    public List<Contact> convert(List<ContactDto> source) {
        return source.stream().map(this::convert).collect(Collectors.toList());
    }
}
