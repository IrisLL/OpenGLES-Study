package com.example.openglstudy;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Constructor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class FGLRender implements GLSurfaceView.Renderer {

    private Triangel triangle;
    private IsoscelesTriangle isoscelesTriangle;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);
        Log.i("iris","onSurfaceCreated");
      //  triangle=new Triangel();
        isoscelesTriangle=new IsoscelesTriangle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {

        Log.i("iris","onSurfaceChanged");
       // GLES20.glViewport(0,0,width,height);
        //计算宽高比
        float ratio=(float)width/height;
        //设置透视投影
        Matrix.frustumM(IsoscelesTriangle.mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        //设置相机位置
        Matrix.setLookAtM(IsoscelesTriangle.mViewMatrix, 0, 0, 0, 7.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(IsoscelesTriangle.mMVPMatrix,0,IsoscelesTriangle.mProjectMatrix,0,IsoscelesTriangle.mViewMatrix,0);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        Log.e("iris","onDrawFrame");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
       // triangle.Draw();
        isoscelesTriangle.Draw();
    }


    public static int loadShader(int type, String shaderCode){

        //创建一个vertex shader类型(GLES20.GL_VERTEX_SHADER)
        //或一个fragment shader类型(GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // 将源码添加到shader并编译它
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}
