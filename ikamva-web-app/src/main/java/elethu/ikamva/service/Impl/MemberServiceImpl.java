package elethu.ikamva.service.Impl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.repositories.ContactDetailsRepository;
import elethu.ikamva.repositories.CorpCompanyRepository;
import elethu.ikamva.repositories.MemberRepository;
import elethu.ikamva.service.MemberService;
import elethu.ikamva.utils.IdentityNumberUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);
    private final MemberRepository memberRepository;
    private final CorpCompanyRepository corpCompanyRepository;
    private final ContactDetailsRepository contactDetailsRepository;

    @Override
    public Member saveNewMember(Member nemMember) {
        LOGGER.info("ServiceInvocation - MemberService.saveNewMember");
        if (!isMemberActive(nemMember.getInvestmentId())) {
            LOGGER.info("Member with a member investment id {} does not exist, will create", nemMember.getInvestmentId());
            var gender = IdentityNumberUtility.getMemberGender(nemMember.getIdentityNo().toString().substring(6, 10));
            nemMember.setCorpMember(corpCompanyRepository.findCorpCompany().get());
            nemMember.setDob(IdentityNumberUtility.getDateOfBirth(nemMember.getIdentityNo().toString().substring(0, 6)));
            nemMember.setGender(gender);
            nemMember.setCreatedDate(DateFormatter.returnLocalDateTime());
            if (!CollectionUtils.isEmpty(nemMember.getMemberContacts())) {
                nemMember.getMemberContacts().forEach(contact -> {
                    contact.setCreatedDate(DateFormatter.returnLocalDate());
                    contact.setMembers(nemMember);
                });
            }
            return memberRepository.save(nemMember);
        } else {
            return updateMember(nemMember);
        }
    }

    @Override
    public Member updateMember(Member updateMember) {
        LOGGER.info("ServiceInvocation - MemberService.updateMember");
        var member = memberRepository.findById(updateMember.getId())
                .orElseThrow(() -> new MemberException(String.format("Member with investment id: %s does not exist to update.", updateMember.getInvestmentId())));

        if (Objects.nonNull(member.getEndDate())) {
            updateMember.setGender(IdentityNumberUtility.getMemberGender(updateMember.getIdentityNo().toString().substring(6, 10)));
            updateMember.setDob(IdentityNumberUtility.getDateOfBirth(updateMember.getIdentityNo().toString().substring(0, 6)));

            updateMember.setEndDate(null);
            updateMember.setCorpMember(corpCompanyRepository.findCorpCompany().get());
            if (!CollectionUtils.isEmpty(updateMember.getMemberContacts())) {
                updateMember.getMemberContacts().forEach(contact -> {
                    contact.setCreatedDate(DateFormatter.returnLocalDate());
                    contact.setEndDate(null);
                    contact.setMembers(updateMember);
                });
            }
        }
        return memberRepository.save(updateMember);
    }

    @Override
    public void saveAllMembers(List<Member> members) {
        LOGGER.info("ServiceInvocation::saveAllMembers");
        members.forEach(this::saveNewMember);
    }

    @Override
    public Member deleteMember(String investmentId) {
        LOGGER.info("ServiceInvocation - MemberService::deleteMember");
        var deleteMember = memberRepository.findMemberByInvestmentId(investmentId)
                .orElseThrow(() -> new MemberException("Member:" + investmentId + " is already inactive or could not been found"));
        List<ContactDetails> memberContacts = deleteMember.getMemberContacts();
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
    public Member findMemberByInvestmentId(String investmentId) {
        LOGGER.info("ServiceInvocation - MemberService::findMemberByInvestmentId");
        return memberRepository.findMemberByInvestmentId(investmentId.toUpperCase())
                .orElseThrow(() -> new MemberException(String.format("Member with investment id: %s has not been found", investmentId)));
    }

    @Override
    public Member findMemberById(Long id) {
        LOGGER.info("ServiceInvocation - MemberService::findMemberById");
        return memberRepository.findMemberById(id)
                .orElseThrow(() -> new MemberException(String.format("Member with id: %d has not been found", id)));
    }

    @Override
    public List<Member> findAllMembers() {
        LOGGER.info("ServiceInvocation - MemberService::findAllMembers");
        List<Member> members = new LinkedList<>();
        memberRepository.findAll().iterator().forEachRemaining(members::add);

        if (members.isEmpty()) {
            throw new MemberException("There are no members found.");
        } else {
            return members.stream()
                    .filter(member -> member.getEndDate() == null)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public boolean isMemberActive(String memberInvestId) {
        LOGGER.info("ServiceInvocation - MemberService::isMemberActive");
        return memberRepository.findMemberByInvestmentId(memberInvestId).isPresent();
    }
}
