package com.SandboxRPG;

import com.RPGE.core.GUIAPI;
import com.RPGE.gui.GUIElement;
import com.RPGE.gui.IGUIElement;
import org.newdawn.slick.Color;

import java.util.HashMap;
import java.util.Map;

public class StatusBarElement extends GUIElement implements IGUIElement
{
    public int w, h;
    private int draw_x, draw_y;
    protected Color fill;
    public float progress;
    protected Color back_color;
    protected boolean has_filling, has_back;
    protected String parent_id;

    public StatusBarElement()
    {
        super(true, true);
    }

    @Override
    public void init(GUIAPI guiapi)
    {
        has_filling = fill != null;
        has_back = back_color != null;
        if (parent_id.length() > 0) parent = guiapi.findElementById(parent_id);
    }

    @Override
    public void onGUILoad(GUIAPI guiapi, HashMap<String, String> args)
    {
        x = 0; y = 0; w = 0; h = 0;
        fill = null;
        back_color = null;
        parent = null;
        visible = true;
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
                case "-w":
                    w = (int)Math.floor(guiapi.getScreenWidth() * Float.parseFloat(e.getValue()));
                    break;
                case "-h":
                    h = (int)Math.floor(guiapi.getScreenHeight() * Float.parseFloat(e.getValue()));
                    break;
                case "-xpx":
                    x = Integer.parseInt(e.getValue());
                    break;
                case "-ypx":
                    y = Integer.parseInt(e.getValue());
                    break;
                case "-wpx":
                    w = Integer.parseInt(e.getValue());
                    break;
                case "-hpx":
                    h = Integer.parseInt(e.getValue());
                    break;
                case "-color":
                    fill = guiapi.getColor(e.getValue());
                    break;
                case "-back-color":
                    back_color = guiapi.getColor(e.getValue());
                    break;
                case "-visible":
                    visible = Integer.parseInt(e.getValue()) != 0;
                    break;
                case "-id":
                    id = e.getValue();
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
        if (has_filling)
        {
            guiapi.drawRectangle(x, y, Math.round(w*progress), h, fill);
        }
        if (has_back)
        {
            guiapi.drawRectangle(x, y, w, h, back_color);
        }
    }

    @Override
    public void destroy(GUIAPI guiapi)
    {

    }

    public static String getName()
    {
        return "status_bar";
    }
}
