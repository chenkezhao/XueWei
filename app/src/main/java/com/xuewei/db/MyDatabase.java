package com.xuewei.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.xuewei.XWApplication;
import org.xutils.DbManager;
import org.xutils.x;
import java.io.File;
import java.io.IOException;

/**
 *
 */
public class MyDatabase {

    private static final String TAG = "MyDatabase";

    private static MyDatabase instance;
    private DbManager.DaoConfig daoConfig;
    private DbManager db;

    // 单例
    public static synchronized MyDatabase getInstance() {
        if (instance == null) {
            instance = new MyDatabase();
        }
        return instance;
    }

    public MyDatabase() {
        // 数据库存储路径
        String dbDir = XWApplication.getInstance().getUserHomePath().getPath();
        // 数据库名称
        String dbName ="xw891.db";
        Log.d(TAG, "dbfilePath:"+dbDir+"/"+dbName);
        if (daoConfig == null) {
            Log.d(TAG, "Current daoConfig is null! It will be create now! ");
            daoConfig = new DbManager.DaoConfig()
                    .setDbName(dbName)  // db名称
                    //.setDbDir(new File(dbDir))  //  db存储路径， //设置数据库路径，默认存储在app的私有目录
                    .setDbVersion(1)    // db版本号
                    .setAllowTransaction(true)  // 允许db使用事务
                    .setDbUpgradeListener(new MyDbUpgradeListener())    // db升级监听
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        @Override
                        public void onDbOpened(DbManager db) {
                            // 开启WAL提升写入速度
                            db.getDatabase().enableWriteAheadLogging();
                        }
                    });
        }

        // 创建数据库
        if (db == null && daoConfig != null) {
            try {
                db = x.getDb(daoConfig);
            } catch (Exception e) {
                Log.e(TAG, "初始化数据库出错");
                throw new RuntimeException("初始化数据库出错");
            }
        }
    }

    public DbManager getDb() {
        return db;
    }

    SQLiteDatabase getSQLiteDatabase(){
        if (db != null) {
            return db.getDatabase();
        }
        return null;
    }

    public void closeDatabase(){
        if (db != null) {
            try {
                Log.e(TAG, "正在关闭数据库！");
                db.close();
            } catch (IOException e) {
                Log.e(TAG, "关闭数据库出错！");
                e.printStackTrace();
            }
        }
    }

    /**
     * 数据库升级监听器
     */
    class MyDbUpgradeListener implements DbManager.DbUpgradeListener {
        @Override
        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
            // 在此加入数据库升级逻辑
            for (int i = oldVersion; i < newVersion; i++) {
                try {
                    switch (i) {
//                        case 1:   TODO 当需要升级数据库结构时在这里加入升级逻辑
//                            updateToVersion2(db);
//                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    throw new RuntimeException("数据库升级失败!");
                }
            }
        }
    }
}
