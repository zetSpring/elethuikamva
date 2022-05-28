package elethu.ikamva.service.Impl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.ContactDetailsException;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.repositories.ContactDetailsRepository;
import elethu.ikamva.repositories.MemberRepository;
import elethu.ikamva.service.ContactDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactDetailsServiceImpl implements ContactDetailsService {
    private final ContactDetailsRepository contactDetailsRepository;
    private final MemberRepository memberRepository;

    @Override
    public ContactDetails saveContactDetail(ContactDetails contactDetails) {
        Member member = memberRepository.findMemberByInvestmentId(contactDetails.getMemberInvestId().toUpperCase())
                .orElseThrow(() -> new ContactDetailsException(String.format("Could not find member with investment id %s to add contacts too.", contactDetails.getMemberInvestId())));
        contactDetails.setMembers(member);
        contactDetails.setCreatedDate(DateFormatter.returnLocalDate());

        return contactDetailsRepository.save(contactDetails);
    }

    @Override
    public ContactDetails deleteContactById(Long id) throws ContactDetailsException {
        ContactDetails contact = contactDetailsRepository.findById(id)
                .orElseThrow(() -> new ContactDetailsException(String.format("Could not find contact with id: %s to delete.", id)));
        contact.setEndDate(DateFormatter.returnLocalDate());

        return contactDetailsRepository.save(contact);
    }

    @Override
    public List<ContactDetails> deleteContactDetails(String investId) {
        var contactDetails = contactDetailsRepository.findAllContactsByMemberInvestId(investId);
        if (CollectionUtils.isEmpty(contactDetails)) {
            throw new ContactDetailsException(String.format("Contact: %s is already inactive or could not be found", investId));
        }

        contactDetails.forEach(memberContacts -> {
            memberContacts.setEndDate(DateFormatter.returnLocalDate());
            contactDetailsRepository.save(memberContacts);
        });

        return contactDetails.stream()
                .filter(contacts -> contacts.getEndDate() != null)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactDetails> findMemberContactByInvestId(String investId) {
        List<ContactDetails> memberContacts = contactDetailsRepository.findAllContactsByMemberInvestId(investId.toUpperCase());

        if (memberContacts.isEmpty()) {
            throw new ContactDetailsException("There are no contact numbers for customer id: " + investId);
        }

        return memberContacts;
    }

    @Override
    public List<ContactDetails> findAllContactDetails() {
        List<ContactDetails> contactDetails = new ArrayList<>();
        contactDetailsRepository.findAll().iterator().forEachRemaining(contactDetails::add);

        if (contactDetails.isEmpty()) {
            throw new ContactDetailsException("There were no contact details found");
        }

        return contactDetails.stream()
                .filter(contact -> Objects.isNull(contact.getEndDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactDetails> findALlContactsByContactType(String contactType) {
        var contactDetailsList = contactDetailsRepository.findContactDetailsByContactType(contactType);

        if (contactDetailsList.isEmpty()) {
            throw new ContactDetailsException("There are no contact details for contact type: " + contactType);
        }

        return contactDetailsList.stream()
                .filter(contact -> contact.getEndDate() == null)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDetails updateContactDetail(ContactDetails contactDetail) throws ContactDetailsException {
        Member member = memberRepository.findMemberByInvestmentId(contactDetail.getMemberInvestId())
                .orElseThrow(() -> new MemberException("Couls not find a member: " + contactDetail.getMemberInvestId() + " to update contact for: "));

        ContactDetails updateContact = contactDetailsRepository.findMemberContact(member.getId(), contactDetail.getContactType())
                .orElseThrow(() -> new ContactDetailsException("Could not find a contacts to update with id: " + contactDetail.getContactType()));


        updateContact.setContact(contactDetail.getContact());

        return contactDetailsRepository.save(updateContact);
    }
}
