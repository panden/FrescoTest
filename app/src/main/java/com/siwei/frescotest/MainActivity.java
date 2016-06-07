package com.siwei.frescotest;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.Postprocessor;

import java.io.File;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private SimpleDraweeView img;
    private static String sString;
    private String mString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initView();
    }

    private void initView(){
        img= (SimpleDraweeView) findViewById(R.id.img);
    }

    private void initData(){
        File gifFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/fd_click_to_open.gif");
        Log.e("","file exists="+gifFile.exists());

        //Fresco中文官方文档：http://fresco-cn.org/docs/index.html#_
        //Fresco框架详细讲解：http://blog.csdn.net/y1scp/article/category/3142345/1
        //Fresco框架XML介绍：http://blog.csdn.net/y1scp/article/details/49245535

        //fresco:failureImageScaleType图片缩放类型
        //fresco:placeholderImage图片加载默认显示的图片
        //fresco:failureImage图片加载失败显示的图片
        //fresco:retryImage重新加载显示的图片,在布局中设置之后必须在代码里使用自动以的Controller并设置setTapToRetryEnabled(true)，这样才能有效。
        //注意：重复加载4次还是没有加载出来的时候才会显示 failureImage(失败图) 的图片
        //fresco:progressBarImage图片加载中显示的图片
        //fresco:progressBarAutoRotateInterval正在加载中图片的动画时间（旋转动画）
        //fresco:fadeDuration设置淡入淡出动画的时长
        //fresco:overlayImage叠加图，叠加在最上层
        //fresco:pressedStateOverlayImage按压时显示的叠加图
        //fresco:roundAsCircle显示圆形图

        //显示圆角图
        //fresco:roundedCornerRadius设置圆角半径
        //fresco:roundXXXX设置所在的方向是否显示为圆角
        //注意：我们同时设置图像显示为圆形图像和圆角图像时，只会显示为圆形图像。
        // fresco:roundingBorderWidth设置圆形圆角边框宽度
        //fresco:roundingBorderColo设置圆形圆角边框的颜色
        //fresco:roundWithOverlayColor圆形圆角图的底层的叠加颜色（背景色）

        img.setAspectRatio(0.5f);//设置图片显示的宽高比;xml中：fresco:viewAspectRatio="0.5"

        DraweeController lController =  Fresco.newDraweeControllerBuilder()
                .setUri("imgPath")
                .setTapToRetryEnabled(true)
                //在设置Contraoller的是有一定要setOldController；因为new一个Contraoller往往比较耗费资源
                .setOldController(img.getController())
                .build();


        img.setImageURI(Uri.fromFile(gifFile));
    }

    private void inieEvent(){
//        getDrawable(R.drawable.mytest);//API 21
//
//        getResources().getDrawable(R.drawable.mytest);
//
//        ContextCompat.getDrawable(this, R.drawable.mytest);
    }


    public void setHierarchy(SimpleDraweeView pView){

    }

    /**设置圆角效果，GenericDraweeHierarchy的使用 */
    public void setGenericDraweeHierarchy(SimpleDraweeView pView){

        //Fresco 实现圆形圆角图片（RoundingParams）：http://blog.csdn.net/y1scp/article/details/49734429
        //DraweeHierarchy 创建比较浪费资源，推荐能够重复使用,SimpleDraweeView也不要重复的去设置DraweeHierarchy;

        RoundingParams lParams=new RoundingParams();
        lParams.setRoundAsCircle(true);//设置为圆图
        lParams.setCornersRadius(20);//设置圆角图片半径
        lParams.setBorderWidth(5);//设置边框宽度
        lParams.setBorderColor(Color.BLUE);//设置边框的颜色
        lParams.setBorder(Color.BLUE, 5);//设置边框颜色和宽度
        lParams.setCornersRadii(new float[]{5 , 5 , 5, 0});//设置四个角的圆角半径
        //分别设置的是：（1-2）tl(top-left):圆弧半径、圆弧角度；（3-4）tr(top-right):圆弧半径、圆弧角度；以此类推。
        lParams.setCornersRadii(new float[]{20, 25, 30, 35, 40, 45, 50, 55});
        lParams.setOverlayColor(Color.GRAY);//设置圆形圆角图片的背景颜色
        //设置圆形圆角模式，当设置setOverlayColor的时候必须要保证RoundingMethod为OVERLAY_COLOR模式，否则设置无效；
        //BITMAP_ONLY 使用圆角BitmapShader绘制Bitmap(使用该模式存在一定的限制)
        //OVERLAY_COLOR 画圆角的底部通过一个纯色绘制，颜色由setOverlayColor指定
        lParams.setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR);

        GenericDraweeHierarchy lHierarchy= GenericDraweeHierarchyBuilder.newInstance(getResources())
                .setFadeDuration(2*1000)//设置淡出动画时长
                .setRoundingParams(RoundingParams.asCircle())//显示为圆角图片
               //.setRoundingParams(RoundingParams.fromCornersRadius(20))//设置圆角半径
                //.setRoundingParams(lParams)//设置圆形圆角的相关参数,需要配置多个参数的时候
                .setBackgrounds(Arrays.asList(getResources().getDrawable(R.drawable.mytest), getResources().getDrawable(R.drawable.mytest)))//设置背景（设置多个背景的时候会发现背景图片会被叠加在一起）
                .setOverlay(getResources().getDrawable(R.drawable.mytest))
                .build();

        //GenericDraweeHierarchy的使用：http://blog.csdn.net/y1scp/article/details/49593319
        lHierarchy.setPlaceholderImage(getResources().getDrawable(R.drawable.mytest));//设置占位图（默认显示的图片）
        lHierarchy.setProgressBarImage(getResources().getDrawable(R.drawable.mytest), ScalingUtils.ScaleType.CENTER);//设置加载中的图片以及缩放方式
        lHierarchy.setFailureImage(getResources().getDrawable(R.drawable.mytest), ScalingUtils.ScaleType.CENTER);//设置加载失败的显示图片
        lHierarchy.setRetryImage(getResources().getDrawable(R.drawable.mytest), ScalingUtils.ScaleType.CENTER);//设置重新加载显示的图片
        lHierarchy.setFadeDuration(3*1000);//设置渐变动画时长

        pView.setHierarchy(lHierarchy);
    }



    //自定义图片加载请求
    Postprocessor mPostprocessor=new BasePostprocessor() {

        @Override
        public void process(Bitmap bitmap) {
            super.process(bitmap);
        }

        @Override
        public String getName() {
            return super.getName();
        }

        
    };
}
