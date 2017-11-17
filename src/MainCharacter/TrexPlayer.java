/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainCharacter;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.scene.Node;

/**
 *
 * @author GL552VX
 */
public class TrexPlayer {
    private TrexModel trexModel;
    private Node player;
    private CharacterControl playerControl;
    public TrexPlayer(AssetManager assetManager) {
        this.trexModel=new TrexModel(assetManager);
    }
    public void loadPlayer(BulletAppState bulletAppState,Node localRootNode){
       Node trex = trexModel.getTrex();
       CapsuleCollisionShape capsulShape = new CapsuleCollisionShape(0.55f, 1.1f);//Membuat kapusl baru 
       playerControl = new CharacterControl(capsulShape, 0.5f);
       playerControl.setGravity(30);//memberikan gravitasi
       player.addControl(playerControl);
       bulletAppState.getPhysicsSpace().add(playerControl);//membuat palyer padat
       trex.setLocalTranslation(0, 0, 0);
       trex.setLocalScale(0.2f);

       player.attachChild(trex);
       localRootNode.attachChild(player);//menambahkan player kedalam localRootNode
    }
    
}
