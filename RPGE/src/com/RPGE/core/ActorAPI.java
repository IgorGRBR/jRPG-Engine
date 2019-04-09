package com.RPGE.core;

import com.RPGE.action.ActorFactory;
import com.RPGE.asset.AssetManager;
import com.RPGE.asset.ImageAsset;
import com.RPGE.gui.GUIController;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ActorAPI
{
    SceneManager scene_manager;
    Graphics gfx;
    AssetManager asset_manager;
    InputState input_state;
    int SCREEN_WIDTH, SCREEN_HEIGHT;
    GUIAPI gAPI;
    GUIController g_controller;

    public ActorAPI(int sw, int sh)
    {
        SCREEN_WIDTH = sw;
        SCREEN_HEIGHT = sh;
    }

    void setSceneManager(SceneManager sm)
    {
        scene_manager = sm;
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
    void setGUIAPI(GUIAPI g)
    {
        gAPI = g;
    }
    void setGUIController(GUIController gc)
    {
        g_controller = gc;
    }

    public AssetManager assetManager() { return asset_manager; }
    //public SceneManager sceneManager() { return scene_manager; }

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

}
