package com.xuewei.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * 穴位组
 * Created by Administrator on 2017/3/7.
 */
@XStreamAlias("GROUPXUEWEI")
@Table(name = "GroupXueWei")
public class GroupXueWei extends BaseEntity {


	public static final String  GROUPXUEWEI = "GROUPXUEWEI";

	@XStreamAlias("SEQ")
	@Column(name = "seq")
	private int		seq;
	@XStreamAlias("TITLE")
	@Column(name = "title")
	private String	title;
	@XStreamAlias("SUBTITLE")
	@Column(name = "subTitl")
	private String	subTitle;
	@XStreamAlias("CONTENT")
	@Column(name = "conten")
	private String	content;
	@XStreamAlias("COLLECTION")
	@Column(name = "collectio")
	private Boolean	collection;
	@XStreamAlias("URL")
	@Column(name = "url")
	private String	url;

	public List<XueWeiEffect> getXueWeiEffectList(DbManager db) throws DbException {
		return db.selector(XueWeiEffect.class).where("groupid", "=", this.id).findAll();
	}

	public GroupXueWei() {
	}

	public GroupXueWei(int seq, String title, String subTitle, String content, Boolean collection, String url){
		this.seq = seq;
		this.title = title;
		this.subTitle = subTitle;
		this.content = content;
		this.collection = collection;
		this.url = url;
	}


	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getCollection() {
		return collection;
	}

	public void setCollection(Boolean collection) {
		this.collection = collection;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
