package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.exception.ContactDetailsException;
import elethu.ikamva.repositories.ContactDetailsRepository;
import elethu.ikamva.services.ContactDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ContactDetailsServiceImpl implements ContactDetailsService {

    @Autowired
    private final ContactDetailsRepository contactDetailsRepository;

    public ContactDetailsServiceImpl(ContactDetailsRepository contactDetailsRepository) {
        this.contactDetailsRepository = contactDetailsRepository;
    }

    @Override
    public void saveOrUpdateContactDetails(ContactDetails contactDetails) {

    }

    @Override
    public void deleteContactDetails(ContactDetails contactDetails) {
        if (isContactActive(contactDetails)) saveOrUpdateContactDetails(contactDetails);
        else
            throw new ContactDetailsException("Contact: " + contactDetails.getContact() + " is already inactive or could not be found");
    }

    @Override
    public List<ContactDetails> getContactDetail(String investId) {

        List<ContactDetails> contactDetailsSet = contactDetailsRepository.findAllContactsByMember(investId);

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
        List<ContactDetails> contactDetailsList = contactDetailsRepository.findContactDetailsByContactType(type);
        if(contactDetailsList.isEmpty()){
            throw new ContactDetailsException("There are no contact numbers for customer type: " + type );
        }

        return contactDetailsList;
    }

  /*  @Override
    public List<ContactDetails> findAllByContactsById(Long Id) {
        List<ContactDetails> contactDetails = new LinkedList<>();
        contactDetailsRepository.findAllById(Id).iterator().forEachRemaining(contactDetails::add);

        return contactDetails;
    }*/

    @Override
    public Boolean isContactActive(ContactDetails contactDetails) {

        return contactDetails.getId() != null && contactDetails.getEndDate() == null;

    }
}
