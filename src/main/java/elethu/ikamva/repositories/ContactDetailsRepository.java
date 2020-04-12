package elethu.ikamva.repositories;

import elethu.ikamva.domain.ContactDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface ContactDetailsRepository extends CrudRepository<ContactDetails, Long> {

    @Query("SELECT contact FROM ContactDetails contact WHERE contact.contact = ?1")
    Optional<ContactDetails> findContactDetailsByContact(String contact);
    @Query("SELECT contact FROM ContactDetails contact WHERE contact.members.investmentId = ?1")
    List<ContactDetails> findAllContactsByMember(@PathVariable String investId);
    @Query("SELECT contact FROM ContactDetails  contact WHERE UPPER(contact.contactType) = UPPER(?1)")
    List<ContactDetails> findContactDetailsByContactType(String type);
}


//0356016§§