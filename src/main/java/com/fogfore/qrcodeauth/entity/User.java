package com.fogfore.qrcodeauth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("uid")
    private String uid;
    @TableField("openid")
    private String openid;
    @TableField("nick_name")
    private String nickName;
    @TableField("session_key")
    private String sessionKey;
    @TableField("city")
    private String city;
    @TableField("province")
    private String province;
    @TableField("country")
    private String country;
    @TableField("avatar_url")
    private String avatarUrl;
    @TableField("gender")
    private String gender;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField("last_visit_time")
    private Date lastVisitTime;
}
