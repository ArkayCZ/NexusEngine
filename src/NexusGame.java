import graphics.ImmediateRenderer;
import graphics.Window;
import input.Input;
import utils.Log;

/**
 * Created by vesel on 30.10.2015.
 */
public abstract class NexusGame implements Runnable {

    private Thread mMainThread;
    private Window mGameWindow;

    /**
     *
     */
    private ImmediateRenderer mImmediateRenderer;

    private String mTitle;

    private boolean mRunning = false;
    private int mFPS = 0, mTPS = 0, mWindowWidth = 600, mWindowHeight = 600;
    public static int TPS = 60;

    public NexusGame(String title) {
        mTitle = title;
    }

    public void start() {
        mRunning = true;
        mMainThread = new Thread(this, "EngineThread");
        mMainThread.start();
    }

    public void stop() {
        mRunning = false;
    }

    public void setWindowSize(int width, int height) {
        if(mGameWindow != null) {
            Log.e("Game window has already been created and it's impossible to change it's size after that!");
            System.exit(-1);
        }
        mWindowHeight = height;
        mWindowWidth = width;
    }

    private void _init() {
        mGameWindow = new Window(mWindowWidth, mWindowHeight, mTitle);
        mGameWindow.setResizable(true);
        mGameWindow.centerWindow();
        mGameWindow.show();

        mGameWindow.initGL();
        mGameWindow.setClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        init();
    }

    public void initializeImmediateRenderer() {
        mImmediateRenderer = new ImmediateRenderer();
    }

    public ImmediateRenderer getImmediateRenderer() {
        return mImmediateRenderer;
    }

    public void run() {
        _init();

        long last = System.nanoTime();
        long lastOut = System.currentTimeMillis();
        double nsPerTick = Math.pow(10, 9) / TPS;
        double unprocessedTicks = 0;
        int measuredFPS = 0;
        int measuredTPS = 0;

        while(mRunning) {
            long now = System.nanoTime();
            unprocessedTicks += (now - last) / nsPerTick;
            last = now;

            while(unprocessedTicks > 1) {
                _update();
                unprocessedTicks--;
                measuredTPS++;
            }

            _render();
            measuredFPS++;

            if((System.currentTimeMillis() - lastOut) > 1000) {
                lastOut = System.currentTimeMillis();
                mFPS = measuredFPS;
                mTPS = measuredTPS;
                measuredFPS = 0;
                measuredTPS = 0;

                _secondlyUpdate();
            }

            if(mGameWindow.shouldClose())
                mRunning = false;
        }

        mGameWindow.destroy();
    }

    private void _render() {
        mGameWindow.clear();

        render();
        //TODO: Rendering code!

        mGameWindow.update();
    }

    private void _update() {
        update(mGameWindow.getInputStatus());
    }

    private void _secondlyUpdate() {
        Log.r("FPS: " + mFPS + " TPS: " + mTPS);

        secondlyUpdate();
    }



    public abstract void update(Input inputStatus);
    public abstract void render();
    public abstract void init();
    public abstract void secondlyUpdate();

    public Window getGameWindow() {
        return mGameWindow;
    }

}
