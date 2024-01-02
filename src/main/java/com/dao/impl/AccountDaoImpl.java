package com.dao.impl;

import com.bean.AccountInfo;
import com.bean.UserInfo;
import com.dao.AccountDao;
import com.utils.Dbutils;
import com.utils.impl.ImageUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Cc
 * @Date 2023-11-27
 */
public class AccountDaoImpl implements AccountDao {
    final Dbutils util = new Dbutils();

    @Override
    public int addAccount(AccountInfo accountInfo) {
        String sql = "insert into Accountinfo " +
                " values(?,?,?,?,?,?,?,?)";
        Object[] params = {
                accountInfo.getUid(),
                accountInfo.getUsername(),
                accountInfo.getPassword(),
                new ImageUtils().stringTobyte(accountInfo.getAvatar()),
                accountInfo.getEmail(),
                accountInfo.getAphone(),
                accountInfo.getRegistrationtime(),
                accountInfo.getAstatus(),
        };

        try {
            return util.executeUpdate(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int SignUpAccount(AccountInfo accountInfo){
        String sql = "insert into Accountinfo(Uid,Username,Password,Email,Registrationtime,Astatus) " +
                " values(?,?,?,?,?,?)";
        Object[] params = {
                accountInfo.getUid(),
                accountInfo.getUsername(),
                accountInfo.getPassword(),
                accountInfo.getEmail(),
                accountInfo.getRegistrationtime(),
                accountInfo.getAstatus(),
        };

        try {
            return util.executeUpdate(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public AccountInfo SignInAccount(AccountInfo accountInfo){
        AccountInfo account = new AccountInfo();
        String sql = "select Accountinfo.*, UserInfo.Nickname from Accountinfo join UserInfo on Accountinfo.Uid = UserInfo.UserUid where Username = ? and Password = ?";
        Object[] params= {
                accountInfo.getUsername(),
                accountInfo.getPassword()
        };
        ResultSet rs;
        try {
            rs = util.executeQuery(sql,params);

            if (rs.next()){
                String uid = rs.getString(1);
                String username = rs.getString(2);
                String pwd = rs.getString(3);
                byte[] avatar = rs.getBytes(4);
                String email = rs.getString(5);
                String aphone = rs.getString(6);
                Timestamp rsgistrationtime = rs.getTimestamp(7);
                Integer astatus = rs.getInt(8);
                String nickname =rs.getString(9);

                UserInfo userInfo =new UserInfo();
                userInfo.setNickname(nickname);

                PackageAccountInfo(account, uid, username, pwd, avatar, email, aphone, rsgistrationtime, astatus);
                account.setUserinfo(userInfo);

                return account;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void PackageAccountInfo(AccountInfo account, String uid, String username, String pwd, byte[] avatar, String email, String aphone, Timestamp rsgistrationtime, Integer astatus) {
        account.setUid(uid);
        account.setUsername(username);
        account.setPassword(pwd);
        if (avatar != null)
            account.setAvatar(new ImageUtils().byteToString(avatar));
        account.setEmail(email);
        account.setAphone(aphone);
        account.setRegistrationtime(rsgistrationtime.toLocalDateTime());
        account.setAstatus(astatus);
    }

    @Override
    public int updateAccount(AccountInfo accountInfo) {
        String sql = "update Accountinfo set Password = ?,Avatar = ?,Email = ?,Aphone = ? where Uid = ?";
        Object[] params = {
                accountInfo.getPassword(),
                new ImageUtils().stringTobyte(accountInfo.getAvatar()),
                accountInfo.getEmail(),
                accountInfo.getAphone(),
                accountInfo.getUid()
        };
        try {
            return util.executeUpdate(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateAstatus(String uid, int statu) {
        String sql = "update Accountinfo set Astatus = ? where Uid = ?";
        Object[] params = {
                statu,
                uid
        };
        try {
            return util.executeUpdate(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AccountInfo> selectAccountByPage(AccountInfo accountInfo, Integer page, Integer limit) {
        List<AccountInfo> accounts = new ArrayList<>();

        StringBuilder sql = new StringBuilder("select * from ( ");
        sql.append("select Accountinfo.*,row_number() over(order by Uid) as rownum from Accountinfo");
        sql.append(" where 1=1");

        List<Object> params = new ArrayList<>();
        CheckParams(accountInfo, sql, params);

        sql.append(") as t");
        sql.append(" where rownum between ? and ?");

        params.add((page - 1) * limit + 1);
        params.add(page * limit);

        ResultSet rs;

        try {
            rs = util.executeQuery(sql, params);

            while (rs.next()) {
                String uid = rs.getString(1);
                String username = rs.getString(2);
                String pwd = rs.getString(3);
                byte[] avatar = rs.getBytes(4);
                String email = rs.getString(5);
                String aphone = rs.getString(6);
                Timestamp rsgistrationtime = rs.getTimestamp(7);
                Integer astatus = rs.getInt(8);

                AccountInfo account = new AccountInfo();

                PackageAccountInfo(account, uid, username, pwd, avatar, email, aphone, rsgistrationtime, astatus);

                accounts.add(account);
            }
            util.closeAll();
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long selectByPageCount(AccountInfo accountInfo) {
        Long count = 0L;

        // 第一：定义要操作数据库的SQL语句 - 动态多条件查询
        StringBuilder sql = new StringBuilder("select count(Uid) from Accountinfo where 1=1");

        List<Object> params = new ArrayList<Object>();

        CheckParams(accountInfo, sql, params);
        return getaLong(count, sql, params, util);
    }

    public void CheckParams(AccountInfo accountInfo, StringBuilder sql, List<Object> params) {
        if (accountInfo != null && accountInfo.getUsername() != null && !"".equals(accountInfo.getUsername())) {
            sql.append(" and Username like ?");

            params.add("%" + accountInfo.getUsername() + "%");
        }

        if (accountInfo != null && accountInfo.getAphone() != null && !"".equals(accountInfo.getAphone())) {
            sql.append(" and Aphone like ?");

            params.add("%" + accountInfo.getAphone() + "%");
        }

        if (accountInfo != null && accountInfo.getEmail() != null && !"".equals(accountInfo.getEmail())) {
            sql.append(" and Email like ?");

            params.add("%" + accountInfo.getEmail() + "%");
        }
    }

    static Long getaLong(Long count, StringBuilder sql, List<Object> params, Dbutils util) {
        ResultSet rs;

        try {
            rs = util.executeQuery(sql, params);

            if (rs.next()) {
                count = rs.getLong(1);
            }

            util.closeAll();
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}