package mx.jmcg.demojuego2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class PantallaSpaceInvaders extends Pantalla {


    //Aliens-enemigos
    private Array<Alien> arraAliens;
    private float timerAnimaAlien;
    private final float TIEMPO_FRAME_ALIEN =  0.5f;
    private float DX_ALIEN = 10; //Cambia de + a -
    private float DY_ALIEN = 60;
    private int pasoAliens = 10;  //0-20 inicia en el cenro

    //Nave
    private Nave nave;
    private Texture texturaNave;

    //Disparo
    private Texture texturebtnDisparo;
    private Bala bala;
    private Texture texturaBala;
    private Texture texturaAlienArriba;
    private Texture texturaAlienAbajo;
    private Texture texturaAlienMuriendo;

    private final Juego juego;
    public PantallaSpaceInvaders(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        crearAliens();
        crerNave();
        crearDisparo();
        crearBala();

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
        //detecta eventos de entrada (touch)
    }

    private void crearBala() {
        texturaBala =  (Texture)this.juego.getManager().get("space/bala.png");
    }

    private void crearDisparo() {
        texturebtnDisparo = (Texture)this.juego.getManager().get("space/disparo.png");
    }

    private void crerNave() {
        texturaNave = (Texture)this.juego.getManager().get("space/nave.png");
        nave = new Nave(texturaNave,ANCHO/2,ALTO*0.1F);

    }

    private void crearAliens() {
        texturaAlienArriba = (Texture) this.juego.getManager().get("space/enemigoArriba1.png");
        texturaAlienAbajo = (Texture)this.juego.getManager().get("space/enemigoAbajo.png");
        texturaAlienMuriendo = (Texture)this.juego.getManager().get("space/enemigoExplota.png");
        arraAliens = new Array<>(12*5);
        for(int i=0; i<5; i++){ //renglones, y
            for(int j=0; j<12; j++){ //columnas, x
                Alien alien = new Alien(texturaAlienArriba,texturaAlienAbajo,texturaAlienMuriendo,220+j*60, 350+i*60);
                arraAliens.add(alien);
            }
        }
    }

    @Override
    public void render(float delta) {

        actualizarBala(delta);
        verificarChoques();
        actualizarAliens(delta);

        borrarPantalla(0.2f,0.2f,0.2f);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        //Dibujar enemigos
        for (Alien alien : arraAliens) {
            alien.render(batch);
        }

        //Dibujar nave
        nave.render(batch);

        //Disparo
        batch.draw(texturebtnDisparo,ANCHO - texturebtnDisparo.getWidth()*1.5f, ALTO*0.2f);

        //Bala
        if(bala != null){
            bala.render(batch);
        }
        batch.end();
    }

    private void actualizarAliens(float delta) {
        timerAnimaAlien += delta;
        if(timerAnimaAlien>=TIEMPO_FRAME_ALIEN){
            //Cmabiar de estado
            for (Alien alien: arraAliens){
                if(alien.getEstado()==EstadoAlien.ARRIBA){
                    alien.setEstado(EstadoAlien.ABAJO);
                } else if (alien.getEstado()==EstadoAlien.ABAJO){
                    alien.setEstado(EstadoAlien.ARRIBA);
                }
                alien.moverHorizontal(DX_ALIEN);
            }
            timerAnimaAlien = 0; //Reinicia el conteo
            //Quitar Aliens MURIENDOe
            for(int i = arraAliens.size-1; i>=0; i--){
                if(arraAliens.get(i).getEstado() == EstadoAlien.MURIENDO){
                    arraAliens.removeIndex(i);
                }
            }
            //Cuenta pasos
            pasoAliens++;
            if(pasoAliens==20){
                pasoAliens = 0;
                DX_ALIEN = -DX_ALIEN;
                for(Alien alien: arraAliens){
                    alien.moverVertical(-DY_ALIEN);
                }
            }
        }
    }

    private void verificarChoques() {
        //Colision entre balas y enemigos
        if(bala!= null) {
            for (int i = arraAliens.size - 1; i >= 0; i--) {
                Alien alien = arraAliens.get(i);
                Object rectangle = null;

                Rectangle rectAlien = alien.sprite.getBoundingRectangle();
                Rectangle rectBala = bala.sprite.getBoundingRectangle();
                if (rectAlien.overlaps(rectBala)) {
                    //Colision
                    if(alien.getEstado()!=EstadoAlien.MURIENDO){
                        alien.setEstado(EstadoAlien.MURIENDO);
                        bala = null;
                        break;
                    }
                }
            }
        }
    }

    private void actualizarBala(float tiempo) {
        if(bala != null){
            bala.mover(tiempo);
            if(bala.sprite.getY() > ALTO){
                bala = null;
            }
        }
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

    private class ProcesadorEntrada implements InputProcessor {
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
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 v = new Vector3(screenX,screenY,0);
            camara.unproject(v); //coordenadas logicas
            float xBoton = ANCHO - texturebtnDisparo.getWidth()*1.5f;
            float yBoton = ALTO*0.2f;
            float anchoBoton = texturebtnDisparo.getWidth();
            float altoBoton = texturebtnDisparo.getHeight();

            Rectangle rectBboton = new Rectangle(xBoton, yBoton, anchoBoton, altoBoton);
            if(rectBboton.contains(v.x,v.y)){
                if(bala==null){
                    bala = new Bala(texturaBala, nave.sprite.getX() + nave.sprite.getWidth()/2, nave.sprite.getY()+nave.sprite.getHeight());
                }
                //Disparar
               }else if(v.x<=ANCHO/2){
                nave.moverIzquierda();
            }else {
                nave.moverDerecha();
            }
            return true;
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
}