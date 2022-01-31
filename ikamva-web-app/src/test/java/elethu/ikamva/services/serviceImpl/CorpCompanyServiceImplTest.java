package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.CorpCompany;
import elethu.ikamva.exception.CorpCompanyException;
import elethu.ikamva.repositories.CorpCompanyRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorpCompanyServiceImplTest {
    @Mock
    private CorpCompanyRepository companyRepository;

    @InjectMocks
    private CorpCompanyServiceImpl service;

    private CorpCompany corpCompany;

    @BeforeEach
    void setUp() {
        corpCompany = new CorpCompany(1L, "12345", "Elethu Ikamva", "2022-01-01", DateFormatter.returnLocalDateTime());
    }

    @Test
    void createCorpCompany() {
        //given
        given(companyRepository.save(any())).willReturn(corpCompany);

        //when
        CorpCompany company = service.saveCorpCompany(corpCompany);

        //then
        then(companyRepository).should(atLeastOnce()).save(any());
        assertThat(company).isNotNull();
    }

    @Test
    void updateCorpCompany() {
        //given
        given(companyRepository.findById(anyLong())).willReturn(Optional.of(corpCompany));
        given(companyRepository.save(any())).willReturn(corpCompany);

        //when
        CorpCompany corpCompany = service.updateCorpCompany(this.corpCompany);

        //then
        then(companyRepository).should().findById(anyLong());
        then(companyRepository).should().save(any());
        assertThat(corpCompany).isNotNull();
    }

    @Test
    void deleteCorpCompany() {
        //given
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(corpCompany));
        when(companyRepository.save(any())).thenReturn(corpCompany);

        //when
        CorpCompany deleteCorpCompany = service.deleteCorpCompany(1L);

        //then
        verify(companyRepository, atLeastOnce()).findById(anyLong());
        verify(companyRepository, atLeastOnce()).save(any());
        assertThat(deleteCorpCompany).isNotNull();
    }

    @Test
    void findAllCorpCompany() {
        //given
        List<CorpCompany> corpCompanies = new ArrayList<>();
        CorpCompany newCompany = new CorpCompany(2L, "54321", "Ikamva Elethu", "2022-01-15", DateFormatter.returnLocalDateTime());
        corpCompanies.add(corpCompany);
        corpCompanies.add(newCompany);
        given(companyRepository.findAll()).willReturn(corpCompanies);

        //when
        List<CorpCompany> allCorpCompanies = service.findAllCorpCompany();

        //then
        verify(companyRepository, atLeastOnce()).findAll();
        assertThat(allCorpCompanies).hasSize(2);
    }

    @Test
    void findCorpCompany() {
        //given
        when(companyRepository.findCorpCompany()).thenReturn(Optional.of(corpCompany));

        //when
        CorpCompany foundCorpCompany = service.findCorpCompany();

        //then
        then(companyRepository).should(atLeastOnce()).findCorpCompany();
        assertThat(foundCorpCompany).isNotNull();
    }

    @Test
    @DisplayName("Save An Existing Corporate Company Exception - Test")
    void saveCorpCompanyExistingExceptionTest(){
        //given
        when(companyRepository.findCorpCompanyByRegistrationNo(anyString())).thenReturn(Optional.ofNullable(corpCompany));

        //when
        assertThrows(CorpCompanyException.class, () -> service.saveCorpCompany(corpCompany));

        //then
        then(companyRepository).should().findCorpCompanyByRegistrationNo(any());
    }


    @Test
    @DisplayName("Update Not Found Corporate Company Exception - Test")
    void updateCorpCompanyNotExistingExceptionTest(){
        //when
        assertThrows(CorpCompanyException.class, () -> service.updateCorpCompany(corpCompany));

        //then
        then(companyRepository).should().findById(anyLong());
    }

    @Test
    @DisplayName("No Corporate Companies Found (Empty List) - Test")
    void findAllCorpCompanyEmptyListExceptionTest(){
        //given
        when(companyRepository.findAll()).thenReturn(Collections.emptyList());

        //when
        assertThrows(CorpCompanyException.class, () -> service.findAllCorpCompany());

        //then
        then(companyRepository).should().findAll();
        verifyNoMoreInteractions(companyRepository);
    }
}