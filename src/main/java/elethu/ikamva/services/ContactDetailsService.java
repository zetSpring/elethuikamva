package elethu.ikamva.services;

import elethu.ikamva.domain.ContactDetails;

import java.util.Set;

public interface ContactDetailsService {
    void saveOrUpdateContactDetails(ContactDetails contactDetails);

    void deleteContactDetails(ContactDetails contactDetails);

    ContactDetails findContactDetail(String contact);

    Set<ContactDetails> findAllContactDetails();

    Boolean isContactActive(ContactDetails contactDetails);
}
