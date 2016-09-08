package com.longray.dao;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import com.longray.entity.BaseEntity;
import com.utils.Group;
import com.utils.Groups;
import com.utils.Page;
import com.utils.PropertyFilter.MatchType;
import com.utils.ReflectionUtils;
import com.utils.SearchAnnotation;
import com.utils.StringUtil;

public class HibernateDao<T> {
	
	private SessionFactory sessionFactory;
	
	private Class<T> entityClass;

	@Resource
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public HibernateDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}
	
	private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
	
	/**
	 * HQL的groups查询
	 * @param groups
	 * @param page
	 * @return
	 */
	public Page findEntityPageByGroups(Groups groups, Page page){
		
		List<Object> values = new LinkedList<Object>();
		
		String hql = createHqlByGroupsAll("",groups, values);
		
		page = findPage(page, hql, values);
		
		return page;
	}
	
	/**
	 * SQL的groups查询
	 * @param groups
	 * @param page
	 * @param sql
	 * @return
	 */
	public Page findPageByGroups(Groups groups, Page page, String sql){
		
		List<Object> values = new LinkedList<Object>();
		sql = createSqlByGroupsAll(sql,groups, values);
		page = findPageBySql(page, sql, values);
		List list = page.getItems();
		List<Object> items = new ArrayList<Object>();
		String fields = sql.substring(sql.indexOf("select")+6,sql.lastIndexOf("from"));
		String[] names = fields.split(",");
		for(Object object : list)
		{
			Map map = new HashMap();
			boolean bb = object instanceof Object[];
			if(bb)
			{
				Object[] objs = (Object[]) object;
				for(int i=0;i<objs.length;i++)
				{
					String name = names[i].trim();
					if(name.contains(" as "))
					{
						name = name.substring(name.indexOf(" as ")+4).trim();
					}
					if(objs[i] != null)
					{
						map.put(name, objs[i].toString());
					}
					else
					{
						map.put(name, "");
					}
				}
			}
			else
			{
				String name = names[0].trim();
				if(name.contains(" as "))
				{
					name = name.substring(name.indexOf(" as ")+4).trim();
				}
				if(object != null)
				{
					map.put(name, object.toString());
				}
				else
				{
					map.put(name, "");
				}
			}
					
			items.add(map);
		}
		page.setItems(items);
		
		return page;
	}
	
	public Page findPageBySql(final Page page, final String sql, final List<Object> parameter) {

		Query q = createSqlQuery(sql, parameter);
		Integer totalCount  = countSqlResult(sql, parameter);
		page.setTotalCount(totalCount);
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		int first = ((pageNo - 1) * pageSize) + 1;
		q.setFirstResult(first - 1);
		q.setMaxResults(pageSize);
		page.setItems(q.list());
		return page;
	}
	
	public List<T> findByGroups(Groups groups){
		List<T> result = new ArrayList<T>();
		// 有条件组内容
		List<Object> values = new LinkedList<Object>();
		
		String hql = createHqlByGroupsAll("",groups, values);
		
		result = findList(hql,values);

		return result;
	}
	/**
	 * 根据条件返回查询总行数
	 */
	public long findTotalCountByGroups(Groups groups){

		List<Object> values = new LinkedList<Object>();
		
		String hql = createHqlByGroupsAll("",groups, values);
		
		long totalCount = countResult(hql, values);		
		
		return totalCount;
	}
	public T findUniqueBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);		
		List<T> lists =  createCriteria(criterion).list();		
		if (!lists.isEmpty())
		{
			return lists.get(0);
		}
		
		return null;			
	}
	
	public List<T> findList(final String hql, final List<Object> parameter) {
		return createQuery(hql, parameter).list();
	}
	
	public Page findPage(final Page page, final String hql, final List<Object> parameter) {

		Query q = createQuery(hql, parameter);
		Long totalCount  = countResult(hql, parameter);
		page.setTotalCount(totalCount.intValue());
		
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		int first = ((pageNo - 1) * pageSize) + 1;
		q.setFirstResult(first - 1);
		q.setMaxResults(pageSize);
		page.setItems(q.list());
		return page;
	}
	
	protected Long countResult(final String hql, final List<Object> parameter) {
		String countHql = prepareCount(hql);
		try {
			Long count = findUnique(countHql, parameter);
			
			return count;
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, sql is:"
					+ countHql, e);
		}
	}
	
	protected <X> X findUnique(final String hql, final List<Object> parameter) {
		return (X) createQuery(hql, parameter).uniqueResult();
	}
	
	public T find(String id){
		return (T) getSession().get(entityClass, id);
	}
	
	public void save(final T entity) {		
		getSession().save(entity);
	}
	
	public void saveOrUpdate(final T entity) {		
		getSession().saveOrUpdate(entity);
	}

	public void update(final T entity) {
		getSession().update(entity);
	}
	
	public void delete(String id){
		getSession().delete(find(id));
	}
	
	/**
	 * 执行HQL进行批量修改/删除操作.
	 * @param hql
	 * @param list
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final List<Object> parameter) {
		return createQuery(hql, parameter).executeUpdate();
	}
	

	protected Integer countSqlResult(final String sql, final List<Object> parameter) {
		String countSql = prepareCount(sql);
		try {
			BigInteger count = findUniqueBySql(countSql, parameter);
			
			return count.intValue();
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, sql is:"
					+ countSql, e);
		}
	}
	protected <X> X findUniqueBySql(final String sql, final List<Object> parameter) {
		return (X) createSqlQuery(sql, parameter).uniqueResult();
	}
	private String prepareCount(String query) {
		// select子句与order by子句会影响count查询,进行简单的排除.
		query = "from " + StringUtils.substringAfter(query, "from");
		query = StringUtils.substringBefore(query, "order by");

		String count = "select count(*) " + query;
		return count;
	}
	
	private Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	private Query createQuery(final String hql, final List<Object> parameter) {
		Query query = getSession().createQuery(hql);
		if (parameter != null) {
			for (int i = 0; i < parameter.size(); i++) {
				query.setParameter(i, parameter.get(i));
			}
		}
		return query;
	}
	
	private Query createSqlQuery(final String sql, final List<Object> parameter) {
		Query query = getSession().createSQLQuery(sql);
		if (parameter != null) {
			for (int i = 0; i < parameter.size(); i++) {
				query.setParameter(i, parameter.get(i));
			}
		}
		return query;
	}
	
	private String createSqlByGroupsAll(String sql,Groups groups, List<Object> values) {
		// from段
		StringBuffer fromBuffer = new StringBuffer(" ");
		// where 段
		StringBuffer whereBufferQian = new StringBuffer(" where 1=1 ");
		StringBuffer whereBufferHou = new StringBuffer("");
		
		// 存取相同前缀
		List<String> Alias1 = new LinkedList<String>();
		fromBuffer.append(sql);		
	
		try {
			appendGroups2(groups, fromBuffer, whereBufferQian, Alias1, whereBufferHou, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sql = fromBuffer.toString()+whereBufferQian.toString()+whereBufferHou.toString();
		
		if(groups.getGroupby() != null && !"".equals(groups.getGroupby()))
		{
			sql += " group by "+groups.getGroupby();
		}
		
		String temp = "";
		if(groups.getOrderbys()!=null && groups.getOrderbys().length>0) {
			StringBuffer sBuffer = new StringBuffer();
			for (int i = 0; i < groups.getOrderbys().length; i++) {// groups接受orderby数组进行多条件排序
				if(i==0){
					sBuffer.append(" order by ");
				}
				// order by 别名处理
				temp = groups.getOrderbys()[i];

				if (temp.isEmpty())
					continue;
				
				if (groups.getOrders()[i]){
					sBuffer.append(temp+" asc,");
				}else{
					sBuffer.append(temp+" desc,");
				}					
			}
			sql += sBuffer.deleteCharAt(sBuffer.length()-1).toString();
		}else if (null != groups.getOrderby()	&& !"".equals(groups.getOrderby().trim())) {
			// 处理order by的别名
			temp = groups.getOrderby();

			if (groups.isOrder()){
				sql += " order by "+temp+" asc";
			}else{
				sql += " order by "+temp+" desc";
			}				
		}
		
		return sql;
	}
	
	private void appendGroups2(Groups groups, StringBuffer fromBuffer,
			StringBuffer whereBufferQian, List<String> Alias1,
			StringBuffer whereBufferHou, List<Object> values){
		if (groups.getGroupList() == null) {
			System.out.println("groups的GroupList不能为空！");
		} else {
			if (groups.getChildGroups2() != null && !groups.getChildGroups2().isEmpty() || groups.getChildrelation()!=null) {
				for (Group group : groups.getGroupList()) {
					appendGroup2(group, fromBuffer, whereBufferQian,Alias1, whereBufferHou, values);
				}
				for (Groups tGroup : groups.getChildGroups2()) {
					if (tGroup.getChildrelation() == MatchType.AND) {
						whereBufferHou.append(" and ( ");
						appendGroups2(tGroup, fromBuffer, whereBufferQian,Alias1, whereBufferHou, values);
						whereBufferHou.append(" )");
					} else if (tGroup.getChildrelation() == MatchType.OR) {
						whereBufferHou.append(" or ( ");
						appendGroups2(tGroup, fromBuffer, whereBufferQian,Alias1, whereBufferHou, values);
						whereBufferHou.append(" )");
					}
				}
			}else{
				StringBuffer whereAnd = new StringBuffer();
				StringBuffer whereOr = new StringBuffer();
				List<Object> tempList = new ArrayList<Object>();
				for (Group group : groups.getGroupList()) {
					if(group.getRelation().equals(MatchType.AND)){
						appendGroup2(group, fromBuffer, whereBufferQian, Alias1, whereBufferHou, values);
					}else{
						if(whereOr.toString().equals("")){
							whereOr.append(" and ( ");
						}
						tempList.add(group.getPropertyValue1());
						values.remove(group.getPropertyValue1());
					}
					
				}
				if(!whereOr.toString().equals("")){
					whereOr.append(" ) ");
				}
				for(Object temp : tempList){
					values.add(temp);
				}
				whereBufferHou.append(whereAnd).append(whereOr);
			}
		}
	}
	
	private void appendGroup2(Group group, StringBuffer fromBuffer,
			StringBuffer whereBufferQian,List<String> Alias1,
			StringBuffer whereBufferHou, List<Object> values) {
		
		String propertyName = group.getPropertyName();
		
		boolean isOver = buildCase2(group, whereBufferHou, propertyName);
		
		//没有到最后（可能是 NULL/ NOT NULL）
		if (!isOver) {
			String matchCase = "";
			if(whereBufferHou.toString().trim().length()>0){
				matchCase = whereBufferHou.toString().trim().substring(
						whereBufferHou.toString().trim().length()-1, whereBufferHou.toString().trim().length());
			}	
			if (group.getMatchType() == MatchType.NULL || group.getMatchType() == MatchType.NOTNULL) {
				whereBufferHou.append(" ");
				if (group.getRelation() == MatchType.AND) {
					try {
						if (!matchCase.equals("(")) {
							whereBufferHou.append(" and ");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						if (!matchCase.equals("(")) {
							whereBufferHou.append(" or ");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
						
				switch (group.getMatchType()) {
				case NOTIN:
					whereBufferHou.append(propertyName).append(" not in  ");							
					break;
				case NOTNULL:
					whereBufferHou.append(propertyName).append(" is not null ");
					break;
				case NULL:
					whereBufferHou.append(propertyName).append(" is null ");
					break;
				}
			} else {
				System.out.println("拼接处的name不能以对象结尾，统一要以基本类型结尾！");
			}
		}
				
		addValues(group, whereBufferHou, values);
	}
	
	private boolean buildCase2(Group group, StringBuffer whereBufferHou, String alisStr) {
		boolean isOver;
		isOver = true;
		whereBufferHou.append(" ");
		String matchCase = "";
		if(whereBufferHou.toString().trim().length()>0){
			matchCase = whereBufferHou.toString().trim().substring(
					whereBufferHou.toString().trim().length()-1, whereBufferHou.toString().trim().length());
		}
		if (group.getRelation() == MatchType.AND) {
			if (!matchCase.equals("(")) {
				whereBufferHou.append(" and ");
			}
		} else {
			if (!matchCase.equals("(")) {
				whereBufferHou.append(" or ");
			}	
		}
		
		matchCase(group, whereBufferHou, alisStr);
		
		return isOver;
	}
	
	/**
	 * 
	 * @param group
	 * @param whereBufferHou
	 * @param temName  别名
	 * @param alisStr
	 */
	private void matchCase(Group group, StringBuffer whereBufferHou,String alisStr) {
		switch (group.getMatchType()) {
		case EQ:
			whereBufferHou.append(alisStr).append(" = ");
			break;
		case LIKE:
			whereBufferHou.append(alisStr).append(" like ");

			break;

		case LT:
			whereBufferHou.append(alisStr).append(" < ");
			break;
		case LE:
			if (group.getRelation() == MatchType.AND)
				whereBufferHou.append(alisStr).append(" <= ");
			break;
		case GT:
			whereBufferHou.append(alisStr).append(" > ");

			break;
		case GE:
			whereBufferHou.append(alisStr).append(" >= ");

			break;
		case NE:
			whereBufferHou.append(alisStr).append(" <> ");
			break;

		case IN:
			whereBufferHou.append(alisStr).append(" in ");
			break;
		case NOTIN:
			whereBufferHou.append(alisStr).append(" not in  ");

			break;
		case BETWEEN:
			whereBufferHou.append(alisStr).append(" between  ");

			break;
		case NULL:
			whereBufferHou.append(alisStr).append(" is null ");
			break;
		case NOTNULL:
			whereBufferHou.append(alisStr).append(" is not null ");
			break;
		}
	}
	
	private String createHqlByGroupsAll(String hql,Groups groups, List<Object> values) {
		// from段
		StringBuffer fromBuffer = new StringBuffer(" ");
		// where 段
		StringBuffer whereBufferQian = new StringBuffer(" where 1=1 ");
		StringBuffer whereBufferHou = new StringBuffer("");
		
		// 存取相同前缀
		List<String> Alias1 = new LinkedList<String>();
		Class<T> tempclass = ReflectionUtils.getSuperClassGenricType(this.getClass());
		fromBuffer.append(" from " + tempclass.getSimpleName() + " as " + tempclass.getSimpleName());		
	
		try {
			appendGroups(groups, fromBuffer, whereBufferQian, tempclass, Alias1, whereBufferHou, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		hql = fromBuffer.toString()+whereBufferQian.toString()+whereBufferHou.toString();
		
		String temp = "";
		if(groups.getOrderbys()!=null && groups.getOrderbys().length>0) {
			StringBuffer sBuffer = new StringBuffer();
			for (int i = 0; i < groups.getOrderbys().length; i++) {// groups接受orderby数组进行多条件排序
				if(i==0){
					sBuffer.append(" order by ");
				}
				// order by 别名处理
				temp = groups.getOrderbys()[i];

				if (temp.isEmpty())
					continue;
				
				if (groups.getOrders()[i]){
					sBuffer.append(tempclass.getSimpleName()+"."+temp+" asc,");
				}else{
					sBuffer.append(tempclass.getSimpleName()+"."+temp+" desc,");
				}					
			}
			hql += sBuffer.deleteCharAt(sBuffer.length()-1).toString();
		}else if (null != groups.getOrderby()	&& !groups.getOrderby().trim().equals("")) {
			// 处理order by的别名
			temp = groups.getOrderby();			

			if (groups.isOrder()){
				hql += " order by "+tempclass.getSimpleName()+"."+temp+" asc";				
			}else{
				hql += " order by "+tempclass.getSimpleName()+"."+temp+" desc";
			}				
		}
		
		return hql;
	}
	
	private void appendGroups(Groups groups, StringBuffer fromBuffer,
			StringBuffer whereBufferQian, Class<?> tempclass, List<String> Alias1,
			StringBuffer whereBufferHou, List<Object> values){
		if (groups.getGroupList() == null) {
			System.out.println("groups的GroupList不能为空！");
		} else {
			if (groups.getChildGroups2() != null && groups.getChildGroups2().size() > 0
					|| groups.getChildrelation()!=null) {
				for (Group group : groups.getGroupList()) {
					appendGroup(group, fromBuffer, whereBufferQian,
							tempclass, Alias1, whereBufferHou, values);
				}
				for (Groups tGroup : groups.getChildGroups2()) {
					if (tGroup.getChildrelation() == MatchType.AND) {
						whereBufferHou.append(" and ( ");
						appendGroups(tGroup, fromBuffer, whereBufferQian,
								 tempclass, Alias1, whereBufferHou, values);
						whereBufferHou.append(" )");
					} else if (tGroup.getChildrelation() == MatchType.OR) {
						whereBufferHou.append(" or ( ");
						appendGroups(tGroup, fromBuffer, whereBufferQian,
								tempclass, Alias1, whereBufferHou, values);
						whereBufferHou.append(" )");
					}
				}
			}else{
				StringBuffer whereAnd = new StringBuffer();
				StringBuffer whereOr = new StringBuffer();
				List<Object> tempList = new ArrayList<Object>();
				for (Group group : groups.getGroupList()) {
					if(group.getRelation().equals(MatchType.AND)){
						appendGroup(group, fromBuffer, whereBufferQian,
								tempclass, Alias1, whereAnd, values);
					}else{
						if(whereOr.toString().equals("")){
							whereOr.append(" and ( ");
						}						
						appendGroup(group, fromBuffer, whereBufferQian,
								tempclass, Alias1, whereOr, values);
						tempList.add(group.getPropertyValue1());
						values.remove(group.getPropertyValue1());
					}
					
				}
				if(!whereOr.toString().equals("")){
					whereOr.append(" ) ");
				}
				for(Object temp : tempList){
					values.add(temp);
				}
				whereBufferHou.append(whereAnd).append(whereOr);
			}
		}
	}
	
	private void appendGroup(Group group, StringBuffer fromBuffer,
			StringBuffer whereBufferQian,Class<?> baseClass, List<String> Alias1,
			StringBuffer whereBufferHou, List<Object> values) {

		String[] strings = StringUtil.split(group.getPropertyName(), ".");
		if (strings == null || strings.length == 0) {
			System.out.println("查询参数错误，请查证！");
		}
		Class<?> temClass = baseClass;
		Field value = null;
		// 存放临时字符串
		StringBuffer temBuffer = new StringBuffer();
		// 得到基类的别名
		String temName = baseClass.getSimpleName();
		// 循环（）
		boolean isOver = false;
		List<String> tempStrs = new ArrayList<String>();
		int t = 0;
		for (String string : strings) {
			boolean isSame = false;
			// 有一样的名字
			if (tempStrs.contains(string)) {
				t++;
				isSame = true;
			}

			tempStrs.add(string);

			// 反射得到字段
			value = ReflectionUtils.getAllField(temClass, string);
			// 赋值到基类
			temClass = value.getType();
			// 加上全名
			String alisStr = "";
			if (!isSame)
				alisStr = string;
			else {
				alisStr = string + t;

			}
			temBuffer.append(alisStr + ".");
			// 如果是集合
			if (ReflectionUtils.isInherit(temClass, List.class, true)) {
				SearchAnnotation searchAnnotation = value.getAnnotation(SearchAnnotation.class);
				if (searchAnnotation != null) {// 查看别名是否存在
					
					if (!Alias1.contains(temBuffer.subSequence(0,temBuffer.length() - 1))) {
						Alias1.add(temBuffer.subSequence(0,temBuffer.length() - 1).toString());
						fromBuffer.append(" left join ").append(temName)
								.append(".").append(alisStr).append(" ").append(" as ").append(alisStr);

					}
					temClass = searchAnnotation.Class();
					temName = alisStr;
				} else {
					System.out.println("多对多关系必须要配置好注解searchAnnotation的别名");
				}
			}else if (ReflectionUtils.isInherit(temClass, BaseEntity.class, false)) {// 如果是类
				String propertyValue=group.getPropertyName().toString();
				if(temName.equals(strings[0])){
					propertyValue = propertyValue.substring(temName.length()+1);
				}
				isOver = buildCase(group, whereBufferHou, temName, propertyValue);
				
				break;
			} else {
				isOver = buildCase(group, whereBufferHou, temName, alisStr);
			}
		}//遍历结束
		
		//没有到最后（可能是 NULL/ NOT NULL）
		if (!isOver) {
			String matchCase = "";
			if(whereBufferHou.toString().trim().length()>0){
				matchCase = whereBufferHou.toString().trim().substring(
						whereBufferHou.toString().trim().length()-1, whereBufferHou.toString().trim().length());
			}
			if (group.getMatchType() == MatchType.NULL || group.getMatchType() == MatchType.NOTNULL) {
				whereBufferHou.append(" ");
				if (group.getRelation() == MatchType.AND) {
					try {						
						if (!matchCase.equals("(")) {
							whereBufferHou.append(" and ");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						if (!matchCase.equals("(")) {
							whereBufferHou.append(" or ");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				switch (group.getMatchType()) {
				case NOTIN:
					whereBufferHou.append(temName).append(" not in  ");

					break;
				case NOTNULL:
					whereBufferHou.append(temName).append(" is not null ");
					break;

				case NULL:
					whereBufferHou.append(temName).append(" is null ");
					break;
				}
			} else {
				System.out.println("拼接处的name不能以对象结尾，统一要以基本类型结尾！");
			}
		}
		
		addValues(group, whereBufferHou, values);
	}
	
	private boolean buildCase(Group group, StringBuffer whereBufferHou, String temName, String alisStr) {
		boolean isOver;
		isOver = true;
		whereBufferHou.append(" ");
		String matchCase = "";
		if(whereBufferHou.toString().trim().length()>0){
			matchCase = whereBufferHou.toString().trim().substring(
					whereBufferHou.toString().trim().length()-1, whereBufferHou.toString().trim().length());
		}
		if (group.getRelation() == MatchType.AND) {
			if (!matchCase.equals("(")) {
				whereBufferHou.append(" and ");
			}
		} else {
			if (!matchCase.equals("(")) {
				whereBufferHou.append(" or ");
			}	
		}
		
		try {
			matchCase(group, whereBufferHou, temName, alisStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isOver;
	}
	
	@SuppressWarnings("incomplete-switch")
	private void matchCase(Group group, StringBuffer whereBufferHou,
			String temName, String alisStr) {
		switch (group.getMatchType()) {
		case EQ:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" = ");
			break;
		case LIKE:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" like ");

			break;

		case LT:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" < ");
			break;
		case LE:
			if (group.getRelation() == MatchType.AND)
				whereBufferHou.append(temName).append(".").append(alisStr).append(" <= ");
			break;
		case GT:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" > ");

			break;
		case GE:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" >= ");

			break;
		case NE:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" <> ");
			break;

		case IN:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" in ");
			break;
		case NOTIN:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" not in  ");

			break;
		case BETWEEN:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" between  ");

			break;
		case NULL:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" is null ");
			break;
		case NOTNULL:
			whereBufferHou.append(temName).append(".").append(alisStr).append(" is not null ");
			break;
		}
	}
	
	private void addValues(Group group, StringBuffer whereBufferHou, List<Object> values){
		// 判断错误
		if (group.getPropertyValue1() == null && !(group.getMatchType() == MatchType.NULL
				|| group.getMatchType() == MatchType.NOTNULL)) {
			System.out.println("传入值为空，但并不是查询NULL OR NOT NULL 请查证！");
		}else if (group.getPropertyValue1() != null && !"".equals(group.getPropertyValue1())) {
			// 是in 或not in
			if (group.getMatchType() == MatchType.IN || group.getMatchType() == MatchType.NOTIN) {
				Collection<?> collection = (Collection<?>) group.getPropertyValue1();
				StringBuffer inBuffer = new StringBuffer(" ( ");
				for (Object object : collection) {
					values.add(object);
					inBuffer.append(" ? ,");
				}
				inBuffer = inBuffer.delete(inBuffer.length() - 1,inBuffer.length());
				inBuffer.append(" ) ");
				whereBufferHou.append(inBuffer);
			}else if (group.getMatchType() == MatchType.BETWEEN) {
				// 如果是bwt
				if (group.getPropertyValue2() == null) {
					System.out.println("第二个参数不能为空");
				}
				if (group.getPropertyValue1().getClass() == Date.class || group.isDate()
						|| group.getPropertyValue1().getClass() == java.sql.Timestamp.class) {
					values.add(group.getPropertyValue1());
					values.add(group.getPropertyValue2());
					whereBufferHou.append(" ? and ? ");
				} else {
					System.out.println("BETWEEN 规定只能用于时间，数字请用大于小于进行");
				}

			}else {
				if (group.getPropertyValue1().getClass() == Date.class) {
					values.add(group.getPropertyValue1());
					whereBufferHou.append(" ? ");
				} else if (group.isDate()) {
					values.add(group.getPropertyValue1());
					whereBufferHou.append(" ? ");
				} else if (group.getPropertyValue1().getClass() == java.sql.Timestamp.class) {
					whereBufferHou.append(" ? ");
				} else {
					whereBufferHou.append(" ? ");
					if (group.getMatchType() == MatchType.LIKE) {
						values.add("%"+group.getPropertyValue1()+"%");
					} else {
						values.add(group.getPropertyValue1());						
					}
				}
			}
		}//PropertyValue1()有值的情况   结束	
	}

}
