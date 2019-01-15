package com.wagonstudios.tictactoe.game;

import android.util.Pair;
import org.andengine.util.adt.color.Color;

public class Player
{
    public enum State
    {
        BLANK,
        X,
        O
    }

    private static final Color noPlayerColor = new Color(0.74f, 0.73f, 0.78f);
    private static final Color playerXColor = new Color(Color.BLUE);
    private static final Color playerOColor = new Color(Color.RED);

    public static final Pair<State, Color> BLANK =
            new Pair<State, Color>(State.BLANK, noPlayerColor);

    public static final Pair<State, Color> PLAYERX =
            new Pair<State, Color>(State.X, playerXColor);

    public static final Pair<State, Color> PLAYERO =
            new Pair<State, Color>(State.O, playerOColor);

    public static Pair<State, Color> getByState(State state)
    {
        if (PLAYERX.first.equals(state))
        {
            return PLAYERX;
        }
        if (Player.PLAYERO.first.equals(state))
        {
            return Player.PLAYERO;
        }
        return Player.BLANK;
    }
}
