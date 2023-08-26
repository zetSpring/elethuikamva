package elethu.ikamva.service.Impl;

import elethu.ikamva.aspects.ExecutionTime;
import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.ContactDetailsException;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.repositories.ContactDetailsRepository;
import elethu.ikamva.repositories.MemberRepository;
import elethu.ikamva.service.ContactDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactDetailsServiceImpl implements ContactDetailsService {
    private final MemberRepository memberRepository;
    private final ContactDetailsRepository contactDetailsRepository;

    @Override
    @ExecutionTime
    public ContactDetails saveContactDetail(ContactDetails contactDetails) {
        log.info("Service Invoccation::{}", getClass().getSimpleName());
        var investId = contactDetails.getMemberInvestId().toUpperCase();
        var member = memberRepository
                .findMemberByInvestmentId(investId)
                .orElseThrow(() -> new ContactDetailsException(
                        String.format("Could not find member with investment id %s to add contacts too.", investId)));
        contactDetails.setMembers(member);
        contactDetails.setCreatedDate(DateFormatter.returnLocalDate());

        log.info("Saving contact details for member: {}", investId);
        return contactDetailsRepository.save(contactDetails);
    }

    @Override
    @ExecutionTime
    public ContactDetails deleteContactById(Long id) throws ContactDetailsException {
        var contact = contactDetailsRepository
                .findById(id)
                .orElseThrow(() -> new ContactDetailsException(
                        String.format("Could not find contact with id: %s to delete.", id)));
        contact.setEndDate(DateFormatter.returnLocalDate());

        return contactDetailsRepository.save(contact);
    }

    @Override
    @ExecutionTime
    public List<ContactDetails> deleteContactDetails(String investId) {
        var contactDetails = contactDetailsRepository.findAllContactsByMemberInvestId(investId);
        if (CollectionUtils.isEmpty(contactDetails)) {
            throw new ContactDetailsException(
                    String.format("Contact: %s is already inactive or could not be found", investId));
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
    @ExecutionTime
    public List<ContactDetails> findMemberContactByInvestId(String investId) {
        var memberContacts = contactDetailsRepository.findAllContactsByMemberInvestId(investId.toUpperCase());

        if (CollectionUtils.isEmpty(memberContacts)) {
            throw new ContactDetailsException("There are no contact numbers for customer id: " + investId);
        }

        return memberContacts;
    }

    @Override
    @ExecutionTime
    public List<ContactDetails> findAllContactDetails() {
        List<ContactDetails> contactDetails = new ArrayList<>();
        contactDetailsRepository.findAll().iterator().forEachRemaining(contactDetails::add);

        if (CollectionUtils.isEmpty(contactDetails)) {
            var msg = "There were no contact details found";
            log.info(msg);
            throw new ContactDetailsException(msg);
        }

        return contactDetails.stream()
                .filter(contact -> Objects.isNull(contact.getEndDate()))
                .collect(Collectors.toList());
    }

    @Override
    @ExecutionTime
    public List<ContactDetails> findALlContactsByContactType(String contactType) {
        var contactDetailsList = contactDetailsRepository.findContactDetailsByContactType(contactType);

        if (contactDetailsList.isEmpty()) {
            var msg = String.format("There are no contact details for contact type: %s", contactType);
            log.info(msg);
            throw new ContactDetailsException(msg);
        }

        return contactDetailsList.stream()
                .filter(contact -> contact.getEndDate() == null)
                .collect(Collectors.toList());
    }

    @Override
    @ExecutionTime
    public ContactDetails updateContactDetail(ContactDetails contactDetail) throws ContactDetailsException {
        Member member = memberRepository
                .findMemberByInvestmentId(contactDetail.getMemberInvestId())
                .orElseThrow(() -> new MemberException(
                        "Could not find a member: " + contactDetail.getMemberInvestId() + " to update contact for: "));

        ContactDetails updateContact = contactDetailsRepository
                .findMemberContact(member.getId(), contactDetail.getContactType())
                .orElseThrow(() -> new ContactDetailsException(
                        "Could not find a contacts to update with id: " + contactDetail.getContactType()));

        updateContact.setContact(contactDetail.getContact());

        return contactDetailsRepository.save(updateContact);
    }
}
