package com.RPGE.asset;

import com.RPGE.exception.RPGEException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SlickException;

public class TilesetAsset extends DrawableAsset
{
    private SpriteSheet tileset;

    private int tile_width, tile_height;
    private int col_count, frame_count;

    public TilesetAsset()
    {
        super();
    }

    public TilesetAsset(String n, String p, int tw, int th)
    {
        super(n, p);
        tile_width = tw;
        tile_height = th;
    }

    public TilesetAsset(String n, String p, boolean l, int tw, int th)
    {
        super(n, p);

        tile_width = tw;
        tile_height = th;
        if (l)
        {
            load();
        }
    }

    @Override
    public void draw(int x, int y)
    {
        draw(x, y, 0);
    }

    @Override
    protected void drawCentered(int x, int y)
    {

    }

    @Override
    public void drawToContext(int x, int y, Graphics gfx)
    {
        drawToContext(x, y, 0, gfx);
    }

    @Override
    protected void drawToContextCentered(int x, int y, Graphics gfx)
    {

    }

    public void draw(int x, int y, int ind)
    {
        if (ind >= 0 && ind < frame_count)
        {
            int cx = ind % col_count;
            int cy = ind / col_count;
            tileset.getSubImage(cx, cy).draw(x, y);
        }
    }

    public void drawToContext(int x, int y, int ind, Graphics gfx)
    {
        if (ind >= 0 && ind < frame_count)
        {
            int cx = ind % col_count;
            int cy = ind / col_count;
            gfx.drawImage(tileset.getSubImage(cx, cy), x, y);
        }
    }

    @Override
    protected void load()
    {
        try
        {
            if (path == "" || path == null) throw new RPGEException("ImageAsset: path is empty!");
            tileset = new SpriteSheet(path, tile_width, tile_height);
            col_count = tileset.getHorizontalCount();
            frame_count = col_count * tileset.getVerticalCount();
        }
        catch(SlickException e)
        {
            e.printStackTrace();
        }
        catch (RPGEException e)
        {
            e.printStackTrace();
        }
    }
}
