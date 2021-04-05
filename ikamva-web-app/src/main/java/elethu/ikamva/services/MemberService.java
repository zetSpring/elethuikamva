package elethu.ikamva.services;

import elethu.ikamva.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member CreateNewMember(Member member);
    Member UpdateMember(Member member);
    List<Member> FindAllMembers();
    void DeleteMember(String investmentId);
    Member FindMemberByInvestmentId(String investmentId);
    Member FindMemberById(Long id);
    Boolean IsMemberActive(Member member);
}

