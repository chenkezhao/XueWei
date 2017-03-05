package com.xuewei.db.dao;

import android.text.TextUtils;

import com.xuewei.db.MyDatabase;

import org.xutils.DbManager;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.SqlInfoBuilder;
import org.xutils.db.table.DbModel;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseDao {
	protected DbManager db;

	public BaseDao(){
		super();
		this.db = MyDatabase.getInstance().getDb();
	}

	/**
	 * 初始化表不存在就创建
	 * 
	 * @param clazz
	 */
	public BaseDao(Class<?> clazz){
		this.db = MyDatabase.getInstance().getDb();
		try {
			TableEntity<?> table = db.getTable(clazz);
			if (!table.tableIsExist()) {
				synchronized (table.getClass()) {
					if (!table.tableIsExist()) {
						SqlInfo sqlInfo = SqlInfoBuilder.buildCreateTableSqlInfo(table);
						db.execNonQuery(sqlInfo);
						String execAfterTableCreated = table.getOnCreated();
						if (!TextUtils.isEmpty(execAfterTableCreated)) {
							db.execNonQuery(execAfterTableCreated);
						}
					}
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	public List<Object> parseDbModelToListObject(Class<?> clazz, List<DbModel> dbModelList) {
		if (dbModelList == null && dbModelList.size() == 0) {
			return null;
		}
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < dbModelList.size(); i++) {
			list.add(parseDbModelToObject(clazz, dbModelList.get(i)));
		}
		return list;
	}

	// 将DbModel转换为Object
	public Object parseDbModelToObject(Class<?> clazz, DbModel dbModel) {
		try {
			Object obj = clazz.newInstance();
			Field[] f = obj.getClass().getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				f[i].setAccessible(true); // 设置些属性是可以访问的
				if (f[i].getType().toString().endsWith("int")) {
					int valueInt = dbModel.getInt(f[i].getName());
					f[i].set(obj, valueInt);
				} else if (f[i].getType().toString().endsWith("String")) {
					String valueString = dbModel.getString(f[i].getName());
					if (valueString != null) {
						f[i].set(obj, valueString);
					}
				} else if (f[i].getType().toString().endsWith("Date")) {
					Date valueDate = dbModel.getDate(f[i].getName());
					if (valueDate != null) {
						f[i].set(obj, valueDate);
					}
				}
			}
			return obj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
