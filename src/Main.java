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
    private Engine engine;
    //private boolean start = true, gameOver = false;

    @Override
    public void simpleInitApp() {
        this.engine = new Engine(this);
        stateManager.attach(this.engine);
        //this.scoreBoard = new ScoreBoard();
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

    /**
     * Method untuk melakukan update Score dan menampilkannya dilayar
     *
     * @param tpf didapatkan dari method update yang berasal dari kelas
     * SimpleApplication
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

        final BitmapText message = new BitmapText(guiFont, false);
        message.setSize(80);
        message.setColor(ColorRGBA.White);
        message.setLocalTranslation(380, 500, 0);

        final BitmapText text = new BitmapText(guiFont, false);
        text.setSize(guiFont.getCharSet().getRenderedSize());
        text.setColor(ColorRGBA.White);
        text.setLocalTranslation(0, 180, 0);
        text.setText(this.getText1() + "\n" + this.getText2()
                + "\n" + this.getText3() + "\n" + this.getText4()
                + "\n" + this.getText5());
        guiNode.attachChild(text);

        if (!this.engine.getIsAlive()) {
            if (!this.engine.getGameOver()) {
                message.setText(this.getTextStart());
            } else {
                message.setText(this.getTextGameOver());
            }
            this.engine.setHighScore();
            this.score=0;
        } else {
            if (this.engine.getIsPause()) {
                message.setText(this.getTextPause());
                this.engine.setScore(score);
            } else {
                if (this.temp >= 0.05f) {
                    this.score++;
                    this.engine.setScore(score);
                    temp = 0;
                } else {
                    temp += tpf;
                }
            }
        }
        guiNode.attachChild(message);
        teks.setText(this.engine.getScore() + "");
        guiNode.attachChild(teks);

        final BitmapText highScoreLabel = new BitmapText(guiFont, false);
        highScoreLabel.setSize(guiFont.getCharSet().getRenderedSize());
        highScoreLabel.setColor(ColorRGBA.Red);
        highScoreLabel.setLocalTranslation(700, 200, 0);
        highScoreLabel.setText("High Score : " + this.engine.getHighScore());
        guiNode.attachChild(highScoreLabel);
    }

    /**
     * getter text pause
     *
     * @return String "PAUSE"
     */
    public String getTextPause() {
        return "PAUSE";
    }

    /**
     * getter text start
     *
     * @return String "Start"
     */
    public String getTextStart() {
        return "START";
    }

    /**
     * getter text game over
     *
     * @return String "game\nover"
     */
    public String getTextGameOver() {
        return "GAME\nOVER";
    }

    /**
     * getter text 1
     *
     * @return "Tekan S untuk start"
     */
    public String getText1() {
        return "Tekan S untuk Start";
    }

    /**
     * getter text 2
     *
     * @return "Tekan R untuk restart"
     */
    public String getText2() {
        return "Tekan R untuk Restart";
    }

    /**
     * getter text 3
     *
     * @return "Tekan Q untuk keluar"
     */
    public String getText3() {
        return "Tekan Q untuk keluar";
    }

    /**
     * getter text 4
     *
     * @return "Tekan P untuk pause"
     */
    public String getText4() {
        return "Tekan P untuk Pause";
    }

    /**
     * getter text 5
     *
     * @return "Tekan Space untuk lompat
     */
    public String getText5() {
        return "Tekan SPACE untuk lompat";
    }
}
