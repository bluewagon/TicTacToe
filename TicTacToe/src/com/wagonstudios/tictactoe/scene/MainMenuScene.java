package com.wagonstudios.tictactoe.scene;

import com.wagonstudios.tictactoe.manager.ResourceManager;
import com.wagonstudios.tictactoe.manager.SceneManager;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.util.adt.color.Color;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener
{

    private final int MENU_PLAY = 0;
    private final int MENU_QUIT = 1;
    private final int MENU_RESET = 2;

    private MenuScene childMenuScene;

    @Override
    public void createScene()
    {
        getBackground().setColor(Color.YELLOW);
        createMenuChildScene();
    }

    @Override
    public void onBackKeyPressed() {
        this.disposeScene();
        SceneManager.getInstance().disposeGameScene();
        System.exit(0);
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_MENU;
    }

    @Override
    public void disposeScene()
    {
        childMenuScene.detachChildren();
        childMenuScene.detachSelf();
        childMenuScene.dispose();

        this.detachSelf();
        this.dispose();

    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
                                     float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID())
        {
            case MENU_PLAY:
                SceneManager.getInstance().createGameScene();
                return true;
            case MENU_QUIT:
                System.exit(0);
                return true;
            case MENU_RESET:
                return true;
            default:
                return false;
        }
    }

    private void createMenuChildScene()
    {
        childMenuScene = new MenuScene(camera);
        childMenuScene.setPosition(0, 0);

        final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY,
                resourceManager.ok_region, vbom), 1.2f, 1);
        final IMenuItem quitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_QUIT,
                resourceManager.quit_region, vbom), 1.2f, 1);

        childMenuScene.addMenuItem(playMenuItem);
        childMenuScene.addMenuItem(quitMenuItem);

        childMenuScene.buildAnimations();

//        playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() + 10);
//        quitMenuItem.setPosition(quitMenuItem.getX(), quitMenuItem.getY() - 110);

        childMenuScene.setOnMenuItemClickListener(this);
        setChildScene(childMenuScene);
    }
}
