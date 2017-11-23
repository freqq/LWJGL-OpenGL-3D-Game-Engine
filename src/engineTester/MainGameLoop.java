package engineTester;
 
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
 
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRender;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import textures.TerrainTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
 
public class MainGameLoop {
 
    public static void main(String[] args) {
 
        DisplayManager.createDisplay();
        Loader loader = new Loader();
   
        //******TERRAIN TEXTURE STAFF*********
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        
        //************************************
        
        
        //******MODELS TEXTURE STAFF**********
        ModelData treeData = OBJFileLoader.loadOBJ("tree");
        RawModel treeModel = loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(), treeData.getNormals(), treeData.getIndices());
        TexturedModel tree = new TexturedModel(treeModel,new ModelTexture(loader.loadTexture("tree")));
        
        ModelData pineData = OBJFileLoader.loadOBJ("pine");
        RawModel pineModel = loader.loadToVAO(pineData.getVertices(), pineData.getTextureCoords(), pineData.getNormals(), pineData.getIndices());
        TexturedModel pine = new TexturedModel(pineModel,new ModelTexture(loader.loadTexture("pineChristmas")));
        pine.getTexture().setHasTransparency(true);
        
        ModelData lampData = OBJFileLoader.loadOBJ("lamp");
        RawModel lampModel = loader.loadToVAO(lampData.getVertices(), lampData.getTextureCoords(), lampData.getNormals(), lampData.getIndices());
        TexturedModel lamp = new TexturedModel(lampModel,new ModelTexture(loader.loadTexture("lamp")));

        ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
        RawModel grassModel = loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(), grassData.getNormals(), grassData.getIndices());
        TexturedModel grass = new TexturedModel(grassModel,new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        //grass.getTexture().setFalseLighting(true);
        
        ModelData fernData = OBJFileLoader.loadOBJ("fern");
        RawModel fernModel = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices());
        TexturedModel fern = new TexturedModel(fernModel,new ModelTexture(loader.loadTexture("fern2")));
        fern.getTexture().setHasTransparency(true);
        fern.getTexture().setFalseLighting(true);

        ModelData lowPolyData = OBJFileLoader.loadOBJ("lowPolyTree");
        RawModel lowPolyModel = loader.loadToVAO(lowPolyData.getVertices(), lowPolyData.getTextureCoords(), lowPolyData.getNormals(), lowPolyData.getIndices());
        TexturedModel low = new TexturedModel(lowPolyModel,new ModelTexture(loader.loadTexture("lowPolyTree")));

        RawModel flowerModel = loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(), grassData.getNormals(), grassData.getIndices());
        TexturedModel flower = new TexturedModel(flowerModel,new ModelTexture(loader.loadTexture("flower")));
        flower.getTexture().setHasTransparency(true);
        flower.getTexture().setFalseLighting(true);

        ModelData playerData = OBJFileLoader.loadOBJ("person");
        RawModel playerModel = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(), playerData.getNormals(), playerData.getIndices());
        TexturedModel player = new TexturedModel(playerModel,new ModelTexture(loader.loadTexture("playerTexture")));
        
        //************************************
        
        
        //******PLAYER CONFIGURATION**********
        Player pl = new Player(player, new Vector3f(100,0,-50),0,0,0,1);
        
        //***************CAMERA***************
        Camera camera = new Camera(pl);
        
        //*************TERRAINS***************
        Terrain terrain =new Terrain(0,-1,loader,texturePack, blendMap,"heightmap");
        Terrain terrain2 =new Terrain(-1,-1,loader,texturePack, blendMap,"heightmap");

        
        //LISTS OF DIFFERENT TYPE
        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        MasterRender renderer = new MasterRender(loader);
        GuiRenderer guiRenderer = new GuiRenderer(loader);
        MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(),terrain);
        List<Entity> entities = new ArrayList<Entity>();
        List<Light> lights = new ArrayList<Light>();
        
        GuiTexture gui = new GuiTexture(loader.loadTexture("logo"),new Vector2f(0.5f,0.5f), new Vector2f(0.25f,0.25f));
        guis.add(gui);
        
        
        //ADDING ENTITIES TO THE TERRAIN
        Random random = new Random();
        for(int i=0;i<400;i++) {
        		float x = random.nextFloat() * 800-400;
        		float z = random.nextFloat() * - 600;
        		float y = terrain.getHeightOfTerrain(x, z);
        		entities.add(new Entity(fern,new Vector3f(x,y,z),0,random.nextFloat()*360,0,0.65f));
	        	if(i%3==0) {	        		
	        		/*float x = random.nextFloat() * 800-400;
	        		float z = random.nextFloat() * - 600;
	        		float y = terrain.getHeightOfTerrain(x, z);
	        		entities.add(new Entity(low,new Vector3f(x,y,z),0,0,0,0.6f));*/
	        		
	        		x = random.nextFloat() * 800-400;
	        		z = random.nextFloat() * - 600;
	        		y = terrain.getHeightOfTerrain(x, z);
	        		entities.add(new Entity(pine,new Vector3f(x,y,z),0,random.nextFloat()*180,0,2f));
	        	}
        	}
        
        
        lights.add(new Light(new Vector3f(0,1000,-7000),new Vector3f(0.4f,0.4f,0.4f)));
        lights.add(new Light(new Vector3f(185,10,-293),new Vector3f(2,0,0), new Vector3f(1,0.01f,0.002f)));
        lights.add(new Light(new Vector3f(370,17,-300),new Vector3f(0,2,2), new Vector3f(1,0.01f,0.002f)));
        lights.add(new Light(new Vector3f(293,7,-305),new Vector3f(2,2,0), new Vector3f(1,0.01f,0.002f)));

        entities.add(new Entity(lamp, new Vector3f(185,-4.7f,-293),0,0,0,1));
        entities.add(new Entity(lamp, new Vector3f(370,4.2f,-300),0,0,0,1));
        entities.add(new Entity(lamp, new Vector3f(293,-6.8f,-305),0,0,0,1));

        
	   	while(!Display.isCloseRequested()){
	           camera.move();
	           picker.update();
	           
	           //player movement
	           if(pl.getPosition().x <0)
	        	   		pl.move(terrain2);
	           else
	        	   		pl.move(terrain);
	           
	           //render models on screen
	           renderer.processEntity(pl);
	           renderer.processTerrain(terrain);
	           renderer.processTerrain(terrain2);
	           for(Entity entits : entities) {
	        	   		renderer.processEntity(entits);
	           }
	           renderer.render(lights, camera);
	           
	           
	           //guiRenderer.render(guis);
	           DisplayManager.updateDisplay();
        }
	   	guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
 
    }
 
}