package com.RPGE.gui.elements;

import com.RPGE.asset.ImageAsset;
import com.RPGE.core.GUIAPI;
import com.RPGE.gui.GUIElement;
import com.RPGE.gui.IGUIElement;

import java.util.HashMap;
import java.util.Map;

public class IconElement extends GUIElement implements IGUIElement
{
    public ImageAsset img;
    public GUIElement parent;
    private int draw_x, draw_y;
    protected String parent_id;

    public IconElement()
    {
        super(true, true);
    }

    public static String getName()
    {
        return "icon";
    }

    @Override
    public void init(GUIAPI guiapi)
    {
        if (parent_id.length() > 0) parent = guiapi.findElementById(parent_id);
    }

    @Override
    public void onGUILoad(GUIAPI guiapi, HashMap<String, String> args)
    {
        x = 0;
        y = 0;
        img = null;
        parent_id = "";
        for (Map.Entry<String, String> e : args.entrySet())
        {
            switch(e.getKey())
            {
                case "-x":
                    x = (int)Math.floor(guiapi.getScreenWidth() * Float.parseFloat(e.getValue()));
                    break;
                case "-y":
                    y = (int)Math.floor(guiapi.getScreenHeight() * Float.parseFloat(e.getValue()));
                    break;
                case "-xpx":
                    x = Integer.parseInt(e.getValue());
                    break;
                case "-ypx":
                    y = Integer.parseInt(e.getValue());
                    break;
                case "-visible":
                    visible = Integer.parseInt(e.getValue()) != 0;
                    break;
                case "-id":
                    id = e.getValue();
                    break;
                case "-img":
                    img = guiapi.assetManager().getImage(e.getValue());
                    break;
                case "-parent":
                    parent_id = e.getValue();
                    break;
            }
        }
    }

    @Override
    public void step(GUIAPI guiapi)
    {
        if (parent == null)
        {
            draw_x = x;
            draw_y = y;
        }
        else
        {
            draw_x = x + parent.x;
            draw_y = y + parent.y;
        }
    }

    @Override
    public void draw(GUIAPI guiapi)
    {
        guiapi.drawImage(img, draw_x, draw_y);
    }

    @Override
    public void destroy(GUIAPI guiapi)
    {

    }
}
