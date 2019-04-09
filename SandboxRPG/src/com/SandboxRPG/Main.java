package com.SandboxRPG;

import com.RPGE.asset.ImageAsset;
import com.RPGE.core.*;
import com.RPGE.file.RPGEFile;
import org.newdawn.slick.Input;

import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        Engine e = new Engine();

        e.setSysLibPath("natives");
        e.init();

        //Testing stuff
        //nothing for now

        //All game init code happens here
        e.setTileSize(32, 32);
        e.setResolution(8, 6, 3.5f);
        //e.setResolution(8, 8, 3f);
        //e.setResolution(3, 3, 8f);
        e.assetManager().addImage("plr", "assets/images/single_dude.png");
        e.assetManager().addImage("npc", "assets/images/single_npc.png");
        e.assetManager().addTileset("tile_overworld", "assets/tilesets/RPGE-Tiles.png");

        e.assetManager().addSprite("plr", "assets/sprites/dude.png",
                4, 4);

        e.addEntityClass(PlayerEntity.class);
        e.addEntityClass(NPCEntity.class);

        e.addGUIElementClass(StatusBarElement.class);

        e.bindKey("reset", Input.KEY_R);

        e.addGUILayout("layouts/ingame.uil");

        e.addLevel("levels/test.lvl");
        e.addLevel("levels/inverse_loading.lvl");
        //e.addLevel("levels/negative_layers.lvl");

        //After game has been initialized...
        e.run();
    }
}
