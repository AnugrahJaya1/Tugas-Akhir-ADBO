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
 *
 * @author GL552VX
 */
public class Scene implements Attach {
    private Spatial Scene;
    protected Node localRootNode;
    /**
     * Constructor yang berfungsi untuk Meload Scene  dan menempelkannya ke dalam sebuah Node
     * @param assetManager 
     */
    public Scene(AssetManager assetManager){
        localRootNode=new Node();
        Spatial scene = assetManager.loadModel("Scenes/MyScene.j3o");
        localRootNode.attachChild(scene);
    }

    /**
     * Method yang berfungsi menelmpeknay sebuah Node kedalam Root
     * @param root 
     */
    @Override
    public void attachToRoot(Node root) {
       root.attachChild(localRootNode);
    }
    
    
}
