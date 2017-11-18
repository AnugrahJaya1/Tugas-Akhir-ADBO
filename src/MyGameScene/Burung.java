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
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author GL552VX
 */
public class Burung  implements Add,Move{
    private Spatial bird;
    private LinkedList<Spatial> listBurung;
    public Burung(AssetManager assetManager) {
        this.listBurung=new LinkedList();
    }
    
    /**
     * method untuk menggerakan burung 
     * @param tpf
     * @param localRootNode 
     */
    
    public void move(float tpf,Node localRootNode) {
       Iterator<Spatial> iteratorCactus = this.listBurung.iterator();

        while (iteratorCactus.hasNext()) {
            Spatial iCactus = iteratorCactus.next();
            localRootNode.attachChild(iCactus);
            //if (player.collideWith(iCactus.getWorldBound(), new CollisionResults()) != 0) {//otomatis kalau kena kaktus
            // System.out.println("collide");
            // setEnabled(!isEnabled());//udah berhenti di ekor kalau 1024 x 740
            // kalau 640 aman
            //}
            iCactus.move(0, 0, -2.5f * tpf);//bergerak di sumbu z dengan kecepatan -2.5f
            //System.out.println("CACTUS " + iCactus.getLocalTranslation());
            if (iCactus.getLocalTranslation().z <= -15) {
                iCactus.setLocalTranslation(0.555555f, 1.52f, 10);//setting lokasi cactus baru 0.5f(posisi x)
                //lokasi x=player.getLocalTranslation().x+0.5555555f

            }
        }
    }
  
   /**
    * Method  untuk menambahakan burung ke dalam linkedList 
    * @param localRootNode 
    */
    public void addToLinkedList(Node localRootNode) {
     this.bird =localRootNode.getChild("Bird");//load cactus dari Scene
//        CapsuleCollisionShape cacbody=new CapsuleCollisionShape(0.55f, 1.1f);
//        RigidBodyControl rb=new RigidBodyControl(cacbody,0f);
//        this.cactus.addControl(rb);
//        bulletAppState.getPhysicsSpace().add(rb);
//        
        this.bird.setLocalScale(0.4f);
        this.bird.setLocalTranslation(0.5f, 1.52f, 10);
        this.listBurung.addFirst(this.bird);//menambahkan cactus ke-1
        this.listBurung.addLast(this.bird);//menambahkan cactus ke-2
        //bulletAppState.getPhysicsSpace().add(this.cactus.getControl(RigidBodyControl.class));
    }
}
