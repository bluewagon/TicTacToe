package com.wagonstudios.tictactoe.game.board;

import com.wagonstudios.tictactoe.game.Player;
import com.wagonstudios.tictactoe.manager.ResourceManager;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

public class BoardHeader extends Entity
{
    private Text playerTurnText;
    private Rectangle background;

    public float centerX;
    public float centerY;

    private String PLAYER_TURN_TEXT = "Player Turn: %d";
    private int current_player = 1;

    private String gameOver = "Player %d Wins!";

    public void loadHeader()
    {
        ResourceManager resourceManager = ResourceManager.getInstance();
        background = resourceManager.headerBackground;

        setSize(resourceManager.camera.getWidth(), resourceManager.gameHeaderHeight);

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        background.setPosition(centerX, centerY);

        playerTurnText = new Text(centerX, centerY,
                resourceManager.font, String.format(PLAYER_TURN_TEXT, current_player), resourceManager.vbom);
        playerTurnText.setColor(Color.WHITE);
        attachChild(background);
        attachChild(playerTurnText);
    }

    public void changeTurn()
    {
        current_player = (current_player == 1) ? 2 : 1;
        playerTurnText.setText(String.format(PLAYER_TURN_TEXT, current_player));
    }

    public void setGameOver(Player.State winner)
    {
        String winText;
        if (winner == Player.State.BLANK)
        {
            winText = "DRAW!";
        }
        else
        {
            winText = (winner == Player.State.X) ? String.format(gameOver, 1) : String.format(gameOver, 2);
        }

        playerTurnText.setText(winText);
    }

    public void disposeHeader()
    {
        playerTurnText.detachSelf();
        playerTurnText.dispose();

        background.detachSelf();
        background.dispose();

        this.detachSelf();
        this.dispose();
    }
}
