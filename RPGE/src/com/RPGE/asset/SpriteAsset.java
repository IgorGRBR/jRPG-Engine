package com.RPGE.asset;

import com.RPGE.exception.RPGEException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SpriteAsset extends DrawableAsset
{
    private SpriteSheet sheet;

    private int sheet_width, sheet_height;
    private int col_count, row_count;
    private int img_width, img_height;

    public SpriteAsset() { super(); }

    public SpriteAsset(String n, String p, int r, int c)
    {
        super(n, p);
        row_count = r;
        col_count = c;
    }

    public SpriteAsset(String n, String p, boolean l, int r, int c)
    {
        super(n, p);
        row_count = r;
        col_count = c;
        if (l)
        {
            load();
        }
    }

    @Override
    protected void draw(int x, int y)
    {
        draw(x, y, 0, 0);
    }

    @Override
    protected void drawToContext(int x, int y, Graphics gfx)
    {
        drawToContext(x, y, 0, 0, gfx);
    }

    @Override
    protected void load()
    {
        try
        {
            if (path == "" || path == null) throw new RPGEException("ImageAsset: path is empty!");
            Image img = new Image(path);
            int tw = img.getWidth() / row_count;
            int th = img.getHeight() / col_count;
            sheet = new SpriteSheet(img, tw, th);
            //col_count = sheet.getHorizontalCount();
            //row_count = sheet.getVerticalCount();
            img_width = sheet.getSubImage(0, 0).getWidth();
            img_height = sheet.getSubImage(0, 0).getHeight();
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

    public final void draw(int x, int y, int r, int c)
    {
        sheet.getSubImage(r, c).draw(x - img_width/2, y - img_height/2);
    }

    public final void drawToContext(int x, int y, int r, int c, Graphics gfx)
    {
        gfx.drawImage(sheet.getSubImage(r, c), x - img_width/2, y - img_height/2);
    }
}
