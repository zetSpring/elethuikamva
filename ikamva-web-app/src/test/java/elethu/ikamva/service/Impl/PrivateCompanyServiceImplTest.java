package elethu.ikamva.service.Impl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Account;
import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.domain.PrivateCompany;
import elethu.ikamva.exception.PrivateCompanyException;
import elethu.ikamva.repositories.PrivateCompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrivateCompanyServiceImplTest {
    @Mock
    private PrivateCompanyRepository privateCompanyRepository;

    @InjectMocks
    private PrivateCompanyServiceImpl privateCompanyService;

    private PrivateCompany privateCompany;

    @BeforeEach
    void setUp() {
        CorpCompany corpCompany = new CorpCompany();
        Account account = new Account();
        privateCompany = new PrivateCompany(1L, "Elethu Ikamva PTY (LTD)", "12345", DateFormatter.returnLocalDate(), DateFormatter.returnLocalDateTime(), corpCompany, account);
    }

    @Test
    void savePrivateCompany() {
        //given
        given(privateCompanyRepository.save(any(PrivateCompany.class))).willReturn(privateCompany);

        //when
        PrivateCompany savePrivateCompany = privateCompanyService.savePrivateCompany(this.privateCompany);

        //then
        verify(privateCompanyRepository).save(any());
        assertThat(savePrivateCompany).isNotNull();
    }

    @Test
    void deletePrivateCompany() {
        //given
        when(privateCompanyRepository.findPrivateCompaniesById(anyLong())).thenReturn(Optional.ofNullable(privateCompany));
        when(privateCompanyRepository.save(any())).thenReturn(privateCompany);

        //when
        PrivateCompany deletePrivateCompany = privateCompanyService.deletePrivateCompany(1L);

        //then
        then(privateCompanyRepository).should(atLeastOnce()).findPrivateCompaniesById(anyLong());
        then(privateCompanyRepository).should(atLeastOnce()).save(any());
        assertThat(deletePrivateCompany).isNotNull();
    }

    @Test
    void findPrivateCompanyById() {
        //given
        given(privateCompanyRepository.findPrivateCompaniesById(anyLong())).willReturn(Optional.ofNullable(privateCompany));

        //when
        PrivateCompany deletePrivateCompany = privateCompanyService.findPrivateCompanyById(1L);

        //then
        verify(privateCompanyRepository, atLeastOnce()).findPrivateCompaniesById(anyLong());
        assertThat(deletePrivateCompany).isNotNull();
    }

    @Test
    void findPrivateCompanyByRegistration() {
        //given
        when(privateCompanyRepository.findPrivateCompanyByRegistrationNo(anyString())).thenReturn(Optional.of(privateCompany));

        //when
        PrivateCompany privateCompanyByRegistration = privateCompanyService.findPrivateCompanyByRegistration("12345");

        //then
        then(privateCompanyRepository).should(atLeastOnce()).findPrivateCompanyByRegistrationNo(anyString());
        assertThat(privateCompanyByRegistration).isNotNull();
    }

    @Test
    void findAllPrivateCompany() {
        //given
        List<PrivateCompany> privateCompanyList = new ArrayList<>();
        privateCompanyList.add(privateCompany);
        given(privateCompanyRepository.findAll()).willReturn(privateCompanyList);

        //when
        List<PrivateCompany> allPrivateCompany = privateCompanyService.findAllPrivateCompany();

        //then
        verify(privateCompanyRepository, atLeastOnce()).findAll();
        verifyNoMoreInteractions(privateCompanyRepository);
        assertThat(allPrivateCompany).isNotNull();
        assertThat(allPrivateCompany).hasSize(1);
    }

    @Test
    @DisplayName("Saving An Existing Company Exception - Test")
    void saveExistingPrivateCompanyThrowsException() {
        when(privateCompanyRepository.findActivePrivateCompany(anyString())).thenReturn(Optional.ofNullable(privateCompany));

        assertThrows(PrivateCompanyException.class, () -> privateCompanyService.savePrivateCompany(privateCompany));

        then(privateCompanyRepository).should().findActivePrivateCompany(anyString());
    }

    @Test
    @DisplayName("Finding No Private Companies Expection (Empty List) - Test")
    void findNoPrivateCompanyThrowsException() {
        when(privateCompanyRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(PrivateCompanyException.class, () -> privateCompanyService.findAllPrivateCompany());

        then(privateCompanyRepository).should().findAll();
    }
}