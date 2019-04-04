package com.RPGE.world;

import com.RPGE.asset.TilesetAsset;
import com.RPGE.exception.RPGEException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TilemapChunk
{
    //private int tilesize_x, tilesize_y; //Size of a single tile
    private int size_x, size_y; //Size of a chunk (in tiles)
    public int pos_x, pos_y; //Global position of given chunk (...in chunks?)
    private Image image;
    private boolean is_empty;
    private int[][] data;


    public TilemapChunk(int sx, int sy)
    {
        //this.tilesize_x = tx;
        //this.tilesize_y = ty;
        size_x = sx;
        size_y = sy;
        is_empty = true;
        data = new int[sx][sy];
    }

    public void setPos(int x, int y)
    {
        pos_x = x;
        pos_y = y;
    }

    public void setData(int[][] input)
    {
        try
        {
            //Check if data is valid
            RPGEException e = new RPGEException("TilemapChunk: input data size mismatch!");
            if (data.length == size_x)
            {
                for (int[] d : data)
                {
                    if (d.length != size_y)
                    {
                        throw e;
                    }
                }
            }
            else
            {
                throw e;
            }

            data = input;
        }
        catch (RPGEException e)
        {
            System.out.println(e.toString());
        }
    }

    public void compile(int tx, int ty, TilesetAsset tileset, Graphics gfx)
    {
        try
        {
            //Generate final image
            image = new Image(tx*this.size_x, ty*this.size_y);

            for (int x = 0; x < size_x; x++)
            {
                for (int y = 0; y < size_y; y++)
                {
                    tileset.drawToContext(x*tx, y*ty, data[x][y], gfx);
                    //tileset.draw(x*tx, y*ty, data[x][y]);
                }
            }

            gfx.copyArea(image, 0, 0);
            gfx.flush();
            image = image.getFlippedCopy(false, true);//Because slick

            is_empty = false;
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }

    public void draw(int px, int py, Graphics gfx) //Specify draw in pixels!
    {
        //if (!is_empty) image.draw(px, py);
        if (!is_empty) gfx.drawImage(image, px, py);
    }
}
