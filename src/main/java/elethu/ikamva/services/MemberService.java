package elethu.ikamva.services;

import elethu.ikamva.domain.Member;

import java.util.Set;

public interface MemberService {
    Member createNewMember(Member member);
    Member updateMember(Member member);

    void deleteMember(String investmentId);

    Member findMemberByInvestmentId(String investmentId);

    Member findMemberById(Long id);

    Set<Member> findAllMembers();

    Boolean isMemberActive(Member member);
}

