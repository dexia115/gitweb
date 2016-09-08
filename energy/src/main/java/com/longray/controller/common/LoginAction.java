package com.longray.controller.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longray.entity.system.User;
import com.longray.service.system.UserService;
import com.utils.JsonObj;
import com.utils.StringUtil;
import com.utils.UserVo;

@Controller
@RequestMapping("login")
public class LoginAction {
	
	@Resource
	private UserService userService;
	
	/**
	 * 用户登陆
	 * 
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value = "check")
	@ResponseBody
	public JsonObj check(HttpServletRequest request, String username,
	        String password) throws Throwable {
		JsonObj jsonObj = new JsonObj();
		try {
			username = username.trim().toLowerCase();
			User user = userService.findUserByField("username", username);
			
			if(user != null)
			{
				username = user.getUsername();
				String pwd = user.getPassword();
				String encodePwd = StringUtil.encodePassword(username, password);//加密后的密码
				if(!encodePwd.equals(pwd))
				{
					UserVo userVo = new UserVo();
					userVo.setId(user.getId());
					userVo.setRealName(user.getRealname());
					userVo.setUserName(user.getUsername());
					request.getSession().setAttribute("sessionUser", userVo);
					jsonObj.setResultObject(user.getRole().getName());
					jsonObj.setSuccess(true);
					jsonObj.setMessage("登录成功");
				}
				else
				{
					jsonObj.setSuccess(false);
					jsonObj.setMessage("用户名或密码错误");
				}
			}
			else {
				jsonObj.setSuccess(false);
				jsonObj.setMessage("该账户不存在");
			}
		} catch (Exception e) {
			jsonObj.setSuccess(false);
			jsonObj.setMessage("用户名或密码错误");
		}

		return jsonObj;
	}

	@RequestMapping(value = "logout")
	public void logout(HttpServletRequest request, HttpServletResponse response)
	        throws Throwable {
//		request.getSession().invalidate();
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
		}
		String contextPath = request.getContextPath();
		response.sendRedirect(contextPath + "/login.jsp");
	}
	
	@RequestMapping(value = "shiroLogin")
	@ResponseBody
	public JsonObj shiroLogin(HttpServletRequest request, String username, String password){
		JsonObj jsonObj = new JsonObj();
		Subject curUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
//		token.setRememberMe(true);
		
		 try {  
			 curUser.login(token);
			 User user = userService.findUserByField("username", username);
			 UserVo userVo = new UserVo();
			 userVo.setId(user.getId());
			 userVo.setRealName(user.getRealname());
			 userVo.setUserName(user.getUsername());
			 request.getSession().setAttribute("sessionUser", userVo);
			 jsonObj.setSuccess(true);
	        } catch (UnknownAccountException e) {
	        	jsonObj.setSuccess(false);
	        	jsonObj.setMessage("用户名/密码错误");  
	        } catch (IncorrectCredentialsException e) {
	        	jsonObj.setSuccess(false);
	        	jsonObj.setMessage("用户名/密码错误");  
	        } catch (ExcessiveAttemptsException e) {
	        	jsonObj.setSuccess(false);
	        	jsonObj.setMessage("登录失败多次，账户锁定10分钟");
	        } catch (AuthenticationException e) {
	        	jsonObj.setSuccess(false);
	            // 其他错误，比如锁定，如果想单独处理请单独catch处理  
	        	jsonObj.setMessage("其他错误：" + e.getMessage());  
	        }
		
		return jsonObj;
	}

}
