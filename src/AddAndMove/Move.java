/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddAndMove;

import com.jme3.scene.Node;

/**
 * interface ini memiliki method Move
 * @author GL552VX
 */
public interface Move {
    /**
     * Method ini berfungsi untuk menggerakan sebuah model
     * @param speed mempengaruhi keceptan untuk menggerakan model
     * @param tpf tpf didapatkan dari Method Update yang berasal dari AbstractAppState
     * @param localRootNode Node yang memiliki child Node
     * @param player node yang memiliki Models yang akan di Load
     */
    
    public void move(float speed,float tpf,Node localRootNode,Node player);
}