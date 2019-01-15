package com.wagonstudios.tictactoe.game.board;

import android.util.Log;
import com.wagonstudios.tictactoe.game.Player;
import com.wagonstudios.tictactoe.game.piece.Piece;
import com.wagonstudios.tictactoe.manager.ResourceManager;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

public class Board extends Entity
{
    private BoardModel boardModel;
    private ResourceManager resourceManager = ResourceManager.getInstance();

    public void loadBoard()
    {
        setSize(resourceManager.camera.getWidth(),
                resourceManager.camera.getHeight() - resourceManager.gameHeaderHeight);
        setColor(Color.YELLOW);

        boardModel = new BoardModel();
        boardModel.loadModel();

        setColor(Color.CYAN);

        for (int row = 0; row < resourceManager.size; row++)
        {
            for (int col = 0; col < resourceManager.size; col++)
            {
                attachChild(boardModel.grid[row][col].getEntity());
            }
        }
    }

    public void disposeBoard()
    {
        boardModel.disposeModel();
        detachChildren();
        this.detachSelf();
        this.dispose();
    }

    public Boolean touchEvent(TouchEvent pSceneTouchEvent)
    {
        for (int row = 0; row < resourceManager.size; row++)
        {
            for (int col = 0; col < resourceManager.size; col++)
            {
                if (checkCollision(row, col, pSceneTouchEvent)) return true;
            }
        }
        return false;
    }

    public Player.State getTurn()
    {
        return boardModel.getTurn();
    }

    private Boolean checkCollision(int row, int col, TouchEvent pSceneTouchEvent)
    {
        Entity tmpEntity = new Entity(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
        Piece gridPiece = boardModel.grid[row][col];
        if (gridPiece.collideWithEntity(tmpEntity))
        {
            if (onCollision(row, col, pSceneTouchEvent, gridPiece)) return true;
        }
        else if (gridPiece.isTouched())
        {
            gridPiece.onTouchLeave();
        }
        return false;
    }

    private Boolean onCollision(int row, int col, TouchEvent pSceneTouchEvent, Piece gridPiece)
    {
        if (pSceneTouchEvent.isActionDown())
        {
            gridOnTouchIfBlank(gridPiece);
        }
        if (pSceneTouchEvent.isActionUp() && gridPiece.isTouched())
        {
            gridPiece.onTouchLeave();
            return boardModel.move(row, col);
        }
        return false;
    }

    private void gridOnTouchIfBlank(Piece gridPiece)
    {
        if (gridPiece.getPlayer() == Player.State.BLANK)
        {
            gridPiece.onTouch();
        }
    }
}