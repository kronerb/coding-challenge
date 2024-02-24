package com.codingchallenge.projectcodingchallenge.Repository;

import com.codingchallenge.projectcodingchallenge.Model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContactRepository extends MongoRepository<Contact, String> {
    /**
     * Finds contacts by searching for a case-insensitive match in the first name or last name.
     *
     * @param firstName The partial or full first name to search for.
     * @param lastName  The partial or full last name to search for.
     * @return A list of contacts matching the specified criteria.
     */
    List<Contact> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

}
