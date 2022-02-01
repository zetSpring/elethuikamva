package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.ContactDetails;
import elethu.ikamva.domain.ContactType;
import elethu.ikamva.domain.Member;
import elethu.ikamva.exception.ContactDetailsException;
import elethu.ikamva.repositories.ContactDetailsRepository;
import elethu.ikamva.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactDetailsServiceImplTest {
    @Mock
    private ContactDetailsRepository contactDetailsRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ContactDetailsServiceImpl contactDetailsService;

    private String createdDate;
    private ContactDetails phoneContact;
    private ContactDetails emailContact;
    private List<ContactDetails> contactDetailsList;

    @BeforeEach
    void setUp() {
        LocalDate now = LocalDate.now();
        createdDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now);
        phoneContact = new ContactDetails(1L, "0722702356", ContactType.CELLPHONE, "KK012015", DateFormatter.returnLocalDate(createdDate));
        emailContact = new ContactDetails(2L, "tame.thedock@ikamva.com", ContactType.EMAIL, "KK012015", DateFormatter.returnLocalDate(createdDate));
        contactDetailsList = new ArrayList<>();
        contactDetailsList.add(phoneContact);
        contactDetailsList.add(emailContact);
    }

    @Test
    @DisplayName("Save Contact Details for Ikamva Member - Test")
    void saveContactDetailT() {
        //given
        Member member = new Member();

        when(contactDetailsRepository.save(any(ContactDetails.class))).thenReturn(phoneContact);
        when(memberRepository.findMemberByInvestmentId(anyString())).thenReturn(Optional.of(member));

        //when
        ContactDetails contactDetails = contactDetailsService.saveContactDetail(phoneContact);

        //then
        verify(contactDetailsRepository).save(any());
        verify(contactDetailsRepository).save(phoneContact);
        assertThat(contactDetails).isNotNull();
        assertThat(contactDetails.getContact()).isEqualTo("0722702356");
    }

    @Test
    @DisplayName("Delete Contacts a list of contacts from member Investment Id- Test")
    void deleteContactDetails() {
        //given
        when(contactDetailsRepository.findAllContactsByMemberInvestId(any())).thenReturn(contactDetailsList);
        when(contactDetailsRepository.save(any())).thenReturn(emailContact);

        //when
        List<ContactDetails> contactDetailsList1 = contactDetailsService.deleteContactDetails("KK012015");
        String contactEndDate = contactDetailsList1.stream()
                .map(ContactDetails::getEndDate)
                .filter(Objects::nonNull)
                .findFirst()
                .toString();

        //then
        verify(contactDetailsRepository).findAllContactsByMemberInvestId(any());
        verify(contactDetailsRepository, atLeast(2)).save(any());
        assertThat(contactDetailsList1).hasSize(2);
        assertThat(contactEndDate).isNotNull();
    }

    @Test
    @DisplayName("Delete Contacts a list of contacts from member Investment Id- Test")
    void deleteContactDetailsThrows() {
        //given
        when(contactDetailsRepository.findAllContactsByMemberInvestId("KK012015")).thenReturn(Collections.emptyList());

        //when
        assertThrows(ContactDetailsException.class, () -> contactDetailsService.deleteContactDetails("KK012015"));

        //then
        verify(contactDetailsRepository).findAllContactsByMemberInvestId(anyString());
    }

    @Test
    @DisplayName("Find Member Contact Details By Member Invest Id - Test")
    void findMemberContactByInvestId() {
        //given
        when(contactDetailsRepository.findAllContactsByMemberInvestId(anyString())).thenReturn(contactDetailsList);

        //when
        List<ContactDetails> contacts = contactDetailsService.findMemberContactByInvestId("KK012015");

        //then
        verify(contactDetailsRepository, atLeastOnce()).findAllContactsByMemberInvestId(anyString());
        assertThat(contacts).hasSize(2);
    }

    @Test
    @DisplayName("Delete Contact Detail By Id - Test")
    void deleteContactById() {
        //given
        given(contactDetailsRepository.findById(anyLong())).willReturn(Optional.of(phoneContact));
        given(contactDetailsRepository.save(any(ContactDetails.class))).willReturn(phoneContact);

        //when
        ContactDetails deleteContact = contactDetailsService.deleteContactById(phoneContact.getId());

        //then
        verify(contactDetailsRepository).findById(anyLong());
        assertThat(deleteContact).isNotNull();
        assertThat(deleteContact.getEndDate()).isNotNull();
        assertThat(deleteContact.getEndDate()).isEqualTo(DateFormatter.returnLocalDate());
    }

    @Test
    void findAllContactDetails() {
        //given
        when(contactDetailsRepository.findAll()).thenReturn(contactDetailsList);

        //when
        List<ContactDetails> allContactDetails = contactDetailsService.findAllContactDetails();

        //then
        then(contactDetailsRepository).should().findAll();
        assertThat(allContactDetails).hasSize(2);
    }

    @Test
    void findALlContactsByContactType() {
        //given
        when(contactDetailsRepository.findContactDetailsByContactType(anyString())).thenReturn(contactDetailsList);

        //when
        List<ContactDetails> contactsByEmail = contactDetailsService.findALlContactsByContactType("EMAIL");

        //then
        then(contactDetailsRepository).should(atLeastOnce()).findContactDetailsByContactType(anyString());
        assertThat(contactsByEmail).hasSize(2);
    }

    @Test
    void updateContactDetail() {
        //given
        Member member = new Member(1L, Long.parseLong("0804268523085"), "KK012015", "Emihle", "Yawa", DateFormatter.returnLocalDate(createdDate), "Female");
        ContactDetails updateEmailContact = new ContactDetails(2L, "emihle.yawa@ikamva.com", ContactType.EMAIL, "KK012015", DateFormatter.returnLocalDate(createdDate));
        when(memberRepository.findMemberByInvestmentId(anyString())).thenReturn(Optional.of(member));
        when(contactDetailsRepository.findMemberContact(anyLong(), any())).thenReturn(Optional.of(emailContact));
        when(contactDetailsRepository.save(any())).thenReturn(updateEmailContact);

        //when
        ContactDetails updateContactDetail = contactDetailsService.updateContactDetail(updateEmailContact);

        //then
        then(contactDetailsRepository).should(atLeastOnce()).findMemberContact(anyLong(), any());
        then(contactDetailsRepository).should(atLeastOnce()).save(any());
        then(memberRepository).should(atLeastOnce()).findMemberByInvestmentId(anyString());
        assertThat(updateContactDetail).isNotNull();
    }

    @Test
    @DisplayName("Delete Contacts a list of contacts from member Investment Id- Test")
    void findMemberContactByInvestIdThrows() {
        //given
        when(contactDetailsRepository.findAllContactsByMemberInvestId(anyString())).thenReturn(Collections.emptyList());

        //when
        assertThrows(ContactDetailsException.class, () -> contactDetailsService.findMemberContactByInvestId("KK012015"));

        //then
        verify(contactDetailsRepository).findAllContactsByMemberInvestId(anyString());

    }

    @Test
    @DisplayName("Delete Contacts a list of contacts from member Investment Id- Test")
    void findAllContactDetailsThrowsExceptionTest() {
        //given
        when(contactDetailsRepository.findAll()).thenReturn(Collections.emptyList());

        //when
        assertThrows(ContactDetailsException.class, () -> contactDetailsService.findAllContactDetails());

        //then
        verify(contactDetailsRepository).findAll();
    }

    @Test
    @DisplayName("Delete Contacts a list of contacts from member Investment Id- Test")
    void findALlContactsByContactTypeThrowsExceptionTest() {
        //given
        when(contactDetailsRepository.findContactDetailsByContactType(anyString())).thenReturn(Collections.emptyList());

        //when
        assertThrows(ContactDetailsException.class, () -> contactDetailsService.findALlContactsByContactType("EMAIL"));

        //then
        verify(contactDetailsRepository).findContactDetailsByContactType(anyString());
    }
}