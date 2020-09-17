package mx.jmcg.demojuego2d;

import com.badlogic.gdx.graphics.Texture;

public class Bala extends Objeto {

    private final float VELOCIDAD_Y = Pantalla.ALTO/2; // 360 pxil/segundo
    public Bala(Texture textura,float x, float y){
        super(textura, x, y);
    }

    public void mover(float tiempo){
        float distancia = VELOCIDAD_Y *tiempo;
        sprite.setY(sprite.getY() + distancia);
    }
}
