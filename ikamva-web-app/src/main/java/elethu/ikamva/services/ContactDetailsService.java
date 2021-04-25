package elethu.ikamva.services;

import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.exception.ContactDetailsException;

import java.util.List;
import java.util.Set;

public interface ContactDetailsService {
    ContactDetails saveContactDetail(ContactDetails contactDetails) throws ContactDetailsException;
    List<ContactDetails> deleteContactDetails(String InvestId) throws ContactDetailsException;
    List<ContactDetails> findMemberContactByInvestId(String investId) throws ContactDetailsException;
    List<ContactDetails> findAllContactDetails() throws ContactDetailsException;
    List<ContactDetails> findALlContactsByContactType(String contactType) throws ContactDetailsException;
    ContactDetails updateContactDetail(ContactDetails newContact, String investI) throws ContactDetailsException;
}
