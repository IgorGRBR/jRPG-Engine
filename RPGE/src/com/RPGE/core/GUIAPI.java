package com.RPGE.core;

import com.RPGE.asset.AssetManager;
import com.RPGE.asset.ImageAsset;
import com.RPGE.core.EntityAPI;
import com.RPGE.exception.RPGEException;
import com.RPGE.gui.GUIController;
import com.RPGE.gui.GUIElement;
import com.RPGE.gui.GUILayout;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class GUIAPI
{
    int TILE_WIDTH, TILE_HEIGHT;
    int SCREEN_WIDTH, SCREEN_HEIGHT;
    Graphics gfx;
    AssetManager asset_manager;
    InputState input_state;
    GUIController g_controller;

    public GUIAPI(int tw, int th, int sw, int sh)
    {
        TILE_WIDTH = tw;
        TILE_HEIGHT = th;
        SCREEN_WIDTH = sw;
        SCREEN_HEIGHT = sh;
    }

    void setGFX(Graphics g)
    {
        gfx = g;
    }

    void setAssetManager(AssetManager am)
    {
        asset_manager = am;
    }

    void setInputState(InputState is)
    {
        input_state = is;
    }

    void setGUIController(GUIController gc)
    {
        g_controller = gc;
    }

    public void setCurrentLayout(GUILayout l)
    {
        g_controller.setCurrentLayout(l);
    }

    public AssetManager assetManager() { return asset_manager; }

    public int getTileWidth() { return TILE_WIDTH; }
    public int getTileHeight() { return TILE_HEIGHT; }
    public int getScreenWidth() { return SCREEN_WIDTH; }
    public int getScreenHeight() { return SCREEN_HEIGHT; }

    public GUILayout findLayout(String name)
    {
        try
        {
            return g_controller.find(name);
        }
        catch (RPGEException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public GUIElement findElementById(String id)
    {
        try
        {
            return g_controller.getCurrentLayout().findElement(id);
        }
        catch (RPGEException e)
        {
            //e.printStackTrace();
            return null;
        }
    }

    public Color getColor(String color)
    {
        switch(color)
        {
            case "black":
                return Color.black;
            case "white":
                return Color.white;
            case "blue":
                return Color.blue;
            case "red":
                return Color.red;
            case "green":
                return Color.green;
            default:
                return null;
        }
    }

    public void drawImage(ImageAsset img, int x, int y)
    {
        img.drawToContext(x, y, gfx);
    }

    public void drawRectangle(int x, int y, int w, int h, Color c)
    {
        gfx.setColor(c);
        gfx.drawRect(x, y, w, h);
    }

    public void drawText(String text, int x, int y)
    {
        gfx.drawString(text, x, y);
    }

    public void drawText(String text, int x, int y, int max_w, int line_h)
    {
        int str_w = getStringWidth(text);
        int str_h = getStringHeight(text);
        int line_c = (int) Math.ceil(str_w/max_w);
        int max_c = (int) Math.floor(str_w/line_c);
        for (int i = 0; i < line_c-1; i++)
        {
            drawText(text.substring(i * str_w, (i+1) * str_w), x, y + (str_h + line_h) * i);
        }
    }

    public int getStringWidth(String text)
    {
        return gfx.getFont().getWidth(text);
    }
    public int getStringHeight(String text)
    {
        return gfx.getFont().getHeight(text);
    }


}
