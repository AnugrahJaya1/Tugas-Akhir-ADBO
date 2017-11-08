package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import game.Engine;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    
    protected Spatial pohon,model;
    
    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(ColorRGBA.White);
        stateManager.attach(new Engine(this));
        model=assetManager.loadModel("Models/Trex/Trex.j3o");
        model.setLocalTranslation(0, 0, 0);
        
        rootNode.attachChild(model);
        
        pohon=assetManager.loadModel("Models/Kaktus/Kaktus.j3o");
        pohon.setLocalTranslation(0, 0, -50);
        rootNode.attachChild(pohon);
        //this.printeScore();
    }

    public void printeScore() {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        final BitmapText helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setColor(ColorRGBA.Black);
        helloText.setLocalTranslation(400, helloText.getLineHeight(), 0);
        int i = 0, score = 0;
        while (true) {
            helloText.setText("Score : " + score + "");
            helloText.setColor(ColorRGBA.Black);
            helloText.setLocalTranslation(400, helloText.getLineHeight(), 0);
            guiNode.attachChild(helloText);
            score++;
        }
        //helloText.setText("TESSSSSSSTTTTTT");
//        helloText.setColor(ColorRGBA.Black);
//        helloText.setLocalTranslation(400,helloText.getLineHeight() , 0);
//        guiNode.attachChild(helloText);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        pohon.move(0, 0, 1*tpf);
        if(model.getLocalTranslation()==pohon.getLocalTranslation()){
            pohon.setLocalTranslation(0, 0, 1*tpf);
        }
        //dapetin koordinat z dari models.getLocalTr....
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
