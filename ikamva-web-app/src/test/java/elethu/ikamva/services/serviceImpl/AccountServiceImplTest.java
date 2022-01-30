package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Account;
import elethu.ikamva.domain.PrivateCompany;
import elethu.ikamva.exception.AccountException;
import elethu.ikamva.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        LocalDate now = LocalDate.now();
        String createdDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now);
        PrivateCompany privateCompany = new PrivateCompany();
        account = new Account(1L, 123456789L, "Business", DateFormatter.returnLocalDate(createdDate), privateCompany);
    }

    @Test
    void saveNewAccount() {
        //given
        given(accountRepository.save(any())).willReturn(account);

        //when
        Account saveNewAccount = accountService.saveNewAccount(this.account);

        //then
        then(accountRepository).should(atLeastOnce()).findAccountsByAccountNo(anyLong());
        then(accountRepository).should(atLeastOnce()).save(any());
        assertThat(saveNewAccount).isNotNull();
    }

    @Test
    void deleteAccount() {
        //given
        given(accountRepository.save(any())).willReturn(account);
        given(accountRepository.findAccountsByAccountNo(anyLong())).willReturn(Optional.of(account));

        //when
        Account deleteAccount = accountService.deleteAccount(123456789L);

        //then
        then(accountRepository).should(atLeastOnce()).findAccountsByAccountNo(anyLong());
        then(accountRepository).should(atLeastOnce()).save(any());
        verifyNoMoreInteractions(accountRepository);
        assertThat(deleteAccount).isNotNull();
    }

    @Test
    void findAccountByAccountNo() {
        //given
        when(accountRepository.findAccountsByAccountNo(anyLong())).thenReturn(Optional.of(account));

        //when
        Account accountNo = accountService.findAccountByAccountNo(123456789L);

        //then
        verify(accountRepository, atLeastOnce()).findAccountsByAccountNo(anyLong());
        verifyNoMoreInteractions(accountRepository);
        assertThat(accountNo).isNotNull();
    }

    @Test
    void findAccountById() {
        //given
        when(accountRepository.findAccountsById(anyLong())).thenReturn(Optional.of(account));

        //when
        Account accountById = accountService.findAccountById(1L);

        //then
        then(accountRepository).should(atLeastOnce()).findAccountsById(anyLong());
        verifyNoMoreInteractions(accountRepository);
        assertThat(accountById).isNotNull();
    }

    @Test
    void findAccountByCompany() {
        //given
        when(accountRepository.findAccountsByCompany(anyLong())).thenReturn(Optional.of(account));

        //when
        Account accountByCompany = accountService.findAccountByCompany(1L);

        //then
        then(accountRepository).should(atLeastOnce()).findAccountsByCompany(anyLong());
        verifyNoMoreInteractions(accountRepository);
        assertThat(accountByCompany).isNotNull();
    }

    @Test
    void findAllAccounts() {
        //given
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        when(accountRepository.findAll()).thenReturn(accountList);

        //when
        List<Account> allAccounts = accountService.findAllAccounts();

        //then
        then(accountRepository).should(atLeastOnce()).findAll();
        verifyNoMoreInteractions(accountRepository);
        assertThat(allAccounts).hasSize(1);
    }

    @Test
    @DisplayName("Save an Existing Account Exception - Test")
    void saveNewAccountAccountActiveThrowsExceptionTest() {
        //given
        when(accountRepository.findAccountsByAccountNo(anyLong())).thenReturn(Optional.ofNullable(account));

        //when
        assertThrows(AccountException.class, () -> accountService.saveNewAccount(account));

        //then
        verify(accountRepository).findAccountsByAccountNo(anyLong());
    }

    @Test
    @DisplayName("No Accounts Found Exception Exception - Test")
    void findAllAccountsEmptyListThrowsExceptionTest() {
        //given
        when(accountRepository.findAll()).thenReturn(Collections.emptyList());

        //when
        assertThrows(AccountException.class, () -> accountService.findAllAccounts());

        //then
        verify(accountRepository).findAll();
    }


}