package com.longray.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.longray.entity.BaseEntity;

@Entity
@Table(name="account_role")
public class Role extends BaseEntity{
	
	@Column(length=20)
	private String name;
	
	private Integer levels;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

}
