package elethu.ikamva.services;

import elethu.ikamva.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member createNewMember(Member member);
    Optional<Member> updateMember(Member member, String investId);
    List<Member> findAllMembers();
    void deleteMember(String investmentId);
    Member findMemberByInvestmentId(String investmentId);
    Member findMemberById(Long id);
    Boolean isMemberActive(Member member);
}

