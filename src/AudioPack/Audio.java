/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioPack;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

/**
 * kelas Untuk memberikan backSound pada game
 * @author GL552VX
 */
public class Audio {

    private AudioNode backsound;
    /**
     * Method ini menambahkan backSounnd ketika game di jalankan
     * @param assetManager
     * @param root Node tujuan  yang akan di attach backSound
     */
    public void initAudio(AssetManager assetManager,Node root) {
        backsound = new AudioNode(assetManager, "Sounds/Backsound/fixed mono.wav", AudioData.DataType.Buffer);//mengambil lagu dari 
        backsound.setLooping(true);
        backsound.setPositional(true);
        backsound.setVolume(50);
        backsound.play();
        root.attachChild(backsound);

    }
    
    
}
