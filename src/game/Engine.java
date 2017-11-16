/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

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
    private boolean jump = false;
    private final Vector3f playerWalkDirection = Vector3f.ZERO;

    private boolean left = false, right = false, up = false, down = false;
    private Spatial cactus;
    private LinkedList<Spatial> listCactus, listFloor;
    private Spatial floor;
    private AudioNode backsound;

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
        this.loadScene();

        //load player
        this.loadPlayer();

        //setting animasi 
        this.settingAnimasi();

        //setting light
        this.settingLight();

        //camera setting
        this.settingCamera();

        //memasukan floor
        this.addFloor();

        //Memasukan Kaktus
        this.addCactus();

        this.initAudio();
    }

    /**
     * Method loadScene method ini berfungsi untuk meload scene dari folder
     * Scene yang berada di project assets
     */
    public void loadScene() {
        Spatial scene = assetManager.loadModel("Scenes/MyScene.j3o");
        localRootNode.attachChild(scene);

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
    public void settingAnimasi() {
        control = player.getChild("Trex").getControl(AnimControl.class);
        channel = control.createChannel();
        channel.setAnim("Walk");
        channel.setLoopMode(LoopMode.Loop);
        channel.setSpeed(2f);//memberikan speed
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
        this.controler();
    }

    /**
     * Method settingCamera menyetting camera agar enak dilihat oleh user
     */
    public void settingCamera() {
        flyByCamera.setEnabled(false);
        // chaseCam = new ChaseCamera(cam, player, inputManager);
        cam.setLocation(new Vector3f(-2.4425137f, 4.6245356f, -5.354741f));//setting lokasi kamera
        cam.setRotation(new Quaternion(0.24459876f, 0.19741872f, -0.050939977f, 0.9479464f));//setting rotasi kamera

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
                jump = true;
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

        this.moveCactus(tpf);//method agar kaktus gerak
        this.moveFloor(tpf);//method agar floor gerak

    }

    /**
     * Method addCactus menambahkan cactus kedalam linkedList
     */
    public void addCactus() {
        this.cactus = localRootNode.getChild("Kaktus");//load cactus dari Scene
//        CapsuleCollisionShape cacbody=new CapsuleCollisionShape(0.55f, 1.1f);
//        RigidBodyControl rb=new RigidBodyControl(cacbody,0f);
//        this.cactus.addControl(rb);
//        bulletAppState.getPhysicsSpace().add(rb);
//        

        this.cactus.setLocalScale(0.4f);

        this.cactus.setLocalTranslation(0.5f, 1.52f, 15);
        this.listCactus.addFirst(this.cactus);//menambahkan cactus ke-1
        this.listCactus.addLast(this.cactus);//menambahkan cactus ke-2
        //bulletAppState.getPhysicsSpace().add(this.cactus.getControl(RigidBodyControl.class));
    }

    /**
     * Method addFloor menambahkan floor kedalam linkedList
     */
    public void addFloor() {
        this.floor = localRootNode.getChild("Floor");//load cactus dari Scene
        this.listFloor.addFirst(this.floor);//menambahkan floor ke-1
        this.listFloor.addLast(this.floor);//menambahkan floor ke-2
        bulletAppState.getPhysicsSpace().add(this.floor.getControl(RigidBodyControl.class));//membuat floor padat
    }

    /**
     * Method moveCactus method yang menangani agar cactus bisa bergerak
     *
     * @param tpf
     */
    public void moveCactus(float tpf) {
        Iterator<Spatial> iteratorCactus = this.listCactus.iterator();

        while (iteratorCactus.hasNext()) {
            Spatial iCactus = iteratorCactus.next();
            if (player.collideWith(iCactus.getWorldBound(), new CollisionResults()) != 0) {//otomatis kalau kena kaktus
                // System.out.println("collide");
                //setEnabled(!isEnabled());//udah berhenti di ekor kalau 1024 x 740
                //kalau 640 aman
            }
            iCactus.move(0, 0, -2.5f * tpf);//bergerak di sumbu z dengan kecepatan -2.5f
            //System.out.println("CACTUS " + iCactus.getLocalTranslation());
            if (iCactus.getLocalTranslation().z <= -15) {
                iCactus.setLocalTranslation(player.getLocalTranslation().x+0.555555f, 1.52f, 10);//setting lokasi cactus baru 0.5f(posisi x)
            }
        }
    }

    /**
     * Method moveFloor method yang mengangani agar floor bisa bergerak
     *
     * @param tpf
     */
    public void moveFloor(float tpf) {
        Iterator<Spatial> iteratorFloor = this.listFloor.iterator();

        while (iteratorFloor.hasNext()) {

            Spatial iFloor = iteratorFloor.next();

            //iFloor.setLocalTranslation(-28.137022f, -3.6917496f, 50.410004f);
            iFloor.move(0, 0, -1.5f * tpf);//bergerak di sumbu z dengan kecepatan -1.5f
            //System.out.println("FLOOR " + iFloor.getLocalTranslation());
            if (iFloor.getLocalTranslation().z <= 11.6f) {
                iFloor.setLocalTranslation(-28.137022f, -3.6917496f, 50f);//setting lokasi floor baru
            }
        }
    }

    /**
     * Method initAudio menambahkan audio audio diloop trus setVolume = 3
     */
    public void initAudio() {
        backsound = new AudioNode(assetManager, "Sounds/Backsound/fixed mono.wav", AudioData.DataType.Buffer);//mengambil lagu dari 
        backsound.setLooping(true);
        backsound.setPositional(true);
        backsound.setVolume(50);
        backsound.play();
        rootNode.attachChild(backsound);

    }

}
