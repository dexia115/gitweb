package com.longray.service.system;

import java.util.List;

import com.longray.entity.system.Role;
import com.longray.entity.system.User;
import com.utils.Groups;
import com.utils.Page;

public interface UserService {
	
	List<Role> findRoleByGroups(Groups groups);
	Page findRoleByPage(Groups groups, Page page);
	void saveRole(Role role) throws Exception;
	void deleteRole(String[] ids) throws Exception;
	
	
	User findUser(String id);
	User findUserByField(String field, Object value);
	Page findUserByPage(Groups groups, Page page);
	void saveUser(User user) throws Exception;
	void updateUser(User user) throws Exception;
	void deleteUser(String[] ids) throws Exception;
	
	void initData();

}
