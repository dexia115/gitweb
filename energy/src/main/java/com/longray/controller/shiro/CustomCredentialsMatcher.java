package com.longray.controller.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

//import com.question.utils.StringUtil;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher{
	
	 @Override  
     public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {  
         UsernamePasswordToken token = (UsernamePasswordToken) authcToken;  
 
         String encodePwd = "";
//         String encodePwd = StringUtil.encodePassword(token.getUsername(), String.valueOf(token.getPassword()));//加密后的密码
         
         Object tokenCredentials = encodePwd;  
         Object accountCredentials = getCredentials(info);  
         //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false  
         return equals(tokenCredentials, accountCredentials);  
     }

}
