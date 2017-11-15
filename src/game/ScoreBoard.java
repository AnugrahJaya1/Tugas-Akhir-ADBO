/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

/**
 *
 * @author User
 */
public class ScoreBoard extends AbstractAppState {

    private Node guiNode;
    private final AssetManager assetManager;
    private BitmapFont guiFont;

    public ScoreBoard(SimpleApplication app) {
        this.assetManager = app.getAssetManager();
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
    }

    @Override
    public void update(float tpf) {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        final BitmapText scoreLabel = new BitmapText(guiFont, false);

        scoreLabel.setSize(guiFont.getCharSet().getRenderedSize());
        scoreLabel.setColor(ColorRGBA.Black);
        scoreLabel.setLocalTranslation(500, 480, 0);
        scoreLabel.setText("Score : ");
        guiNode.attachChild(scoreLabel);

        final BitmapText highScoreLabel = new BitmapText(guiFont, false);
        highScoreLabel.setSize(guiFont.getCharSet().getRenderedSize());
        highScoreLabel.setColor(ColorRGBA.Black);
        highScoreLabel.setLocalTranslation(300, 480, 0);
        highScoreLabel.setText("High Score : ");
        guiNode.attachChild(highScoreLabel);
    }

}
