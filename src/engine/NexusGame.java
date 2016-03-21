package engine;

import engine.entities.Entity;
import engine.graphics.ForwardRenderer;
import engine.graphics.window.Window;
import engine.input.Input;
import engine.layers.Layer;
import engine.utils.ConsoleWatcher;
import engine.utils.Log;
import engine.utils.Settings;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by vesel on 30.10.2015.
 */
public abstract class NexusGame implements Runnable
{

    private Thread mMainThread;
    private Thread mConsoleWatcherThread;
    private Window mGameWindow;
    private ConsoleWatcher mWatcher;

    private String mTitle;
    private boolean mExitOnEsc = true;

    private boolean mRunning = false;
    private int mFPS = 0, mTPS = 0, mWindowWidth = 600, mWindowHeight = 600;
    public static int sTPS = 60;

    private Stack<Layer> mLayerStack;

    public NexusGame(String title)
    {
        mTitle = title;
        mLayerStack = new Stack<>();
    }

    public void start()
    {
        mRunning = true;
        mMainThread = new Thread(this, "EngineThread");
        mConsoleWatcherThread = new Thread(() -> {
            mWatcher = new ConsoleWatcher();
            while (mRunning)
            {
                mWatcher.executeCommand(mWatcher.getCommand());
            }
        });

        mConsoleWatcherThread.start();
        mMainThread.start();
    }

    public void stop()
    {
        mRunning = false;
    }

    public void setWindowSize(int width, int height)
    {
        if (mGameWindow != null)
        {
            Log.e("game.Game window has already been created and it's impossible to change it's size after that!");
            System.exit(-1);
        }
        mWindowHeight = height;
        mWindowWidth = width;
    }

    public void run()
    {
        initInternal();

        long last = System.nanoTime();
        long lastOut = System.currentTimeMillis();
        double nsPerTick = Math.pow(10, 9) / sTPS;
        double unprocessedTicks = 0;
        int measuredFPS = 0;
        int measuredTPS = 0;

        while (mRunning)
        {
            long now = System.nanoTime();
            unprocessedTicks += (now - last) / nsPerTick;
            last = now;

            while (unprocessedTicks > 1)
            {
                updateInternal();
                unprocessedTicks--;
                measuredTPS++;
            }

            renderInternal();
            measuredFPS++;

            if ((System.currentTimeMillis() - lastOut) > 1000)
            {
                lastOut = System.currentTimeMillis();
                mFPS = measuredFPS;
                mTPS = measuredTPS;
                measuredFPS = 0;
                measuredTPS = 0;

                outputInternal();
            }

            if (mGameWindow.shouldClose())
            {
                mRunning = false;
            }
        }

        mGameWindow.destroy();
        System.exit(0);
    }

    private void initInternal()
    {
        mGameWindow = new Window(mWindowWidth, mWindowHeight, mTitle);
        mGameWindow.setResizable(true);
        mGameWindow.centerWindow();
        mGameWindow.show(false);
        mGameWindow.initGL();

        init();

        for (Iterator<Layer> it = mLayerStack.iterator(); it.hasNext(); )
        {
            Layer layer = it.next();
            layer.onInit();
        }
    }

    private void renderInternal()
    {
        mGameWindow.clear();
        render();

        for (Iterator<Layer> it = mLayerStack.iterator(); it.hasNext(); )
        {
            Layer layer = it.next();
            layer.onRender();
        }

        mGameWindow.update();
    }

    private void updateInternal()
    {
        update(mGameWindow.getInputStatus());
        if (mExitOnEsc && mGameWindow.getInputStatus().isKeyDown(Input.KEY_ESC))
        {
            mRunning = false;
        }

        for (Iterator<Layer> it = mLayerStack.iterator(); it.hasNext(); )
        {
            Layer layer = it.next();
            layer.onUpdate(mGameWindow.getInputStatus());
        }
    }

    private void outputInternal()
    {
        if (Settings.FPS_LOGGING_ENABLED)
        {
            Log.r("FPS: " + mFPS + " TPS: " + mTPS);
        }

        output();
    }

    public abstract void update(Input inputStatus);

    public abstract void render();

    public abstract void init();

    public abstract void output();

    public void pushLayer(Layer layer)
    {
        mLayerStack.push(layer);
    }

    public void popLayer()
    {
        mLayerStack.pop();
    }

    public Window getGameWindow()
    {
        return mGameWindow;
    }

    public Thread getMainThread()
    {
        return mMainThread;
    }

    public void setMainThread(Thread mainThread)
    {
        mMainThread = mainThread;
    }

    public Thread getConsoleWatcherThread()
    {
        return mConsoleWatcherThread;
    }

    public void setConsoleWatcherThread(Thread consoleWatcherThread)
    {
        mConsoleWatcherThread = consoleWatcherThread;
    }

    public void setGameWindow(Window gameWindow)
    {
        mGameWindow = gameWindow;
    }

    public ConsoleWatcher getWatcher()
    {
        return mWatcher;
    }

    public void setWatcher(ConsoleWatcher watcher)
    {
        mWatcher = watcher;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(String title)
    {
        mTitle = title;
    }

    public boolean isExitOnEsc()
    {
        return mExitOnEsc;
    }

    public void setExitOnEsc(boolean exitOnEsc)
    {
        mExitOnEsc = exitOnEsc;
    }

    public boolean isRunning()
    {
        return mRunning;
    }

    public void setRunning(boolean running)
    {
        mRunning = running;
    }

    public int getFPS()
    {
        return mFPS;
    }

    public void setFPS(int FPS)
    {
        mFPS = FPS;
    }

    public int getTPS()
    {
        return mTPS;
    }

    public void setTPS(int TPS)
    {
        mTPS = TPS;
    }

    public int getWindowWidth()
    {
        return mWindowWidth;
    }

    public void setWindowWidth(int windowWidth)
    {
        mWindowWidth = windowWidth;
    }

    public int getWindowHeight()
    {
        return mWindowHeight;
    }

    public void setWindowHeight(int windowHeight)
    {
        mWindowHeight = windowHeight;
    }

}
