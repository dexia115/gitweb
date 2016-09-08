package com.longray.controller.shiro;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.longray.dao.system.UserDao;
import com.longray.entity.system.User;

public class ShiroDbRealm extends AuthorizingRealm{
	
	@Resource
	private UserDao userDao;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		info.setRoles(roles);
//		info.setStringPermissions(stringPermissions);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		//获取基于用户名和密码的令牌 
	    //实际上这个authcToken是从LoginController里面
		//currentUser.login(token)传过来的 
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userDao.findUniqueBy("username", token.getUsername());
//		User user = null;
		
		if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if(Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号锁定
        }
        
		//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
		//此处无需比对,比对的逻辑Shiro会做,
		//我们只需返回一个和令牌相关的正确的验证信息 
//        SimpleAuthenticationInfo authenticationInfo =  new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
        return authenticationInfo;
	}
	
//	 @PostConstruct  
//	 public void initCredentialsMatcher() {
//		 //该句作用是重写shiro的密码验证，让shiro用我自己的验证  
//		 setCredentialsMatcher(new CustomCredentialsMatcher());  
//	 }
}
