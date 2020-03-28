package com.fogfore.qrcodeauth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 岳英豪
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitorInfoVo {
    private Integer id;
    private String nickName;
    private String realName;
    private String phone;
    private String avatarUrl;
    private String auth;
}
