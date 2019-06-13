package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.repositories.MemberRepository;
import elethu.ikamva.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void saveOrUpdateMember(Member member) {
        memberRepository.save(member);
    }


    @Override
    public void deleteMember(Member member) {
        if (isMemberActive(member))
            saveOrUpdateMember(member);
        else
            throw new MemberException("Member: " + member.getInvestmentId() + " is already inactive or could not been found");
    }

    @Override
    public Member findMemberByInvestmentId(String investmentId) {
        Optional<Member> memberOptional = memberRepository.findMemberByInvestmentId(investmentId);

        if (!memberOptional.isPresent()) {
            throw new MemberException("Member: " + investmentId + " has not been found");
        }

        return memberOptional.get();
    }

    @Override
    public Member findMemberById(Long id) {
        Optional<Member> member = memberRepository.findById(id);

        if (!member.isPresent()) {
            throw new MemberException("Member id: " + id + " has not been found");
        }

        return member.get();
    }

    @Override
    public Set<Member> findAllMembers() {
        Set<Member> members = new HashSet<>();

        memberRepository.findAll().iterator().forEachRemaining(members::add);

        return members;
    }

    @Override
    public Boolean isMemberActive(Member member) {
        return member.getId() != null && member.getEndDate() == null;
    }
}
