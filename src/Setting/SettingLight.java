/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setting;

import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 * kelas yang mengimplementasikan interface Setting
 * @author GL552VX
 */
public class SettingLight implements Setting{
    DirectionalLight directionalLight;
    /**
     * Constructor kelas SettingLight
     * menginisialisaikan kelas DirectionalLight
     */
    public SettingLight(){
        this.directionalLight=new DirectionalLight();
    }
    
    @Override
    public void setting() {
        directionalLight.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        directionalLight.setColor(ColorRGBA.White);//set color white
    }
    /**
     * getter untuk DirectionalLight
     * @return directionLight
     */
    public DirectionalLight getDirectionalLight(){
        return this.directionalLight;
    }
    
    
}
