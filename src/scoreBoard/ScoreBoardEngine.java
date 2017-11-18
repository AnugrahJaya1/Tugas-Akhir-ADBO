/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoreBoard;

import MyGameScene.PlayGame;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;

/**
 *
 * @author GL552VX
 */
public class ScoreBoardEngine  extends SimpleApplication{
    private int score;
    private float temp;

    private ScoreBoard scoreBoard;
    private PlayGame playGame;
    
    
    @Override
    public void simpleInitApp() {
           this.scoreBoard = new ScoreBoard();
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
