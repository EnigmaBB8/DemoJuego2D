package mx.jmcg.demojuego2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class bolaFuego extends Objeto {

    private float vx = 350;

    public bolaFuego (Texture textura, float x, float y){
        super(textura,x,y);
    }

    public void moverDerecha(){
        float lapso = Gdx.graphics.getDeltaTime();
        float dx = vx*lapso;
        sprite.setX(sprite.getX() + dx);
    }

}
