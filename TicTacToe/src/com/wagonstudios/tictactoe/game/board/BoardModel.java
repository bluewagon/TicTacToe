package com.wagonstudios.tictactoe.game.board;

import com.wagonstudios.tictactoe.game.Player;
import com.wagonstudios.tictactoe.game.piece.Piece;
import com.wagonstudios.tictactoe.manager.ResourceManager;
import com.wagonstudios.tictactoe.manager.SceneManager;

public class BoardModel
{
    private final int size = 3;

    public Piece[][] grid = new Piece[size][size];
    private int moveCount = 0;

    Player.State currentTurn = Player.State.X;
    Player.State nextTurn = Player.State.O;

    public void loadModel()
    {
        this.grid = ResourceManager.getInstance().grid;
    }

    public Boolean move(int x, int y)
    {
        if (grid[x][y].getPlayer() == Player.State.BLANK)
        {
            grid[x][y].setPlayer(Player.getByState(currentTurn));
            moveCount++;
            if(checkWin(x, y))
            {
                SceneManager.getInstance().gameWin();
                return true;
            }
            if (checkDraw())
            {
                currentTurn = Player.State.BLANK;
                SceneManager.getInstance().gameWin();
                return true;
            }
            changeTurns();
            return true;
        }
        return false;
    }

    public Player.State getTurn()
    {
        return currentTurn;
    }

    public void disposeModel()
    {
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                grid[row][col].disposePiece();
            }
        }
    }

    private void changeTurns()
    {
        Player.State tmp = currentTurn;
        currentTurn = nextTurn;
        nextTurn = tmp;
    }

    private Boolean checkWin(int x, int y)
    {
        if(checkColWin(x, y)) return true;
        if(checkRowWin(x, y)) return true;
        if ((x + y) % 2 == 0)
        {
            if(checkDiagonalWin(x, y)) return true;
            if(checkAntiDiagonalWin()) return true;
        }
        return false;
    }

    private Boolean checkRowWin(int x, int y)
    {
        return checkAllWin(x, y, false, true);
    }

    private Boolean checkColWin(int x, int y)
    {
        return checkAllWin(x, y, true, false);
    }

    private Boolean checkDiagonalWin(int x, int y)
    {
         return checkAllWin(x, y, true, true);
    }

    private Boolean checkAntiDiagonalWin()
    {
        for (int i = 0; i < size; i++)
        {
            if(grid[i][(size-1)-i].getPlayer() != currentTurn)
            {
                break;
            }
            if(i == size-1)
            {
                return true;
            }
        }
        return false;
    }

    private Boolean checkAllWin(int useForX, int userForY, boolean useIForX, boolean useIForY)
    {
        int mX, mY;
        for (int i = 0; i < size; i++)
        {
            mX = useIForX ? i : useForX;
            mY = useIForY ? i : userForY;

            if(grid[mX][mY].getPlayer() != currentTurn)
            {
                break;
            }
            if (i == size-1)
            {
                return true;
            }
        }
        return false;
    }

    private boolean checkDraw()
    {
        return (moveCount == (size * size));
    }
}
