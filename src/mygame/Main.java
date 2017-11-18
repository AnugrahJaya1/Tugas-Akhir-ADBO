package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import Engine.Engine;
import MyGameScene.PlayGame;
import scoreBoard.ScoreBoard;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    private int score;
    private float temp;

    private ScoreBoard scoreBoard;
    private PlayGame playGame;

    @Override
    public void simpleInitApp() {
        stateManager.attach(new Engine(this));
        this.scoreBoard = new ScoreBoard();
        viewPort.setBackgroundColor(ColorRGBA.White);
        //this.startGame();

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
    /**
     * Method untuk melakukan update Score dan 
     * menampilkannya dilayar
     * @param tpf didapatkan dari method update yang berasal dari kelas SimpleApplication
     */
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

//    public void startGame(){
//        guiNode.detachAllChildren();
//        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
//        final BitmapText teksAwal = new BitmapText(guiFont, false);
//        teksAwal.setSize(guiFont.getCharSet().getRenderedSize());
//        teksAwal.setColor(ColorRGBA.Red);
//        teksAwal.setLocalTranslation(700, 200, 0);
//        teksAwal.setText("NFONDFNHIBFONBHIOF");
//        guiNode.attachChild(teksAwal);
//    }
}
