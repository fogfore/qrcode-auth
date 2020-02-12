package com.fogfore.qrcodeauth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddressAuth {
    private Integer addrId;
    private String addrName;
    private String userAuth;
}
