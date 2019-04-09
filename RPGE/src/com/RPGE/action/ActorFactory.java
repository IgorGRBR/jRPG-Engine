package com.RPGE.action;

import com.RPGE.core.Entity;
import com.RPGE.core.IEntity;
import com.RPGE.exception.RPGEException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ActorFactory
{
    HashMap<String, Class<? extends Actor>> binds;

    public ActorFactory()
    {
        binds = new HashMap<>();
    }

    public void bind(Class<? extends Actor> cla) throws RPGEException
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

    public Actor buildActor(String name)
    {
        Actor new_actor;
        Class<?> e_class = binds.get(name);
        try
        {
            Constructor<?> e_constructor = e_class.getConstructor();
            new_actor = (Actor) e_constructor.newInstance(new Object[]{});
            return new_actor;
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
