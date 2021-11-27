package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.repositories.ContactDetailsRepository;
import elethu.ikamva.repositories.CorpCompanyRepository;
import elethu.ikamva.repositories.MemberRepository;
import elethu.ikamva.services.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);
    private final MemberRepository memberRepository;
    private final CorpCompanyRepository corpCompanyRepository;
    private final ContactDetailsRepository contactDetailsRepository;
    private final DateFormatter dateFormatter;

    @Override
    public Member CreateNewMember(Member member) {
        if(IsMemberActive(member.getInvestmentId())){
            LOGGER.info("Member with a member investment id {} does not exist, will create", member.getInvestmentId());
            member.setCorpMember(corpCompanyRepository.findCorpCompany().get());
            OffsetDateTime todayEndDate;
            todayEndDate = new Date().toInstant().atOffset(ZoneOffset.UTC);
            member.setDob(dateFormatter.returnLocalDate(member.getDob().toString()));
            member.setCreatedDate(todayEndDate);
            if(!CollectionUtils.isEmpty(member.getMemberContacts())){
                member.getMemberContacts().forEach(contactDetails -> {
                    contactDetails.setCreatedDate(dateFormatter.returnLocalDate());
                    contactDetails.setMembers(member);
                });
            }
            return memberRepository.save(member);
        }else
            throw new MemberException("Member with investment id: " + member.getInvestmentId() + " already exists");
    }

    @Override
    public Member UpdateMember(Member member, String memberInvestId) {
        Optional<Member> memberOptional = memberRepository.findMemberByInvestmentId(memberInvestId);
        Member updateMember = memberOptional.orElseThrow(() -> new MemberException("The Member invest id: " + memberInvestId + " cannot been found to update"));

        //to redo and find better implementation
        if(member.getCorpMember() != null){
            updateMember.setCorpMember(member.getCorpMember());
        }
        if(member.getDob() != null){
            updateMember.setDob(member.getDob());
        }
        if(member.getFirstName() != null) {
            updateMember.setFirstName(member.getFirstName());
        }

        if(member.getLastName() != null) {
            updateMember.setLastName(member.getLastName());
        }

        if(member.getGender() != null) {
            updateMember.setGender(member.getGender());
        }

        if(member.getIdentityNo() != null) {
            updateMember.setIdentityNo(member.getIdentityNo());
        }

        if(member.getInvestmentId() != null) {
            updateMember.setInvestmentId(member.getInvestmentId());
        }


        return memberRepository.save(updateMember) ;
    }

    @Override
    public void SaveAllMembers(List<Member> members) {
        LOGGER.info("ServiceInvocation:SaveAllMembers ");
        AtomicInteger savedCount = new AtomicInteger(0);
        members.forEach(member -> {
            if(IsMemberActive(member.getInvestmentId())){
                LOGGER.info("Saving member with investment id: {}", member.getInvestmentId());
                member.setCorpMember(corpCompanyRepository.findCorpCompany().get());
                member.setCreatedDate(new Date().toInstant().atOffset(ZoneOffset.UTC));
                member.setDob(dateFormatter.returnLocalDate(member.getDob().toString()));
                if(!CollectionUtils.isEmpty(member.getMemberContacts())){
                    member.getMemberContacts().forEach(contacts -> {
                        contacts.setCreatedDate(dateFormatter.returnLocalDate());
                        contacts.setMembers(member);
                    });
                }
                memberRepository.save(member);
                savedCount.getAndIncrement();
            }else
                LOGGER.error("Member with investment id: {} already exists", member.getInvestmentId());
        });
    }

    @Override
    public Member DeleteMember(String investmentId) {
        Optional<Member> memberOptional = memberRepository.findMemberByInvestmentId(investmentId);
        Member deleteMember = memberOptional.orElseThrow(() -> new MemberException("Member:" + investmentId + " is already inactive or could not been found"));
        List<ContactDetails> memberContacts = contactDetailsRepository.findAllContactsByMemberInvestId(investmentId);
        OffsetDateTime todayEndDate;
        todayEndDate = new Date().toInstant().atOffset(ZoneOffset.UTC);
        deleteMember.setEndDate(todayEndDate);
        if(!CollectionUtils.isEmpty(memberContacts)){
            memberContacts.forEach(contactDetails -> {
                contactDetails.setEndDate(dateFormatter.returnLocalDate());
                contactDetailsRepository.save(contactDetails);
            });
        }
        return memberRepository.save(deleteMember);
    }

    @Override
    public Member FindMemberByInvestmentId(String investmentId) {
        Optional<Member> memberOptional = memberRepository.findMemberByInvestmentId(investmentId.toUpperCase());
        return memberOptional.orElseThrow(() -> new MemberException("Member: " + investmentId + " has not been found"));
    }

    @Override
    public Member FindMemberById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.orElseThrow(() -> new MemberException("Member id: " + id + " has not been found"));
    }

    @Override
    public List<Member> FindAllMembers() {
        List<Member> members = new LinkedList<>();
        memberRepository.findAllActiveMembers().iterator().forEachRemaining(members::add);

        return members;
    }

    @Override
    public boolean IsMemberActive(String memberInvestId) {
        return memberRepository.findMemberByInvestmentId(memberInvestId).isEmpty();
    }
}
