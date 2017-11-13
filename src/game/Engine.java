/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author User
 */
public class Engine extends AbstractAppState {

    private final Node rootNode;
    private final Node localRootNode = new Node("Level 1");
    private final AssetManager assetManager;
    private final InputManager inputManager;
    private BulletAppState bulletAppState;
    private Spatial player, cactus;
    private CharacterControl playerControl, cactusControl;
    private final FlyByCamera flyByCamera;
    private final Camera camera;
    private ChaseCamera chaseCam;
    private AudioNode backsound;
    private final Vector3f playerWalkDirection = Vector3f.ZERO;

    private boolean left = false, right = false, up = false, down = false;

    public Engine(SimpleApplication app) {
        rootNode = app.getRootNode();
        assetManager = app.getAssetManager();
        inputManager = app.getInputManager();
        flyByCamera = app.getFlyByCamera();
        camera = app.getCamera();

    }

    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        bulletAppState = new BulletAppState();

        bulletAppState.setDebugEnabled(true);
        stateManager.attach(bulletAppState);

        rootNode.attachChild(localRootNode);
//        this.initSound();
        this.loadScene();

        this.loadFloor();

        this.loadPlayer();

        this.loadCactus();

        this.setUpLight();
        //(1, 0, -2)
        this.controler();

        flyByCamera.setEnabled(false);

        this.setCamera();

    }

    public void loadScene() {
        Spatial scene = assetManager.loadModel("Scenes/MyScene.j3o");
        localRootNode.attachChild(scene);
    }

    public void loadFloor() {
        //..load floor
        Spatial floor = localRootNode.getChild("Floor");
        //floor.move(0, 0, -3 * tpf);
        bulletAppState.getPhysicsSpace().add(floor.getControl(RigidBodyControl.class));
    }

    public void loadPlayer() {
        //load player
        player = localRootNode.getChild("Trex");
        player.setMaterial(assetManager.loadMaterial("Materials/mats.j3m"));
        CapsuleCollisionShape playerShape = new CapsuleCollisionShape(1, 10, 2);

        playerControl = new CharacterControl(playerShape, 1.0f);
        playerControl.setFallSpeed(100);
        playerControl.setGravity(14.5f);
        player.addControl(playerControl);

        bulletAppState.getPhysicsSpace().add(playerControl);
    }

    public void loadCactus() {
        cactus = localRootNode.getChild("Kaktus");
        //this.cactus.setLocalTranslation(0f, 0,0 );
        //cactus.setMaterial(assetManager.loadMaterial("Materials/mats.j3m"));
        //CapsuleCollisionShape cactusShape = new CapsuleCollisionShape(1, 1, 1);

        //cactusControl = new CharacterControl(cactusShape, 1.0f);
        //cactus.move(0, 0, -3 * tpf);
        //cactus.addControl(cactusControl);
        //bulletAppState.getPhysicsSpace().add(cactusControl);
    }

    private void setUpLight() {
        // We add light so we see the scene
        DirectionalLight dr = new DirectionalLight();
        dr.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        dr.setColor(ColorRGBA.White);
        rootNode.addLight(dr);
    }

    public void setCamera() {
        //chaseCam = new ChaseCamera(camera, player, inputManager);
        camera.setLocation(new Vector3f(-19.6191f, 13.182131f, -27.741205f));

        camera.setRotation(new Quaternion(0.2087082f, 0.33064002f, -0.07522783f, 0.9173106f));
    }

    public void controler() {
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("jump", new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addListener(actionListener, "up");
        inputManager.addListener(actionListener, "down");
        inputManager.addListener(actionListener, "left");
        inputManager.addListener(actionListener, "right");
        inputManager.addListener(actionListener, "Pause");
        inputManager.addListener(actionListener, "jump");

    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Pause") && !keyPressed) {
                setEnabled(!isEnabled());
            } else if (name.equals("up")) {
                up = keyPressed;
            } else if (name.equals("down")) {
                down = keyPressed;
            } else if (name.equals("left")) {
                left = keyPressed;
            } else if (name.equals("right")) {
                right = keyPressed;
            } else if (name.equals("jump")) {
                playerControl.jump();
            }

        }

    };

    @Override
    public void cleanup() {
        rootNode.detachChild(localRootNode);
        super.cleanup();

    }

    @Override
    public void update(float tpf) {
        Vector3f camDir = camera.getDirection().clone();
        Vector3f camLeft = camera.getLeft().clone();
        camDir.y = 0;
        camLeft.y = 0;

        camDir.normalizeLocal();
        camLeft.normalizeLocal();

        playerWalkDirection.set(0, 0, 0);

        if (left) {
            playerWalkDirection.addLocal(camLeft);
        }
        if (right) {
            playerWalkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            playerWalkDirection.addLocal(camDir);
        }
        if (down) {
            playerWalkDirection.addLocal(camDir.negate());
        }

        if (player != null) {
            playerWalkDirection.multLocal(100f).multLocal(tpf);
            playerControl.setWalkDirection(playerWalkDirection);
        }

//        Spatial kaktus = localRootNode.getChild("Kaktus");
//        ****\
        this.cactus.move(0, 0, -3 * tpf);
        //this.loadFloor(tpf);
        //this.loadCactus();
//        System.out.println("X = " + this.player.getLocalTranslation().x);
//        System.out.println("Y = " + this.player.getLocalTranslation().y);
//        System.out.println("Z = " + this.player.getLocalTranslation().z);
//
//        System.out.println("X = " + this.cactus.getLocalTranslation().x);
//        System.out.println("Y = " + this.cactus.getLocalTranslation().y);
//        System.out.println("Z = " + this.cactus.getLocalTranslation().z);
        if (Math.floor(this.player.getLocalTranslation().z) ==
                Math.ceil(this.cactus.getLocalTranslation().z)) {
            System.out.println("X = " + this.player.getLocalTranslation().x);
            System.out.println("Y = " + this.player.getLocalTranslation().y);
            System.out.println("Z = " + this.player.getLocalTranslation().z);

            System.out.println("X = " + this.cactus.getLocalTranslation().x);
            System.out.println("Y = " + this.cactus.getLocalTranslation().y);
            System.out.println("Z = " + this.cactus.getLocalTranslation().z);
        }
    }

}

//    public void initSound(){
//        backsound=new AudioNode(assetManager, "Sounds/BackSound/Backsound.wav", DataType.Stream);
//        backsound.setLooping(true);
//        backsound.setPositional(true);
//        backsound.setVolume(3);
//        rootNode.attachChild(backsound);
//        backsound.play();
//        
//    }
//        ****
//      BoundingBox boundingBox = (BoundingBox) kaktus.getWorldBound();
//      float radius = boundingBox.getXExtent();
//      float height = boundingBox.getYExtent();
//      CapsuleCollisionShape kaktusShape = new CapsuleCollisionShape(radius,height);
//       
//      CharacterControl kaktusControl = new CharacterControl(kaktusShape,1.0f);
//     
//   
//      bulletAppState.getPhysicsSpace().add(kaktusControl);
