package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.domain.Member;
import elethu.ikamva.repositories.ContactDetailsRepository;
import elethu.ikamva.repositories.CorpCompanyRepository;
import elethu.ikamva.repositories.MemberRepository;
import elethu.ikamva.services.CorpCompanyService;
import elethu.ikamva.services.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ContactDetailsRepository contactDetailsRepository;
    @Mock
    private CorpCompanyRepository corpCompanyRepository;
    private MemberService underTest;
    //@Mock
   // private CorpCompanyService corpCompanyService;

    @BeforeEach
    void setUp() {
        underTest = new MemberServiceImpl(memberRepository, corpCompanyRepository, contactDetailsRepository);

        //CorpCompany corpCompany = new CorpCompany("1232323", "TestCorp", "2021-12-07", DateFormatter.returnLocalDateTime());
        //corpCompanyService.createCorpCompany(corpCompany);
    }

    @Test
    void canCreateNewMember() {
        //given
        Member testMember = new Member(Long.parseLong("0804268523085"), "EY012015", "Emihle", "Yawa", DateFormatter.returnLocalDate("2008-04-26"), "Female");

        //when
        underTest.CreateNewMember(testMember);

        //then
        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);

        verify(memberRepository).save(memberArgumentCaptor.capture());
        List<Member> capturedMember = memberArgumentCaptor.getAllValues();
        assertThat(capturedMember).isEqualTo(testMember);
    }

    @Test
    @Disabled
    void updateMember() {
    }

    @Test
    @Disabled
    void saveAllMembers() {
    }

    @Test
    @Disabled
    void deleteMember() {
    }

    @Test
    @Disabled
    void findMemberByInvestmentId() {
    }

    @Test
    @Disabled
    void findMemberById() {
    }

    @Test
    @Disabled
    void findAllMembers() {
    }

    @Test
    @Disabled
    void isMemberActive() {
    }
}