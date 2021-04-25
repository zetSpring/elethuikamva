package elethu.ikamva.services;

import elethu.ikamva.domain.Member;

import java.util.List;

public interface MemberService {
    Member CreateNewMember(Member member);
    Member UpdateMember(Member member, String memberInvestId);
    List<Member> FindAllMembers();
    Member DeleteMember(String investmentId);
    Member FindMemberByInvestmentId(String investmentId);
    Member FindMemberById(Long id);
    Boolean IsMemberActive(String memberInvestId);
}

