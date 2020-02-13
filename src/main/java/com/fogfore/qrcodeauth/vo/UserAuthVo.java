package com.fogfore.qrcodeauth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthVo {
    private Integer id;
    private String realName;
    private String avatarUrl;
    private String addrAuth;
}
