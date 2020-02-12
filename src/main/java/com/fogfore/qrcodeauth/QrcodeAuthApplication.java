package com.fogfore.qrcodeauth;

import com.fogfore.qrcodeauth.entity.UserAddress;
import com.fogfore.qrcodeauth.entity.UserAddressAuth;
import com.fogfore.qrcodeauth.mapper.UserAddressMapper;
import com.fogfore.qrcodeauth.service.UserService;
import com.fogfore.qrcodeauth.vo.UserDetailVo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class QrcodeAuthApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(QrcodeAuthApplication.class, args);
//		UserAddressMapper bean = context.getBean(UserAddressMapper.class);
////		List<UserAddressAuth> addrs = bean.listAddrs(1);
////		System.out.println(addrs);
//		UserService bean = context.getBean(UserService.class);
//		List<UserDetailVo> list = bean.fuzzyQuery("岳", 2);
//		System.out.println(list);
	}
}
