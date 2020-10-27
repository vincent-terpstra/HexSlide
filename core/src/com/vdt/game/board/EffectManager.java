package com.vdt.game.board;

import com.vdt.game.draw.HexBatch;

import java.util.Vector;

public class EffectManager {
    final float duration = .3f;
    private float timer = 0;
    private Vector<Effect> effects;

    public boolean active(){
        return timer > 0f;
    }

    public void update(float delta){
        if(timer > 0)
            timer -= delta;
    }

    public void draw(HexBatch batch){
        for(Effect e : effects){
            e.draw(batch);
        }
    }

    public void reset(){
        timer = duration; //duration of the effect
        effects = new Vector<>();
    }

    public void zeroTime(){
        timer = 0;
    }

    void addStationary(final float x, final float y, final int number){
        effects.add(new Effect(){
            @Override
            public void draw(HexBatch batch){
                batch.drawHexValue(x, y, number);
            }
        });
    }

    void addRandom(final float x, final float y, final int number){
        effects.add(0, new Effect(){
            @Override
            public void draw(HexBatch batch){
                batch.drawHexValue(x, y, number, 1 - timer / duration);
            }
        });
    }

    void addShift(final float x0, final float y0, final float x1, final float y1, final int number){
        effects.add(new Effect(){
            @Override
            public void draw(HexBatch batch){
                float dx = x1 - x0;
                float dy = y1 - y0;

                float shift = 1 - timer / duration;

                batch.drawHexValue(x0 + dx * shift, y0 + dy * shift, number);
            }
        });
    }
    void addIncrease(final float x0, final float y0, final float x1, final float y1, final int number){
        effects.add(new Effect(){
            @Override
            public void draw(HexBatch batch){
                float dx = x1 - x0;
                float dy = y1 - y0;

                float shift = 1 - timer / duration;
                batch.drawHexValue(x0 + dx * shift, y0 + dy * shift, number);
                batch.drawHexValue(x0 + dx * shift, y0 + dy * shift, number + 1, shift);
            }
        });
    }

    interface Effect {
        void draw(HexBatch batch);
    }
}
