package com.RPGE.world;

import com.RPGE.core.Entity;

public class Camera
{
    private int TILE_WIDTH, TILE_HEIGHT;
    private int TILE_COUNT_H, TILE_COUNT_V; //Screen width and height in tiles!
    private int screen_width, screen_height;

    //Camera control
    private int boundary_x, boundary_y;
    private float x, y;
    private int offset_x, offset_y;
    private float hspeed, vspeed; //Maximum movement speeds
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

        hspeed = 0;
        vspeed = 0;

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
            moveFlat(
                    focus.getRealPosX() + TILE_WIDTH/2,
                    focus.getRealPosY() + TILE_HEIGHT/2);
            moveSmooth(
                    focus.getRealPosX() + TILE_WIDTH/2,
                    focus.getRealPosY() + TILE_HEIGHT/2);
        }
    }

    public float getX() { return x + offset_x; }
    public float getY() { return y + offset_y; }

    public int getCornerX() { return (int)getX() - screen_width/2; }
    public int getCornerY() { return (int)getY() - screen_height/2; }

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
        move((int)((ix - x) * smoothing),
                (int)((iy - y) * smoothing));
    }

    private void moveFlat(int ix, int iy)
    {
        if (((ix + offset_x >= screen_width/2
                && ix + offset_x <= boundary_x - screen_width/2)
                && Math.abs((ix - x)) > hspeed*TILE_WIDTH))
        {
            move((Math.signum(ix - x) * hspeed * TILE_WIDTH), 0);
        }

        if((iy + offset_y >= screen_height/2
                && iy + offset_y <= boundary_y - screen_height/2)
                && Math.abs((iy - y)) > vspeed*TILE_HEIGHT)
        {
            move(0, (Math.signum(iy - y) * vspeed * TILE_HEIGHT));
        }
    }

    public void setOffset(int ix, int iy)
    {
        offset_x = ix;
        offset_y = iy;
    }

    public void setSpeed(float h, float v)
    {
        hspeed = h;
        vspeed = v;
    }

    public void setSmoothing(float factor)
    {
        smoothing = factor;
    }
}
