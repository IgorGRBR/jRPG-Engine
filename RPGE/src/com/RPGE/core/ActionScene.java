package com.RPGE.core;

import com.RPGE.action.ActionLayer;
import com.RPGE.action.Actor;
import com.RPGE.file.RPGEFile;
import org.newdawn.slick.Graphics;

import java.util.*;

public class ActionScene extends AbstractScene
{
    private ArrayList<Actor> actors;
    private ArrayList<ActionLayer> layers;
    private ActorAPI aAPI;

    ActionScene(String n, String p, ActorAPI a)
    {
        super();
        name = n;
        path = p;
        aAPI = a;
    }

    @Override
    public void load()
    {
        if (loaded) return;
        RPGEFile file = new RPGEFile(path, true);

        //Temp variables
        int current_layer_index = 0;
        HashMap<Integer, ActionLayer> temp_layers = new HashMap<>();
        ActionLayer current_layer = null;

        ArrayList<String> line = file.getLine();
        while(line != null)
        {
            if (line.get(0).charAt(0) != '#' || line.get(0).length() > 0)
            switch (line.get(0))
            {
                case "name":
                    name = line.get(1);
                    for (int i = 2; i < line.size(); i++)
                    {
                        name += " " + line.get(i);
                    }
                    break;
                case "layer":
                    current_layer_index = Integer.parseInt(line.get(1));
                    temp_layers.putIfAbsent(current_layer_index,
                            new ActionLayer());
                    current_layer = temp_layers.get(current_layer_index);
                    break;
                case "actor":
                    Actor new_actor = aAPI.scene_manager.actorFactory().buildActor(line.get(1));
                    HashMap<String, String> args = new HashMap<>();
                    for (int i = 2; i + 1 < line.size(); i+=2)
                    {
                        args.put(line.get(i), line.get(i + 1));
                    }
                    new_actor.sceneLoad(aAPI, args);
                    current_layer.add(new_actor);
                    actors.add(new_actor);
                    break;
                default:
                    System.out.println("Action scene loading error: unknown token '"
                            + line.get(0) + "'!");
                    break;
            }
        }

        List<Map.Entry<Integer, ActionLayer>> entries = new ArrayList<>(temp_layers.entrySet());
        Collections.sort(entries,
                new Comparator<Map.Entry<Integer, ActionLayer>>()
                {
                    @Override
                    public int compare(Map.Entry<Integer, ActionLayer> o1,
                                       Map.Entry<Integer, ActionLayer> o2)
                    {
                        return Integer.compare(o1.getKey(), o2.getKey());
                    }
                });

        int index = 0;
        for (Map.Entry<Integer, ActionLayer> i : entries)
        {
            for (Actor e : i.getValue().actors)
            {
                e.setLayer(index);
            }
            layers.add(i.getValue());
            index++;
        }
    }

    @Override
    public void init()
    {
        for (Actor a : actors)
        {
            a.init(aAPI);
        }
    }

    @Override
    public void step(float delta)
    {
        for (Actor a : actors)
        {
            a.step(aAPI);
        }
    }

    @Override
    public void draw(Graphics gfx)
    {
        for (ActionLayer a : layers)
        {
            a.draw(aAPI);
        }
    }
}
