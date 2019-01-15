package com.wagonstudios.tictactoe.scene;

import com.wagonstudios.tictactoe.manager.SceneManager.SceneType;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

public class LoadingScene extends BaseScene
{
    @Override
    public void createScene()
    {
        setBackground(new Background(Color.BLACK));
        attachChild(new Text(400, 240, resourceManager.font, "Loading...", vbom));
    }

    @Override
    public void onBackKeyPressed()
    {
        return;
    }

    @Override
    public SceneType getSceneType()
    {
        return SceneType.SCENE_LOADING;
    }

    @Override
    public void disposeScene()
    {

    }
}
