package com.pyg.shop.service;

import com.pyg.manager.service.SellerService;
import com.pyg.pojo.TbSeller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by on 2018/9/25.
 * 自定义认证
 */
public class UserService implements UserDetailsService {

    //注入远程商家服务对象
    private SellerService sellerService;

    public SellerService getSellerService() {
        return sellerService;
    }

    //通过set方法把远程seller对象注入到自定义认证类
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //构建角色
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        //查询数据库动态密码
        //根据用户名查询数据库对象，查询出密码
        TbSeller seller = sellerService.findOne(username);

        //判断此用户是否存在
        if (seller != null) {
            //判断此商家是否处于审核通过状态
            if ("1".equals(seller.getStatus())) {
                //返回
                return new User(username, seller.getPassword(), authorities);
            }
        }

        return null;


    }
}
