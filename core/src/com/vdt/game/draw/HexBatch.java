package com.vdt.game.draw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vdt.game.PointXY;
import com.vdt.game.control.Button;
import com.vdt.game.HexXYZ;


public class HexBatch extends MyBatch {
    public final TextureRegion hex, newgame, undo;
    public final TextureRegion[] numbers;

    final float ratio = (float)Math.sqrt(3) / 2f;

    public static ColorSource colors;


    public HexBatch() {
        colors = new ColorSource();

        AtlasReader atlas = new AtlasReader("images");
        hex = atlas.getRegion("hex");
        undo = atlas.getRegion("undo");
        newgame = atlas.getRegion("newgame");

        numbers= new TextureRegion[10];
        for(int i = 0; i< 10; i++){
            numbers[i] = atlas.getRegion("" + i);
        }
    }

    public void setPackedColor(int idx){
        setPackedColor( colors.shadow(idx));
    }

    public void drawScore(final int score){
        final float x = 2.3f;
        float y = 3 / (float)Gdx.graphics.getWidth() * (float)Gdx.graphics.getHeight() - .7f;
        int s = score;
        float tmp = x;
        setPackedColor(shadow);
        do {
            drawShadow(hex, tmp, y, 1, 1, 1);
            tmp -= .4f;
        } while((s/=10) > 0);

        setPackedColor(color);
        s = score;
        tmp = x;

        do {
            draw(hex, tmp, y, 1, 1);
            tmp -= .2f;
            if(s >= 10)
            draw(hex, tmp, y, 1, 1);
            tmp -= .2f;
        } while((s/=10) > 0);


        setPackedColor(colors.background);
        s = score;
        tmp = x;

        do {
            draw(hex, tmp, y, .9f, .9f);
            tmp-= .2f;
            if(s >= 10)
            draw(hex, tmp, y, .9f, .9f);
            tmp-= .2f;

        } while((s/=10) > 0);

            s = score;
            tmp = x;
        do {
            drawInt(s % 10, tmp, y, 1f);
            tmp -= .4f;
        } while((s /= 10) > 0);

    }

    public void drawHexValue(float x, float y, int colorIdx){
        drawHexValue(x, y, colorIdx, 1);
    }

    private final float hexScreenX(float x){
        return -x * ratio;
    }
    private final float hexScreenY(float x, float y){
        return (-x / 2 + y);
    }

    public void drawHexValue(float x, float y, int colorIdx, float width){
        if(colorIdx == 0) return;
        y = hexScreenY(x, y);
        x = hexScreenX(x);
        drawHex(x, y, colorIdx, width );
        drawNumber(colorIdx, x, y, width);
    }

    public void drawHex(PointXY point, int colorIdx, float width){
        setColor(colorIdx);
        drawShadow(hex, point.x(), point.y(), width, width ,width);
    }

    public void drawHex(float x, float y, int colorIdx, float width){
        setColor(colorIdx);
        drawShadow(hex, x , y , width, width, width);
        if(width > .3f) {
            setPackedColor(colors.background);
            draw(hex, x, y, .9f * width, .9f * width);
        }
    }

    public void drawNumber(int number, float x, float y, float multi){
        x-=.01f * multi;
        final float shift = .18f * multi;
        if(number > 9)
            x += shift;

        drawInt(number % 10, x, y, multi);
        if(number > 9) {
            x -= 2 * shift;
            drawInt(number / 10, x, y, multi);
        }
    }

    public void drawButton(Button button, TextureRegion region){
        drawHex(button.x(), button.y(), -1, 1.3f);
        drawShadow(region, button.x(), button.y(), .9f, .9f, .3f);
    }

    public void drawIndicator(HexXYZ direction){
        if(direction.getX() == 0 && direction.getY() == 0)
            return;

        float dx = direction.getX();
        float dy = direction.getY();

        float multi = 3f;
        float x = multi * hexScreenX(dx);
        float y = multi * hexScreenY(dx, dy);

        drawHex(x + .5f * hexScreenX(-dy), y + .5f * hexScreenY(-dy, -dy + dx),-1, .3f);
        drawHex(x + .5f * hexScreenX(-dx+ dy), y + .5f * hexScreenY(-dx + dy, -dx),-1, .3f);

        drawHex(x, y, -1, .6f);
    }
    private final void drawShadow(TextureRegion region, float x, float y, float width, float height, float multi){
        setPackedColor(shadow);
        draw(region, x - .06f * multi, y - .06f * multi, width, height);
        setPackedColor(color);
        draw(region, x, y, width, height);
    }
    public void setColor(int colorIdx){
        if(colorIdx < 0)
            return;
        shadow = colors.shadow(colorIdx);
        color  = colors.color(colorIdx);
    }

    private float shadow, color;


    void drawInt(int num, float x, float y, float multi){
        drawShadow(numbers[num], x, y, .35f * multi, .45f * multi,multi * .5f);
    }




    @Override
    public void dispose(){
        super.dispose();
        hex.getTexture().dispose();
    }

    public final void resize(int screenX, int screenY){
        super.setSize(6, 6 / (float)screenX * screenY);
    }
}
