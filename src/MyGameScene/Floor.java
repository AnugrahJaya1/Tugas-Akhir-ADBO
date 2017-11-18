/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyGameScene;

import AddAndMove.Add;
import AddAndMove.Move;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * kelas yang mengimplementasikan interface Move dan Korrdinat
 * @author GL552VX
 */
public class Floor implements Move, KoordinatAwal {

    private Spatial floor;
    private LinkedList<Spatial> listFloor;
    /**
     * Contructor kelas Floor
     * @param assetManager mendapatkan kelas AssetManager dari kelsa Engine
     * agar memiliki assetManager yang sama
     */
    public Floor(AssetManager assetManager) {
        this.listFloor = new LinkedList();

    }

    @Override
    public void move(float tpf, Node localRootNode, Node player) {
        Iterator<Spatial> iteratorFloor = this.listFloor.iterator();

        while (iteratorFloor.hasNext()) {

            Spatial iFloor = iteratorFloor.next();

            //iFloor.setLocalTranslation(-28.137022f, -3.6917496f, 50.410004f);
            iFloor.move(0, 0, -1.5f * tpf);//bergerak di sumbu z dengan kecepatan -1.5f
            //System.out.println("FLOOR " + iFloor.getLocalTranslation());
            if (iFloor.getLocalTranslation().z <= 11.6f) {
                iFloor.setLocalTranslation(-28.137022f, -3.6917496f, 50.410004f);//setting lokasi floor baru
            }
        }
    }
    /**
     * Method ini berfungsi untuk menambahkan model kedalam LinkedList
     * @param localRootNode Node yang memiliki child Node
     * @param bullet mendapatkan bulletAppState dari kjelas Engine
     * memiliki fungssi untuk mengeraskan floor
     */
    public void addToLinkedList(Node localRootNode, BulletAppState bullet) {
        this.floor = localRootNode.getChild("Floor");//load cactus dari Scene
        this.listFloor.addFirst(this.floor);//menambahkan floor ke-1
        this.listFloor.addLast(this.floor);//menambahkan floor ke-2
        bullet.getPhysicsSpace().add(this.floor.getControl(RigidBodyControl.class));//membuat floor padat
    }

    @Override
    public void setKoordinatAwal() {
        this.floor.setLocalTranslation(-28.137022f, -3.6917496f, 45.410004f);
    }

}
