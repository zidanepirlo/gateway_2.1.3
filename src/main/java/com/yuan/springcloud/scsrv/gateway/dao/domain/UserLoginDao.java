package com.yuan.springcloud.scsrv.gateway.dao.domain;

import com.yuan.springcloud.scsrv.gateway.dao.entity.UserLogin;
import org.apache.ibatis.annotations.Param;

public interface UserLoginDao {

    int insert(UserLogin record);
    int insertSelective(UserLogin record);
    UserLogin userLogin(@Param("user_id") String user_id, @Param("user_passwd") String user_passwd);
}