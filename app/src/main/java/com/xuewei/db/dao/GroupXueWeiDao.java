package com.xuewei.db.dao;

import com.xuewei.entity.GroupXueWei;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.SqlInfoBuilder;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import java.util.List;

/**
 * 
 * Created by Administrator on 2017/3/7.
 */

public class GroupXueWeiDao extends BaseDao{
    private static  GroupXueWeiDao mGroupXueWeiDao = new GroupXueWeiDao();
    private GroupXueWeiDao() {
        super();
    }
    public static GroupXueWeiDao getInstance(){
        return mGroupXueWeiDao;
    }
    /**
     * 插入
     * @param groupXueWei
     */
    public void insert(GroupXueWei groupXueWei) {
        try {
            db.save(groupXueWei);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量插入
     * @param groupXueWeiList
     */
    public void insert(List<GroupXueWei> groupXueWeiList) {
        try {
            db.save(groupXueWeiList);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空表
     */
    public void clearTable(){

        try {
            db.delete(GroupXueWei.class, WhereBuilder.b("id", "!=", "-1"));
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
            db.delete(GroupXueWei.class, WhereBuilder.b("id", "=", id));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除
     */
    public void delete(GroupXueWei groupXueWei){
        try {
            db.delete(groupXueWei);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新
     * @param groupXueWei
     * @param updateColumnNames
     */
    public boolean update(GroupXueWei groupXueWei, String... updateColumnNames){
        try {
            db.update(groupXueWei, updateColumnNames);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新
     */
    public void update(GroupXueWei groupXueWei){
        try {
            KeyValue[] karr = {};
            List<KeyValue> keyValues = SqlInfoBuilder.entity2KeyValueList(db.getTable(GroupXueWei.class), groupXueWei);
            db.update(GroupXueWei.class, WhereBuilder.b("id", "=", groupXueWei.getId()), keyValues.toArray(karr));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有
     */
    public List<GroupXueWei> getAll(){
        try {
            List<GroupXueWei> groupXueWeiList = db.findAll(GroupXueWei.class);
            return groupXueWeiList;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取表数据行
     * @return
     */
    public Long getAllCount(){
        try {
            return db.selector(GroupXueWei.class).count();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 根据id查询
     * @param id
     */
    public GroupXueWei getGroupXueWeiById(String id){
        try {
            return db.selector(GroupXueWei.class).where("id", "=", id).findFirst();
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
    public List<GroupXueWei> getBy(String columnName, Object value){
        try {
            return db.selector(GroupXueWei.class).where(columnName, "=", value).findAll();
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
    public List<GroupXueWei> getLikeBy(String columnName, String value){
        try {
            return db.selector(GroupXueWei.class).where(columnName, "like", "%"+value+"%").findAll();
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
    public GroupXueWei getGroupXueWeiBy(String code, String date) {
        try {
            return db.selector(GroupXueWei.class).where("CODE", "=", code).and("DATE","=",date).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
}
