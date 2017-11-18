/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyGameScene;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * kelas yang mengimplementasikan interface Attach
 * @author GL552VX
 */
public class Scene implements Attach {
    private Spatial Scene;
    protected Node localRootNode;
    /**
     * Contructor kelas Scene
     * @param assetManager mendapatkan kelas AssetManager dari kelsa Engine
     * agar memiliki assetManager yang sama
     */
    public Scene(AssetManager assetManager){
        localRootNode=new Node();
        Spatial scene = assetManager.loadModel("Scenes/MyScene.j3o");
        localRootNode.attachChild(scene);
    }

    @Override
    public void attachToRoot(Node root) {
       root.attachChild(localRootNode);
    }
    
    
}
