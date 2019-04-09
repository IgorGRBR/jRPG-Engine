package com.RPGE.core;

import com.RPGE.action.ActorFactory;
import com.RPGE.asset.AssetManager;
import com.RPGE.exception.RPGEException;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SceneManager
{
    private LinkedList<WorldScene> worlds;
    private LinkedList<ActionScene> action_scenes;
    WorldScene current_world;
    ActionScene current_action;
    AbstractScene current_scene;
    private EntityFactory entity_factory;
    private ActorFactory actor_factory;

    public SceneManager(EntityFactory ef, ActorFactory af)
    {
        worlds = new LinkedList<>();
        action_scenes = new LinkedList<>();
        entity_factory = ef;
    }

    public SceneManager(List<WorldScene> w, List<ActionScene> a, EntityFactory ef, ActorFactory af)
    {
        worlds = new LinkedList<>(w);
        action_scenes = new LinkedList<>(a);
        entity_factory = ef;
        actor_factory = af;
    }

    public EntityFactory entityFactory() { return entity_factory; }
    public ActorFactory actorFactory() { return actor_factory; }

    public void setCurrentWorld(int i)
    {
        current_world = worlds.get(i);
    }

    public WorldScene getCurrentWorld() { return current_world; }

    public void setCurrentActionScene(int i)
    {
        current_action = action_scenes.get(i);
    }

    public ActionScene getCurrentActionScene() { return current_action; }

    public void add(WorldScene ws)
    {
        worlds.add(ws);
    }
    public void add(ActionScene as)
    {
        action_scenes.add(as);
    }

    public void addWorld(String path, int tw, int th, EntityAPI eAPI)
    {
        WorldScene ws = new WorldScene("", path, tw, th, eAPI);
        worlds.add(ws);
    }

    public void addActionScene(String path)
    {
        //TODO: add action scenes
    }

    public void load(int i)
    {
        action_scenes.get(i).load();
        worlds.get(i).load();
    }

    public void load()
    {
        for (WorldScene w : worlds)
        {
            w.load();
        }
        for (ActionScene a : action_scenes)
        {
            a.load();
        }
    }

    public void addWorlds(List<String> paths, int tw, int th, EntityAPI eAPI)
    {
        for (String s : paths)
        {
            WorldScene ws = new WorldScene("", s, tw, th, eAPI);
            add(ws);
        }
    }

    public void addActionScenes(List<String> paths, ActorAPI aAPI)
    {
        for (String s : paths)
        {
            ActionScene as = new ActionScene("", s, aAPI);
            add(as);
        }
    }

    public WorldScene findWorld(String name) throws RPGEException
    {
        for (WorldScene w : worlds)
        {
            if (w.getName().equals(name))
                return w;
        }
        throw new RPGEException("World '" + name + "' was not found!");
    }

    public void remove(WorldScene ws)
    {
        worlds.remove(ws);
    }
    public void remove(ActionScene as)
    {
        action_scenes.remove(as);
    }

    public void reload(WorldScene ws, int tw, int th, EntityAPI eAPI)
    {
        worlds.remove(ws);
        ws = new WorldScene(ws.name, ws.path, tw, th, eAPI);
        worlds.add(ws);
        ws.load();
        current_world = ws;
        init();
    }

    public void reload(ActionScene as)
    {
        //TODO: reload action scenes
    }

    public void switchTo(AbstractScene s)
    {
        current_scene = s;
        init();
    }

    public void switchTo(WorldScene ws)
    {
        current_world = ws;
        current_scene = ws;
        init();
    }

    public void switchTo(ActionScene as)
    {
        current_action = as;
        current_scene = as;
        init();
    }

    public void switchToWorld() { current_scene = current_world; }
    public void switchToActionScene() { current_scene = current_action; }

    public void init()
    {
        current_scene.init();
    }

    public void step(float delta)
    {
        current_scene.step(delta);
    }

    public void draw(Graphics gfx)
    {
        current_scene.draw(gfx);
    }

}
