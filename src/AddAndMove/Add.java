/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddAndMove;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * interface yang memiliki Method addTOLinkedList
 * @author GL552VX
 */



public interface Add {
    /**
     * Method ini berfungsi untuk mengambil node dari localRootNode
     * lalu menyimpannya kedalam LinkedList
     * @param localRootNode //Node yang memiliki banyak Node
     */
    public void addToLinkedList(Node localRootNode);
}
