package com.xuewei.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 穴位效果|作用 Created by Administrator on 2017/3/7.
 */

@XStreamAlias("XUEWEIEFFECT")
@Table(name = "XueWeiEffect")
public class XueWeiEffect extends BaseEntity {

	public static final String  XUEWEIEFFECT = "XUEWEIEFFECT";

	@XStreamAlias("SEQ")
	@Column(name = "seq")
	private int		seq;

	@XStreamAlias("XUEWEI")
	@Column(name = "xueWei")
	private String	xueWei;

	@XStreamAlias("EFFECT")
	@Column(name = "effect")
	private String	effect;

	@XStreamAlias("URL")
	@Column(name = "url")
	private String	url;

	@XStreamAlias("GROUPID")// 外键表id
	@Column(name = "groupid")
	private int	groupid;

	public XueWeiEffect() {
	}

	public XueWeiEffect(int seq, String xueWei, String effect, String url, int groupid){
		this.seq = seq;
		this.xueWei = xueWei;
		this.effect = effect;
		this.url = url;
		this.groupid = groupid;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getXueWei() {
		return xueWei;
	}

	public void setXueWei(String xueWei) {
		this.xueWei = xueWei;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
}
