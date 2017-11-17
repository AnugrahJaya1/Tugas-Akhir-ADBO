/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainCharacter;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.scene.Node;

/**
 *
 * @author GL552VX
 */
public class TrexModel {
    protected Node trex;
    public TrexModel(AssetManager assetManager){
        this.trex=new Node();
        this.trex=(Node) assetManager.loadModel("Models/TyrannoBlender/Trex.j3o");
    }

    public Node getTrex() {
        return trex;
    }

    public void setTrex(Node trex) {
        this.trex = trex;
    }
    
    

}
