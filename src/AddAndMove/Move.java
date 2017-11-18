/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddAndMove;

import com.jme3.scene.Node;

/**
 *
 * @author GL552VX
 */
public interface Move {
    /**
     * Method ini berfungsi untuk menggerakan Sebuah model
     * @param tpf
     * @param localRootNode 
     */
    public void move(float tpf,Node localRootNode);
}
