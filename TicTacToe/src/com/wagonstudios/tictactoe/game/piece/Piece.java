package com.wagonstudios.tictactoe.game.piece;

import android.util.Pair;
import com.wagonstudios.tictactoe.game.Player;
import com.wagonstudios.tictactoe.utils.FloatPair;
import org.andengine.entity.IEntity;
import org.andengine.util.adt.color.Color;

public class Piece
{
    private PieceModel pieceModel;
    private PieceView pieceView;

    public Piece(FloatPair location, float width, float height)
    {
        pieceModel = new PieceModel(location);
        pieceView = new PieceView(width, height);
    }

    public FloatPair getLocation()
    {
        return pieceModel.getLocation();
    }

    public void onTouch()
    {
        pieceView.onTouch();
    }

    public void onTouchLeave()
    {
        pieceView.onTouchLeave();
    }

    public Boolean isTouched()
    {
        return pieceView.isTouched();
    }

    public IEntity getEntity()
    {
        return pieceView;
    }

    public Player.State getPlayer()
    {
        return pieceModel.getPlayer();
    }

    public void setPlayer(Pair<Player.State, Color> player)
    {
        pieceView.onTouchLeave();
        pieceModel.setPlayer(player.first);
        pieceView.changeColor(player.second);
    }

    public Boolean collideWithEntity(IEntity entity)
    {
        return pieceView.collidesWith(entity);
    }

    public void reset()
    {
        pieceModel.reset();
        pieceView.resetView();
    }

    public void disposePiece()
    {
        pieceView.disposeView();
        pieceModel.disposePiece();
    }
}
