package com.utils;

import java.util.ArrayList;
import java.util.List;

public class PageVo {
	
	private Integer page;
	
	private Integer rows;
	
	private Long total;
	
	private Long records;
	
	private List root;
	
	private String sortname;
	
	private String order;
	
	private List<Group> searchGroups = new ArrayList();

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getRecords() {
		return records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}

	public List getRoot() {
		return root;
	}

	public void setRoot(List root) {
		this.root = root;
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

	public List<Group> getSearchGroups() {
		return searchGroups;
	}

	public void setSearchGroups(List<Group> searchGroups) {
		this.searchGroups = searchGroups;
	}

}
