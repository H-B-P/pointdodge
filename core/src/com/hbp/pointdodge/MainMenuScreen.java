package com.hbp.pointdodge;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.utils.viewport.*;

public class MainMenuScreen implements Screen {
    final PointDodge game;
	OrthographicCamera camera;
	
	private Rectangle nxt_r;
	private Texture nxt_t;	
	
	private Rectangle prv_r;
	private Texture prv_t;	
	
	
	private Preferences prefs;
	
	private Rectangle TUTORIAL_r;
	private Texture TUTORIAL_t;
	
	private Rectangle CAMPAIGN_I_r;
	private Texture CAMPAIGN_I_t;
	
	private Rectangle CAMPAIGN_II_r;
	private Texture CAMPAIGN_II_t;
	
	
	private Rectangle LEVELS_r;
	private Texture LEVELS_t;
	
	private Texture TRIM_t;
	private Texture SQUARETRIM_t;
	
	private Texture contact_t;
	
	private int score_one;
	private int score_two;
	private int score_three;
	private int score_four;
	
	private BitmapFont font;
	
	private int MINESPEED;
	
	private Rectangle selector_r;
	private Rectangle selector_prv_r;
	private Rectangle selector_nxt_r;
	private Texture selector_t;
	
	private float tp_x;
	private float tp_y;
	
	boolean are_instructions_visible;
	
	private String preferred_mode;
	private String preferred_topic;
	
	private Sound hellosound;
	private Sound arrowsound;
	
	private boolean ANDROID;
	
	private boolean wastouched;
	
	private ScreenViewport viewport;
	
	private int ylocus;
	private int xlocus;
	
	public MainMenuScreen(final PointDodge gam, int minespeed, boolean android, boolean play_the_sound) {
		
		ylocus=1;
		xlocus=0;
		
		wastouched=false;
		
		ANDROID=android;
		
		MINESPEED=minespeed;
		
		prefs = Gdx.app.getPreferences("galen_preferences");
		
		if (!prefs.contains("MODE")){
			prefs.putString("MODE", "intro");
			prefs.flush();
		}
		if (!prefs.contains("TOPIC")){
			prefs.putString("TOPIC", "NONE");
			prefs.flush();
		}
		
		preferred_mode=prefs.getString("MODE");
		preferred_topic=prefs.getString("TOPIC");
		
		
		selector_r = new Rectangle();
		selector_r.x=10;
		selector_r.y=390;
		selector_r.height=80;
		selector_r.width=140;
		selector_t = new Texture(Gdx.files.internal("selector_minespeed.png"));
		
		nxt_t = new Texture(Gdx.files.internal("fwd_but.png"));
		prv_t = new Texture(Gdx.files.internal("bak_but.png"));
		
		selector_prv_r = new Rectangle();
		selector_prv_r.x=selector_r.x;
		selector_prv_r.y=selector_r.y;
		selector_prv_r.height=40;
		selector_prv_r.width=40;
		
		selector_nxt_r = new Rectangle();
		selector_nxt_r.x=selector_r.x+100;
		selector_nxt_r.y=selector_r.y;
		selector_nxt_r.height=40;
		selector_nxt_r.width=40;
		
		
		
		TUTORIAL_r = new Rectangle();
		TUTORIAL_r.x=60;
		TUTORIAL_r.y=300;
		TUTORIAL_r.height=60;
		TUTORIAL_r.width=200;
		TUTORIAL_t = new Texture(Gdx.files.internal("abutton_long_tutorial.png"));
		
		CAMPAIGN_I_r = new Rectangle();
		CAMPAIGN_I_r.x=60;
		CAMPAIGN_I_r.y=220;
		CAMPAIGN_I_r.height=60;
		CAMPAIGN_I_r.width=200;
		CAMPAIGN_I_t = new Texture(Gdx.files.internal("abutton_long_campaign_I.png"));
		
		CAMPAIGN_II_r = new Rectangle();
		CAMPAIGN_II_r.x=60;
		CAMPAIGN_II_r.y=140;
		CAMPAIGN_II_r.height=60;
		CAMPAIGN_II_r.width=200;
		CAMPAIGN_II_t = new Texture(Gdx.files.internal("abutton_long_campaign_II.png"));
		
		LEVELS_r = new Rectangle();
		LEVELS_r.x=60;
		LEVELS_r.y=60;
		LEVELS_r.height=60;
		LEVELS_r.width=200;
		LEVELS_t = new Texture(Gdx.files.internal("abutton_long_freeplay.png"));

		TRIM_t = new Texture(Gdx.files.internal("abutton_long_trim.png"));
		SQUARETRIM_t = new Texture(Gdx.files.internal("fb_trim.png"));
		
		contact_t=new Texture(Gdx.files.internal("contact_block.png"));
		
		game = gam;
		
		
		font = new BitmapFont();
		
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 480);
		viewport=new ScreenViewport(camera);
		
		
		hellosound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/341250__jeremysykes__select01.wav"));
		arrowsound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/344510__jeremysykes__select03.wav"));
		if (play_the_sound){
			hellosound.play();
		}
	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		
		
		
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Gdx.graphics.setWindowedMode(320, 480);
		
