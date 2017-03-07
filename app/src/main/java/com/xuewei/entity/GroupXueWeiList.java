package com.xuewei.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * 穴位组集合
 * Created by Administrator on 2017/3/7.
 */
@XStreamAlias("GROUPROOT")
public class GroupXueWeiList {

    @XStreamAlias("GROUPXUEWEILIST")
	private List<GroupXueWei> groupXueWeiList;

	public List<GroupXueWei> getGroupXueWeiList() {
		return groupXueWeiList;
	}

	public void setGroupXueWeiList(List<GroupXueWei> groupXueWeiList) {
		this.groupXueWeiList = groupXueWeiList;
	}
}
