package com.xuewei.utils;

import android.content.res.AssetManager;

import com.thoughtworks.xstream.XStream;
import com.xuewei.XWApplication;
import com.xuewei.entity.GroupXueWeiList;
import com.xuewei.entity.XueWeiEffectList;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * Created by Administrator on 2017/3/9.
 */

public class XmlReadUtils {
    public static XmlReadUtils xmlReadUtils;
    private XmlReadUtils(){
    }
    public static XmlReadUtils getInstance(){
        if(xmlReadUtils==null){
            xmlReadUtils = new XmlReadUtils();
        }
        return xmlReadUtils;
    }


    /**
     * 读取xml文件转对象
     * @return
     */
    public GroupXueWeiList fromXMLByGroup(){
        GroupXueWeiList groupXueWeiList;
        try {
            AssetManager assetManager = XWApplication.getInstance().getAssets();
            InputStream ims = assetManager.open("GroupXueWei.xml");
            XStream xstream = new XStream();
            groupXueWeiList = (GroupXueWeiList) xstream.fromXML(ims);
            return  groupXueWeiList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取xml文件转对象
     * @return
     */
    public XueWeiEffectList fromXMLByEffect(){
        XueWeiEffectList xueWeiEffectList;
        try {
            AssetManager assetManager = XWApplication.getInstance().getAssets();
            InputStream ims = assetManager.open("XueWeiEffect.xml");
            XStream xstream = new XStream();
            xueWeiEffectList = (XueWeiEffectList) xstream.fromXML(ims);
            return  xueWeiEffectList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
