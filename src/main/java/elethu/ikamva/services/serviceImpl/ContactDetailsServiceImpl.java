package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.exception.ContactDetailsException;
import elethu.ikamva.repositories.ContactDetailsRepository;
import elethu.ikamva.services.ContactDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


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
    public ContactDetails findContactDetail(String contact) {
        Optional<ContactDetails> contactDetailsOptional = contactDetailsRepository.findContactDetailsByContact(contact);

        if (!contactDetailsOptional.isPresent()) {
            throw new ContactDetailsException("Contact: " + contact + " could not be found");
        }

        return contactDetailsOptional.get();
    }

    @Override
    public Set<ContactDetails> findAllContactDetails() {

        Set<ContactDetails> contactDetailsSet = new HashSet<>();

        contactDetailsRepository.findAll().iterator().forEachRemaining(contactDetailsSet::add);

        return contactDetailsSet;
    }

    @Override
    public Boolean isContactActive(ContactDetails contactDetails) {

        return contactDetails.getId() != null && contactDetails.getEndDate() == null;

    }
}
