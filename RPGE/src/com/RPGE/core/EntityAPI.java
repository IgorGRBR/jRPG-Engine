package com.RPGE.core;

import com.RPGE.asset.AssetManager;
import com.RPGE.asset.ImageAsset;
import com.RPGE.asset.SpriteAsset;
import com.RPGE.exception.RPGEException;
import com.RPGE.gui.GUIController;
import com.RPGE.system.PhysicsSystem;
import com.RPGE.world.Camera;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

public class EntityAPI
{
    //Entity API is where is all the magic happens

    //System variables
    Graphics gfx;
    ArrayList<Entity> entities;
    AssetManager asset_manager;
    InputState input_state;
    int tile_width, tile_height;
    int screen_tile_count_h, screen_tile_count_v; //Horizontal and vertical screen size (in tiles)
    PhysicsSystem phys_system;
    Camera camera;
    WorldController world_controller;
    GUIAPI gAPI;
    GUIController g_controller;

    EntityAPI()
    {

    }

    void setGFX(Graphics g)
    {
        gfx = g;
    }

    void setScreenSize(int h, int v)
    {
        screen_tile_count_h = h;
        screen_tile_count_v = v;
    }

    void setAssetManager(AssetManager am)
    {
        asset_manager = am;
    }

    void setInputState(InputState is)
    {
        input_state = is;
    }

    void setPhysicsSystem(PhysicsSystem ps)
    {
        phys_system = ps;
    }

    void setEntityList(ArrayList<Entity> e)
    {
        entities = e;
    }

    void setTileDimensions(int tw, int th)
    {
        tile_width = tw;
        tile_height = th;
    }

    void setCamera(Camera cam) { camera = cam; }

    void setWorldController(WorldController wc) { world_controller = wc; }

    void setGUIAPI(GUIAPI g)
    {
        gAPI = g;
    }

    void setGUIController(GUIController gc)
    {
        g_controller = gc;
    }

    public Camera getCamera() { return camera; }

    public int getTileWidth() { return tile_width; }
    public int getTileHeight() { return tile_height; }

    public void reloadWorld()
    {
        world_controller.reload(world_controller.getCurrentWorld(),
            tile_width, tile_height, this);
    }

    public void switchWorld(String world)
    {
        try
        {
            WorldScene ws = world_controller.find(world);
            world_controller.switchTo(ws);
        }
        catch (RPGEException e)
        {
            e.printStackTrace();
        }
    }

    public void drawImage(ImageAsset img, int tile_x, int tile_y)
    {
        img.drawToContextCentered(
                tile_x*tile_width + tile_width/2,
                tile_y*tile_height + tile_height/2,
                gfx
                );
    }

    public void drawImageOffset(ImageAsset img, int tile_x, int tile_y, int offset_x, int offset_y)
    {
        img.drawToContextCentered(
                tile_x*tile_width + tile_width/2 + offset_x,
                tile_y*tile_height + tile_height/2 + offset_y,
                gfx
        );
    }

    public void drawSprite(SpriteAsset spr, int tile_x, int tile_y, int idx, int idy)
    {
        spr.drawToContext(
                tile_x*tile_width + tile_width/2,
                tile_y*tile_height + tile_height/2,
                idx, idy,
                gfx
        );
    }

    public void drawSpriteOffset(SpriteAsset spr,
                           int tile_x, int tile_y,
                           int offset_x, int offset_y,
                           int idx, int idy)
    {
        spr.drawToContextCentered(
                tile_x*tile_width + tile_width/2 + offset_x,
                tile_y*tile_height + tile_height/2 + offset_y,
                idx, idy,
                gfx
        );
    }

    public void drawImageFree(ImageAsset img, int x, int y)
    {
        img.drawToContextCentered(x, y, gfx);
    }

    public void drawImageFreeOffset(ImageAsset img, int x, int y, int offset_x, int offset_y)
    {
        img.drawToContextCentered(x + offset_x, y + offset_y, gfx);
    }

    public void drawSpriteFree(SpriteAsset spr,
                           int x, int y,
                           int idx, int idy)
    {
        spr.drawToContext(x, y, idx, idy, gfx);
    }

    public void drawSpriteFreeOffset(SpriteAsset spr,
                               int x, int y,
                               int offset_x, int offset_y,
                               int idx, int idy)
    {
        spr.drawToContext(x + offset_x, y + offset_y, idx, idy, gfx);
    }

    public void drawText(String str, int pos_x, int pos_y)
    {
        gfx.drawString(str, pos_x, pos_y);
    }

    public void drawRectangle(int x, int y, int w, int h, Color c)
    {
        gfx.setColor(c);
        gfx.drawRect(x, y, w, h);
    }

    public AssetManager assetManager() { return asset_manager; }

    public void move(Entity e, Direction d)
    {
        int dx = 0, dy = 0;
        switch (d)
        {
            case UP:
                dy = -1;
                break;
            case DOWN:
                dy = 1;
                break;
            case LEFT:
                dx = -1;
                break;
            case RIGHT:
                dx = 1;
                break;
        }
        phys_system.moveEvent(e, dx, dy);
    }

    public void translate(Entity e, int x, int y)
    {
        e.setPosX(x, tile_width);
        e.setPosY(y, tile_height);
    }

    public void translateDelta(Entity e, int dx, int dy)
    {
        e.setPosX(e.getPosX() + dx, tile_width);
        e.setPosY(e.getPosY() + dy, tile_height);
    }

    public boolean keyDown(String key) { return input_state.isDown(key); }
    public boolean keyPressed(String key) { return input_state.isPressed(key); }
    public boolean keyReleased(String key) { return input_state.isReleased(key); }
}
