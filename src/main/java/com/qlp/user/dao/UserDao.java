package com.qlp.user.dao;

import com.qlp.commons.orm.QlpJpaRepository;
import com.qlp.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by qlp on 14-4-2.
 */
public interface UserDao extends QlpJpaRepository<User, String> {

    /**
     * 根据登录名和密码查询用户
     *
     * @param loginName
     * @param password
     * @return
     */
    public User findByLoginNameAndPassword(String loginName, String password);

    @Query("select u.id as uid,u.name as uname,u.sex as sex,"
            + "u.email as email,u.state as ustate,r.name as rname "
            + "from User u left outer join u.roles r where u.name like ?1 and r.name like ?2 and u.type = ?3")
    public Page<Object[]> findByNameAndType(String userName, String roleName, String type, Pageable pageable);
}
