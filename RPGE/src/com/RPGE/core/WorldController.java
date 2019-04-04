package com.RPGE.core;

import com.RPGE.asset.AssetManager;
import com.RPGE.exception.RPGEException;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WorldController
{
    private LinkedList<WorldScene> worlds;
    WorldScene current_world;
    private EntityFactory entity_factory;

    public WorldController(EntityFactory ef)
    {
        worlds = new LinkedList<>();
        entity_factory = ef;
    }

    public WorldController(List<WorldScene> scenes, EntityFactory ef)
    {
        worlds = new LinkedList<>(scenes);
        entity_factory = ef;
    }

    public EntityFactory entityFactory() { return entity_factory; }

    public void setCurrentWorld(int i)
    {
        current_world = worlds.get(i);
    }

    public WorldScene getCurrentWorld() { return current_world; }

    public void add(WorldScene ws)
    {
        worlds.add(ws);
    }

    public void add(String path, int tw, int th, EntityAPI eAPI)
    {
        WorldScene ws = new WorldScene("", path, tw, th, eAPI);
        worlds.add(ws);
    }

    public void load(int i, AssetManager am)
    {
        worlds.get(i).load(am, entity_factory);
    }

    public void load(AssetManager am)
    {
        for (WorldScene w : worlds)
        {
            w.load(am, entity_factory);
        }
    }

    public WorldScene find(String name) throws RPGEException
    {
        for (WorldScene w : worlds)
        {
            if (w.getName().equals(name))
                return w;
        }
        throw new RPGEException("Scene '" + name + "' was not found!");
    }

    public void remove(WorldScene ws)
    {
        worlds.remove(ws);
    }

    public void reload(WorldScene ws, int tw, int th, EntityAPI eAPI, AssetManager am)
    {
        worlds.remove(ws);
        ws = new WorldScene(ws.name, ws.path, tw, th, eAPI);
        worlds.add(ws);
        ws.load(am, entity_factory);
        current_world = ws;
        init();
    }

    public void switchTo(WorldScene ws)
    {
        current_world = ws;
        init();
    }

    public void init()
    {
        current_world.init();
    }

    public void step(float delta)
    {
        current_world.step(delta);
    }

    public void draw(Graphics gfx)
    {
        current_world.draw(gfx);
    }

}
