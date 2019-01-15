package com.wagonstudios.tictactoe.manager;

import com.wagonstudios.tictactoe.scene.*;
import org.andengine.engine.Engine;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

public class SceneManager
{
    public enum SceneType
    {
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_GAME,
        SCENE_LOADING
    }

    public static final SceneManager INSTANCE = new SceneManager();

    public static SceneManager getInstance()
    {
        return INSTANCE;
    }

    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene loadingScene;
    private GameScene gameScene;
    private Engine engine = ResourceManager.getEngine();
    private BaseScene currentScene;
    private SceneType currentSceneType;

    public void setScene(BaseScene scene)
    {
        currentScene = scene;
        currentSceneType = scene.getSceneType();
        engine.setScene(scene);
    }

    public void setScene(SceneType sceneType)
    {
        switch (sceneType)
        {
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_MENU:
                setScene(menuScene);
                break;
            default:
                setScene(menuScene);
                break;
        }
    }

    public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
    {
        ResourceManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        setScene(splashScene);
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }

    public void disposeSplashScreen()
    {
        ResourceManager.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }

    public void createMenuScene()
    {
        ResourceManager.getInstance().loadMenuResources();
        this.menuScene = new MainMenuScene();
        this.loadingScene = new LoadingScene();
        setScene(this.menuScene);
        disposeSplashScreen();
    }

    public void createGameScene()
    {
        setScene(this.loadingScene);
        createOrResetGame();
        this.gameScene = new GameScene();
        setScene(this.gameScene);
    }

    public void disposeGameScene()
    {
        this.gameScene.disposeScene();
    }

    public SceneType getCurrentSceneType()
    {
        return currentSceneType;
    }

    public BaseScene getCurrentScene()
    {
        return currentScene;
    }

    public void loadMenuScene()
    {
        SceneManager.getInstance().setScene(menuScene);
    }

    public void gameWin()
    {
        gameScene.setGameOver();
    }

    private boolean checkIfGameAlreadyLoaded()
    {
        return (ResourceManager.getInstance().grid != null);
    }

    private void createOrResetGame()
    {
        if(checkIfGameAlreadyLoaded())
        {
            gameScene.resetScene();
        }
        else
        {
            ResourceManager.getInstance().loadGameResources();
        }
    }
}
