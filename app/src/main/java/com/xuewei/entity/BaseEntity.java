package com.xuewei.entity;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import org.xutils.db.annotation.Column;

import java.io.Serializable;

/**
 * 基础类 Created by Administrator on 2017/3/7.
 */

public class BaseEntity implements Serializable {
	@XStreamOmitField
	@Column(name = "id", isId = true)
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
