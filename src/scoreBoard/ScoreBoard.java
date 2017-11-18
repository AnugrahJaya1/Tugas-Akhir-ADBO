/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoreBoard;

/**
 * kelas yang menampung score
 * dan menampung highScore
 * @author User
 */
public class ScoreBoard {
    protected int score,highScore;
    /**
     * Contructor kelas ScoreBoard
     * menginisialisasikan higscore dengan nilai 0;
     */
    public ScoreBoard(){
        this.highScore=0;
    }
    /**
     * getter Score
     * @return score
     */
    public int getScore() {
        return score;
    }
    /**
     * setter score
     * @param score nilai score yang ingin dimasukan
     */
    public void setScore(int score) {
        this.score = score;
    }
    /**
     * getter highScore
     * @return highScore
     */
    public int getHighScore() {
        return highScore;
    }
    /**
     * Method yang berfungsi untuk menampung hioghScore
     */
    public void setHighScore() {
        if(this.score<=this.highScore){
            this.highScore=this.highScore;
        }else{
            this.highScore=this.score;
        }
    }
    
    
}
