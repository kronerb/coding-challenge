package com.codingchallenge.projectcodingchallenge.Service;

import com.codingchallenge.projectcodingchallenge.Exceptions.ContactException;
import com.codingchallenge.projectcodingchallenge.Model.Contact;
import com.codingchallenge.projectcodingchallenge.Repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact getContactById(String id){
        Contact contact = contactRepository.findById(id).orElse(null);
        if (contact != null) {
            return contact;
        } else {
            throw new ContactException("Contact not found!");
        }
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact createContact(Contact newContact){
        return contactRepository.save(newContact);
    }

    public Contact updateContact(String id, Contact updatedContact) {
        Contact existingContact = contactRepository.findById(id).orElse(null);
            if(existingContact != null) {
                existingContact.setFirstName(updatedContact.getFirstName());
                existingContact.setLastName(updatedContact.getLastName());
                existingContact.setHouseNumber(updatedContact.getHouseNumber());
                existingContact.setZipCode(updatedContact.getZipCode());
                existingContact.setCity(updatedContact.getCity());
                existingContact.setPhoneNumber(updatedContact.getPhoneNumber());
                return contactRepository.save(existingContact);
            } else {
                throw new ContactException("Contact not found!");
            }
    }

    public void deleteContact(String id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
        } else {
            throw new ContactException("Contact not found!");
        }
    }

    public List<Contact> searchContactsByName(String name){
        // Searches for contacts by a partial or full match in the first name or last name, ignoring the case.
        return contactRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }

}
