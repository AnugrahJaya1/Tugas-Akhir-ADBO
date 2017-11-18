package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import Engine.Engine;
import scoreBoard.ScoreBoard;

/**
 * Kelas main untuk menjalankan game
 * @author GL552VX
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    private int score;
    private float temp;
    
    private ScoreBoard scoreBoard;

    @Override
    public void simpleInitApp() {
        stateManager.attach(new Engine(this));
        this.scoreBoard=new ScoreBoard();
        viewPort.setBackgroundColor(ColorRGBA.White);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        this.updateScore(tpf);
        //this.scoreBoard();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public void updateScore(float tpf) {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        final BitmapText teks = new BitmapText(guiFont, false);
        teks.setSize(guiFont.getCharSet().getRenderedSize());
        teks.setColor(ColorRGBA.Red);
        teks.setLocalTranslation(950, 200, 0);
        
        final BitmapText scoreLabel = new BitmapText(guiFont, false);
        scoreLabel.setSize(guiFont.getCharSet().getRenderedSize());
        scoreLabel.setColor(ColorRGBA.Red);
        scoreLabel.setLocalTranslation(850, 200, 0);
        scoreLabel.setText("Score : ");
        guiNode.attachChild(scoreLabel);
        
        final BitmapText highScoreLabel = new BitmapText(guiFont, false);
        highScoreLabel.setSize(guiFont.getCharSet().getRenderedSize());
        highScoreLabel.setColor(ColorRGBA.Red);
        highScoreLabel.setLocalTranslation(700, 200, 0);
        highScoreLabel.setText("High Score : ");
        guiNode.attachChild(highScoreLabel);
        
        
        if (this.temp >= 0.05f) {
            this.score++;
            this.scoreBoard.setScore(score);
            temp = 0;
        } else {
            temp += tpf;
        }
        teks.setText(this.scoreBoard.getScore() + "");
        guiNode.attachChild(teks); 
    }
}
/**
//     * Method untuk menampilkan String String Scrore dan String High Score
//     */
//    public void scoreBoard() {
//        guiNode.detachAllChildren();
//        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
//        final BitmapText scoreLabel = new BitmapText(guiFont, false);
//
//        scoreLabel.setSize(guiFont.getCharSet().getRenderedSize());
//        scoreLabel.setColor(ColorRGBA.Red);
//        scoreLabel.setLocalTranslation(500, 480, 0);
//        scoreLabel.setText("Score : ");
//        guiNode.attachChild(scoreLabel);
//
//        final BitmapText highScoreLabel = new BitmapText(guiFont, false);
//        highScoreLabel.setSize(guiFont.getCharSet().getRenderedSize());
//        highScoreLabel.setColor(ColorRGBA.Red);
//        highScoreLabel.setLocalTranslation(300, 480, 0);
//        highScoreLabel.setText("High Score : ");
//        guiNode.attachChild(highScoreLabel);
//    }