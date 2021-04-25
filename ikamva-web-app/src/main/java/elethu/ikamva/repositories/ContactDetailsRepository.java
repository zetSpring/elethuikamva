package elethu.ikamva.repositories;

import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.domain.ContactType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface ContactDetailsRepository extends CrudRepository<ContactDetails, Long> {

    @Query("SELECT contact FROM ContactDetails contact WHERE contact.endDate = NULL")
    List<ContactDetails> findAllMemberContactDetails();
    @Query("SELECT contact FROM ContactDetails contact WHERE contact.members.investmentId = ?1")
    List<ContactDetails> findAllContactsByMemberInvestId(@PathVariable String investId);
    @Query("SELECT contact FROM ContactDetails contact WHERE UPPER(contact.contactType) = UPPER(?1)")
    List<ContactDetails> findContactDetailsByContactType(String type);
    @Query("SELECT contact FROM ContactDetails contact WHERE contact.id = ?1 AND contact.endDate = NULL")
    Boolean isContactActive(Long id);
    @Query("SELECT contact FROM ContactDetails contact WHERE contact.members.id = ?1 AND contact.contactType = ?2 AND contact.endDate = NULL")
    Optional<ContactDetails> findMemberContact(Long id, ContactType contactType);
}


//0356016§§