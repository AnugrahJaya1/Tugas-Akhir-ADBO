/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyGameScene;

import AddAndMove.Add;
import AddAndMove.Move;
import Animation.Animation;
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
public class Burung extends PlayGame implements Add, Move,KoordinatAwal {

    private Spatial bird;
    private LinkedList<Spatial> listBurung;
    /**
     * Contructor kelas Burung
     * @param assetManager mendapatkan kelas AssetManager dari kelsa Engine
     * agar memiliki assetManager yang sama
     */
    public Burung(AssetManager assetManager) {
        this.listBurung = new LinkedList();
    }

    @Override
    public void move(float tpf, Node localRootNode, Node player) {
        Iterator<Spatial> iteratorCactus = this.listBurung.iterator();

        while (iteratorCactus.hasNext()) {
            Spatial iBurung = iteratorCactus.next();
            localRootNode.attachChild(iBurung);
            if (player.collideWith(iBurung.getWorldBound(), new CollisionResults()) != 0) {//otomatis kalau kena kaktus
                super.setIsPlay(false);
            }
            iBurung.move(0, 0, -3f * tpf);//bergerak di sumbu z dengan kecepatan -2.5f
            if (iBurung.getLocalTranslation().z <= -15) {
                iBurung.setLocalTranslation(player.getLocalTranslation().x + 0.3f, 2f, 20);//setting lokasi burung baru 0.5f(posisi x)
                //lokasi x=player.getLocalTranslation().x+0.5555555f

            }
        }
    }

    @Override
    public void addToLinkedList(Node localRootNode) {
        this.bird = localRootNode.getChild("Bird");//load bird dari Scene
        this.bird.setLocalTranslation(0.3f, 2f, 20);
        this.bird.setLocalScale(0.25f);
        this.bird.setLocalTranslation(0.5f, 1.52f, 10);
        this.listBurung.addFirst(this.bird);//menambahkan bird ke-1
        this.listBurung.addLast(this.bird);//menambahkan bird ke-2
    }

    @Override
    public void setKoordinatAwal() {
        this.bird.setLocalTranslation(0.3f, 2, 20);
    }
}
