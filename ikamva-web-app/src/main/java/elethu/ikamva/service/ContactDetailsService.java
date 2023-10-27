package elethu.ikamva.service;

import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.exception.ContactDetailsException;

import java.util.List;

public interface ContactDetailsService {
    ContactDetails deleteContactById(Long id) throws ContactDetailsException;

    List<ContactDetails> findAllContactDetails() throws ContactDetailsException;

    List<ContactDetails> deleteContactDetails(String InvestId) throws ContactDetailsException;

    ContactDetails updateContactDetail(ContactDetails newContact) throws ContactDetailsException;

    ContactDetails saveContactDetail(ContactDetails contactDetails) throws ContactDetailsException;

    List<ContactDetails> findMemberContactByInvestId(String investId) throws ContactDetailsException;

    List<ContactDetails> findALlContactsByContactType(String contactType) throws ContactDetailsException;
}
