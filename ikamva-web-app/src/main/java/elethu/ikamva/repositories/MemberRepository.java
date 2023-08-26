package elethu.ikamva.repositories;

import elethu.ikamva.domain.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface MemberRepository extends CrudRepository<Member, Long> {
    @Query("SELECT mem FROM Member mem WHERE mem.investmentId = UPPER(?1) AND mem.endDate = NULL")
    Optional<Member> findMemberByInvestmentId(String investmentId);
    @Query("SELECT mem FROM Member mem WHERE mem.id = ?1 AND mem.endDate = NULL")
    Optional<Member> findMemberById(Long id);
}
