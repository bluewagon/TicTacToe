package com.wagonstudios.tictactoe.game.piece;

import com.wagonstudios.tictactoe.game.Player;
import com.wagonstudios.tictactoe.utils.FloatPair;

public class PieceModel
{
    private Player.State state;
    private FloatPair location;

    public PieceModel(FloatPair location)
    {
        this.location = location;
        setPlayer(Player.BLANK.first);
    }

    public Player.State getPlayer()
    {
        return state;
    }

    public void setPlayer(Player.State player)
    {
        this.state = player;
    }

    public FloatPair getLocation()
    {
        return location;
    }

    public void reset()
    {
        setPlayer(Player.BLANK.first);
    }

    public void disposePiece()
    {
        state = null;
        location = null;
    }
}
