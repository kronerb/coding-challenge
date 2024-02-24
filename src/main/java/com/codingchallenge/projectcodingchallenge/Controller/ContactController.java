package com.codingchallenge.projectcodingchallenge.Controller;

import com.codingchallenge.projectcodingchallenge.Model.Contact;
import com.codingchallenge.projectcodingchallenge.Service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
