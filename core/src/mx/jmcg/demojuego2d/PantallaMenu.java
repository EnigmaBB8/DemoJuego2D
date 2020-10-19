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

    private final Juego juego;  //para setScreen
    //Fondo
    private Texture texturaFondo;
    //Menu (botones)
    private Stage escenarioMenu;

    public PantallaMenu(Juego juego) {
        this.juego = juego;
    }
    // Cuando la pantalla se va a mostrar
    //Inicializamos los objetos
    @Override
    public void show() {
        texturaFondo = new Texture("fondo.jpg");

        crearMenu();
    }

    private void crearMenu() {
        escenarioMenu = new Stage(vista);

        //boton jugar
        Texture textureBtnJugar = new Texture("BotonesMenu/btnjugar.png");
        TextureRegionDrawable trdBtnJugar = new TextureRegionDrawable(new TextureRegion(textureBtnJugar));
        Texture textureBtnJugarInverso = new Texture("BotonesMenu/btnjugar2.png");
        TextureRegionDrawable trdBtnJugarInverso = new TextureRegionDrawable(new TextureRegion(textureBtnJugarInverso));

        ImageButton btnJugar = new ImageButton(trdBtnJugar,trdBtnJugarInverso);
        btnJugar.setPosition(ANCHO/2,ALTO/2, Align.center);

        //Programar el evento de click
        btnJugar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //cambiamos de pantalla
                //juego.setScreen(new PantallaSpaceInvaders(juego)); //Para que pueda salir
                //juego.setScreen(new PantallaRunner(juego));
                juego.setScreen(new PantallaMapas(juego));
            }
        });

        escenarioMenu.addActor(btnJugar);

        Gdx.input.setInputProcessor(escenarioMenu);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined); //escalar la info
        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.end();
        escenarioMenu.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
        batch.dispose();
    }
}
