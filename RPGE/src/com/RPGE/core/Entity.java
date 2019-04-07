package com.RPGE.core;

import java.util.List;

public abstract class Entity
{
    public Entity(boolean v, boolean a, boolean s)
    {
        visible = v;
        active = a;
        solid = s;
        removed = false;
    };

    private int x, y; // x and y positions on the world grid (in pixels)
    private int tile_x, tile_y; // x and y positions on the world grid (in tiles)
    int layer; // layer entity will be drawn in
    boolean visible, active, solid, removed; // event activity flags

    public boolean isVisible() { return visible; }
    public boolean isActive() { return active; }
    public boolean isSolid() { return solid; }
    public boolean isRemoved() { return removed; }
    public int getLayer() { return layer; }

    void setPosX(int ix, int tile_width)
    {
        x = ix * tile_width + tile_width/2;
        tile_x = ix;
    }
    void setPosY(int iy, int tile_height)
    {
        y = iy * tile_height + tile_height/2;
        tile_y = iy;
    }
    void setRealPosX(int ix, int tile_width)
    {
        x = ix;
        tile_x = (int)Math.floor((float)ix/(float)tile_width);
    }
    void setRealPosY(int iy, int tile_height)
    {
        y = iy;
        tile_y = (int)Math.floor((float)iy/(float)tile_height);
    }

    public int getPosX() { return tile_x; }
    public int getPosY() { return tile_y; }
    public int getRealPosX() { return x; }
    public int getRealPosY() { return y; }

    //Events
    public abstract void init(EntityAPI eAPI);
    public abstract void worldLoad(EntityAPI eAPI, List<String> args);
    public abstract void levelCollision(EntityAPI eAPI, int tile_data);
    public abstract void step(EntityAPI eAPI);
    public abstract void draw(EntityAPI eAPI);
}
