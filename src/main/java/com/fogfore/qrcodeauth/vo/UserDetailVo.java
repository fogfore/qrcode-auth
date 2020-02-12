package com.fogfore.qrcodeauth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailVo {
    private Integer id;
    private String nickName;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String gender;
    private String realName;
    private String phone;
}
