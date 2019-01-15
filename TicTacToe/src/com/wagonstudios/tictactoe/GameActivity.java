package com.wagonstudios.tictactoe;

import android.view.KeyEvent;
import com.wagonstudios.tictactoe.manager.ResourceManager;
import com.wagonstudios.tictactoe.manager.SceneManager;
import com.wagonstudios.tictactoe.scene.SplashScene;
import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

public class GameActivity extends BaseGameActivity
{

    private Camera camera;
    private final int CAMERA_WIDTH = 480;
    private final int CAMERA_HEIGHT = 800;

    @Override
    public EngineOptions onCreateEngineOptions() {
        this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new FillResolutionPolicy(), this.camera);

        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);

        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
    {
        ResourceManager.setup(mEngine, this, this.getVertexBufferObjectManager(), this.getApplicationContext(),
                this.camera);
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException
    {
        SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
    {

        mEngine.registerUpdateHandler(new TimerHandler(1, new ITimerCallback(){

            // Override ITimerCallback's 'onTimePassed' method to allow
            // us to control what happens after the timer duration ends
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                // When 4 seconds is up, switch to our menu scene
                mEngine.unregisterUpdateHandler(pTimerHandler);
                SceneManager.getInstance().createMenuScene();
            }
        }));

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
        }
        return false;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        System.exit(0);
    }
}