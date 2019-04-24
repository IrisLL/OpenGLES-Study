package com.example.openglstudy;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class FGLView extends GLSurfaceView {

    private FGLRender renderer;



    public FGLView(Context context){
        super(context);

        // 创建OpenGL ES 2.0的上下文
        setEGLContextClientVersion(2);

        renderer = new FGLRender();

        //设置Renderer用于绘图
        setRenderer(renderer);

        //只有在绘制数据改变时才绘制view，可以防止GLSurfaceView帧重绘
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


}
