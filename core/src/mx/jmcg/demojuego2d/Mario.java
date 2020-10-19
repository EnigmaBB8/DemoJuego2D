package mx.jmcg.demojuego2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Mario extends Objeto{
    private Animation<TextureRegion> animacion;

    //SALTO
    private float timerAnimacion;
    private float yBase;
    private float tAire;
    private final float  v0 = 100;
    private final float G = 20;
    private float tVuelo;
    private EstadoMario estado;  //O camina o salta

    //Estado de Caminar a la izquierda o derecha
    private EstadoCaminando estadoCaminando;

    private float DX = 5;

    public Mario(Texture textura, float x,float y){
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturaFrame = region.split(32,64);

        //Quieto
        sprite = new Sprite(texturaFrame[0][0]);
        sprite.setPosition(x,y);

        //Animcion
        TextureRegion[] arrFrames = {texturaFrame[0][3],texturaFrame[0][2],texturaFrame[0][1]};
        animacion = new Animation<TextureRegion>(0.1f,arrFrames); //velocidad
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;

        //Salto
        yBase = y;
        estado = EstadoMario.CAMINANDO;
        //Direccion de desplazamiento
        estadoCaminando = EstadoCaminando.QUIETO;
    }

    public void saltar(){
        estado = EstadoMario.SALTANDO;
        tAire = 0;
        tVuelo = 2*v0/G;
    }

    public EstadoMario getEstado(){
        return estado;
    }

    public void render(SpriteBatch batch){
        actualizar();
        float delta = Gdx.graphics.getDeltaTime(); //1/60
        timerAnimacion += delta;  //Acumula
        if(estado==EstadoMario.CAMINANDO){
            TextureRegion frame = animacion.getKeyFrame(timerAnimacion);
            if(estadoCaminando == EstadoCaminando.DERECHA && frame.isFlipX()){
                frame.flip(true,false);
            } else if (estadoCaminando == EstadoCaminando.IZQUIERDA && !frame.isFlipX()){
                frame.flip(true,false);
            } else{
                frame.flip(false,false);
            }
            batch.draw(frame,sprite.getX(),sprite.getY());
        } else {
            tAire += 9*delta;
            float y = yBase + v0*tAire - 0.5f*G*tAire*tAire;
            sprite.setY(y);
            super.render(batch);
            if(tAire>=tVuelo){
                sprite.setY(yBase);
                estado = EstadoMario.CAMINANDO;
            }
        }
    }

    private void actualizar() {
        if(estadoCaminando == EstadoCaminando.DERECHA){
            mover(DX);
        } else if (estadoCaminando == EstadoCaminando.IZQUIERDA){
            mover(-DX);
        }
    }

    private void mover(float dx) {
        sprite.setX(sprite.getX() + dx);
    }

    public void setEstadoCaminando(EstadoCaminando nuevoEstado) {
        estadoCaminando = nuevoEstado;
    }
}