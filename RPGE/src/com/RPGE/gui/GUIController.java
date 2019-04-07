package com.RPGE.gui;

import com.RPGE.file.RPGEFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class GUIController
{
    LinkedList<GUILayout> layouts;
    private int screen_width, screen_height;
    GUIAPI guiapi;
    GUIElementFactory ge_factory;

    public GUIController(GUIAPI ga, GUIElementFactory ge_factory, int w, int h)
    {
        guiapi = ga;
        screen_width = w;
        screen_height = h;
    }

    public void load(String path)
    {
        RPGEFile file = new RPGEFile(path, true);

        //Temp variables
        GUILayer current_layer = null;
        HashMap<Integer, GUILayer> temp_layers = new HashMap<>();
        String layout_name = "";
        int current_layer_index = -1;

        ArrayList<String> line = file.getLine();
        while(line != null)
        {
            if (line.get(0).charAt(0) != '#' || line.get(0).length() > 0)
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
                        new_element.onGUILoad(guiapi, args);
                        break;
                    default:
                        System.out.println("Layout loading error: unknown token '"
                                + line.get(0) + "'!");
                        break;
                }
            line = file.getLine();
        }
    }

}
