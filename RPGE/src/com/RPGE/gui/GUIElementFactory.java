package com.RPGE.gui;

import com.RPGE.exception.RPGEException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class GUIElementFactory
{
    HashMap<String, Class<? extends GUIElement>> binds;

    public GUIElementFactory()
    {
        binds = new HashMap<>();
    }

    public void bind(Class<? extends GUIElement> cla) throws RPGEException {
        if (IGUIElement.class.isAssignableFrom(cla))
        {
            try
            {
                String name = (String) cla.asSubclass(IGUIElement.class)
                        .getDeclaredMethod("getName")
                        .invoke(null);
                binds.put(name, cla);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
            catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            throw new RPGEException("GUIElement class does not implement IGUIElement!");
        }
    }

    public GUIElement buildElement(String name)
    {
        GUIElement element;
        Class<?> e_class = binds.get(name);
        try
        {
            Constructor<?> e_constructor = e_class.getConstructor();
            element = (GUIElement) e_constructor.newInstance(new Object[]{});
            return element;
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
