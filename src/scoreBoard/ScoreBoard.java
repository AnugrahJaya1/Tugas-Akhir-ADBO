/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoreBoard;

/**
 *
 * @author User
 */
public class ScoreBoard {
    protected int score,highScore;
    
    public ScoreBoard(){
        this.highScore=0;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore() {
        if(this.score<=this.highScore){
            this.highScore=this.highScore;
        }else{
            this.highScore=this.score;
        }
    }
    
    
}
