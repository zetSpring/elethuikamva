package elethu.ikamva.service;

import elethu.ikamva.domain.Member;

import java.util.List;

public interface MemberService {
    Member saveNewMember(Member member);
    void saveAllMembers(List<Member> members);
    Member updateMember(Member member);
    List<Member> findAllMembers();
    Member deleteMember(String investmentId);
    Member findMemberByInvestmentId(String investmentId);
    Member findMemberById(Long id);
    boolean isMemberActive(String memberInvestId);
}

