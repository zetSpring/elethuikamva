package elethu.ikamva.services.serviceImpl;

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

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final ContactDetailsRepository contactDetailsRepository;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    //public MemberServiceImpl(MemberRepository memberRepository) {
       // this.memberRepository = memberRepository;
   // }

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
    public Optional<Member> UpdateMember(Member member, String investId) {
        if(IsMemberActive(member)){
           return memberRepository.findMemberByInvestmentId(investId)
                   .map(newMember ->{
                       newMember.setFirstname(member.getFirstname());
                       newMember.setLastname(member.getLastname());
                       newMember.setGender(member.getGender());
                       newMember.setDob(member.getDob());
                       newMember.setMemberIdentityNo(member.getMemberIdentityNo());
                       //newMember.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
                       newMember.setCorpMember(newMember.getCorpMember());

                       return memberRepository.save(newMember);
                   });
        }
        throw new MemberException("Member: " + investId + " has not been found to update");
    }


    @Override
    public void DeleteMember(String investmentId) {
        Member deleteMember = memberRepository.findMemberByInvestmentId(investmentId).get();
        OffsetDateTime todayEndDate;
        if (IsMemberActive(deleteMember)) {
            todayEndDate = new Date().toInstant().atOffset(ZoneOffset.UTC);
            deleteMember.setEndDate(todayEndDate);
            UpdateMember(deleteMember, investmentId);
        }
        else
            throw new MemberException("Member: " + deleteMember.getInvestmentId() + " is already inactive or could not been found");
    }

    @Override
    public Member FindMemberByInvestmentId(String investmentId) {
        Optional<Member> memberOptional = memberRepository.findMemberByInvestmentId(investmentId);

        if (!memberOptional.isPresent()) {
            throw new MemberException("Member: " + investmentId + " has not been found");
        }

        return memberOptional.get();
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
