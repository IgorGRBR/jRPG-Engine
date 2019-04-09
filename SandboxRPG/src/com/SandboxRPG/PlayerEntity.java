package com.SandboxRPG;

import com.RPGE.asset.SpriteAsset;
import com.RPGE.core.Direction;
import com.RPGE.core.Entity;
import com.RPGE.core.EntityAPI;
import com.RPGE.core.IEntity;

import java.util.List;

public class PlayerEntity extends Entity implements IEntity
{
    SpriteAsset plr_sprite;
    Direction direction;
    boolean moving;
    int horizontal, vertical;
    int prev_x, prev_y, prev_real_x, prev_real_y;

    //CONSTANTS
    final float MOVE_SPEED = 0.05f;
    final float ANIM_SPEED = 0.1f;

    //Animation
    float anim_progress, walk_progress;

    public PlayerEntity()
    {
        super(true, true, true);
    }

    @Override
    public void init(EntityAPI eAPI)
    {
        //Load sprite
        plr_sprite = eAPI.assetManager().getSprite("plr");
        direction = Direction.DOWN;
        moving = false;
        anim_progress = 0.0f;
        walk_progress = 0.0f;

        //Set camera to self
        eAPI.getCamera().setFocus(this);
        eAPI.getCamera().setSmoothing(1.0f);

        prev_real_x = this.getRealPosX();
        prev_real_y = this.getRealPosY();
    }

    @Override
    public void sceneLoad(EntityAPI eAPI, List<String> args)
    {
        System.out.println("PlayerEntity.worldLoad");
    }

    @Override
    public void levelCollision(EntityAPI eAPI, int tile_data)
    {
        //System.out.println("Collision happened!: " + tile_data);
        if (tile_data == 1)
            moving = false;
        if (tile_data == 2)
        {
            eAPI.translate(this, prev_x, prev_y);
            moving = false;
            eAPI.switchWorld("Inverse Loading Test");
        }

        if (tile_data == 5)
        {
            eAPI.translate(this, prev_x, prev_y);
            moving = false;
            eAPI.switchWorld("Test Place");
        }
    }

    @Override
    public void step(EntityAPI eAPI)
    {
        if (!moving)
        {
            int down = eAPI.keyDown("down") ? 1 : 0;
            int up = eAPI.keyDown("up") ? -1 : 0;
            int left = eAPI.keyDown("left") ? -1 : 0;
            int right = eAPI.keyDown("right") ? 1 : 0;
            horizontal = left + right;
            vertical = up + down;

            if (Math.abs(horizontal) > 0)
            {
                direction = horizontal > 0 ? Direction.RIGHT : Direction.LEFT;
            }
            if (Math.abs(vertical) > 0)
            {
                direction = vertical > 0 ? Direction.DOWN : Direction.UP;
                horizontal = 0;
            }

            if (Math.abs(horizontal) > 0 || Math.abs(vertical) > 0)
            {
                //Setup previous positions values
                prev_x = this.getPosX();
                prev_y = this.getPosY();
                prev_real_x = this.getRealPosX();
                prev_real_y = this.getRealPosY();

                //Move
                eAPI.move(this, direction);
                moving = true;
            }
            else
                anim_progress = 0.0f;
        }
        else
        {
            //Animation stuff
            anim_progress += ANIM_SPEED;
            if (anim_progress >= 4.0f)
            {
                anim_progress = 0.0f;
            }
            walk_progress += MOVE_SPEED;
            if (walk_progress >= 1.0f)
            {
                walk_progress = 0.0f;
                moving = false;
            }
            else
            {
                //Smooth out camera between tiles
                eAPI.getCamera().interp(prev_real_x, prev_real_y,
                        1.0f - walk_progress,
                        1.0f - walk_progress);
            }
        }

        if (eAPI.keyPressed("reset")) eAPI.reloadWorld();
    }

    @Override
    public void draw(EntityAPI eAPI)
    {
        int idx = (int)Math.floor(anim_progress);
        int idy = 0;
        switch(direction)
        {
            case RIGHT:
                idy = 2;
                break;
            case LEFT:
                idy = 1;
                break;
            case DOWN:
                idy = 0;
                break;
            case UP:
                idy = 3;
                break;
        }
        if (!moving)
            eAPI.drawSpriteOffset(plr_sprite,
                    this.getPosX(), this.getPosY(),
                    0, -14,
                    idx, idy);
        else
            eAPI.drawSpriteOffset(plr_sprite,
                    this.getPosX(), this.getPosY(),
                    Math.round(horizontal*eAPI.getTileWidth()*(walk_progress-1.0f)),
                    Math.round(vertical*eAPI.getTileHeight()*(walk_progress-1.0f)) - 14,
                    idx, idy);

    }

    public static String getName()
    {
        return "player";
    }
}
