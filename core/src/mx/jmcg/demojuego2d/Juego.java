package mx.jmcg.demojuego2d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Juego principal (Aplicación)
//Autor: José Manuel Cruz Gil
public class Juego extends Game {
	private AssetManager manager;

	public Juego() {
	}

	public void create() {
		this.manager = new AssetManager();
		setScreen(new PantallaCargando(this, Pantallas.MENU));
	}

	public AssetManager getManager() {
		return this.manager;
	}

	public void render() {
		super.render();
	}
}
