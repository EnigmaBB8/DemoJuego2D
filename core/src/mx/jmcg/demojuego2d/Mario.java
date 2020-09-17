package mx.jmcg.demojuego2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Mario extends Objeto{
    private Animation<TextureRegion> animacion;
    private float timerAnimacion;

    public Mario(Texture textura, float x,float y){
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturaFrame = region.split(32,64);

        //Quieto
        sprite = new Sprite(texturaFrame[0][0]);
        sprite.setPosition(x,y);

        //Animcion
        TextureRegion[] arrFrames = {texturaFrame[0][1],texturaFrame[0][2],texturaFrame[0][3]};
        animacion = new Animation<TextureRegion>(0.1f,arrFrames);
        timerAnimacion = 0;
    }

    public void render(SpriteBatch batch){
        float delta = Gdx.graphics.getDeltaTime(); //1/60
        timerAnimacion += delta;  //Acumula
        TextureRegion frame = animacion.getKeyFrame(timerAnimacion);
        batch.draw(frame,sprite.getX(),sprite.getY());
    }
}