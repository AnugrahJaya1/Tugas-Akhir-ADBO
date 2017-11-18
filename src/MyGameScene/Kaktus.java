/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyGameScene;

import AddAndMove.Add;
import AddAndMove.Move;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * kelas ini merupakan subclass dari PlayGame
 * kelas ini juga mengimplementasikan Add,Move,Koordinat 
 * @author GL552VX
 */
public class Kaktus extends PlayGame implements Move, Add, KoordinatAwal {

    private Spatial cactus;
    private LinkedList<Spatial> listCactus;
    /**
     * Contructor kelas Kaktus
     * @param assetManager mendapatkan kelas AssetManager dari kelsa Engine
     * agar memiliki assetManager yang sama
     */
    //private boolean isPlay=true;
    public Kaktus(AssetManager assetManager) {
        this.listCactus = new LinkedList();
    }

    @Override
    public void move(float tpf, Node localRootNode, Node player) {
        Iterator<Spatial> iteratorCactus = this.listCactus.iterator();

        while (iteratorCactus.hasNext()) {
            Spatial iCactus = iteratorCactus.next();
            localRootNode.attachChild(iCactus);
            if (player.collideWith(iCactus.getWorldBound(), new CollisionResults()) != 0) {//otomatis kalau kena kaktus
                super.setIsPlay(false);
            }
            iCactus.move(0, 0, -3f * tpf);//bergerak di sumbu z dengan kecepatan -2.5f
            //System.out.println("CACTUS " + iCactus.getLocalTranslation());
            if (iCactus.getLocalTranslation().z <= -15) {
                iCactus.setLocalTranslation(player.getLocalTranslation().x + 0.5555555f, 1.52f, 10);//setting lokasi cactus baru 0.5f(posisi x)
                //lokasi x=player.getLocalTranslation().x+0.5555555f

            }
        }
    }

    @Override
    public void addToLinkedList(Node localRootNode) {
        this.cactus = localRootNode.getChild("Kaktus");//load cactus dari Scene
        this.cactus.setLocalTranslation(0.5f, 1.52f, 10);
        this.cactus.setLocalScale(0.3f);
        this.cactus.setLocalTranslation(0.5f, 1.52f, 10);
        this.listCactus.addFirst(this.cactus);//menambahkan cactus ke-1
        this.listCactus.addLast(this.cactus);//menambahkan cactus ke-2  
    }

    @Override
    public void setKoordinatAwal() {
        this.cactus.setLocalTranslation(0.5f, 1.52f, 10f);
    }
}
