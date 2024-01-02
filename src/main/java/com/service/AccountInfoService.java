package com.service;

import com.bean.AccountInfo;
import com.dao.AccountDao;
import com.dao.impl.AccountDaoImpl;

import java.util.List;

/**
 * @Author Cc
 * @Date 2023-11-27
 */
public class AccountInfoService {
    final AccountDao dao=new AccountDaoImpl();

    public List<AccountInfo> getAccountInfo(AccountInfo accountInfo, Integer page, Integer limit){
        return dao.selectAccountByPage(accountInfo,page,limit);
    }
    public Long getAccountInfoCount(AccountInfo accountInfo){
        return dao.selectByPageCount(accountInfo);
    }
    public int SignAccount(AccountInfo accountInfo){
        return dao.SignUpAccount(accountInfo);
    }
    public AccountInfo SignInAccount(AccountInfo accountInfo){
        return dao.SignInAccount(accountInfo);
    }
    public int UpdateStatu(String uid, int statu){
        return dao.updateAstatus(uid,statu);
    }
    public int UpdateAccountInfo(AccountInfo accountInfo){
        return dao.updateAccount(accountInfo);
    }
    public int AddAccount(AccountInfo accountInfo){
        return dao.addAccount(accountInfo);
    }
}
