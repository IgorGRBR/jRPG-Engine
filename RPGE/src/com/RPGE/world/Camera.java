package com.RPGE.world;

import com.RPGE.core.Entity;
import org.lwjgl.Sys;

public class Camera
{
    private int TILE_WIDTH, TILE_HEIGHT;
    private int TILE_COUNT_H, TILE_COUNT_V; //Screen width and height in tiles!
    private int screen_width, screen_height;

    //Camera control
    private int boundary_x, boundary_y;
    private float x, y;
    private float offset_x, offset_y;
    private float smoothing; //Smoothing factor

    private Entity focus;

    public Camera(int tw, int th, int tch, int tcv, int sw, int sh)
    {
        TILE_WIDTH = tw;
        TILE_HEIGHT = th;
        TILE_COUNT_H = tch;
        TILE_COUNT_V = tcv;
        screen_width = TILE_WIDTH * TILE_COUNT_H;
        screen_height = TILE_HEIGHT * TILE_COUNT_V;

        boundary_x = TILE_WIDTH * sw;
        boundary_y = TILE_HEIGHT * sh;

        x = 0;
        y = 0;
        offset_x = 0;
        offset_y = 0;

        smoothing = 1.0f;
    }

    public void setFocus(Entity f)
    {
        focus = f;
    }

    public void update()
    {
        if (focus != null)
        {
            moveSmooth(
                    focus.getRealPosX(),
                    focus.getRealPosY());
        }
    }

    public float getX() { return Math.round(x + offset_x); }
    public float getY() { return Math.round(y + offset_y); }

    public int getCornerX() { return Math.round(getX() - screen_width/2.0f); }
    public int getCornerY() { return Math.round(getY() - screen_height/2.0f); }

    public void interp(float pre_x, float pre_y, float x_factor, float y_factor)
    {
        setPos(x - (x - pre_x) * x_factor, y - (y - pre_y) * y_factor);
    }

    public void setPos(float ix, float iy)
    {
        x = Math.max(screen_width/2, Math.min( boundary_x - screen_width/2, ix + offset_x));
        y = Math.max(screen_height/2, Math.min( boundary_y - screen_height/2, iy + offset_y));
    }

    private void move(float ix, float iy)
    {
        setPos(x + ix, y + iy);
    }

    private void moveSmooth(int ix, int iy)
    {
        move(((ix - x) * smoothing),
                ((iy - y) * smoothing));
    }

    public void setOffset(float ix, float iy)
    {
        offset_x = ix;
        offset_y = iy;
    }

    public void setSmoothing(float factor)
    {
        smoothing = factor;
    }
}
