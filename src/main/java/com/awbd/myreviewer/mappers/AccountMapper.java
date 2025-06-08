package com.awbd.myreviewer.mappers;

import com.awbd.myreviewer.domain.Account;
import com.awbd.myreviewer.dtos.AccountDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO toDto (Account account);
    Account toAccount (AccountDTO accountDTO);
}
