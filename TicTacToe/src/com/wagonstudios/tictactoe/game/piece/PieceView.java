package com.wagonstudios.tictactoe.game.piece;

import com.wagonstudios.tictactoe.game.Player;
import com.wagonstudios.tictactoe.manager.ResourceManager;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.adt.color.Color;

public class PieceView extends Entity
{
    private Rectangle piece;

    public PieceView(float width, float height)
    {
        setSize(width, height);
        piece = new Rectangle(width / 2, height / 2, width, height, ResourceManager.getVbom());
        piece.setColor(Player.BLANK.second);
        attachChild(piece);
    }

    public void changeColor(Color color)
    {
        piece.setColor(color);
    }

    public void onTouch()
    {
        piece.setScale(1.2f);
    }

    public void onTouchLeave()
    {
        piece.setScale(1f);
    }

    public Boolean isTouched()
    {
        return piece.isScaled();
    }

    public void resetView()
    {
        piece.setColor(Player.BLANK.second);
    }

    public void disposeView()
    {
        piece.detachSelf();
        piece.dispose();

        this.detachSelf();
        this.dispose();
    }
}
