package com.longray.service.system.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.longray.dao.system.RoleDao;
import com.longray.dao.system.UserDao;
import com.longray.entity.system.Role;
import com.longray.entity.system.User;
import com.longray.service.system.UserService;
import com.utils.Groups;
import com.utils.Page;
import com.utils.StringUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private RoleDao roleDao;
	@Resource
	private UserDao userDao;
	
	public List<Role> findRoleByGroups(Groups groups) {
		// TODO Auto-generated method stub
		return roleDao.findByGroups(groups);
	}

	public Page findRoleByPage(Groups groups, Page page) {
		return roleDao.findEntityPageByGroups(groups, page);
	}

	@Transactional
	public void saveRole(Role role) throws Exception {
		roleDao.saveOrUpdate(role);
	}

	@Transactional
	public void deleteRole(String[] ids) throws Exception {
		for(String id : ids){
			roleDao.delete(id);
		}
	}

	public Page findUserByPage(Groups groups, Page page) {
		// TODO Auto-generated method stub
		return userDao.findEntityPageByGroups(groups, page);
	}

	@Transactional
	public void saveUser(User user) throws Exception {
		userDao.save(user);
	}

	@Transactional
	public void updateUser(User user) throws Exception {
		userDao.update(user);
	}

	@Transactional
	public void deleteUser(String[] ids) throws Exception {
		for(String id : ids){
			userDao.delete(id);
		}
	}

	public User findUser(String id) {
		// TODO Auto-generated method stub
		return userDao.find(id);
	}

	public User findUserByField(String field, Object value) {
		// TODO Auto-generated method stub
		return userDao.findUniqueBy(field, value);
	}
	
	@Transactional
	@Override
	public void initData() {
		Role role = roleDao.findUniqueBy("name", "系统管理员");
		if(role == null){
			role = new Role();
			role.setName("系统管理员");
			role.setLevels(1);
			roleDao.save(role);
		}
		
		User user = userDao.findUniqueBy("username", "liuxiaodong");
		if(user == null){
			user = new User();
			user.setSex("男");
			user.setRole(role);
			user.setRealname("刘小东");
			user.setUsername("liuxiaodong");
			User tempUser = StringUtil.encodeShiroPassword(user.getUsername(), "123456");//加密后的密码
			user.setPassword(tempUser.getPassword());
			user.setSalts(tempUser.getSalts());
			userDao.save(user);
		}
		
	}


}
