package elethu.ikamva.repositories;

import elethu.ikamva.domain.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {

    Optional<Member> findMemberByInvestmentId(String investmentId);
}
