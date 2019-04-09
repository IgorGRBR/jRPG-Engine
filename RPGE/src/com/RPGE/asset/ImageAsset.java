package com.RPGE.asset;

import com.RPGE.exception.RPGEException;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.awt.*;

public class ImageAsset extends DrawableAsset
{
    private Image img;

    public ImageAsset()
    {
        super();
    }

    public ImageAsset(String n, String p)
    {
        super(n, p);
    }

    public ImageAsset(String n, String p, boolean l)
    {
        super(n, p);
        if (l)
        {
            load();
        }
    }

    @Override
    public void draw(int x, int y)
    {
        img.draw(x, y);
    }

    @Override
    public void drawCentered(int x, int y)
    {
        img.drawCentered(x, y);
    }

    @Override
    public void drawToContext(int x, int y, Graphics gfx)
    {
        gfx.drawImage(img, x, y);
    }

    @Override
    public void drawToContextCentered(int x, int y, Graphics gfx)
    {
        gfx.drawImage(img, x - width/2, y - height/2);
    }

    @Override
    protected void load()
    {
        try
        {
            if (path == "" || path == null) throw new RPGEException("ImageAsset: path is empty!");
            img = new Image(path);
            width = img.getWidth();
            height = img.getHeight();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
        catch (RPGEException e)
        {
            e.printStackTrace();
        }
    }
}
