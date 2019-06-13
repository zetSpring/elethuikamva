package elethu.ikamva.services;

import elethu.ikamva.domain.Member;

import java.util.Set;

public interface MemberService {
    void saveOrUpdateMember(Member member);

    void deleteMember(Member member);

    Member findMemberByInvestmentId(String investmentId);

    Member findMemberById(Long id);

    Set<Member> findAllMembers();

    Boolean isMemberActive(Member member);
}

