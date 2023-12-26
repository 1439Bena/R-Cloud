package com.dao;

import com.bean.AccountInfo;

import java.util.List;

/**
 * @Author Cc
 * @Date 2023-11-27
 */
public interface AccountDao {
    /**
     * 注册账号
     *
     * @param accountInfo
     * @return
     */
    int addAccount(AccountInfo accountInfo);

    /**
     * 修改账号信息
     * @param accountInfo
     * @return
     */
    int updateAccount(AccountInfo accountInfo);

    /**
     * 修改账号状态
     * @param uid
     * @param statu
     * @return
     */
    int updateAstatus(String uid, int statu);

    /**
     * 动态条件 + 分页查询账号信息
     * @param accountInfo
     * @param page
     * @param limit
     * @return
     */
    List<AccountInfo> selectAccountByPage(AccountInfo accountInfo, Integer page, Integer limit);

    /**
     * 动态条件 + 分页查询 - 总记录数
     * @param accountInfo
     * @return
     */
    Long selectByPageCount(AccountInfo accountInfo);
}
