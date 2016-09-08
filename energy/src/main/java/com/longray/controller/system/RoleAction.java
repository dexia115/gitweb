package com.longray.controller.system;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.longray.entity.system.Role;
import com.longray.service.system.UserService;
import com.utils.Group;
import com.utils.Groups;
import com.utils.JsonObj;
import com.utils.Page;
import com.utils.PropertyFilter.MatchType;

@Controller
@RequestMapping("system")
public class RoleAction {
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "role", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {

		return "system/role";
	}
	
	@RequestMapping(value = "loadRole")
	@ResponseBody
	public Page loadRole(Page page) {
		Groups groups = new Groups();
		List<Group> searchGroups = page.getSearchGroups();
		if(searchGroups.size()>0){
			for (Group group : searchGroups) {
			  group.setRelation(MatchType.AND);
			  groups.Add(group);
			}
		}

		userService.findRoleByPage(groups, page);

		return page;
	}
	
	@RequestMapping(value = "findRoles")
	@ResponseBody
	public List<Role> findRoles() throws Exception{
		Groups groups = new Groups();
		return userService.findRoleByGroups(groups);
	}
	
	@RequestMapping(value = "saveRole")
	@ResponseBody
	public JsonObj saveRole(Role role) throws Exception{
		userService.saveRole(role);
		
		return JsonObj.newSuccessJsonObj("保存成功");
	}
	
	@RequestMapping(value = "deleteRole")
	@ResponseBody
	public JsonObj deleteRole(String id) throws Exception
	{
		String[] ids = id.split(",");
		userService.deleteRole(ids);
		return JsonObj.newSuccessJsonObj("删除成功");
	}

}
