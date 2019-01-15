package com.wagonstudios.tictactoe.scene;

import android.util.Log;
import android.widget.Toast;
import com.wagonstudios.tictactoe.game.GameOverLayer;
import com.wagonstudios.tictactoe.game.board.Board;
import com.wagonstudios.tictactoe.game.board.BoardHeader;
import com.wagonstudios.tictactoe.manager.ResourceManager;
import com.wagonstudios.tictactoe.manager.SceneManager;
import com.wagonstudios.tictactoe.manager.SceneManager.SceneType;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

public class GameScene extends BaseScene implements IOnSceneTouchListener
{
    private Board gameBoard;
    private BoardHeader header;
    private GameOverLayer gameOverLayer;

    private boolean gameOver = false;

    @Override
    public void createScene()
    {
        ResourceManager.getInstance().loadGameResources();
        getBackground().setColor(Color.WHITE);
        createHeader();
        createBoard();
        attachEntities();
        setOnSceneTouchListener(this);
    }

    @Override
    public void onBackKeyPressed()
    {
        SceneManager.getInstance().loadMenuScene();
    }

    @Override
    public SceneType getSceneType()
    {
        return SceneType.SCENE_GAME;
    }

    @Override
    public void disposeScene()
    {
        gameBoard.disposeBoard();
        header.disposeHeader();

        gameBoard = null;
        header = null;

        this.detachSelf();
        this.dispose();
    }

    public void resetScene()
    {
        gameBoard.reset();
        header.reset();
    }

    public void setGameOver()
    {
        gameOver = true;
        header.setGameOver(gameBoard.getTurn());
        gameOverLayer = new GameOverLayer();
        gameOverLayer.loadLayer();
        attachChild(gameOverLayer);
    }

    private void attachEntities()
    {
        attachChild(header);
        attachChild(gameBoard);
    }

    private void createHeader()
    {
        header = new BoardHeader();
        header.loadHeader();
        header.setPosition(baseCenterX, CAMERA_HEIGHT - header.centerY);
    }

    private void createBoard()
    {
        gameBoard = new Board();
        gameBoard.loadBoard();
        gameBoard.setPosition(baseCenterX, baseCenterY - header.centerY);
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
    {
        if (pSceneTouchEvent.getY() < (camera.getHeight() - resourceManager.gameHeaderHeight)
                && !gameOver)
        {
            onBoardTouch(pSceneTouchEvent);
        }
        else if(gameOver)
        {
            gameOverLayer.onTouch(pSceneTouchEvent);
        }
        return false;
    }

    private void onBoardTouch(TouchEvent pSceneTouchEvent)
    {
        if (gameBoard.touchEvent(pSceneTouchEvent) && !gameOver)
        {
            header.changeTurn();
        }
    }
}
