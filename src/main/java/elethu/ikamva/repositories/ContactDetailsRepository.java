package elethu.ikamva.repositories;

import elethu.ikamva.domain.ContactDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.Set;

public interface ContactDetailsRepository extends CrudRepository<ContactDetails, Long> {

    Optional<ContactDetails> findContactDetailsByContact(String contact);
    Set<ContactDetails> findAllContactsByMember(@PathVariable Long memberId);
    Set<ContactDetails> findContactDetailsByContactType(String type, Long member);
}


//0356016§§