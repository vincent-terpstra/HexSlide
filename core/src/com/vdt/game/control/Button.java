package com.vdt.game.control;

import com.vdt.game.PointXY;

public abstract class Button extends PointXY {
    private int touchIdx = -1;

    public boolean isTouched(){
        return touchIdx != -1;
    }

    public boolean touchDown(float touchX, float touchY, int touch){
        if(inRange(touchX, touchY, .5f)){
            touchIdx = touch;
            return true;
        }
        return false;
    }

    public boolean touchUp(float touchX, float touchY, int touch){
        if(inRange(touchX, touchY, .52f)){
            if(touchIdx == touch)
                run();
            touchIdx = -1;
            return true;
        }
        if(touchIdx == touch){
            touchIdx = -1;
            return true;
        }

        return false;
    }

    public abstract void run();

    private boolean inRange(float touchX, float touchY, float range){
        touchX -= x;
        touchY -= y;

        return  touchX * touchX + touchY * touchY < range;
    }
}
