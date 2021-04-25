package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.ContactDetailsException;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.repositories.ContactDetailsRepository;
import elethu.ikamva.repositories.MemberRepository;
import elethu.ikamva.services.ContactDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.*;

@Service
public class ContactDetailsServiceImpl implements ContactDetailsService {

    private final ContactDetailsRepository contactDetailsRepository;
    private final MemberRepository memberRepository;
    private final DateFormatter dateFormatter;

    @Autowired
    public ContactDetailsServiceImpl(ContactDetailsRepository contactDetailsRepository, MemberRepository memberRepository, DateFormatter dateFormatter) {
        this.contactDetailsRepository = contactDetailsRepository;
        this.memberRepository = memberRepository;
        this.dateFormatter = dateFormatter;
    }

    @Override
    public ContactDetails saveContactDetail(ContactDetails contactDetails) {
        Member member = memberRepository.findMemberByInvestmentId(contactDetails.getMemberInvestId().toUpperCase())
                .orElseThrow(() -> new ContactDetailsException("Contact: " + contactDetails.getContact() + " is already inactive or could not be found"));
        contactDetails.setMembers(member);
        contactDetails.setCreatedDate(dateFormatter.returnLocalDate());

        return contactDetailsRepository.save(contactDetails);
    }

    @Override
    public List<ContactDetails> deleteContactDetails(String investId) {
        List<ContactDetails> contactDetails = contactDetailsRepository.findAllContactsByMemberInvestId(investId);

        if(!CollectionUtils.isEmpty(contactDetails)){
            contactDetails.forEach(memberContacts -> {
                memberContacts.setEndDate(dateFormatter.returnLocalDate());
                contactDetailsRepository.save(memberContacts);
            });
        }
        else
            throw new ContactDetailsException("Contact: " + investId + " is already inactive or could not be found");

        return contactDetails;
    }

    @Override
    public List<ContactDetails> findMemberContactByInvestId(String investId) {
        List<ContactDetails> memberContacts = contactDetailsRepository.findAllContactsByMemberInvestId(investId.toUpperCase());

        if (memberContacts.isEmpty()) {
            throw new ContactDetailsException("There are no contact numbers for customer id: " + investId );
        }

        return memberContacts;
    }

    @Override
    public List<ContactDetails> findAllContactDetails() {
        List<ContactDetails> contactDetailsList = new LinkedList<>();
        contactDetailsRepository.findAllMemberContactDetails().iterator().forEachRemaining(contactDetailsList::add);

        return contactDetailsList;
    }

    @Override
    public List<ContactDetails> findALlContactsByContactType(String contactType) {
        List<ContactDetails> contactDetailsList = new LinkedList<>();
        contactDetailsRepository.findContactDetailsByContactType(contactType).iterator().forEachRemaining(contactDetailsList::add);
        if(contactDetailsList.isEmpty()){
            throw new ContactDetailsException("There are no contact details for contact type: " + contactType );
        }
        return contactDetailsList;
    }

    @Override
    public ContactDetails updateContactDetail(ContactDetails contactDetail, String investId) throws ContactDetailsException {
        Optional<Member> memberContact = memberRepository.findMemberByInvestmentId(investId);
        Member member = memberContact.orElseThrow(() -> new MemberException("Couls not find a member: " + investId + " to update contact for: "));
        Optional<ContactDetails> contactDetailsOptional = contactDetailsRepository.findMemberContact(member.getId(), contactDetail.getContactType());
        ContactDetails updateContact = contactDetailsOptional.
                orElseThrow(() -> new ContactDetailsException("Could not find a contacts to update with id: "+ contactDetail.getContactType()));

        updateContact.setContact(contactDetail.getContact());

        return  contactDetailsRepository.save(updateContact);
    }
}
