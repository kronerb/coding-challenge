package com.codingchallenge.projectcodingchallenge.Repository;

import com.codingchallenge.projectcodingchallenge.Model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact, String> {
}
