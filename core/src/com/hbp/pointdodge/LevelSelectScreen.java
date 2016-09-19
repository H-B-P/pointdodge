package com.hbp.pointdodge;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;

public class LevelSelectScreen implements Screen {
    final PointDodge game;
	OrthographicCamera camera;
	
	private Rectangle nxt_r;
	private Texture nxt_t;	
	
	private Rectangle prv_r;
	private Texture prv_t;
	
	private Rectangle one_r;
	private Texture one_t;
	
	private Rectangle two_r;
	private Texture two_t;
	
	private String TOPIC;
	
	private Rectangle three_r;
	private Texture three_t;
	
	private Rectangle four_r;
	private Texture four_t;
	
	private Preferences prefs;
	
	private int score_one;
	private int score_two;
	private int score_three;
	private int score_four;
	
	private BitmapFont font;
	
	private int MINESPEED;
	
	private float tp_x;
	private float tp_y;
	
	private Texture TRIM_t;
	
	private Rectangle selector_r;
	private Rectangle selector_prv_r;
	private Rectangle selector_nxt_r;
	private Texture selector_t;
	
	private Texture mode_background_t;
	
	private Rectangle menu_button_r;
	private Rectangle endless_button_r;
	private Texture abutton_corner_t;
	private Texture abutton_corner_trim_t;
	
	private Texture difficulty_arrow_t;
	
	private String MODE;
	
	boolean are_instructions_visible;
	
	private Sound hellosound;
	private Sound arrowsound;
	
	private boolean ANDROID;
	
	private boolean wastouched;
	
