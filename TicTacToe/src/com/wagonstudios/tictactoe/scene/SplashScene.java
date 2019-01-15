package com.wagonstudios.tictactoe.scene;

import android.content.Intent;
import android.net.Uri;
import com.wagonstudios.tictactoe.manager.ResourceManager;
import com.wagonstudios.tictactoe.manager.SceneManager.SceneType;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;


public class SplashScene extends BaseScene
{

    private Sprite splashSprite;

    @Override
    public void createScene()
    {
        getBackground().setColor(Color.CYAN);
        splashSprite = new Sprite(240, 400, resourceManager.splash_region, vbom)
        {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,
                                         final float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionDown()) {
                    ResourceManager.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://wagonstudios.com"));
                            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ResourceManager.getContext().startActivity(browserIntent);
                        }
                    });
                }
                return false;
            }
        };
        splashSprite.setScale(2.0f);
        attachChild(splashSprite);
        registerTouchArea(splashSprite);
        setTouchAreaBindingOnActionDownEnabled(true);
    }

    @Override
    public void onBackKeyPressed()
    {
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.SCENE_SPLASH;
    }

    @Override
    public void disposeScene()
    {
        splashSprite.detachSelf();
        splashSprite.dispose();
        this.detachSelf();
        this.dispose();
    }
}
