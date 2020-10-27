package com.vdt.game.draw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class ColorSource {
    final float[] colorList, shadowList;
    float background;
    public ColorSource(){
        int[] clrs = {
                165, 165, 200,

                160, 160, 190,
                160, 160, 190,

                155, 152, 179,
                155, 152, 179,

                138, 130, 153,
                138, 130, 153,

                121, 108, 128,
                99, 113, 140,
                96, 121, 135,
                93, 140, 121,
                85, 140, 95,
                181, 160,  85,
                190, 149, 86,
                172, 112, 73,
                145,  67, 51,
                140, 49, 49
        };

        colorList = new float[clrs.length / 3];
        int _idx = 0;
        for(int i = 0; i < clrs.length;){
            colorList[_idx++] = Color.toFloatBits(clrs[i++], clrs[i++], clrs[i++], 255);
        }

        shadowList = new float[clrs.length / 3];
        _idx = 0;
        for(int i = 0; i < clrs.length;){
            shadowList[_idx++] = Color.toFloatBits((int)(clrs[i++] * .5f), (int)(clrs[i++] * .6f), (int)(clrs[i++] * .5f), 255);
        }


       //int r = 38, g = 42, b = 44;
        switchColor();
    }

    int[] clr1 = {38, 42, 44, 235, 234, 240};
    boolean toggle = false;

    public void switchColor(){
        toggle = !toggle;
        int shift = toggle? 0 : 3;

        int r = clr1[shift++], g = clr1[shift++], b = clr1[shift];

        Gdx.gl.glClearColor(r / 255f, g /255f, b/255f, 1.0f);
        background = Color.toFloatBits(r ,g, b, 255);
    }

    int max(int idx){
        return Math.min(idx, colorList.length -1);
    }


    public final float shadow(int idx){
        return shadowList[max(idx)];
    }

    public final float color(int idx){
        return colorList[max(idx)];
    }
}
