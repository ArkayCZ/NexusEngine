package game;

import engine.NexusGame;
import engine.input.Input;
import engine.layers.Layer2D;

public class Game extends NexusGame
{

    public Game()
    {
        super("Test game");
        this.setWindowSize(1280, 720);
        this.start();
    }

    @Override
    public void init()
    {
        pushLayer(new Test3D(getGameWindow()));
        //pushLayer(new Test2D(getGameWindow()));
    }

    @Override
    public void update(Input inputStatus)
    {

    }

    @Override
    public void render()
    {

    }

    @Override
    public void output()
    {

    }
}

