/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyGameScene;

/**
 * kelas yang mengatur game mulai atau tidak
 * @author User
 */
public class PlayGame {
    protected boolean isPlay;
    /**
     * Contructor kelas PlayGame
     */
    public PlayGame() {
        this.isPlay=true;
    }
    /**
     * getter untuk isPlay
     * @return true atau false
     */
    public boolean getIsPlay() {
        return isPlay;
    }
    /**
     * setter untuk isPlay
     * @param isPlay boolean yang akan disetting
     */
    public void setIsPlay(boolean isPlay) {
        this.isPlay = isPlay;
    }
    
    
}
