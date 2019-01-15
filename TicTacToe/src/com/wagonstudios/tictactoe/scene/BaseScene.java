package com.wagonstudios.tictactoe.scene;

import android.app.Activity;
import android.content.Context;
import com.wagonstudios.tictactoe.GameActivity;
import com.wagonstudios.tictactoe.manager.ResourceManager;
import com.wagonstudios.tictactoe.manager.SceneManager.SceneType;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract  class BaseScene extends Scene
{

    protected Engine engine;
    protected GameActivity activity;
    protected ResourceManager resourceManager;
    protected VertexBufferObjectManager vbom;
    protected Camera camera;
    protected float CAMERA_WIDTH;
    protected float CAMERA_HEIGHT;
    protected float baseCenterY;
    protected float baseCenterX;
    protected Context context;

    public BaseScene()
    {
        this.engine = ResourceManager.getEngine();
        this.activity = ResourceManager.getActivity();
        this.resourceManager = ResourceManager.getInstance();
        this.vbom = ResourceManager.getVbom();
        this.context = ResourceManager.getContext();
        this.camera = ResourceManager.getCamera();
        CAMERA_WIDTH = camera.getWidth();
        CAMERA_HEIGHT = camera.getHeight();
        baseCenterX = CAMERA_WIDTH / 2;
        baseCenterY = CAMERA_HEIGHT / 2;
        createScene();
    }

    public abstract void createScene();

    public abstract void onBackKeyPressed();

    public abstract SceneType getSceneType();

    public abstract void disposeScene();
}
