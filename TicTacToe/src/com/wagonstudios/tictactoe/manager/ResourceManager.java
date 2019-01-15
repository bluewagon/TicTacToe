package com.wagonstudios.tictactoe.manager;

import android.content.Context;
import android.graphics.Typeface;
import com.wagonstudios.tictactoe.GameActivity;
import com.wagonstudios.tictactoe.game.piece.Piece;
import com.wagonstudios.tictactoe.utils.FloatPair;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

public class ResourceManager
{
    private static final ResourceManager INSTANCE = new ResourceManager();

    public Engine engine;
    public GameActivity activity;
    public VertexBufferObjectManager vbom;
    public Context context;
    public Camera camera;

    public Font font;

    public static ResourceManager getInstance()
    {
        return INSTANCE;
    }

    public static void setup(Engine engine, GameActivity gameActivity, VertexBufferObjectManager vbom,
                        Context context, Camera camera)
    {
        getInstance().engine = engine;
        getInstance().activity = gameActivity;
        getInstance().vbom = vbom;
        getInstance().context = context;
        getInstance().camera = camera;
    }

    public static Engine getEngine()
    {
        return getInstance().engine;
    }

    public static GameActivity getActivity()
    {
        return getInstance().activity;
    }

    public static VertexBufferObjectManager getVbom()
    {
        return getInstance().vbom;
    }

    public static Context getContext()
    {
        return getInstance().context;
    }

    public static Camera getCamera()
    {
        return getInstance().camera;
    }

    public BitmapTextureAtlas splashTextureAtlas;
    public ITextureRegion splash_region;
    public void loadSplashScreen()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,
                activity, "WagonLogo.png", 0, 0);
        splashTextureAtlas.load();
    }

    public void unloadSplashScreen()
    {
        splashTextureAtlas.unload();
        splash_region = null;
    }

    public void loadMenuResources()
    {
        loadMenuGraphics();
        loadMenuFont();
    }

    public BuildableBitmapTextureAtlas menuTextureAtlas;
    public ITextureRegion ok_region;
    public ITextureRegion quit_region;
    public ITextureRegion reset_region;
    private void loadMenuGraphics()
    {
        menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024,
                1024, TextureOptions.BILINEAR);
        ok_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity,
                "menu_ok.png");
        quit_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity,
                "menu_quit.png");
        reset_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity,
                "menu_reset.png");

        try
        {
            this.menuTextureAtlas.build(new
                    BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.menuTextureAtlas.load();
        }
        catch(final ITextureAtlasBuilder.TextureAtlasBuilderException e)
        {
            Debug.e(e);
        }
    }

    public void loadMenuFont()
    {
        font = FontFactory.create(activity.getFontManager(), activity.getTextureManager(),
                256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, Color.WHITE_ABGR_PACKED_INT);
        font.load();
    }

    public Rectangle headerBackground;
    public float gameHeaderHeight = 100f;
    public void loadGameResources()
    {
        loadGameHeader();
        createGrid();
    }

    private void loadGameHeader()
    {
        headerBackground = new Rectangle(0, 0, camera.getWidth(), gameHeaderHeight, vbom);
        headerBackground.setColor(Color.BLACK);
    }

    public int padding = 50;
    public Piece[][] grid;
    public final int size = 3;
    private void createGrid()
    {
        float width = (camera.getWidth() - padding) / size;
        float height = ((camera.getHeight() - gameHeaderHeight - padding) / size);
        float centerX = width / 2;
        float centerY = height / 2;
        float padding_for_each = padding/2;
        float boardHeight = camera.getHeight() - gameHeaderHeight;

        grid = new Piece[size][size];

        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                float posX = (width * col) + centerX;
                float posY = (boardHeight - (height * row)) - centerY;

                if (col >= 1)
                {
                    posX = (posX + (padding_for_each * col));
                }
                if (row >= 1)
                {
                    posY = (posY - (padding_for_each * row));
                }

                Piece piece = new Piece(new FloatPair(posX, posY), width, height);
                piece.getEntity().setPosition(posX, posY);
                grid[row][col] = piece;
            }
        }
    }
}
