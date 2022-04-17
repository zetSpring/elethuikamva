package elethu.ikamva.service.Impl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.domain.enums.ContactType;
import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.domain.Member;
import elethu.ikamva.domain.enums.Gender;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.repositories.ContactDetailsRepository;
import elethu.ikamva.repositories.CorpCompanyRepository;
import elethu.ikamva.repositories.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ContactDetailsRepository contactDetailsRepository;
    @Mock
    private CorpCompanyRepository corpCompanyRepository;

    @InjectMocks
    private MemberServiceImpl memberService;


    @Test
    @DisplayName("Create a Member with No Contact Details - Test")
    void saveNewMemberWithContactDetailsTest() {
        //given
        Member member = new Member(1L, Long.parseLong("1006145427081"), "EY012015", "Emihle", "Yawa", DateFormatter.returnLocalDate("2008-04-26"), Gender.FEMALE);
        List<ContactDetails> memberContacts = new ArrayList<>();
        memberContacts.add(new ContactDetails(1L, "0712345678", ContactType.CELLPHONE, "EY012015",  DateFormatter.returnLocalDate("2008-04-26")));
        memberContacts.add(new ContactDetails(2L, "emihle.yawa@ikamva.com", ContactType.EMAIL, "EY012015",  DateFormatter.returnLocalDate("2008-04-26")));
        member.setMemberContacts(memberContacts);
        when(corpCompanyRepository.findCorpCompany()).thenReturn(Optional.of(new CorpCompany(1L, "12345", "Elethu Ikamva", "2022-01-01", DateFormatter.returnLocalDateTime())));
        when(memberRepository.save(any())).thenReturn(member);

        //when
        Member saveNewMember = memberService.saveNewMember(member);

        //then
        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberArgumentCaptor.capture());
        List<Member> capturedMember = memberArgumentCaptor.getAllValues();
        assertThat(capturedMember.get(0)).isEqualTo(member);
        assertThat(saveNewMember).isNotNull();
    }

    @Test
    @DisplayName("Create a Member with No Contact Details - Test")
    void saveExistingMemberWithContactsTest() {
        //given
        Member member = new Member(1L, Long.parseLong("1006145427081"), "EY012015", "Emihle", "Yawa", DateFormatter.returnLocalDate("2008-04-26"), Gender.FEMALE);
        List<ContactDetails> memberContacts = new ArrayList<>();
        memberContacts.add(new ContactDetails(1L, "0712345678", ContactType.CELLPHONE, "EY012015",  DateFormatter.returnLocalDate("2008-04-26")));
        memberContacts.add(new ContactDetails(2L, "emihle.yawa@ikamva.com", ContactType.EMAIL, "EY012015",  DateFormatter.returnLocalDate("2008-04-26")));
        member.setMemberContacts(memberContacts);

        Member existingMember = new Member(1L, Long.parseLong("1006145427081"), "EY012015", "Emihle", "Yawa", DateFormatter.returnLocalDate("2008-04-26"), Gender.FEMALE);
        existingMember.setEndDate(DateFormatter.returnLocalDateTime());
        List<ContactDetails> existingMemberContacts = new ArrayList<>();
        existingMemberContacts.add(new ContactDetails(1L, "0712345678", ContactType.CELLPHONE, "EY012015",  DateFormatter.returnLocalDate("2008-04-26")));
        existingMemberContacts.add(new ContactDetails(2L, "emihle.yawa@ikamva.com", ContactType.EMAIL, "EY012015",  DateFormatter.returnLocalDate("2008-04-26")));
        existingMember.setMemberContacts(existingMemberContacts);

        when(memberRepository.findMemberByInvestmentId(anyString())).thenReturn(Optional.of(existingMember));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(existingMember));
        when(corpCompanyRepository.findCorpCompany()).thenReturn(Optional.of(new CorpCompany(1L, "12345", "Elethu Ikamva", "2022-01-01", DateFormatter.returnLocalDateTime())));
        when(memberRepository.save(any())).thenReturn(member);

        //when
        Member saveNewMember = memberService.saveNewMember(member);

        //then
        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository, atLeast(1)).save(memberArgumentCaptor.capture());
        List<Member> capturedMember = memberArgumentCaptor.getAllValues();
        assertThat(capturedMember.get(0)).isEqualTo(member);
        assertThat(saveNewMember).isNotNull();
        assertThat(saveNewMember.getEndDate()).isNull();
    }



    @Test
    void updateMember() {
        //given
        Member member = new Member(1L, Long.parseLong("1007238523085"), "EY012015", "Emihle", "Yawa", DateFormatter.returnLocalDate("2008-04-26"), Gender.FEMALE);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        //when
        Member updateMember = memberService.updateMember(member);

        //THEN
        then(memberRepository).should(atLeastOnce()).findById(anyLong());
        then(memberRepository).should(atLeastOnce()).save(any(Member.class));
        assertThat(updateMember).isNotNull();
    }

    @Test
    void saveAllMembers() {
        //given
        Member member =  new Member(1L, Long.parseLong("8507235427081"), "TM012015", "Thabo", "Mbheki", DateFormatter.returnLocalDate("2008-04-26"), Gender.MALE);
        List<ContactDetails> memberContacts = new ArrayList<>();
        memberContacts.add(new ContactDetails(1L, "0712345678", ContactType.CELLPHONE, "TM012015",  DateFormatter.returnLocalDate("2008-04-26")));
        memberContacts.add(new ContactDetails(1L, "thabo.mbeki@ikamva.com", ContactType.EMAIL, "TM012015",  DateFormatter.returnLocalDate("2008-04-26")));
        member.setMemberContacts(memberContacts);

        Member member1 =  new Member(2L, Long.parseLong("85072354207081"), "NN012015", "Nomvula", "Nonkonyane", DateFormatter.returnLocalDate("2021-04-26"), Gender.FEMALE);
        List<ContactDetails> member1Contacts = new ArrayList<>();
        member1Contacts.add(new ContactDetails(1L, "07287654321", ContactType.CELLPHONE, "NN012015",  DateFormatter.returnLocalDate("2021-04-26")));
        member1Contacts.add(new ContactDetails(1L, "nomvula.nonkonyane@ikamva.com", ContactType.EMAIL, "TM012015",  DateFormatter.returnLocalDate("2008-04-26")));
        member1.setMemberContacts(member1Contacts);

        List<Member> members = new ArrayList<>();
        members.add(member);
        members.add(member1);

        CorpCompany corpCompany = new CorpCompany(1L, "12345", "Ikamva", "2022-01-01", DateFormatter.returnLocalDateTime());

        //when(memberRepository.saveAll(anyCollection())).thenReturn(members);
        when(corpCompanyRepository.findCorpCompany()).thenReturn(Optional.of(corpCompany));

        //when
        memberService.saveAllMembers(members);

        //then
        verify(memberRepository).save(members.get(0));
        //assertThat()
    }

    @Test
    void deleteMember() {
        //given
        Member member =  new Member(1L, Long.parseLong("9010105433086"), "TM012015", "Thabo", "Mbheki", DateFormatter.returnLocalDate("2008-04-26"), Gender.MALE);
        List<ContactDetails> memberContacts = new ArrayList<>();
        memberContacts.add(new ContactDetails(1L, "0712345678", ContactType.CELLPHONE, "TM012015",  DateFormatter.returnLocalDate("2008-04-26")));
        memberContacts.add(new ContactDetails(1L, "thabo.mbeki@ikamva.com", ContactType.EMAIL, "TM012015",  DateFormatter.returnLocalDate("2008-04-26")));
        member.setMemberContacts(memberContacts);
        when(memberRepository.findMemberByInvestmentId(anyString())).thenReturn(Optional.of(member));
        when(memberRepository.save(any())).thenReturn(member);

        //when
        Member deleteMember = memberService.deleteMember("TM012015");

        //then
        verify(memberRepository).save(any());
        assertThat(deleteMember).isNotNull();
        assertThat(deleteMember.getEndDate()).isNotNull();
    }

    @Test
    @DisplayName("Find a Member By Investment Id - Test")
    void findMemberByInvestmentId() {
        //Given
        Member member = new Member(1L, Long.parseLong("0804268523085"), "EY012015", "Emihle", "Yawa", DateFormatter.returnLocalDate("2008-04-26"), Gender.FEMALE);
        when(memberRepository.findMemberByInvestmentId("EY012015")).thenReturn(Optional.of(member));

        //when
        Member foundMember = memberService.findMemberByInvestmentId("EY012015");

        //then
        verify(memberRepository).findMemberByInvestmentId("EY012015");
        assertThat(foundMember).isNotNull();
    }

    @Test
    @DisplayName("Find a Member By Id - Test")
    void FindMemberByIdTest() {
        //given
        Member testMember = new Member(2L, Long.parseLong("0804268523085"), "EY012015", "Emihle", "Yawa", DateFormatter.returnLocalDate("2008-04-26"), Gender.FEMALE);
        when(memberRepository.findMemberById(2L)).thenReturn(Optional.of(testMember));

        //when
        Member foundMember = memberService.findMemberById(2L);

        //then
        verify(memberRepository).findMemberById(2L);
        assertThat(foundMember).isNotNull();
    }

    @Test
    @DisplayName("Find All Members - Test")
    void findAllMembers() {
        //given
        Member member = new Member(1L, Long.parseLong("0804268523085"), "EY012015", "Emihle", "Yawa", DateFormatter.returnLocalDate("2008-04-26"), Gender.FEMALE);
        Member member1 = new Member(2L, Long.parseLong("8507224992080"), "ZY012015", "Zuko", "Yawa", DateFormatter.returnLocalDate("2022-01-11"), Gender.MALE);
        List<Member> members = new ArrayList<>();
        members.add(member);
        members.add(member1);
        when(memberRepository.findAll()).thenReturn(members);

        //when
        List<Member> foundMembers = memberService.findAllMembers();

        //then
        assertThat(foundMembers).hasSize(2);
        verify(memberRepository).findAll();
    }

    @Test
    @DisplayName("Find All Member Empty List Throws Exception - Test")
    void findAllMembersThrowsException(){
        //given
        List<Member> emptyMembers = new ArrayList<>();
        when(memberRepository.findAll()).thenReturn(emptyMembers);

        //when then
        assertThrows(MemberException.class, () -> memberService.findAllMembers());
    }
}