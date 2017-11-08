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
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author GL552VX
 */
public class Engine extends AbstractAppState {

    private final Node rootNode;
    private final Node localRootNode = new Node("Level 1");
    private final AssetManager assetManager;
    private final InputManager inputManager;
    private final FlyByCamera flyByCamera;
    private final Camera camera;
    private final Vector3f playerWalkDirection = Vector3f.ZERO;
    private BulletAppState bulletAppState;
    private Spatial sceneModels, player;
    private CharacterControl playerControl;
    private ChaseCamera chaseCam;
    private RigidBodyControl landscape;
    //private boolean left = false, right = false, up = false, down = false;

    public Engine(SimpleApplication app) {
        rootNode = app.getRootNode();
        assetManager = app.getAssetManager();
        inputManager = app.getInputManager();
        flyByCamera = app.getFlyByCamera();
        camera = app.getCamera();
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        rootNode.attachChild(rootNode);

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        bulletScene();
        //bulletModel();

        setUpLight();

    }

    public void bulletScene() {

        sceneModels = assetManager.loadModel("Scenes/MyScene.j3o");


        CollisionShape sceneShape = CollisionShapeFactory.createMeshShape(sceneModels);

        landscape = new RigidBodyControl(sceneShape, 0);

        CapsuleCollisionShape capsuleCollisionShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        playerControl = new CharacterControl(sceneShape, 0.05f);
        playerControl.setGravity(0);

        //sceneModels.addControl(playerControl);
        rootNode.attachChild(sceneModels);

        //bulletAppState.getPhysicsSpace().add(landscape);
        //bulletAppState.getPhysicsSpace().add(playerControl);

    }

    public void bulletModel() {
        player = assetManager.loadModel("Models/Trex/Trex.j3o");

        BoundingBox bouindingBox = (BoundingBox) player.getWorldBound();
        float radius = bouindingBox.getXExtent();
        float height = bouindingBox.getYExtent();

        CollisionShape modelsShape = CollisionShapeFactory.createDynamicMeshShape((Node) player);
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(radius, height);
        playerControl = new CharacterControl(capsuleShape, 0.05f);
        playerControl.setFallSpeed(30);
        playerControl.setGravity(0);
        player.addControl(playerControl);
        rootNode.attachChild(player);
        bulletAppState.getPhysicsSpace().add(player);
    }

    private void setUpLight() {
        // We add light so we see the scene
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1, 0, -2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
    }

    public void setBackgroundLight() {
        DirectionalLight dr = new DirectionalLight();
        dr.setDirection(new Vector3f(1, 0, -2).normalizeLocal());
        dr.setColor(ColorRGBA.White);
        rootNode.addLight(dr);
    }

}
