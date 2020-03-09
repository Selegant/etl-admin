package com.selegant.common.response;

import lombok.Data;

@Data
public class UserInfo {


    /**
     * id : Mock.mock('@guid')
     * name : Mock.mock('@name')
     * username : admin
     * password :
     * avatar : https://gw.alipayobjects.com/zos/rmsportal/jZUIxmJycoymBprLOUbT.png
     * status : 1
     * telephone :
     * lastLoginIp : 27.154.74.117
     * lastLoginTime : 1534837621348
     * creatorId : admin
     * createTime : 1497160610259
     * deleted : 0
     * roleId : admin
     * lang : zh-CN
     * token : 4291d7da9005377ec9aec4a71ea837f
     */

    private String id;
    private String name;
    private String username;
    private String password;
    private String avatar;
    private int status;
    private String telephone;
    private String lastLoginIp;
    private long lastLoginTime;
    private String creatorId;
    private long createTime;
    private int deleted;
    private String roleId;
    private String lang;
    private String token;
    private RoleInfo role;

}
