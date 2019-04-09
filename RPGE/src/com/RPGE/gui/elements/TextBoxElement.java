package com.RPGE.gui.elements;

import com.RPGE.core.GUIAPI;
import com.RPGE.gui.IGUIElement;

import java.util.HashMap;
import java.util.Map;

public class TextBoxElement extends BoxElement implements IGUIElement
{
    public String text;
    public int line_height;

    public TextBoxElement() { super(); }

    @Override
    public void init(GUIAPI guiapi)
    {
        super.init(guiapi);
        line_height = 0;
    }

    @Override
    public void onGUILoad(GUIAPI guiapi, HashMap<String, String> args)
    {
        super.onGUILoad(guiapi, args);
//        for (Map.Entry<String, String> e : args.entrySet())
//        {
//            switch(e.getKey())
//            {
//                default:
//                    break;
//            }
//        }
    }

    @Override
    public void step(GUIAPI guiapi)
    {
        super.step(guiapi);
        //uuuuuuuuuh
    }

    @Override
    public void draw(GUIAPI guiapi)
    {
        super.draw(guiapi);
        if (visible)
        {
            guiapi.drawText(text, x + outline_width, y + outline_width,
                    w - outline_width*2, line_height);
        }
    }

    public static String getName()
    {
        return "text_box";
    }
}
