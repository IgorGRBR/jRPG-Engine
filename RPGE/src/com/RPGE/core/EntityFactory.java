package com.RPGE.core;

import com.RPGE.exception.RPGEException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class EntityFactory
{
    HashMap<String, Class<? extends Entity>> binds;
    int tile_width, tile_height;

    public EntityFactory()
    {
        binds = new HashMap<>();
    }

    public void bind(Class<? extends Entity> cla) throws RPGEException
    {
        if (IEntity.class.isAssignableFrom(cla))
        {
            String name = "";
            try
            {
                name = (String) cla.asSubclass(IEntity.class)
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
            throw new RPGEException("Entity class does not implement IEntity!");
        }
    }

    public Entity buildEntity(String name)
    {
        Entity new_entity;
        Class<?> e_class = binds.get(name);
        try
        {
            Constructor<?> e_constructor = e_class.getConstructor();
            new_entity = (Entity) e_constructor.newInstance(new Object[]{});
            return new_entity;
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
