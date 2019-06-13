package elethu.ikamva.repositories;

import elethu.ikamva.domain.ContactDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContactDetailsRepository extends CrudRepository<ContactDetails, Long> {

    Optional<ContactDetails> findContactDetailsByContact(String contact);
}


//0356016