		if (Gdx.input.isKeyJustPressed(Keys.DOWN)){ylocus=(ylocus+1+5)%5;}
		if (Gdx.input.isKeyJustPressed(Keys.UP)){ylocus=(ylocus-1+5)%5;}
		
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT)){xlocus=(xlocus+1+2)%2;}
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)){xlocus=(xlocus-1+2)%2;}
		
		if (!ANDROID){Gdx.graphics.setWindowedMode(320, 480);}
		
		camera.update();
		
		Vector3 scr_vec= new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
		Vector3 irl_vec=camera.unproject(scr_vec);
		tp_x=irl_vec.x;
		tp_y=irl_vec.y;
		
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		
	    font.setColor(Color.BLACK);
	    game.batch.draw(TUTORIAL_t, TUTORIAL_r.x, TUTORIAL_r.y);
		game.batch.draw(CAMPAIGN_I_t, CAMPAIGN_I_r.x, CAMPAIGN_I_r.y);
		game.batch.draw(CAMPAIGN_II_t, CAMPAIGN_II_r.x, CAMPAIGN_II_r.y);
		game.batch.draw(LEVELS_t, LEVELS_r.x, LEVELS_r.y);
		
		game.batch.draw(selector_t, selector_r.x, selector_r.y);
		game.batch.draw(prv_t, selector_prv_r.x, selector_prv_r.y);
		game.batch.draw(nxt_t, selector_nxt_r.x, selector_nxt_r.y);
		font.draw(game.batch, ""+MINESPEED, selector_r.x+60, selector_r.y+25);
		
		if (TUTORIAL_r.contains(tp_x,tp_y)){
			ylocus=1;
		}
		
		if (CAMPAIGN_I_r.contains(tp_x,tp_y)){
			ylocus=2;
		}
		
		if (CAMPAIGN_II_r.contains(tp_x,tp_y)){
			ylocus=3;
		}
		
		if (LEVELS_r.contains(tp_x,tp_y)){
			ylocus=4;
		}
		
		if (ylocus==1){
			game.batch.draw(TRIM_t, TUTORIAL_r.x, TUTORIAL_r.y);
		}
		if (ylocus==2){
			game.batch.draw(TRIM_t, CAMPAIGN_I_r.x, CAMPAIGN_I_r.y);
		}
		if (ylocus==3){
			game.batch.draw(TRIM_t, CAMPAIGN_II_r.x, CAMPAIGN_II_r.y);
		}
		if (ylocus==4){
			game.batch.draw(TRIM_t, LEVELS_r.x, LEVELS_r.y);
		}
		if (ylocus==0 && xlocus==0){
			game.batch.draw(SQUARETRIM_t, selector_prv_r.x, selector_prv_r.y);
		}
		if (ylocus==0 && xlocus==1){
			game.batch.draw(SQUARETRIM_t, selector_nxt_r.x, selector_nxt_r.y);
		}
		
		
		game.batch.draw(contact_t, selector_r.x+160, selector_r.y+20);
		
		game.batch.end();
		
		//tp_x=Gdx.input.getX();
		//tp_y=Gdx.input.getY();
		
		if ((!ANDROID&&(Gdx.input.justTouched()||Gdx.input.isKeyJustPressed(Keys.SPACE)))||(ANDROID&&wastouched&&!Gdx.input.isTouched())) {
			
				if (ylocus==0 && xlocus==0 && MINESPEED>100){
					MINESPEED-=5;
					arrowsound.play();
				}
				if (ylocus==0 && xlocus==1 && MINESPEED<400){
					MINESPEED+=5;
					arrowsound.play();
				}
				
				if (ylocus==1){
					game.setScreen(new GameScreen_2(game, MINESPEED, "NONE", "tutorial" , "endless", ANDROID));
		            dispose();
				}
				
				if (ylocus==2){
					
				}

				if (ylocus==3){
					
				}
				
				if (ylocus==4){
		            game.setScreen(new LevelSelectScreen(game, "NONE", MINESPEED, "chill", ANDROID));
		            dispose();
				}
		}
		
		wastouched=false;
	      
	      if (Gdx.input.isTouched()){
	    	  wastouched=true;
	      }
	}

	@Override
	public void resize(int width, int height) {
		float scale=0f;
		if (width>=160 && height>=240){
			scale=0.5f;
		}
		if (width>=320 && height>=480){
			scale=1f;
		}
		while (width>=(320*(scale+1)) && height>=(480*(scale+1))){
			scale+=1f;
		}
		System.out.println("Target scale is: "+ scale);
		//System.out.println("width: "+ width);
		//System.out.println("height: "+ height);
		camera.setToOrtho(false, (float)width/(float)scale, (float)height/(float)scale);
		camera.translate(-((float)width/(float)scale-320)/2f, -((float)height/(float)scale-480)/2f);
		//camera.update();
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		nxt_t.dispose();	
		
		prv_t.dispose();	
		
		CAMPAIGN_I_t.dispose();
		CAMPAIGN_II_t.dispose();
		
		
		LEVELS_t.dispose();
		
		
		TRIM_t.dispose();
		
		contact_t.dispose();

		font.dispose();
		
		selector_t.dispose();

				
		hellosound.stop();
		hellosound.dispose();
	}
}