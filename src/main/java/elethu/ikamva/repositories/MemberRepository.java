package elethu.ikamva.repositories;

import elethu.ikamva.domain.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface MemberRepository extends CrudRepository<Member, Long> {

    Optional<Member> findMemberByInvestmentId(String investmentId);
    @Query("SELECT mem FROM Member mem WHERE mem.endDate = null")
    Set<Member> findAllActiveMembers();
}
