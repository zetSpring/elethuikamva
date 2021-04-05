package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.domain.ContactType;
import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.ContactDetailsException;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.repositories.ContactDetailsRepository;
import elethu.ikamva.repositories.MemberRepository;
import elethu.ikamva.services.ContactDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //contactDetails.setContactType(ContactType.CELLPHONE);
        contactDetails.setMembers(member);
        contactDetails.setCreatedDate(dateFormatter.GetTodayDate());

        return contactDetailsRepository.save(contactDetails);
    }

    @Override
    public void deleteContactDetails(ContactDetails contactDetails) {
        if (contactDetailsRepository.isContactActive(contactDetails.getId())){
            contactDetails.setEndDate(dateFormatter.GetTodayDate());
            saveContactDetail(contactDetails);
        }
        else
            throw new ContactDetailsException("Contact: " + contactDetails.getContact() + " is already inactive or could not be found");
    }

    @Override
    public List<ContactDetails> getContactDetail(String investId) {
        System.out.println("Size of set: "+contactDetailsRepository.findAllContactsByMember(investId).size());
        List<ContactDetails> contactDetailsSet = contactDetailsRepository.findAllContactsByMember(investId.toUpperCase());

        if (contactDetailsSet.isEmpty()) {
            throw new ContactDetailsException("There are no contact numbers for customer id: " + investId );
        }

        return contactDetailsSet;
    }

    @Override
    public List<ContactDetails> findAllContactDetails() {

        List<ContactDetails> contactDetailsSet = new LinkedList<>();

        contactDetailsRepository.findAll().iterator().forEachRemaining(contactDetailsSet::add);

        return contactDetailsSet;
    }

    @Override
    public List<ContactDetails> findALlContactTypes(String type) {
        List<ContactDetails> contactDetailsList = new LinkedList<>();
        contactDetailsRepository.findContactDetailsByContactType(type).iterator().forEachRemaining(contactDetailsList::add);
        if(contactDetailsList.isEmpty()){
            throw new ContactDetailsException("There are no contact numbers for customer type: " + type );
        }

        return contactDetailsList;
    }

    @Override
    public ContactDetails updateContactDetail(ContactDetails contactDetail, String investId) throws ContactDetailsException {
        Optional<Member> memberContact = memberRepository.findMemberByInvestmentId(investId);
        Member member = memberContact
                .orElseThrow(() -> new MemberException("Couls not find a member: " + investId + " to update contact for: "));
        Optional<ContactDetails> contactDetailsOptional = contactDetailsRepository.findMemberContact(member.getId(), contactDetail.getContactType());
        ContactDetails updateContact = contactDetailsOptional.
                orElseThrow(() -> new ContactDetailsException("Could not find a contacts to update with id: "+ contactDetail.getContactType()));

        updateContact.setContact(contactDetail.getContact());

        return  contactDetailsRepository.save(updateContact);
    }

    public ContactType resolveContactType(String type){
        ContactType contactType;
        switch (type.toUpperCase()){
            case "CELLPHONE":
                contactType = ContactType.CELLPHONE;
            case "EMAIL":
                contactType = ContactType.EMAIL;
            default:
                contactType = ContactType.HOME_PHONE;

        }

        return contactType;
    }

}
