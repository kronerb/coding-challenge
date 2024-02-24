package com.codingchallenge.projectcodingchallenge.Controller;

import com.codingchallenge.projectcodingchallenge.Exceptions.ContactException;
import com.codingchallenge.projectcodingchallenge.Model.Contact;
import com.codingchallenge.projectcodingchallenge.Service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable String id){
        Contact contact = contactService.getContactById(id);
        if (contact != null){
            return ResponseEntity.ok(contact);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        List <Contact> contacts = contactService.getAllContacts();
        if(contacts.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(contacts);
        }
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact newContact){
        Contact createdContact = contactService.createContact(newContact);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable String id, @RequestBody Contact updatedContact){
        try {
            Contact updated = contactService.updateContact(id, updatedContact);
            return ResponseEntity.ok(updated);
        } catch (ContactException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable String id) {
        try {
            contactService.deleteContact(id);
            return ResponseEntity.ok("Successfully deleted contact!");
        } catch (ContactException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Contact>> searchContactByName(@RequestParam String name){
        List<Contact> contacts = contactService.searchContactsByName(name);
        if(contacts.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(contacts);
        }
    }

}
