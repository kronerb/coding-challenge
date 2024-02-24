package com.codingchallenge.projectcodingchallenge.ServiceTest;

import com.codingchallenge.projectcodingchallenge.Exceptions.ContactException;
import com.codingchallenge.projectcodingchallenge.Model.Contact;
import com.codingchallenge.projectcodingchallenge.Repository.ContactRepository;
import com.codingchallenge.projectcodingchallenge.Service.ContactService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    public void testGetContactById_ContactFound() {
        String contactId = "65d9be08de9b1afa4ced2923";
        Contact mockContact = new Contact();
        mockContact.setId(contactId);
        when(contactRepository.findById(contactId)).thenReturn(Optional.of(mockContact));

        Contact resultContact = contactService.getContactById(contactId);

        assertNotNull(resultContact);
        assertEquals(contactId, resultContact.getId());
    }

    @Test
    public void testGetContactById_ContactNotFound() {
        String contactId = "nonexistentId";
        when(contactRepository.findById(contactId)).thenReturn(Optional.empty());

        assertThrows(ContactException.class, () -> contactService.getContactById(contactId));
    }

    @Test
    public void testCreateContact() {
        Contact newContact = new Contact();
        when(contactRepository.save(newContact)).thenReturn(newContact);

        Contact resultContact = contactService.createContact(newContact);

        assertNotNull(resultContact);
        assertSame(newContact, resultContact);
    }

    @Test
    public void testGetAllContacts() {
        Contact contact1 = new Contact();
        Contact contact2 = new Contact();
        when(contactRepository.findAll()).thenReturn(Arrays.asList(contact1, contact2));

        List<Contact> resultContacts = contactService.getAllContacts();

        assertNotNull(resultContacts);
        assertEquals(2, resultContacts.size());
        assertTrue(resultContacts.contains(contact1));
        assertTrue(resultContacts.contains(contact2));
    }

    @Test
    public void testUpdateContact_ContactFound() {
        String contactId = "65d9be08de9b1afa4ced2923";
        Contact existingContact = new Contact();
        existingContact.setId(contactId);
        existingContact.setLastName("Benjamin");
        existingContact.setLastName("Kroner");
        existingContact.setCity("Rinchnach");

        Contact updatedContact = new Contact();
        updatedContact.setFirstName("Andreas");
        updatedContact.setLastName("Kroner");

        when(contactRepository.findById(contactId)).thenReturn(Optional.of(existingContact));
        when(contactRepository.save(existingContact)).thenReturn(existingContact);

        Contact resultContact = contactService.updateContact(contactId, updatedContact);

        assertNotNull(resultContact);
        assertEquals(updatedContact.getFirstName(), resultContact.getFirstName());
        assertEquals(updatedContact.getLastName(), resultContact.getLastName());
    }

    @Test
    public void testUpdateContact_ContactNotFound() {
        String contactId = "nonexistentId";
        Contact updatedContact = new Contact();

        when(contactRepository.findById(contactId)).thenReturn(Optional.empty());

        ContactException thrownException = assertThrows(ContactException.class,
                () -> contactService.updateContact(contactId, updatedContact));

        assertEquals("Contact not found!", thrownException.getMessage());
    }

    @Test
    public void testDeleteContact_ContactFound() {
        String contactId = "65d9be08de9b1afa4ced2923";
        Contact mockContact = new Contact();
        when(contactRepository.existsById(contactId)).thenReturn(true);

        // contact exists
        assertDoesNotThrow(() -> contactService.deleteContact(contactId));

        // Verify that deleteById was called with the correct ID
        verify(contactRepository, times(1)).deleteById(contactId);
    }

    @Test
    public void testSearchContactsByName_ContactsFound() {
        String searchName = "John";
        Contact contact1 = new Contact();
        contact1.setFirstName("John");
        Contact contact2 = new Contact();
        contact2.setLastName("Johnson");
        when(contactRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchName, searchName))
                .thenReturn(Arrays.asList(contact1, contact2));

        List<Contact> resultContacts = contactService.searchContactsByName(searchName);

        assertNotNull(resultContacts);
        assertEquals(2, resultContacts.size());
    }

}
