package Animation;


import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.scene.Node;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GL552VX
 */
public class Animation {
    protected AnimChannel channel;
    protected AnimControl control;
     public void setAnimation(Node root,String modelName){
        control = root.getChild(modelName).getControl(AnimControl.class);
        channel = control.createChannel();
        channel.setAnim("Walk");
        channel.setLoopMode(LoopMode.Loop);
        channel.setSpeed(2f);//memberikan speed
    }
}
