package elethu.ikamva.repositories;

import elethu.ikamva.domain.ContactDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.Set;

public interface ContactDetailsRepository extends CrudRepository<ContactDetails, Long> {

    @Query("SELECT contact FROM ContactDetails contact WHERE contact.contact = ?1")
    Optional<ContactDetails> findContactDetailsByContact(String contact);
//    @Query("SELECT contact FROM ContactDetails contact WHERE contact.id = ?1")
//    Set<ContactDetails> findAllContactsByMember(@PathVariable Long memberId);
//    Set<ContactDetails> findContactDetailsByContactType(String type);
}


//0356016§§