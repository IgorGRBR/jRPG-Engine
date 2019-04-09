package com.RPGE.gui;

import com.RPGE.core.GUIAPI;
import com.RPGE.exception.RPGEException;
import com.RPGE.file.RPGEFile;

import java.util.*;

public class GUIController
{
    LinkedList<GUILayout> layouts;
    GUILayout current_layout;
    GUILayout empty_layout;
    private int screen_width, screen_height;
    private int current_layout_index;
    GUIAPI gAPI;
    GUIElementFactory ge_factory;

    public GUIController(GUIAPI ga, GUIElementFactory gef)
    {
        gAPI = ga;
        ge_factory = gef;
        screen_width = 0;
        screen_height = 0;
        current_layout_index = 0;
        empty_layout = new GUILayout(new ArrayList<>(), new ArrayList<>());
        current_layout = empty_layout;
        layouts = new LinkedList<>();
    }

    public GUILayout load(String path)
    {
        RPGEFile file = new RPGEFile(path, true);

        //Temp variables
        GUILayer current_layer = null;
        HashMap<Integer, GUILayer> temp_layers = new HashMap<>();
        ArrayList<GUIElement> elements = new ArrayList<>();
        String layout_name = "";
        int current_layer_index = -1;

        ArrayList<String> line = file.getLine();
        while(line != null)
        {
            if (line.get(0).charAt(0) != '#' && line.get(0).length() > 0)
                switch (line.get(0))
                {
                    case "name":
                        layout_name = line.get(1);
                        for (int i = 2; i < line.size(); i++)
                        {
                            layout_name += " " + line.get(i);
                        }
                        break;
                    case "layer":
                        current_layer_index = Integer.parseInt(line.get(1));
                        temp_layers.putIfAbsent(current_layer_index, new GUILayer());
                        current_layer = temp_layers.get(current_layer_index);
                        break;
                    case "element":
                        GUIElement new_element = ge_factory.buildElement(line.get(1));
                        HashMap<String, String> args = new HashMap<>();
                        for (int i = 2; i + 1 < line.size(); i+=2)
                        {
                            args.put(line.get(i), line.get(i + 1));
                        }
                        new_element.onGUILoad(gAPI, args);
                        current_layer.add(new_element);
                        elements.add(new_element);
                        break;
                    default:
                        System.out.println("Layout loading error: unknown token '"
                                + line.get(0) + "'!");
                        break;
                }
            line = file.getLine();
        }

        ArrayList<GUILayer> final_layers = new ArrayList<>();
        List<Map.Entry<Integer, GUILayer>> entries =
                new ArrayList<>(temp_layers.entrySet());

        Collections.sort(entries,
                new Comparator<Map.Entry<Integer, GUILayer>>()
                {
                    @Override
                    public int compare(Map.Entry<Integer, GUILayer> o1,
                                       Map.Entry<Integer, GUILayer> o2)
                    {
                        return Integer.compare(o1.getKey(), o2.getKey());
                    }
                });
        int index = 0;
        for (Map.Entry<Integer, GUILayer> i : entries)
        {
            for (GUIElement e : i.getValue().elements)
            {
                e.setLayer(index);
            }
            final_layers.add(i.getValue());
            index++;
        }

        //Now load parsed data
        GUILayout layout = new GUILayout(final_layers, elements);
        layout.name = layout_name;
        layouts.add(layout);
        return layout;
    }

    public void load(List<String> paths)
    {
        for (String p : paths)
        {
            load(p);
        }
    }

    public void clearLayout()
    {
        current_layout = empty_layout;
    }

    public void setCurrentLayout(int i)
    {
        current_layout = layouts.get(i);
    }

    public void setCurrentLayout(GUILayout l)
    {
        current_layout = l;
    }

    public GUILayout getCurrentLayout()
    {
        return current_layout;
    }

    public GUILayout find(String name) throws RPGEException
    {
        for (GUILayout l : layouts)
        {
            if (l.name.equals(name))
            {
                return l;
            }
        }
        throw new RPGEException("GUI Layout '" + name + "' was not found!");
    }

    public void remove(GUILayout l)
    {
        layouts.remove(l);
    }

    public void reload(GUILayout l, String path)
    {
        layouts.remove(l);
        l = load(path);
        init();
    }

    public void reload(String path)
    {
        reload(current_layout, path);
    }

    public void swtichLayout(GUILayout l)
    {
        current_layout = l;
        init();
    }

    public void init()
    {
        current_layout.init(gAPI);
    }

    public void step()
    {
        current_layout.step(gAPI);
    }

    public void draw()
    {
        current_layout.draw(gAPI);
    }
}
