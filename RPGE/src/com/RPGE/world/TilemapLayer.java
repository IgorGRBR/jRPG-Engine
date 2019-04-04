package com.RPGE.world;

import com.RPGE.asset.TilesetAsset;
import org.newdawn.slick.Graphics;

public class TilemapLayer
{
    private TilemapChunk[][] map;
    private TilesetAsset tileset;
    private int size_x, size_y; //Size of the layer (in chunks)
    private int offset_x, offset_y; //Offset of the entire layer (...in chunks?)
    private int tile_width, tile_height; //Size of a single tile
    private int chunk_width, chunk_height; //Size of a single chunk (...in tiles)
    private boolean initialized;

    public TilemapLayer(int sx, int sy, int tx, int ty, int cx, int cy, TilesetAsset t)
    {
        size_x = sx;
        size_y = sy;
        offset_x = 0;
        offset_y = 0;
        tile_width = tx;
        tile_height = ty;
        chunk_width = cx;
        chunk_height = cy;
        map = new TilemapChunk[sx][sy];
        tileset = t;
    }

    public int offsetX() { return offset_x; }
    public int offsetY() { return offset_y; }

    public int offsetX(int x) { offset_x += x; return offset_x; }
    public int offsetY(int y) { offset_y += y; return offset_y; }

    public int getSizeX() { return size_x; }
    public int getSizeY() { return size_y; }

    public void set(int x, int y, TilemapChunk tc)
    {
        map[x-offset_x][y-offset_y] = tc;
    }

    public void setDirect(int x, int y, TilemapChunk tc)
    {
        map[x][y] = tc;
    }

    public void init()
    {
        map = new TilemapChunk[size_x][size_y];
        for(int i = 0; i < size_x; i++)
        {
            for (int j = 0; j < size_y; j++)
            {
                map[i][j] = new TilemapChunk(chunk_width, chunk_height);
            }
        }
        initialized = true;
    }

    public TilemapChunk get(int x, int y)
    {
        return map[x][y];
    }

    public void compileChunks(Graphics gfx)
    {
        if (!initialized) return;
        for (int x = 0; x < size_x; x++)
        {
            for (int y = 0; y < size_y; y++)
            {
                map[x][y].compile(tile_width, tile_height, tileset, gfx);
                gfx.clear();
            }
        }
    }

    public void draw(Graphics gfx, Camera cam)
    {
        int final_size_x = tile_width*chunk_width;
        int final_size_y = tile_height*chunk_height;
        for (int x = 0; x < size_x; x++)
        {
            for (int y = 0; y < size_y; y++)
            {
                map[x][y].draw((x + offset_x)*final_size_x, (y + offset_y)*final_size_y, gfx);
            }
        }
    }
}
