package com.wagonstudios.tictactoe.game;

import com.wagonstudios.tictactoe.manager.ResourceManager;
import com.wagonstudios.tictactoe.manager.SceneManager;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

public class GameOverLayer extends Entity
{
    private Rectangle layerBackground;
    private Text gameOverText;

    private String Player1Wins = "Player 1 Wins!";
    private String Player2Wins = "Player 2 Wins!";

    private Rectangle startNewGameBg;
    private String startNewGame = "Start a New Game";
    private Text startNewGameText;

    private Rectangle quitGameBg;
    private String quitGame = "Quit Game";
    private Text quitGameText;

    ResourceManager resourceManager = ResourceManager.getInstance();
    private float camera_height = resourceManager.camera.getHeight();
    private float camera_width = resourceManager.camera.getWidth();
    private float camera_centerX = camera_width / 2;
    private float camera_centerY = camera_height / 2;

    private float layerWidth = 375;
    private float layerHeight = 500;

    public void loadLayer()
    {
        loadBackground();
        loadGameOver();
        loadOptions();
        attach();
    }

    public void onTouch(TouchEvent touchEvent)
    {
        findCollision(touchEvent);
    }

    private void findCollision(TouchEvent touchEvent)
    {
        Entity tmpEntity = new Entity(touchEvent.getX(), touchEvent.getY());
        if (startNewGameBg.collidesWith(tmpEntity))
        {
            if (touchEvent.isActionDown())
            {
                startNewGameBg.setScale(1.2f);
            }
            else if (touchEvent.isActionUp() && startNewGameBg.isScaled())
            {
                SceneManager.getInstance().createGameScene();
            }
            else
            {
                startNewGameBg.setScale(1f);
            }
        }

        if (quitGameBg.collidesWith(tmpEntity))
        {
            if (touchEvent.isActionDown())
            {
                quitGameBg.setScale(1.2f);
            }
            else if (touchEvent.isActionUp() && quitGameBg.isScaled())
            {
                System.exit(0);
            }
            else
            {
                quitGameBg.setScale(1f);
            }
        }
    }

    private void loadBackground()
    {
        layerBackground = new Rectangle(camera_centerX, camera_centerY, layerWidth, layerHeight,
                resourceManager.vbom);
        layerBackground.setColor(1f, 1f, 1f, .4f);
    }

    private void loadGameOver()
    {
        gameOverText = new Text(camera_centerX, layerHeight + 100, resourceManager.font,
                "Start A New Game?", resourceManager.vbom);
        gameOverText.setColor(Color.BLACK);
    }

    private void loadOptions()
    {
        loadNewGame();
        loadQuit();
    }

    private void loadNewGame()
    {
        startNewGameBg = new Rectangle(camera_centerX, layerHeight - 100, 350, 100, resourceManager.vbom);
        startNewGameBg.setColor(Color.BLACK);
        startNewGameText = new Text(camera_centerX, layerHeight - 100, resourceManager.font, startNewGame, resourceManager.vbom);
        startNewGameText.setColor(Color.WHITE);
    }

    private void loadQuit()
    {
        quitGameBg = new Rectangle(camera_centerX, layerHeight - 300, 350, 100, resourceManager.vbom);
        quitGameBg.setColor(Color.BLACK);
        quitGameText = new Text(camera_centerX, layerHeight - 300, resourceManager.font, quitGame, resourceManager.vbom);
        quitGameText.setColor(Color.WHITE);
    }

    private void attach()
    {
        attachChild(layerBackground);
        attachChild(gameOverText);

        attachChild(startNewGameBg);
        attachChild(startNewGameText);

        attachChild(quitGameBg);
        attachChild(quitGameText);
    }
}
