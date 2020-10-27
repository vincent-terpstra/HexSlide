package com.vdt.game.draw;

import com.vdt.game.PointXY;

/**
 * Draws a fractal using recursion
 */
public class Dragon {
    public Dragon(float x, float y){

        start = new PointXY().set(x ,y);
    }

    private final PointXY start;
    private final PointXY rotate = new PointXY().degrees(60);

    public void drawDragon(HexBatch batch){
        drawDragon(batch, start.clone(), new PointXY().degrees(-95), 1.0f, 8);
    }

    private void drawDragon(HexBatch batch, PointXY point, PointXY angle, float width, int depth){
        if(width < .2f)
            return;
        batch.drawHex(point, depth, width);
        PointXY angleTMP = angle.clone().rotate(rotate);
        drawDragon(batch, point.clone().move(angleTMP.clone(), width * .9f), angleTMP, width *.8f, ++ depth);
        drawDragon(batch, point.move(angle.rotateCC(rotate), width * .85f), angle, width * .6f, depth);

    }
}
