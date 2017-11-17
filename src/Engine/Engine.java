/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import Animation.Animation;
import AudioPack.Audio;
import MyGameScene.Burung;
import MyGameScene.Floor;
import MyGameScene.Kaktus;
import MyGameScene.Scene;
import Setting.SettingLight;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
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
import com.jme3.collision.CollisionResults;
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
import java.util.Iterator;
import java.util.LinkedList;

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
    private Node player;//diedit harusna spatial
    private CharacterControl playerControl;
    private final FlyByCamera flyByCamera;
    private final Camera cam;
    private ChaseCamera chaseCam;
    private Node localNode;

    private AnimChannel channel;
    private AnimControl control;

    private final Vector3f playerWalkDirection = Vector3f.ZERO;

    private boolean left = false, right = false, up = false, down = false;
    private Spatial cactus;
    //private Spatial burung;
    private LinkedList<Spatial> listCactus, listFloor;
    private AudioNode backsound;
    private AnimControl control2;
    private AnimChannel channel2;
    private Audio audio;
    private Scene scene;
    private Kaktus kaktus;
    private Floor floor;
    private SettingLight settingLight;
    private Burung burung;
    private Animation animation;
    /**
     * Constructor kelas Engine
     *
     * @param app
     */
    public Engine(SimpleApplication app) {
        rootNode = app.getRootNode();
        assetManager = app.getAssetManager();
        inputManager = app.getInputManager();
        flyByCamera = app.getFlyByCamera();
        cam = app.getCamera();
        localNode = new Node();
        player = new Node();
        this.listCactus = new LinkedList();
        this.listFloor = new LinkedList();
        this.audio =new Audio();
        this.scene=new Scene(assetManager);
        this.kaktus=new Kaktus(assetManager);
        this.floor=new Floor(assetManager);
        this.settingLight=new SettingLight();
        this.burung=new Burung(assetManager);
        this.animation=new Animation();
    }

    /**
     * inisialisai awal pada saat app dijalankan
     *
     * @param stateManager
     * @param app
     */
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        bulletAppState = new BulletAppState();
        //bulletAppState.setDebugEnabled(true);
        stateManager.attach(bulletAppState);
        rootNode.attachChild(localRootNode);
        
        
        //load scene
        this.scene.attachToRoot(localRootNode);
        //load Kaktus
        this.kaktus.addToLinkedList(localRootNode);
        //load player
        this.loadPlayer();
        //load burung
        this.burung.addToLinkedList(localRootNode);
        //load animasi
        this.animation.setAnimation(localRootNode, "Burung");
        
        this.settingAnimasiTrex();//blom di bikin oop
        
        //setting light
        this.settingLight();
        localRootNode.addLight(this.settingLight.getDirectionalLight());
        //camera setting
        this.settingCamera();//bloom oop 
        //load floor
        this.floor.addToLinkedList(localRootNode,bulletAppState);
        
        this.audio.initAudio(assetManager, localRootNode);
      
        
    }
    @Override
    public void update(float tpf) {
        Vector3f camDir = cam.getDirection().clone();
        Vector3f camLeft = cam.getLeft().clone();
        camDir.y = 0;
        camLeft.y = 0;
        // memgubah koor tapi ga bisa lompat kalau di taro disini;

        //playerControl.setPhysicsLocation(new Vector3f(0, 0, 0));
        System.out.println(player.getLocalTranslation().x);//masih rubah2
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

        //this.moveCactus(tpf);//method agar kaktus gerak
       
        this.kaktus.move(tpf,localRootNode);
        this.floor.move(tpf, localRootNode);
        this.burung.move(tpf, localRootNode);
    }


    
    

    /**
     * Method loadPlayer method ini berfungsi untuk meload player dari folder
     * Scene yang berada di project assets dalam method ini juga memasukan
     * player(models) kedalam Node selain itu di method ini menyetting graviti
     * model dan membuatnya menjadi padat
     */
    public void loadPlayer() {
        Node trex = (Node) assetManager.loadModel("Models/TyrannoBlender/Trex.j3o");//Node yang ada Model Trex
        CapsuleCollisionShape capsulShape = new CapsuleCollisionShape(0.55f, 1.1f);//Membuat kapusl baru 
        playerControl = new CharacterControl(capsulShape, 0.5f);
        playerControl.setGravity(30);//memberikan gravitasi
        player.addControl(playerControl);
        bulletAppState.getPhysicsSpace().add(playerControl);//membuat palyer padat
        trex.setLocalTranslation(0, 0, 0);
        trex.setLocalScale(0.2f);

        player.attachChild(trex);
        localRootNode.attachChild(player);//menambahkan player kedalam localRootNode
    }
    

    /**
     * Method settingAnimasi menyetting animasi memberikan speed
     */
    public void settingAnimasiTrex() {
        control = player.getChild("Trex").getControl(AnimControl.class);
        channel = control.createChannel();
        channel.setAnim("Walk");
        channel.setLoopMode(LoopMode.Loop);
        channel.setSpeed(2f);//memberikan speed
    }
    
//    public void settingAnimasiBurung() {
//        control2 = localRootNode.getChild("Burung").getControl(AnimControl.class);
//        channel2 = control2.createChannel();
//        channel2.setAnim("Walk");
//        channel2.setLoopMode(LoopMode.Loop);
//        channel2.setSpeed(2f);//memberikan speed
//    }
    

    /**
     * Method settingLight memberikan light kepada backgroud agar scene bisa
     * terlihat
     */
    public void settingLight() {
        DirectionalLight dr = new DirectionalLight();
        dr.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        dr.setColor(ColorRGBA.White);//set color white
        rootNode.addLight(dr);//menambahkan dr kepada rootNode
        this.controler();
    }

    /**
     * Method settingCamera menyetting camera agar enak dilihat oleh user
     */
    public void settingCamera() {
        flyByCamera.setEnabled(false);
        chaseCam = new ChaseCamera(cam, player, inputManager);
        cam.setLocation(new Vector3f(-3.1215358f, 5.0140944f, -7.774544f));
        cam.setRotation(new Quaternion(0.20579396f, 0.18203616f, -0.03899343f, 0.9607243f)); 

    }

    /**
     * Method controler
     *
     */
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
    /**
     *
     */
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

    /**
     * Method cleanup
     */
    @Override
    public void cleanup() {
        rootNode.detachChild(localRootNode);
        super.cleanup();

    }

    /**
     * Method update untuk menangani setiap update
     *
     * @param tpf
     */
    
}