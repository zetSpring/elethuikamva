package elethu.ikamva.services;

import elethu.ikamva.domain.ContactDetails;

import java.util.List;
import java.util.Set;

public interface ContactDetailsService {
    void saveOrUpdateContactDetails(ContactDetails contactDetails);
    void deleteContactDetails(ContactDetails contactDetails);
    List<ContactDetails> getContactDetail(String investId);
    List<ContactDetails> findAllContactDetails();
    List<ContactDetails> findALlContactTypes(String type);
    //List<ContactDetails> findAllByContactsById(Long Id);
    Boolean isContactActive(ContactDetails contactDetails);
}
