package com.utils;

import java.util.ArrayList;
import java.util.List;

public class Page {
	
	public Page()
	{
	}
	
	public Page(int pageSize, int currentPage)
	{
		this.pageSize = pageSize;
		this.pageNo = currentPage;
	}
	
	private List items;

	private int totalCount;// 总记录数

	private int totalPageCount;// 总页数

	private int pageSize;// 每页记录个数

	private int pageNo;// 当前页数
	
	//查询索引位置
	private int fromIndex = -1;
	
	private String sortname;
	
	private String order;
	
	private List<Group> searchGroups = new ArrayList();

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPageCount() {
		totalPageCount = totalCount%pageSize==0?totalCount/pageSize : totalCount/pageSize + 1;
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFromIndex() {
		return fromIndex;
	}

	public void setFromIndex(int fromIndex) {
		this.fromIndex = fromIndex;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List<Group> getSearchGroups() {
		return searchGroups;
	}

	public void setSearchGroups(List<Group> searchGroups) {
		this.searchGroups = searchGroups;
	}

}
