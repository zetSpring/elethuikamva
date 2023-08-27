package elethu.ikamva.service.Impl;

import elethu.ikamva.aspects.ExecutionTime;
import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.repositories.ContactDetailsRepository;
import elethu.ikamva.repositories.CorpCompanyRepository;
import elethu.ikamva.repositories.MemberRepository;
import elethu.ikamva.service.MemberService;
import elethu.ikamva.utils.IdentityNumberUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final CorpCompanyRepository corpCompanyRepository;
    private final ContactDetailsRepository contactDetailsRepository;

    @Override
    @ExecutionTime
    public Member saveNewMember(Member nemMember) {
        log.info("ServiceInvocation - MemberService.saveNewMember");

        if (isMemberActive(nemMember.getInvestmentId())) {
            return this.updateMember(nemMember);
        }

        log.info("Member with a member investment id {} does not exist, will create", nemMember.getInvestmentId());
        var gender = IdentityNumberUtility.getMemberGender(
                nemMember.getIdentityNo().toString().substring(6, 10));
        var dob = IdentityNumberUtility.getDateOfBirth(
                nemMember.getIdentityNo().toString().substring(0, 6));
        nemMember.setCorpMember(corpCompanyRepository.findCorpCompany().orElse(null));
        nemMember.setDob(dob);
        nemMember.setGender(gender);
        nemMember.setCreatedDate(DateFormatter.returnLocalDateTime());

        if (!CollectionUtils.isEmpty(nemMember.getMemberContacts())) {
            nemMember.getMemberContacts().forEach(contact -> {
                contact.setCreatedDate(DateFormatter.returnLocalDate());
                contact.setMembers(nemMember);
            });
        }

        return memberRepository.save(nemMember);
    }

    @Override
    @ExecutionTime
    public Member updateMember(Member updateMember) {
        log.info("ServiceInvocation - MemberService.updateMember");
        var member = memberRepository
                .findMemberByInvestmentId(updateMember.getInvestmentId())
                .orElseThrow(() -> new MemberException(String.format(
                        "Member with investment id: %s does not exist to update.", updateMember.getInvestmentId())));

        updateMember.setId(member.getId());
        var gender = IdentityNumberUtility.getMemberGender(
                updateMember.getIdentityNo().toString().substring(6, 10));
        var dob = IdentityNumberUtility.getDateOfBirth(
                updateMember.getIdentityNo().toString().substring(0, 6));
        updateMember.setGender(gender);
        updateMember.setDob(dob);
        updateMember.setEndDate(null);
        updateMember.setCorpMember(corpCompanyRepository.findCorpCompany().get());
        if (!CollectionUtils.isEmpty(updateMember.getMemberContacts())) {
            updateMember.getMemberContacts().forEach(contact -> {
                contact.setCreatedDate(DateFormatter.returnLocalDate());
                contact.setEndDate(null);
                contact.setMembers(updateMember);
            });
        }

        return memberRepository.save(updateMember);
    }

    @Override
    @ExecutionTime
    public void saveAllMembers(List<Member> members) {
        log.info("ServiceInvocation::saveAllMembers");
        members.forEach(this::saveNewMember);
    }

    @Override
    @ExecutionTime
    public Member deleteMember(String investmentId) {
        log.info("ServiceInvocation - MemberService::deleteMember");
        var deleteMember = memberRepository
                .findMemberByInvestmentId(investmentId)
                .orElseThrow(() ->
                        new MemberException("Member:" + investmentId + " is already inactive or could not been found"));
        var memberContacts = deleteMember.getMemberContacts();
        deleteMember.setEndDate(DateFormatter.returnLocalDateTime());
        if (!CollectionUtils.isEmpty(memberContacts)) {
            memberContacts.forEach(contactDetails -> {
                contactDetails.setEndDate(DateFormatter.returnLocalDate());
                contactDetailsRepository.save(contactDetails);
            });
        }
        return memberRepository.save(deleteMember);
    }

    @Override
    @ExecutionTime
    public Member findMemberByInvestmentId(String investmentId) {
        log.info("ServiceInvocation - MemberService::findMemberByInvestmentId");
        return memberRepository
                .findMemberByInvestmentId(investmentId.toUpperCase())
                .orElseThrow(() -> new MemberException(
                        String.format("Member with investment id: %s has not been found", investmentId)));
    }

    @Override
    @ExecutionTime
    public Member findMemberById(Long id) {
        log.info("ServiceInvocation - MemberService::findMemberById");
        return memberRepository
                .findMemberById(id)
                .orElseThrow(() -> new MemberException(String.format("Member with id: %d has not been found", id)));
    }

    @Override
    @ExecutionTime
    public List<Member> findAllMembers() {
        log.info("ServiceInvocation - MemberService::findAllMembers");
        List<Member> members = new ArrayList<>();
        memberRepository.findAll().iterator().forEachRemaining(members::add);

        if (CollectionUtils.isEmpty(members)) {
            throw new MemberException("There are no members found.");
        }

        return members.stream()
                .filter(member -> Objects.isNull(member.getEndDate()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isMemberActive(String memberInvestId) {
        log.info("ServiceInvocation - MemberService::isMemberActive");
        return memberRepository.findMemberByInvestmentId(memberInvestId).isPresent();
    }
}
