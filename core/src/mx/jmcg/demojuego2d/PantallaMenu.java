package mx.jmcg.demojuego2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import mx.jmcg.demojuego2d.Plataformas.PantallaMapas;

public class PantallaMenu extends Pantalla {
    private final Juego juego;
    private Texture texturaFondo;
    private Stage escenaMenu;

    public PantallaMenu(Juego juego) {
        this.juego = juego;
    }

    public void show() {
        this.texturaFondo = (Texture)this.juego.getManager().get("fondo.jpg");
        this.crearMenu();
    }

    private void crearMenu() {
        this.escenaMenu = new Stage(this.vista);
        Texture texturaBtnJugar = (Texture)this.juego.getManager().get("BotonesMenu/btnjugar.png");
        TextureRegionDrawable trdBtnJugar = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));
        Texture texturaBtnJugarInverso = (Texture)this.juego.getManager().get("BotonesMenu/btnjugar2.png");

        TextureRegionDrawable trdBtnJugarInverso = new TextureRegionDrawable(new TextureRegion(texturaBtnJugarInverso));
        ImageButton btnJugar = new ImageButton(trdBtnJugar, trdBtnJugarInverso);
        btnJugar.setPosition(640.0F, 360.0F, 1);
        btnJugar.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego,Pantallas.SPACE_INVADERS));
            }
        });
        this.escenaMenu.addActor(btnJugar);
        Gdx.input.setInputProcessor(this.escenaMenu);
    }

    public void render(float delta) {
        this.borrarPantalla();
        this.batch.setProjectionMatrix(this.camara.combined);
        this.batch.begin();
        this.batch.draw(this.texturaFondo, 0.0F, 0.0F);
        this.batch.end();
        this.escenaMenu.draw();
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
        this.texturaFondo.dispose();
        juego.getManager().unload("fondo.jpg");
        this.batch.dispose();
    }
}

