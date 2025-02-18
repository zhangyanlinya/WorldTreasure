package manejadores;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.JsonReader;

import componentes.ModeloComponente;
import componentes.ComponenteFisica;
import fisicas.EstadoMovimiento;


/**
 * Created by aortiz on 29/01/18.
 */

public class EntidadFactoria {

    public static Entity crearEntidadEstatica(Model model, float x, float y, float z){

        //cojemos los limites del cuerpo
        final BoundingBox limitedelcuerpo=new BoundingBox();
        //creamos la entidad vacia y le añadiremos todos los componentes
        Entity entidad=new Entity();

        //componenteAshley para los objetos modelo y textura
        ModeloComponente componente = new ModeloComponente(model,x,y,z);

        //compotenteBulletAshley para añadir fisica a la entidad :)
        ComponenteFisica componenteFisica=new ComponenteFisica();

        //calculamos los limites del modelo pasado por parametos con sus dimensiones
        model.calculateBoundingBox(limitedelcuerpo);

        //vector para guardar informacino, no es necesario crearlo aqui es solo para que sea mas claro
        Vector3 tmpV=new Vector3();

        //construimos la forma para la colision aqui usamos ese vector, se puede sustuir tmpV por new Vertor3()
        btCollisionShape collisionShape = new btBoxShape(tmpV.set(limitedelcuerpo.getWidth()*0.5f,limitedelcuerpo.getHeight()*0.5f,limitedelcuerpo.getDepth()*0.5f));

       //ponemos toda la informacion , mass=0 non-dynamic
        componenteFisica.info=new btRigidBody.btRigidBodyConstructionInfo(0,null,collisionShape, Vector3.Zero);
        //construimos el cuerpo con esa informacion proporcionada
        componenteFisica.cuerpo=new btRigidBody(componenteFisica.info);
        //
        componenteFisica.cuerpo.userData=entidad;
        //establecemos el movimiento a partir de la posicion del objeto
        componenteFisica.movimiento=new EstadoMovimiento(componente.instancia.transform);

        //referenciamos al padre, añadimos el movimiento
        ((btRigidBody)componenteFisica.cuerpo).setMotionState(componenteFisica.movimiento);


        //añadimos los componentes a la entidad
        entidad.add(componente);
        entidad.add(componenteFisica);


        //la devolvemos
        return entidad;
    }

    public static Entity crearSmaug(float x, float y, float z){

        ModelLoader<?> cargadormodelo=new G3dModelLoader(new JsonReader());
        ModelData datosmodelo= cargadormodelo.loadModelData(Gdx.files.internal("smaug/smaug.g3dj"));

        Model modelo=new Model(datosmodelo,new TextureProvider.FileTextureProvider());

        ModeloComponente componente=new ModeloComponente(modelo,x,y,z);
        componente.instancia.transform.scale(0.09f,0.09f,0.05f);
        Entity entidad=new Entity();

        entidad.add(componente);

        return entidad;

    }

    public static Entity crearKnigth(float x, float y, float z){

        ModelLoader<?> cargadormodelo=new G3dModelLoader(new JsonReader());
        ModelData datosmodelo= cargadormodelo.loadModelData(Gdx.files.internal("knight/knight.g3dj"));
        //new AssetManager().load("knight/knight.obj",Model.class);//
        Model modelo=new Model(datosmodelo,new TextureProvider.FileTextureProvider());

        ModeloComponente componente=new ModeloComponente(modelo,x,y,z);
        componente.instancia.transform.scale(1f,1f,1f);
        Entity entidad=new Entity();

        entidad.add(componente);

        return entidad;

    }
    public static Entity crearDragon(float x, float y, float z){

        ModelLoader<?> cargadormodelo=new G3dModelLoader(new JsonReader());
        ModelData datosmodelo= cargadormodelo.loadModelData(Gdx.files.internal("dragon/dragon.g3dj"));

        Model modelo=new Model(datosmodelo,new TextureProvider.FileTextureProvider());

        ModeloComponente componente=new ModeloComponente(modelo,x,y,z);
       // componente.instancia.transform.scale(0.09f,0.09f,0.05f);
        Entity entidad=new Entity();

        entidad.add(componente);

        return entidad;

    }
}
