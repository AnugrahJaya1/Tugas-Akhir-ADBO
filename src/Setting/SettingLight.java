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
 *
 * @author GL552VX
 */
public class SettingLight implements Setting{
    DirectionalLight directionalLight;
    
    public SettingLight(){
        this.directionalLight=new DirectionalLight();
    }
    @Override
    public void setting() {
        directionalLight.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        directionalLight.setColor(ColorRGBA.White);//set color white
    }
    
    public DirectionalLight getDirectionalLight(){
        return this.directionalLight;
    }
    
    
}
