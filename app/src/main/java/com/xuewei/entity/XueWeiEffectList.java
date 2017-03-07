package com.xuewei.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * 穴位效果|作用 集合 Created by Administrator on 2017/3/7.
 */
@XStreamAlias("EFFECTROOT")
public class XueWeiEffectList {

    @XStreamAlias("XUEWEIEFFECTLIST")
	private List<XueWeiEffect> xueWeiEffectList;

	public List<XueWeiEffect> getXueWeiEffectList() {
		return xueWeiEffectList;
	}

	public void setXueWeiEffectList(List<XueWeiEffect> xueWeiEffectList) {
		this.xueWeiEffectList = xueWeiEffectList;
	}
}
