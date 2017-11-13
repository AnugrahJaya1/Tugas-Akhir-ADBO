package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import game.Engine;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author User
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    private int score = 0;
    private float temp = 0;
    //private ScoreBoard scoreBoard1 ; 

    @Override
    public void simpleInitApp() {
        stateManager.attach(new Engine(this));
        //stateManager.attach(new ScoreBoard(this));
        viewPort.setBackgroundColor(ColorRGBA.White);
    }

    @Override
    public void simpleUpdate(float tpf) {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        final BitmapText text = new BitmapText(guiFont, false);
        text.setSize(guiFont.getCharSet().getRenderedSize());
        text.setColor(ColorRGBA.Black);
        text.setLocalTranslation(550, 480, 0);
        if (this.temp >= 0.05f) {
            this.score++;
            text.setText(this.score + "");
            //System.out.println(this.score);
            temp = 0;
        } else {
            temp += tpf;
        }
        this.scoreBoard();
        //this.scoreBoard1.update(tpf);
        guiNode.attachChild(text);
    }

    public void scoreBoard() {
        guiNode.detachAllChildren();;
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

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