	public LevelSelectScreen(final PointDodge gam, String topic, int minespeed, String mode, boolean android) {
		
		wastouched=false;
		
		TOPIC=topic;
		
		ANDROID=android;
		
		MODE=mode;
		
		MINESPEED=minespeed;
		
		are_instructions_visible=false;
		
		prefs = Gdx.app.getPreferences("galen_preferences");
		
		if (TOPIC=="NONE"){
			score_two=prefs.getInteger("score_NONE_basic");
			
			one_t = new Texture(Gdx.files.internal("abutton_intro.png"));
			two_t = new Texture(Gdx.files.internal("abutton_basis.png"));
			three_t = new Texture(Gdx.files.internal("abutton_intro.png"));
			four_t = new Texture(Gdx.files.internal("abutton_basis.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_boring.png"));
		}
		if (TOPIC=="CARTESIAN_I"){
			score_one=prefs.getInteger("score_CARTESIAN_add");
			score_two=prefs.getInteger("score_CARTESIAN_flip");
			score_three=prefs.getInteger("score_CARTESIAN_multiply");
			score_four=prefs.getInteger("score_CARTESIAN_lines");
			
			one_t = new Texture(Gdx.files.internal("abutton_xdotydot.png"));
			two_t = new Texture(Gdx.files.internal("abutton_xdotdotydotdot.png"));
			three_t = new Texture(Gdx.files.internal("abutton_xy.png"));
			four_t = new Texture(Gdx.files.internal("abutton_xdotdotydot.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_yellow.png"));

		}
		
		if (TOPIC=="CARTESIAN_II"){
			score_one=prefs.getInteger("score_CARTESIAN_add");
			score_two=prefs.getInteger("score_CARTESIAN_flip");
			score_three=prefs.getInteger("score_CARTESIAN_multiply");
			score_four=prefs.getInteger("score_CARTESIAN_lines");
			
			one_t = new Texture(Gdx.files.internal("abutton_xdoty.png"));
			two_t = new Texture(Gdx.files.internal("abutton_xdotdoty.png"));
			three_t = new Texture(Gdx.files.internal("abutton_xdotdotdoty.png"));
			four_t = new Texture(Gdx.files.internal("abutton_xdotdotdotydotdotdot.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_yellow.png"));

		}
		
		if (TOPIC=="CARTESIAN_III"){
			score_one=prefs.getInteger("score_CARTESIAN_add");
			score_two=prefs.getInteger("score_CARTESIAN_flip");
			score_three=prefs.getInteger("score_CARTESIAN_multiply");
			score_four=prefs.getInteger("score_CARTESIAN_lines");
			
			one_t = new Texture(Gdx.files.internal("abutton_xdotydash.png"));
			two_t = new Texture(Gdx.files.internal("abutton_xdotydashdot.png"));
			three_t = new Texture(Gdx.files.internal("abutton_xdotydotdash.png"));
			four_t = new Texture(Gdx.files.internal("abutton_xdotydashdash.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_yellow.png"));

		}
		
		if (TOPIC=="POLAR"){
			score_one=prefs.getInteger("score_POLAR_theta");
			score_two=prefs.getInteger("score_POLAR_r");
			score_three=prefs.getInteger("score_POLAR_power");
			score_four=prefs.getInteger("score_POLAR_switch");
			
			one_t = new Texture(Gdx.files.internal("abutton_theta.png"));
			two_t = new Texture(Gdx.files.internal("abutton_radius.png"));
			three_t = new Texture(Gdx.files.internal("abutton_powers.png"));
			four_t = new Texture(Gdx.files.internal("abutton_switch.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_green.png"));

		}
		if (TOPIC=="POWERS"){
			score_one=prefs.getInteger("score_POWERS_positive");
			score_two=prefs.getInteger("score_POWERS_roots");
			score_three=prefs.getInteger("score_POWERS_negative");
			score_four=prefs.getInteger("score_POWERS_exponent");
			
			one_t = new Texture(Gdx.files.internal("abutton_positive.png"));
			two_t = new Texture(Gdx.files.internal("abutton_roots.png"));
			three_t = new Texture(Gdx.files.internal("abutton_negative.png"));
			four_t = new Texture(Gdx.files.internal("abutton_exponent.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_purple.png"));

		}
		if (TOPIC=="MATRIX"){
			score_one=prefs.getInteger("score_MATRIX_rotation");
			score_two=prefs.getInteger("score_MATRIX_diagonal");
			score_three=prefs.getInteger("score_MATRIX_singular");
			score_four=prefs.getInteger("score_MATRIX_arbitrary");
			
			one_t = new Texture(Gdx.files.internal("abutton_rotation.png"));
			two_t = new Texture(Gdx.files.internal("abutton_diagonal.png"));
			three_t = new Texture(Gdx.files.internal("abutton_singular.png"));
			four_t = new Texture(Gdx.files.internal("abutton_arbitrary.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_red.png"));

		}
		
		
		
		if (TOPIC=="ARGAND"){
			score_one=prefs.getInteger("score_ARGAND_function");
			score_two=prefs.getInteger("score_ARGAND_add");
			score_three=prefs.getInteger("score_ARGAND_multiply");
			score_four=prefs.getInteger("score_ARGAND_power");
			
			one_t = new Texture(Gdx.files.internal("abutton_function.png"));
			two_t = new Texture(Gdx.files.internal("abutton_add.png"));
			three_t = new Texture(Gdx.files.internal("abutton_multiply.png"));
			four_t = new Texture(Gdx.files.internal("abutton_powers.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_cyan.png"));

		}
		
		
		nxt_r = new Rectangle();
		nxt_r.x=260;
		nxt_r.y=20;
		nxt_r.height=40;
		nxt_r.width=40;
		nxt_t = new Texture(Gdx.files.internal("fwd_but.png"));
		
		prv_r = new Rectangle();
		prv_r.x=20;
		prv_r.y=20;
		prv_r.height=40;
		prv_r.width=40;
		prv_t = new Texture(Gdx.files.internal("bak_but.png"));
		
		selector_r = new Rectangle();
		selector_r.x=10;
		selector_r.y=390;
		selector_r.height=80;
		selector_r.width=140;
		
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
		
		selector_r = new Rectangle();
		selector_r.x=10;
		selector_r.y=390;
		selector_r.height=80;
		selector_r.width=140;
		
		selector_t = new Texture(Gdx.files.internal("selector_timespeed.png"));
		
		menu_button_r = new Rectangle();
		menu_button_r.x=170;
		menu_button_r.y=440;
		menu_button_r.height=30;
		menu_button_r.width=100;
		
		endless_button_r = new Rectangle();
		endless_button_r.x=170;
		endless_button_r.y=400;
		endless_button_r.height=30;
		endless_button_r.width=100;
		
		
		abutton_corner_t=new Texture(Gdx.files.internal("abutton_corner.png"));
		abutton_corner_trim_t=new Texture(Gdx.files.internal("abutton_corner_trim.png"));

		
		one_r = new Rectangle();
		one_r.x=10;
		one_r.y=480-180;
		one_r.height=60;
		one_r.width=140;
		
		
		two_r = new Rectangle();
		two_r.x=10;
		two_r.y=480-250;
		two_r.height=60;
		two_r.width=140;
		
		
		three_r = new Rectangle();
		three_r.x=10;
		three_r.y=480-320;
		three_r.height=60;
		three_r.width=140;
		
		
		four_r = new Rectangle();
		four_r.x=10;
		four_r.y=480-390;
		four_r.height=60;
		four_r.width=140;
		
		
		difficulty_arrow_t = new Texture(Gdx.files.internal("difficulty_arrow.png"));
		
		mode_background_t = new Texture(Gdx.files.internal("blank_block.png"));
		
		game = gam;
		
		
		
		font = new BitmapFont();
		
		arrowsound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/344510__jeremysykes__select03.wav"));
		hellosound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/344508__jeremysykes__select04.wav"));
		//hellosound.play();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 480);
	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		
		//tp_x=Gdx.input.getX();
		//tp_y=Gdx.input.getY();
		
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (!ANDROID){Gdx.graphics.setWindowedMode(320, 480);}
		
		camera.update();
		
		Vector3 scr_vec= new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
		Vector3 irl_vec=camera.unproject(scr_vec);
		tp_x=irl_vec.x;
		tp_y=irl_vec.y;
		
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		
		game.batch.draw(difficulty_arrow_t, 260, 90);
		
		
	    font.setColor(Color.BLACK);
	    
	    if (TOPIC!="NONE"){
	    	
			game.batch.draw(one_t, one_r.x, one_r.y);
			game.batch.draw(two_t, two_r.x, two_r.y);
			game.batch.draw(three_t, three_r.x, three_r.y);
			game.batch.draw(four_t, four_r.x, four_r.y);
			
			//if (MODE=="gen"){
			if (1==0){
				font.draw(game.batch, "SCORE:", one_r.x+150, one_r.y+35);
				font.draw(game.batch, ((Integer)score_one).toString(), one_r.x+220, one_r.y+35);
				font.draw(game.batch, "SCORE:", two_r.x+150, two_r.y+35);
				font.draw(game.batch, ((Integer)score_two).toString(), two_r.x+220, two_r.y+35);
				font.draw(game.batch, "SCORE:", three_r.x+150, three_r.y+35);
				font.draw(game.batch, ((Integer)score_three).toString(), three_r.x+220, three_r.y+35);
				font.draw(game.batch, "SCORE:", four_r.x+150, four_r.y+35);
				font.draw(game.batch, ((Integer)score_four).toString(), four_r.x+220, four_r.y+35);
			}
	    }
	    else{
	    	game.batch.draw(one_t, one_r.x, one_r.y);

			game.batch.draw(two_t, two_r.x, two_r.y);
			if (MODE=="gen"){
				font.draw(game.batch, "SCORE:", two_r.x+150, two_r.y+35);
				font.draw(game.batch, ((Integer)score_two).toString(), two_r.x+220, two_r.y+35);
			}
	    }
	    
	    game.batch.draw(abutton_corner_t, menu_button_r.x,menu_button_r.y);
	    game.batch.draw(abutton_corner_t, endless_button_r.x,endless_button_r.y);
	    font.draw(game.batch, "MENU", menu_button_r.x+35,menu_button_r.y+20);
	    
	    font.draw(game.batch, MODE, endless_button_r.x+5,endless_button_r.y+20);
	    
	    
	    if (menu_button_r.contains(tp_x,tp_y)){
			game.batch.draw(abutton_corner_trim_t, menu_button_r.x, menu_button_r.y);
		}
	    if (endless_button_r.contains(tp_x,tp_y)){
			game.batch.draw(abutton_corner_trim_t, endless_button_r.x, endless_button_r.y);
		}
	    
		game.batch.draw(mode_background_t, 70, 20);
		font.draw(game.batch, "TOPIC:  " + TOPIC, 70+15, 20+27);
		game.batch.draw(prv_t, prv_r.x, prv_r.y);
		game.batch.draw(nxt_t, nxt_r.x, nxt_r.y);
		
		game.batch.draw(selector_t, selector_r.x, selector_r.y);
		game.batch.draw(prv_t, selector_prv_r.x, selector_prv_r.y);
		game.batch.draw(nxt_t, selector_nxt_r.x, selector_nxt_r.y);
		font.draw(game.batch, ""+MINESPEED, selector_r.x+60, selector_r.y+25);
		if (one_r.contains(tp_x,tp_y)){
			game.batch.draw(TRIM_t, one_r.x, one_r.y);
		}
		
		if (two_r.contains(tp_x,tp_y)){
			game.batch.draw(TRIM_t, two_r.x, two_r.y);
		}
		if (TOPIC!="NONE"){
			if (three_r.contains(tp_x,tp_y)){
				game.batch.draw(TRIM_t, three_r.x, three_r.y);
			}
			if (four_r.contains(tp_x,tp_y)){
				game.batch.draw(TRIM_t, four_r.x, four_r.y);
			}
		}
		game.batch.end();

		if ((!ANDROID&&Gdx.input.justTouched())||(ANDROID&&wastouched&&!Gdx.input.isTouched())) {
			if (!are_instructions_visible){
				if (selector_prv_r.contains(tp_x, tp_y) && MINESPEED>40){
					MINESPEED-=10;
					//arrowsound.play();
				}
				if (selector_nxt_r.contains(tp_x, tp_y) && MINESPEED<500){
					MINESPEED+=10;
					//arrowsound.play();
				}
				if (menu_button_r.contains(tp_x, tp_y)){
					game.setScreen(new MainMenuScreen(game, MINESPEED, ANDROID, true));
		            dispose();
				}
				if (endless_button_r.contains(tp_x, tp_y)){
					if (MODE.equals("chill")){
						MODE="gen";
					}
					else if (MODE.equals("gen")){
						MODE="wall";
					}
					else if (MODE.equals("wall")){
						MODE="chill";
					}
				}
				if (TOPIC=="NONE"){
					if (one_r.contains(tp_x,tp_y)){
			            game.setScreen(new GameScreen_2(game, MINESPEED, "NONE", "intro" , "endless", ANDROID));
			            dispose();
					}
					if (two_r.contains(tp_x,tp_y)){
					}
					if (prv_r.contains(tp_x,tp_y)){
			            game.setScreen(new LevelSelectScreen(game, "ARGAND", MINESPEED, MODE, ANDROID));
			            dispose();
					}
					if (nxt_r.contains(tp_x,tp_y)){
			            game.setScreen(new LevelSelectScreen(game, "CARTESIAN_I", MINESPEED, MODE, ANDROID));
			            dispose();
					}
	
				}
				
				if (TOPIC=="CARTESIAN_I"){
					if (one_r.contains(tp_x,tp_y)){
						game.setScreen(new GameScreen_2(game, MINESPEED, "CARTESIAN_I", "xdotydot" , MODE, ANDROID));
						dispose();
					}
					if (two_r.contains(tp_x,tp_y)){
						game.setScreen(new GameScreen_2(game, MINESPEED, "CARTESIAN_I", "xdotdotydotdot" , MODE, ANDROID));
						dispose();
					}
					if (three_r.contains(tp_x,tp_y)){
						game.setScreen(new GameScreen_2(game, MINESPEED, "CARTESIAN_I", "xy" , MODE, ANDROID));
						dispose();
					}
					if (four_r.contains(tp_x,tp_y)){
						game.setScreen(new GameScreen_2(game, MINESPEED, "CARTESIAN_I", "xdotdotydot" , MODE, ANDROID));
						dispose();
					}
					if (prv_r.contains(tp_x,tp_y)){
						//game.setScreen(new LevelSelectScreen(game, "NONE", MINESPEED, MODE, ANDROID));
						game.setScreen(new LevelSelectScreen(game, "CARTESIAN_III", MINESPEED, MODE, ANDROID));
			            dispose();
					}
					if (nxt_r.contains(tp_x,tp_y)){
						game.setScreen(new LevelSelectScreen(game, "CARTESIAN_II", MINESPEED, MODE, ANDROID));
			            dispose();
					}
	
				}
				
				if (TOPIC=="CARTESIAN_II"){
					if (one_r.contains(tp_x,tp_y)){
						game.setScreen(new GameScreen_2(game, MINESPEED, "CARTESIAN_II", "xdoty" , MODE, ANDROID));
						dispose();
					}
					if (two_r.contains(tp_x,tp_y)){
						game.setScreen(new GameScreen_2(game, MINESPEED, "CARTESIAN_II", "xdotdoty" , MODE, ANDROID));
						dispose();
					}
					if (three_r.contains(tp_x,tp_y)){
						game.setScreen(new GameScreen_2(game, MINESPEED, "CARTESIAN_II", "xdotdotdoty" , MODE, ANDROID));
						dispose();
					}
					if (four_r.contains(tp_x,tp_y)){
						game.setScreen(new GameScreen_2(game, MINESPEED, "CARTESIAN_II", "xdotdotdotydotdotdot" , MODE, ANDROID));
						dispose();
					}
					if (prv_r.contains(tp_x,tp_y)){
						game.setScreen(new LevelSelectScreen(game, "CARTESIAN_I", MINESPEED, MODE, ANDROID));
			            dispose();
					}
					if (nxt_r.contains(tp_x,tp_y)){
						game.setScreen(new LevelSelectScreen(game, "CARTESIAN_III", MINESPEED, MODE, ANDROID));
			            dispose();
					}
	
				}
				
				if (TOPIC=="CARTESIAN_III"){
					if (one_r.contains(tp_x,tp_y)){
						game.setScreen(new GameScreen_2(game, MINESPEED, "CARTESIAN_III", "xdotydash" , MODE, ANDROID));
						dispose();
					}
					if (two_r.contains(tp_x,tp_y)){
						game.setScreen(new GameScreen_2(game, MINESPEED, "CARTESIAN_III", "xdotydashdot" , MODE, ANDROID));
						dispose();
					}
					if (three_r.contains(tp_x,tp_y)){
						game.setScreen(new GameScreen_2(game, MINESPEED, "CARTESIAN_III", "xdotydotdash" , MODE, ANDROID));
						dispose();
					}
					if (four_r.contains(tp_x,tp_y)){
						game.setScreen(new GameScreen_2(game, MINESPEED, "CARTESIAN_III", "xdotydashdash" , MODE, ANDROID));
						dispose();
					}
					if (prv_r.contains(tp_x,tp_y)){
						game.setScreen(new LevelSelectScreen(game, "CARTESIAN_II", MINESPEED, MODE, ANDROID));
			            dispose();
					}
					if (nxt_r.contains(tp_x,tp_y)){
						//game.setScreen(new LevelSelectScreen(game, "POLAR", MINESPEED, MODE, ANDROID));
						game.setScreen(new LevelSelectScreen(game, "CARTESIAN_I", MINESPEED, MODE, ANDROID));
			            dispose();
					}
	
				}
				
				if (TOPIC=="POLAR"){
					if (one_r.contains(tp_x,tp_y)){
					}
					if (two_r.contains(tp_x,tp_y)){
					}
					if (three_r.contains(tp_x,tp_y)){
					}
					if (four_r.contains(tp_x,tp_y)){
					}
					if (prv_r.contains(tp_x,tp_y)){
			            game.setScreen(new LevelSelectScreen(game, "CARTESIAN_III", MINESPEED, MODE, ANDROID));
			            dispose();
					}
					if (nxt_r.contains(tp_x,tp_y)){
			            game.setScreen(new LevelSelectScreen(game, "POWERS", MINESPEED, MODE, ANDROID));
			            dispose();
					}
	
				}
				
				if (TOPIC=="POWERS"){
					if (one_r.contains(tp_x,tp_y)){
					}
					if (two_r.contains(tp_x,tp_y)){
					}
					if (three_r.contains(tp_x,tp_y)){
					}
					if (four_r.contains(tp_x,tp_y)){
					}
					if (prv_r.contains(tp_x,tp_y)){
			            game.setScreen(new LevelSelectScreen(game, "POLAR", MINESPEED, MODE, ANDROID));
			            dispose();
					}
					if (nxt_r.contains(tp_x,tp_y)){
			            game.setScreen(new LevelSelectScreen(game, "MATRIX", MINESPEED, MODE, ANDROID));
			            dispose();
					}
	
				}
				if (TOPIC=="MATRIX"){
					if (one_r.contains(tp_x,tp_y)){
					}
					if (two_r.contains(tp_x,tp_y)){
					}
					if (three_r.contains(tp_x,tp_y)){
					}
					if (four_r.contains(tp_x,tp_y)){
					}
					if (prv_r.contains(tp_x,tp_y)){
			            game.setScreen(new LevelSelectScreen(game, "POWERS", MINESPEED, MODE, ANDROID));
			            dispose();
					}
					if (nxt_r.contains(tp_x,tp_y)){
			            game.setScreen(new LevelSelectScreen(game, "ARGAND", MINESPEED, MODE, ANDROID));
			            dispose();
					}
	
				}
				
				if (TOPIC=="ARGAND"){
					if (one_r.contains(tp_x,tp_y)){
					}
					if (two_r.contains(tp_x,tp_y)){
					}
					if (three_r.contains(tp_x,tp_y)){
					}
					if (four_r.contains(tp_x,tp_y)){
					}
					if (prv_r.contains(tp_x,tp_y)){
			            game.setScreen(new LevelSelectScreen(game, "MATRIX", MINESPEED, MODE, ANDROID));
			            dispose();
					}
					if (nxt_r.contains(tp_x,tp_y)){
			            game.setScreen(new LevelSelectScreen(game, "NONE", MINESPEED, MODE, ANDROID));
			            dispose();
					}
	
				}
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
		
		camera.setToOrtho(false, (float)width/(float)scale, (float)height/(float)scale);
		camera.translate(-((float)width/(float)scale-320)/2, -((float)height/(float)scale-480)/2);
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
		
		one_t.dispose();
		two_t.dispose();
		three_t.dispose();
		four_t.dispose();
		
		
		font.dispose();
		TRIM_t.dispose();
		
		selector_t.dispose();
		
		mode_background_t.dispose();
		
		abutton_corner_t.dispose();
		abutton_corner_trim_t.dispose();
		
		difficulty_arrow_t.dispose();
		
		arrowsound.stop();
		arrowsound.dispose();
		hellosound.stop();
		hellosound.dispose();
	}
}