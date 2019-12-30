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

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final ContactDetailsRepository contactDetailsRepository;

    //public MemberServiceImpl(MemberRepository memberRepository) {
       // this.memberRepository = memberRepository;
   // }

    @Override
    public Member createNewMember(Member member) {
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
    public Member updateMember(Member member) {
        if(isMemberActive(member)){
            memberRepository.save(member);
        }
        return null;
    }

    @Override
    public void deleteMember(String investmentId) {
        Member deleteMember = memberRepository.findMemberByInvestmentId(investmentId).get();
        Date todayEndDate;
        //Date memberEndDate;
        //DateFormat dateFormat;
        if (isMemberActive(deleteMember)) {
            todayEndDate = new Date();
            //dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            //memberEndDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(todayEndDate.toString());
            deleteMember.setEndDate(todayEndDate);
            updateMember(deleteMember);
        }
        else
            throw new MemberException("Member: " + deleteMember.getInvestmentId() + " is already inactive or could not been found");
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
