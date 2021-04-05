package elethu.ikamva.services;

import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.exception.ContactDetailsException;

import java.util.List;
import java.util.Set;

public interface ContactDetailsService {
    ContactDetails saveContactDetail(ContactDetails contactDetails) throws ContactDetailsException;
    void deleteContactDetails(ContactDetails contactDetails) throws ContactDetailsException;
    List<ContactDetails> getContactDetail(String investId) throws ContactDetailsException;
    List<ContactDetails> findAllContactDetails() throws ContactDetailsException;
    List<ContactDetails> findALlContactTypes(String type) throws ContactDetailsException;
    ContactDetails updateContactDetail(ContactDetails newContact, String investI) throws ContactDetailsException;
}
