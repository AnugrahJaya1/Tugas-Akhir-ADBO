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
import MyGameScene.KoordinatAwal;
import MyGameScene.PlayGame;
import MyGameScene.Scene;
import Setting.SettingLight;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
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
public class Engine extends AbstractAppState implements KoordinatAwal {

    private final Node rootNode;
    private final Node localRootNode = new Node("Level 1");
    private final AssetManager assetManager;
    private final InputManager inputManager;
    private BulletAppState bulletAppState;
    private Node player;
    private CharacterControl playerControl;
    private final FlyByCamera flyByCamera;
    private final Camera cam;
    private ChaseCamera chaseCam;
    private final Vector3f playerWalkDirection = Vector3f.ZERO;
    private Spatial cactus;
    private Audio audio;
    private Scene scene;
    private Kaktus kaktus;
    private Floor floor;
    private SettingLight settingLight;
    private Burung burung;
    private Animation animation;
    private PlayGame playGame;

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
        playerControl = new CharacterControl();
        player = new Node();
        this.audio = new Audio();
        this.scene = new Scene(assetManager);
        this.kaktus = new Kaktus(assetManager);
        this.floor = new Floor(assetManager);
        this.settingLight = new SettingLight();
        this.burung = new Burung(assetManager);
        this.animation = new Animation();
        playGame = new PlayGame();
        setEnabled(false);
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
        this.kaktus.setKoordinatAwal();

        //load player
        this.loadPlayer();

        //load burung
        this.burung.addToLinkedList(localRootNode);
        this.burung.setKoordinatAwal();

        //load animasi
        this.animation.setAnimation(localRootNode, "Burung");
        this.animation.setAnimation(player, "Trex");

        //setting light
        this.settingLight();
        localRootNode.addLight(this.settingLight.getDirectionalLight());

        //camera setting
        this.settingCamera();

        //load floor
        this.floor.addToLinkedList(localRootNode, bulletAppState);

        //add background
        this.audio.initAudio(assetManager, localRootNode);

        //add controler
        this.controler();

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

        if (player != null) {
            playerWalkDirection.multLocal(100f).multLocal(tpf);
            playerControl.setWalkDirection(playerWalkDirection);
        }
        //System.out.println(this.play);
        
        
        this.kaktus.move(tpf, localRootNode, player);
        this.floor.move(tpf, localRootNode, player);
        this.burung.move(tpf, localRootNode, player);
        if (!this.kaktus.getIsPlay() || !this.burung.getIsPlay()) {
            setEnabled(false);
            
        }
        if (this.rest == true) {
            this.restart();
            this.kaktus.setIsPlay(true);
            this.burung.setIsPlay(true);
        }

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
     * Method settingLight memberikan light kepada backgroud agar scene bisa
     * terlihat
     */
    public void settingLight() {
        DirectionalLight dr = new DirectionalLight();
        dr.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        dr.setColor(ColorRGBA.White);//set color white
        rootNode.addLight(dr);//menambahkan dr kepada rootNode
    }

    /**
     * Method settingCamera menyetting camera agar enak dilihat oleh user
     */
    public void settingCamera() {
        flyByCamera.setEnabled(false);
        //chaseCam = new ChaseCamera(cam, player, inputManager);
        cam.setLocation(new Vector3f(-5.3264656f, 6.359166f, -8.943834f));
        cam.setRotation(new Quaternion(0.21726707f, 0.24867764f, -0.057346355f, 0.9421602f));
    }

    /**
     * Method controler
     * 
     */
    public void controler() {
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("restart", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addMapping("quit", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("start", new KeyTrigger(KeyInput.KEY_S));

        inputManager.addListener(actionListener, "Pause");
        inputManager.addListener(actionListener, "jump");
        inputManager.addListener(actionListener, "restart");
        inputManager.addListener(actionListener, "quit");
        inputManager.addListener(actionListener, "start");
    }
    private boolean rest = false;
    
    /**
     *
     */
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Pause") && !keyPressed) {
                setEnabled(!isEnabled());
            } else if (name.equals("jump")) {
                playerControl.jump();
            } else if (name.equals("restart")) {
                rest = true;
                setEnabled(true);
            } else if (name.equals("quit")) {
                System.exit(0);
            } else if (name.equals("start")) {
                setEnabled(true);
            }
        }
    };

    @Override
    public void cleanup() {
        rootNode.detachChild(localRootNode);
        super.cleanup();

    }
    /**
     * Method ini berfungsi untuk mengembalikan
     * posisi Trex(player),kaktus,floor,burung
     * ke posisi awal pada saat game dimulai
     * 
     */
    public void restart() {
        this.kaktus.setKoordinatAwal();
        this.floor.setKoordinatAwal();
        this.burung.setKoordinatAwal();
        this.setKoordinatAwal();
        this.rest = false;
    }

    @Override
    public void setKoordinatAwal() {
        this.player.setLocalTranslation(0, 0, 0);
    }

}
