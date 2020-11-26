package mx.jmcg.demojuego2d;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PantallaCargando extends Pantalla {
    private Sprite spriteCargando;
    public static final float TIEMPO_ENTRE_FRAMES = 0.05F;
    private float timerAnimacion = 0.05F;
    private Texture texturaCargando;
    private AssetManager manager;
    private Juego juego;
    private Pantallas siguientePantalla;
    private int avance;
    private Texto texto;
    private Texture texturaFondo;

    public PantallaCargando(Juego juego, Pantallas siguientePantalla) {
        this.juego = juego;
        this.siguientePantalla = siguientePantalla;
    }

    public void show() {
        this.texturaFondo = new Texture("cargando/loading21.png");
        this.texturaCargando = new Texture("cargando/loading.png");
        this.spriteCargando = new Sprite(this.texturaCargando);
        this.spriteCargando.setPosition(640.0F - this.spriteCargando.getWidth() / 2.0F, 360.0F - this.spriteCargando.getHeight() / 2.0F);
        this.cargarRecursos(this.siguientePantalla);
        this.texto = new Texto("runner/game.fnt");
    }

    private void cargarRecursos(Pantallas siguientePantalla) {
        this.manager = this.juego.getManager();
        this.avance = 0;
        switch(siguientePantalla) {
            case MENU:
                this.cargarRecursosMenu();
                break;
            case SPACE_INVADERS:
                this.cargarRecursosSpace();
                break;
        }

    }

    private void cargarRecursosMenu() {
        this.manager.load("fondo.jpg", Texture.class);
        this.manager.load("BotonesMenu/btnjugar.png", Texture.class);
        this.manager.load("BotonesMenu/btnjugar2.png", Texture.class);
    }

    private void cargarRecursosSpace() {
        this.manager.load("space/bala.png", Texture.class);
        this.manager.load("space/disparo.png", Texture.class);
        this.manager.load("space/nave.png", Texture.class);
        this.manager.load("space/enemigoArriba1.png", Texture.class);
        this.manager.load("space/enemigoAbajo.png", Texture.class);
        this.manager.load("space/enemigoExplota.png", Texture.class);
    }

    public void render(float delta) {
        this.borrarPantalla(0.5F, 0.2F, 0.5F);
        this.batch.setProjectionMatrix(this.camara.combined);
        this.batch.begin();
        this.batch.draw(this.texturaFondo, 0.0F, 0.0F);
        this.spriteCargando.draw(this.batch);
        this.texto.mostrarMensaje(this.batch, this.avance + "%", 640.0F, 360.0F);
        this.batch.end();
        this.timerAnimacion -= delta;
        if (this.timerAnimacion <= 0.0F) {
            this.timerAnimacion = 0.05F;
            this.spriteCargando.rotate(20.0F);
        }

        this.actualizarCarga();
    }

    private void actualizarCarga() {
        if (this.manager.update()) {
            switch(this.siguientePantalla) {
                case MENU:
                    this.juego.setScreen(new PantallaMenu(this.juego));
                    break;
                case SPACE_INVADERS:
                    this.juego.setScreen(new PantallaSpaceInvaders(this.juego));
                    break;
            }
        }
        this.avance = (int)(this.manager.getProgress() * 100.0F);
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
        this.texturaCargando.dispose();
    }
}

