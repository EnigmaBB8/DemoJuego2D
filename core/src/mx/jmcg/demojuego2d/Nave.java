package mx.jmcg.demojuego2d;

import com.badlogic.gdx.graphics.Texture;

public class Nave extends Objeto {

    private final float DX_Nave = 10;

    public Nave(Texture texrura, float x, float y){
     super(texrura,x,y);
    }

    public void moverIzquierda() {
        //Posicion actual
        sprite.setX(sprite.getX() - DX_Nave);
    }

    public void moverDerecha() {
        //Posicion actual
        sprite.setX(sprite.getX() + DX_Nave);
    }
}
