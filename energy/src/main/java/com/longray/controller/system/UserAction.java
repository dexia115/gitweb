package com.longray.controller.system;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.longray.entity.system.User;
import com.longray.service.system.UserService;
import com.utils.Group;
import com.utils.Groups;
import com.utils.JsonObj;
import com.utils.Page;
import com.utils.PropertyFilter.MatchType;
import com.utils.StringUtil;

@Controller
@RequestMapping("system")
public class UserAction {
	
	@Resource
	private UserService userService;

	@RequestMapping(value = "user", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {

		return "system/user";
	}
	
	@RequestMapping(value = "loadUser")
	@ResponseBody
	public Page loadUser(Page page) {
		Groups groups = new Groups();
		List<Group> searchGroups = page.getSearchGroups();
		if(searchGroups.size()>0){
			for (Group group : searchGroups) {
			  group.setRelation(MatchType.AND);
			  groups.Add(group);
			}
		}

		userService.findUserByPage(groups, page);

		return page;
	}
	
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test(HttpServletRequest request){
		userService.initData();
		return "welcome";
	}
	
	@RequestMapping(value = "checkUser")
	@ResponseBody
	public Boolean checkUser(HttpServletRequest request)
	{
		String username = request.getParameter("username");
		String userId = request.getParameter("userId");
		Boolean valid = true;
		User user = userService.findUserByField("username", username);
		if (user != null)
		{
			if (user.getId().equals(userId)) {//修改时
				return true;
			}
			valid = false;
		}

		return valid;
	}
	
	@RequestMapping(value = "saveUser")
	@ResponseBody
	public JsonObj saveUser(User user) throws Exception{
		String username = user.getUsername().trim().toLowerCase();
		if("".equals(user.getId()))
		{
			if(!"".equals(user.getPassword()))
			{
				User tempUser = StringUtil.encodeShiroPassword(username, user.getPassword().trim());
//				String password = StringUtil.encodePassword(username, user.getPassword().trim());//加密后的密码
				user.setPassword(tempUser.getPassword());
				user.setSalts(tempUser.getSalts());
			}
			user.setUsername(username);
			userService.saveUser(user);
		}
		else
		{
			User temp = userService.findUser(user.getId());
			temp.setRole(user.getRole());
			temp.setTelphone(user.getTelphone());
			temp.setRealname(user.getRealname());
			temp.setSex(user.getSex());
			temp.setEmail(user.getEmail());
			if(!"".equals(user.getPassword()))
			{
				User tempUser = StringUtil.encodeShiroPassword(username, user.getPassword().trim());
//				String password = StringUtil.encodePassword(username, user.getPassword().trim());//加密后的密码
				temp.setPassword(tempUser.getPassword());
				temp.setSalts(tempUser.getSalts());
			}
			userService.updateUser(temp);
		}
		
		return JsonObj.newSuccessJsonObj("保存成功");
	}
	
	@RequestMapping(value = "deleteUser")
	@ResponseBody
	public JsonObj deleteUser(String id) throws Exception
	{
		String[] ids = id.split(",");
		userService.deleteUser(ids);
		return JsonObj.newSuccessJsonObj("删除成功");
	}
	
}
