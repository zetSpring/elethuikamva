package elethu.ikamva.service;

import elethu.ikamva.domain.Member;

import java.util.List;

public interface MemberService {
    List<Member> findAllMembers();
    Member findMemberById(Long id);
    Member updateMember(Member member);
    Member saveNewMember(Member member);
    Member deleteMember(String investmentId);
    void saveAllMembers(List<Member> members);
    boolean isMemberActive(String memberInvestId);
    Member findMemberByInvestmentId(String investmentId);
}

