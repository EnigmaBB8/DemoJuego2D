package mx.jmcg.demojuego2d;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class PantallaRunner extends Pantalla {

    private Juego juego;

    //Personaje
    private Mario mario;
    private Texture texturaMario;

    //Fondo
    private Texture texturaFondo;
    private float xFondo = 0;


    public PantallaRunner(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        crearMario();
        crearFondo();
    }

    private void crearFondo() {
        texturaFondo = new Texture("fondoMario_5.jpg");
    }

    private void crearMario() {
        texturaMario = new Texture("marioSprite.png");
        mario = new Mario(texturaMario,ANCHO/2,64);

    }

    @Override
    public void render(float delta) {
        actulizar();
        
        borrarPantalla(0,0,0.5f);
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, xFondo,0);
        batch.draw(texturaFondo,xFondo + texturaFondo.getWidth(),0);
        mario.render(batch);
        batch.end();
    }

    private void actulizar() {
        /*xFondo-=5;
        if(xFondo==-texturaFondo.getWidth()){
            xFondo = 0;
        }
         */
        actualizarMario();
        actulizarCamara();
    }

    private void actualizarMario() {
        mario.sprite.setX(mario.sprite.getX()+2);
    }

    private void actulizarCamara() {
        float xCamara = camara.position.x;
        //xCamara++;
        xCamara=mario.sprite.getX();
        camara.position.x = xCamara;
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
}
