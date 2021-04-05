package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.repositories.ContactDetailsRepository;
import elethu.ikamva.repositories.MemberRepository;
import elethu.ikamva.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;
    private final ContactDetailsRepository contactDetailsRepository;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    public Member CreateNewMember(Member member) {
        Member savedMember = memberRepository.save(member);
        if(!CollectionUtils.isEmpty(member.getMemberContacts())){
            member.getMemberContacts().forEach(contactDetails -> {
                contactDetails.setMembers(savedMember);
                contactDetailsRepository.save(contactDetails);
            });
        }
        return savedMember;
    }

    @Override
    public Member UpdateMember(Member member) {
        Member newMember = memberRepository.findMemberById(member.getId());
        if(newMember == null){
            throw new MemberException("The Member invest id: " + member.getInvestmentId() + " cannot been found to update");
        }
        return memberRepository.save(member) ;
    }


    @Override
    public void DeleteMember(String investmentId) {
        Optional<Member> memberOptional = memberRepository.findMemberByInvestmentId(investmentId);
        Member deleteMember = memberOptional.orElseThrow(() -> new MemberException("Member:" + investmentId + " is already inactive or could not been found"));
        OffsetDateTime todayEndDate;
        todayEndDate = new Date().toInstant().atOffset(ZoneOffset.UTC);
        deleteMember.setEndDate(todayEndDate);
        memberRepository.save(deleteMember);
    }

    @Override
    public Member FindMemberByInvestmentId(String investmentId) {
        Optional<Member> memberOptional = memberRepository.findMemberByInvestmentId(investmentId.toUpperCase());
        return memberOptional.orElseThrow(() -> new MemberException("Member: " + investmentId + " has not been found"));
    }

    @Override
    public Member FindMemberById(Long id) {
        Optional<Member> member = memberRepository.findById(id);

        if (!member.isPresent()) {
            throw new MemberException("Member id: " + id + " has not been found");
        }

        return member.get();
    }

    @Override
    public List<Member> FindAllMembers() {
        List<Member> members = new LinkedList<>();
        memberRepository.findAll().iterator().forEachRemaining(members::add);

        return members;
    }

    @Override
    public Boolean IsMemberActive(Member member) {
        return member.getId() != null && member.getEndDate() == null;
    }
}
