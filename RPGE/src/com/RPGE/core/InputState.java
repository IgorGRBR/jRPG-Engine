package com.RPGE.core;

import org.newdawn.slick.Input;

import java.util.HashMap;

public class InputState
{
    HashMap<Integer, String> key_map;
    HashMap<String, KeyState> key_states;

    Input slick_input;

    public InputState()
    {
        key_map = new HashMap<>();
        key_states = new HashMap<>();
    }

    void setInputProvider(Input inp)
    {
        slick_input = inp;
    }

    enum KeyState
    {
        UP,
        DOWN,
        PRESSED,
        RELEASED
    }

    void bind(String str, int slick_value)
    {
        key_map.put(slick_value, str);
        key_states.put(str, KeyState.UP);
    }

    void update()
    {
        //slick_input.poll();

        for (HashMap.Entry<Integer, String> k : key_map.entrySet())
        {
            boolean current_state = slick_input.isKeyDown(k.getKey());
            if (current_state)
            {
                switch (key_states.get(k.getValue()))
                {
                    case UP:
                        key_states.put(k.getValue(), KeyState.PRESSED);
                        break;
                    case PRESSED:
                        key_states.put(k.getValue(), KeyState.DOWN);
                        break;
                    case RELEASED:
                        key_states.put(k.getValue(), KeyState.PRESSED);
                        break;
                }
            }
            else
            {
                switch (key_states.get(k.getValue()))
                {
                    case DOWN:
                        key_states.put(k.getValue(), KeyState.RELEASED);
                        break;
                    case RELEASED:
                        key_states.put(k.getValue(), KeyState.UP);
                        break;
                    case PRESSED:
                        key_states.put(k.getValue(), KeyState.RELEASED);
                        break;
                }
            }
        }
    }

    boolean isDown(String key)
    {
        return key_states.get(key) == KeyState.DOWN;
    }

    boolean isPressed(String key)
    {
        return key_states.get(key) == KeyState.PRESSED;
    }

    boolean isReleased(String key)
    {
        return key_states.get(key) == KeyState.RELEASED;
    }
}
