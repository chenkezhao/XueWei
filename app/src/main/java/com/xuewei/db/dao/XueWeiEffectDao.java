package com.xuewei.db.dao;

import com.xuewei.entity.XueWeiEffect;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.SqlInfoBuilder;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import java.util.List;

/**
 *
 * Created by Administrator on 2017/3/7.
 */

public class XueWeiEffectDao extends BaseDao{
    private static  XueWeiEffectDao mXueWeiEffectDao = new XueWeiEffectDao();
    private XueWeiEffectDao() {
        super();
    }
    public static XueWeiEffectDao getInstance(){
        return mXueWeiEffectDao;
    }
    /**
     * 插入
     * @param xueWeiEffect
     */
    public void insert(XueWeiEffect xueWeiEffect) {
        try {
            db.save(xueWeiEffect);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量插入
     * @param xueWeiEffectList
     */
    public void insert(List<XueWeiEffect> xueWeiEffectList) {
        try {
            db.save(xueWeiEffectList);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空表
     */
    public void clearTable(){

        try {
            db.delete(XueWeiEffect.class, WhereBuilder.b("id", "!=", "-1"));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据id删除
     * @param id
     */
    public void deleteById(String id){

        try {
            db.delete(XueWeiEffect.class, WhereBuilder.b("id", "=", id));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除
     */
    public void delete(XueWeiEffect xueWeiEffect){
        try {
            db.delete(xueWeiEffect);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新
     * @param xueWeiEffect
     * @param updateColumnNames
     */
    public boolean update(XueWeiEffect xueWeiEffect, String... updateColumnNames){
        try {
            db.update(xueWeiEffect, updateColumnNames);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新
     */
    public void update(XueWeiEffect xueWeiEffect){
        try {
            KeyValue[] karr = {};
            List<KeyValue> keyValues = SqlInfoBuilder.entity2KeyValueList(db.getTable(XueWeiEffect.class), xueWeiEffect);
            db.update(XueWeiEffect.class, WhereBuilder.b("id", "=", xueWeiEffect.getId()), keyValues.toArray(karr));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有
     */
    public List<XueWeiEffect> getAll(){
        try {
            List<XueWeiEffect> xueWeiEffectList = db.findAll(XueWeiEffect.class);
            return xueWeiEffectList;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据id查询
     * @param id
     */
    public XueWeiEffect getXueWeiEffectById(String id){
        try {
            return db.selector(XueWeiEffect.class).where("id", "=", id).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 根据指定列查询（等值查询）
     * @param columnName    列名（全大写）
     * @param value         查询value
     * @return
     */
    public List<XueWeiEffect> getBy(String columnName, String value){
        try {
            return db.selector(XueWeiEffect.class).where(columnName, "=", value).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据code、date获取
     * @param code
     * @param date
     * @return
     */
    public XueWeiEffect getXueWeiEffectBy(String code, String date) {
        try {
            return db.selector(XueWeiEffect.class).where("CODE", "=", code).and("DATE","=",date).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
}
