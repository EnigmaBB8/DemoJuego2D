package mx.jmcg.demojuego2d.Plataformas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.JViewport;

import mx.jmcg.demojuego2d.Juego;
import mx.jmcg.demojuego2d.Pantalla;

public class PantallaMapas extends Pantalla {

    //Mapa
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer rendererMapa;

    private float timerBloque;

    private EstadoJuego estado = EstadoJuego.JUGANDO;
    private EscenaPausa escenaPausa;

    public PantallaMapas(Juego juego) {
    }

    @Override
    public void show() {
        crearMapa();
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearMapa() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("Mapas/mapa_mario.tmx",TiledMap.class);
        manager.finishLoading();
        mapa = manager.get("Mapas/mapa_mario.tmx");
        rendererMapa = new OrthogonalTiledMapRenderer(mapa);

        //Modificar el mapa
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(0);
        TiledMapTileLayer.Cell celda = capa.getCell(0,0);
        for (int y = 1; y < capa.getHeight(); y++){
            capa.setCell(0,y,celda);
        }

    }

    @Override
    public void render(float delta) {
        //Actualizar
        if(estado == EstadoJuego.JUGANDO){
            actualizarCamara();
            quitarBloques(delta);
        }
        borrarPantalla(0,1,0);

        batch.setProjectionMatrix(camara.combined); //SIEMPRE

        rendererMapa.setView(camara);
        rendererMapa.render();

        if(estado==EstadoJuego.PAUSADO){
            escenaPausa.draw();
        }

    }

    private void quitarBloques(float delta) {
        timerBloque += delta;
        if(timerBloque>1){
            timerBloque = 0;
            TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(0);
            TiledMapTileLayer.Cell celda = capa.getCell(0,0);
            int x = MathUtils.random(0,40);
            int y = MathUtils.random(0, 21);
            capa.setCell(x,y,null);
        }
    }

    private void actualizarCamara() {
        camara.position.x = camara.position.x + 5;
        camara.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    //Estados del juego
    private enum  EstadoJuego {
        JUGANDO,
        PAUSADO
    }

    private class ProcesadorEntrada implements InputProcessor {

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if(estado == EstadoJuego.JUGANDO){
                estado = EstadoJuego.PAUSADO;
                //if(escenaPausa == null){
                    escenaPausa = new EscenaPausa(vista,batch);
                //}
            }else if (estado== EstadoJuego.PAUSADO){
                estado = EstadoJuego.JUGANDO;
            }
            return true;
        }

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }


        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }

    private class EscenaPausa extends Stage
    {
        public EscenaPausa(Viewport vista , SpriteBatch batch){
            super(vista, batch);
            Pixmap pixmap = new Pixmap((int)(ANCHO*.75f), (int)(ALTO*0.8f), Pixmap.Format.RGB888);
            pixmap.setColor(0,0,1,0.5f);
            pixmap.fillCircle(300,300,250);
            Texture texture = new Texture(pixmap);
            Image imgPausa = new Image(texture);
            imgPausa.setPosition(camara.position.x, ALTO/2-pixmap.getHeight());
            this.addActor(imgPausa);
        }
    }
}
