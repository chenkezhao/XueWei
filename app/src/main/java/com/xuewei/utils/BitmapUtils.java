package com.xuewei.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaiser on 2015/10/31.
 * 位图处理工具
 */
public class BitmapUtils {


    private static final String TAG = BitmapUtils.class.getSimpleName();
    public static BitmapUtils bitmapUtils;
    private BitmapUtils(){
    }
    public static BitmapUtils getInstance(){
        if(bitmapUtils==null){
            bitmapUtils = new BitmapUtils();
        }
        return bitmapUtils;
    }
    public Bitmap xx(){
        /*updateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        canvas = new Canvas(updateBitmap);
        paint = new Paint();
        final ColorMatrix cm = new ColorMatrix();
        //设置颜色5*4矩阵，范围0.0f~2.0f，1为保持原图的RGBA值
        cm.set(new float[] {
                2.0f, 0, 0, 0, 0,// R红色值，第一位
                0, 1, 0, 0, 0,// G绿色值，第二位
                0, 0, 1, 0, 0,// B蓝色值，第三位
                0, 0, 0, 1, 0 // A透明度，第四位
        });
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        final Matrix matrix = new Matrix();
        canvas.drawBitmap(bitmap, matrix, paint);
        imageView.setImageBitmap(updateBitmap);*/
        return null;
    }

    /**
     * 回收bitmap
     * setImageBitmap()这个方法的源码，实际上它内部做了类似指针传递的操作，
     * 它并没有新建Bitmap对象，而是对其进行了引用而已。
     * @param bitmaps Bitmap[]
     */
    public void recycleBitmaps(Bitmap[] bitmaps){
        int length = bitmaps.length;
        for(int i=0;i<length;i++){
            if(!bitmaps[i].isRecycled()){
                bitmaps[i].recycle();//回收bitmap，释放内存点存
            }
        }
    }

    /**
     * 回收bitmap
     * setImageBitmap()这个方法的源码，实际上它内部做了类似指针传递的操作，
     * 它并没有新建Bitmap对象，而是对其进行了引用而已。
     * @param bitmap Bitmap[]
     */
    public void recycleBitmap(Bitmap bitmap){
        if(!bitmap.isRecycled()){
            bitmap.recycle();
        }
    }

    /**
     * 对图片进行压缩，传入的宽和高，计算出合适的inSampleSize值
     * @param options BitmapFactory.Options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 生成缩略图
     * @param bitmap
     * @param view
     * @return
     */
    public Bitmap compressBitmapFromBitmap(Bitmap bitmap,View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int width = params.width;
        int height = params.height;
        return ThumbnailUtils.extractThumbnail(bitmap,width,height);
    }

    /**
     * 先解析得到合适的大小，再进行处理显示
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap compressBitmapFromResource(Resources res, int resId,
                                             int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 先解析得到合适的大小，再进行处理显示
     * @param res
     * @param resId
     * @param view 对象，用于取出view的width,height
     * @return
     */
    public Bitmap compressBitmapFromResource(Resources res, int resId,View view) {
        //获取组件布局参数
        ViewGroup.LayoutParams params = view.getLayoutParams();
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, params.width, params.height);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    /**
     * 设置指定像素区域的颜色
     * 只有透明和白色的像素点是不修改的
     * @param bitmap 位图
     * @param startX 开始X轴的像素
     * @param startY 开始Y轴的像素
     * @param endX 结束X轴的像素
     * @param endY 结束Y轴的像素
     * @param setColor xml定义的颜色资源ID
     * @return 修改后的Bitmap
     */
    public Bitmap setBitmapPixel(Bitmap bitmap,int startX,int startY,int endX,int endY,int setColor){
        //没必要每次都循环图片中的所有点，因为这样会比较耗时。
        int loopCount = startY * startX;
        //填充颜色的起始点 开始 到 终点
        for(int i=startX;i<endX;i++){
            for(int j=startY;j<endY;j++){
                //在这说明一下 如果color 是全透明 或者全黑 返回值为 0
                //getPixel()不带透明通道 getPixel32()才带透明部分 所以全透明是0x00000000
                //而不透明黑色是0xFF000000 如果不计算透明部分就都是0了
                int color = bitmap.getPixel(i,j);
                if (color != 0 && color != Color.WHITE) {
                    //int temp = Color.argb(argb_[0],argb_[1],argb_[2],argb_[3]);
                    //bitmap.setPixel(i, j, Color.BLACK);
                    bitmap.setPixel(i,j,setColor);
                }
                //还原图片颜色
                /*bitmap.setPixel(i,j,arrayColor[loopCount]);
                loopCount++;*/
            }
        }

        return bitmap;
    }

    /**
     * 获取位图的像素点
     * @param resources
     * @param resid
     * @return
     */
    public int[] getBitmapPixel(Resources resources,int resid){
        Bitmap bitmap = getMutableBitmap(resources, resid);
        //处理图片的第个像素点
        int bitmapWidth = 0;
        int bitmapHeight = 0;
        int arrayColor[] = null;

        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        arrayColor = new int[bitmapHeight*bitmapWidth];
        //循环每一个像素点
        int count = 0;
        for(int i=0;i<bitmapWidth;i++){
            for(int j=0;j<bitmapHeight;j++){
                //获得Bitmap 图片中每一个点的color颜色值
                int color = bitmap.getPixel(i,j);
                //将颜色值存到数组里，方便修改
                arrayColor[count]=color;

                //如果你想做的更细致的话 可以把颜色值的R G B 拿到做响应的处理
                /*
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                */
                count++;
            }
        }
        return arrayColor;
    }

    /**
     * 九宫格图片截取数字
     * @param context 上下文
     * @param resid 九宫格图片ID
     * @param btnColors 颜色集数组
     * @return
     */
    public List<Map<String,Object>> getSquaredUpNum(Context context,int resid,int[] btnColors){

        //洗牌颜色数组
        List colors = Arrays.asList(btnColors);
        Collections.shuffle(colors);

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resid);
        //封装成数组
        int size = 10;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int x = width/5;
        int y = height/2;
        int j  = 0;
        for(int i=0;i<size;i++){
            Map<String,Object> map = new HashMap<String,Object>();
            if(i<5){
                j=i;
            }else{
                j=i-5;
            }

            Bitmap b = Bitmap.createBitmap(bitmap, j * x, i > 4 ? y : 0, x, y);
            b = setBitmapPixel(b,0,0,x,y,btnColors[i]);
            map.put("bitmap",b);
            map.put("number",(i+1)==10?0:(i+1));
            list.add(map);

        }
        return list;
    }


    /**
     * 你正在试图修改的不可变的位图的像素为单位）。
     * 您不能修改的不可变的位图的像素。如果您尝试它将抛出 IllegalStateException。
     * 使用这个方法从资源获取可变位图
     * @param resources
     * @param resId
     * @return
     */
    public Bitmap getMutableBitmap(Resources resources,int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        return BitmapFactory.decodeResource(resources, resId, options);
    }
}
