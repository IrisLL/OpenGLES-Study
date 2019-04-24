package com.example.openglstudy.Shape;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Cone {

    /*
    圆锥 相当于 一个锥面 + 一个圆形
    除了锥面的中心点坐标有高度，其他和圆形相同
     */
    public static int mProgram;

    public static Round round;
    private FloatBuffer vertexBuffer;

    public static float[] mViewMatrix=new float[16];
    public static float[] mProjectMatrix=new float[16];
    public static  float[] mMVPMatrix=new float[16];

    private int n=360;  //切割份数
    private float height=2.0f;  //圆锥高度
    private float radius=1.0f;  //圆锥底面半径
    private float[] colors={1.0f,1.0f,1.0f,1.0f};

    private int vSize;

    public Cone(){
        round=new Round();
        ArrayList<Float> pos=new ArrayList<>();
        pos.add(0.0f);
        pos.add(0.0f);
        pos.add(height);  //增加了高度
        float angDegSpan=360f/n;
        for(float i=0;i<360+angDegSpan;i+=angDegSpan){
            pos.add((float) (radius*Math.sin(i*Math.PI/180f)));
            pos.add((float)(radius*Math.cos(i*Math.PI/180f)));
            pos.add(0.0f);
        }
        float[] d=new float[pos.size()];
        for (int i=0;i<d.length;i++){
            d[i]=pos.get(i);
        }
        vSize=d.length/3;

        ByteBuffer buffer=ByteBuffer.allocateDirect(d.length*4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer=buffer.asFloatBuffer();
        vertexBuffer.put(d);
        vertexBuffer.position(0);
    }

    public void Draw() {
        GLES20.glUseProgram(mProgram);
        Log.e("iris","mProgram:"+mProgram);
        int mMatrix=GLES20.glGetUniformLocation(mProgram,"vMatrix");
        GLES20.glUniformMatrix4fv(mMatrix,1,false,mMVPMatrix,0);
        int mPositionHandle=GLES20.glGetAttribLocation(mProgram,"vPosition");
        Log.e("iris","Get Position:"+mPositionHandle);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle,3,GLES20.GL_FLOAT,false,0,vertexBuffer);
//        int mColorHandle=GLES20.glGetUniformLocation(mProgram,"vColor");
//        GLES20.glEnableVertexAttribArray(mColorHandle);
//        GLES20.glUniform4fv(mColorHandle,1,colors,0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,vSize);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        round.setMatrix(mMVPMatrix);
        round.Draw();
    }
}
