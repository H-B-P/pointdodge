package com.hbp.pointdodge;

import java.util.Iterator;
//import java.math.*;
//import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.Input.Buttons;
//import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Preferences;
import com.hbp.pointdodge.Dot;


public class GameScreen_2 implements Screen {
	
	final PointDodge game;
	OrthographicCamera camera;
	private Texture pod_t;
	private Rectangle pod_r;
	private float pod_x;
	private float pod_y;
	private float pod_ydot;
	private float pod_xdot;
	private float pod_xdotdot;
	private float pod_ydotdot;
	private FitViewport viewport;
	private int GAMESPEED;
	private String TOPIC;
	private String LEVEL;
	private String MODE;
	
	private float input_x;
	private float input_y;
	
	private float effective_delta;
	
	private Array<Dot> dots;
	private Texture dot_t_r;
	private Texture dot_t_n;
	private Texture dot_t_p;
	private Texture dot_t_b;
	private Texture dot_t_g;
	private Texture dot_t_y;
	private Texture dot_t_t;
	private Texture dot_t_c;
	
	private SpriteBatch batch;
	
	private float total_time;
	private int seconds;
	
	private int UNIT_LENGTH_IN_PIXELS;
	
   public GameScreen_2(final PointDodge gam, int gamespeed, String topic, String level, String mode, boolean android) {
	   
	   GAMESPEED=gamespeed;
	   TOPIC=topic;
	   LEVEL=level;
	   MODE=mode;
	 this.game = gam;
      
	 dots=new Array<Dot>();
	 
	 dot_t_n=new Texture(Gdx.files.internal("sniperdot.png"));
	 
	 dot_t_y=new Texture(Gdx.files.internal("dots/dot_yellow.png"));
	 dot_t_g=new Texture(Gdx.files.internal("dots/dot_green.png"));
	 dot_t_t=new Texture(Gdx.files.internal("dots/dot_tucker.png"));
	 
	 dot_t_r=new Texture(Gdx.files.internal("dots/dot_red.png"));
	 dot_t_c=new Texture(Gdx.files.internal("dots/dot_cyan.png"));
	 dot_t_p=new Texture(Gdx.files.internal("dots/dot_purple.png"));
	 
	 
	 
	 pod_x=0;
	 pod_y=0;
	 pod_xdot=0;
	 pod_ydot=0;
	 pod_xdot=0;
	 pod_ydot=0;
		
	pod_t= new Texture(Gdx.files.internal("base_dodger.png"));
	pod_r= new Rectangle();
	pod_r.width=40;
	pod_r.height=40;
	pod_r.x=pod_x*80+160-20;
	pod_r.y=pod_y*80+320-20;
	
	camera = new OrthographicCamera();
	camera.setToOrtho(false, 320, 480);
	viewport = new FitViewport(320, 480, camera);
    batch = new SpriteBatch();
    
    total_time=0;
    seconds=0;
    
    UNIT_LENGTH_IN_PIXELS=80;
   }
   
   //---FUNCTIONS---
   
   private void spawnTutorialDot(float xposn, float yposn) {
	      Dot dot = new Dot();
	      dot.rect = new Rectangle();
	      dot.rect.width = 11;
	      dot.rect.height = 11;
	      dot.rect.setCenter(xposn*UNIT_LENGTH_IN_PIXELS+160f, yposn*UNIT_LENGTH_IN_PIXELS+320f);
	      
	      dot.vert_speed = 0;
	      dot.horz_speed = 0;
	      dot.vert_accel = 0;
	      dot.horz_accel = 0;
	      
	      dot.colour="null";
	      
	      dots.add(dot);
	   }
   
   private void spawnCartesianDot_horz(float posn, float speed) {
	      Dot dot = new Dot();
	      dot.rect = new Rectangle();
	      dot.rect.width = 11;
	      dot.rect.height = 11;
	      if (speed>0){
	    	  dot.rect.setCenter(-10, posn*UNIT_LENGTH_IN_PIXELS+320f);
	      }
	      if (speed<0){
	    	  dot.rect.setCenter(330, posn*UNIT_LENGTH_IN_PIXELS+320f);
	      }
	      
	      dot.vert_speed = 0;
	      dot.horz_speed = speed;
	      dot.vert_accel = 0;
	      dot.horz_accel = 0;
	      
	      dot.colour="yellow";
	      
	      dots.add(dot);
	   }
   
   private void spawnCartesianDot_vert(float posn, float speed) {
	      Dot dot = new Dot();
	      dot.rect = new Rectangle();
	      dot.rect.width = 11;
	      dot.rect.height = 11;
	      if (speed>0){
	    	  dot.rect.setCenter(posn*UNIT_LENGTH_IN_PIXELS+160f, 150);
	      }
	      if (speed<0){
	    	  dot.rect.setCenter(posn*UNIT_LENGTH_IN_PIXELS+160f, 490);
	      }
	      
	      dot.vert_speed = speed;
	      dot.horz_speed = 0;
	      dot.vert_accel = 0;
	      dot.horz_accel = 0;
	      
	      dot.colour="yellow";
	      
	      dots.add(dot);
	   }
   
   //---RENDER---
   @Override
   public void render(float delta) {
	   
	   
	   
	   effective_delta=delta*((float)GAMESPEED)/100f;
	   
	   total_time+=effective_delta;
	   
	   Gdx.gl.glClearColor(0, 0, 0, 1);
	   Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	   
	   camera.update();
	   
	   batch.begin();
	   batch.draw(pod_t, pod_r.x-10, pod_r.y-10);
	   for(Dot dot: dots) {
		   if (dot.colour=="yellow"){
			   batch.draw(dot_t_y, dot.rect.x-6, dot.rect.y-6);
		   }
		   else{
			   batch.draw(dot_t_n, dot.rect.x-6, dot.rect.y-6);
			   //System.out.println("DEFO GOT THIS FAR");
		   }
	   }
	   batch.end();
	   
	   if((seconds+1)<(total_time)){
		  System.out.println(seconds);
    	  seconds+=1;
    	  
    	  if (seconds==2){
    		  spawnTutorialDot(-1,-1);
    	  }
	   }
	   
	   if (Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT)){
		   input_x=1;
	   }
	   else if (Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT)){
		   input_x=-1;
	   }
	   else{
		   input_x=0;
	   }
	   if (Gdx.input.isKeyPressed(Keys.UP) && !Gdx.input.isKeyPressed(Keys.DOWN)){
		   input_y=1;
	   }
	   else if (Gdx.input.isKeyPressed(Keys.DOWN) && !Gdx.input.isKeyPressed(Keys.UP)){
		   input_y=-1;
	   }
	   else{
		   input_y=0;
	   }
	   
	   if (TOPIC=="NONE"){
		   pod_xdot=input_x;
		   pod_ydot=input_y;
	   }
	   
	   pod_xdot+=pod_xdotdot*effective_delta;
	   pod_ydot+=pod_ydotdot*effective_delta;
	   
	   pod_x+=pod_xdot*effective_delta;
	   pod_y+=pod_ydot*effective_delta;
	   
	   pod_r.x=pod_x*UNIT_LENGTH_IN_PIXELS+160-20;
	   pod_r.y=pod_y*UNIT_LENGTH_IN_PIXELS+320-20;
	   
	   Iterator<Dot> iter = dots.iterator();
  
	  while(iter.hasNext()) {
	     Dot dot = iter.next();
	     dot.rect.y += dot.vert_speed * effective_delta*UNIT_LENGTH_IN_PIXELS;
	     dot.rect.x += dot.horz_speed * effective_delta*UNIT_LENGTH_IN_PIXELS;
	     if((dot.rect.x+dot.rect.width/2)>360 || (dot.rect.x+dot.rect.width/2)<-40 || (dot.rect.y+dot.rect.height/2)>520 || (dot.rect.y+dot.rect.height/2)<120) iter.remove();
	  }
	   
   }
   @Override
   
   //---END THE WORLD RESPONSIBLY---
   
   //(Still need to do this properly, but leaving most of the images etc
   //running doesn't appear to be causing any problems yet.)
   public void dispose() {
      // dispose of all the native resources
	   pod_t.dispose();

   }

@Override
public void show() {
	// TODO Auto-generated method stub
	
}

@Override
public void resize(int width, int height) {
	// TODO Auto-generated method stub
	//viewport.update(width, height);
    //camera.update();
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
public void pause() {
	// TODO Auto-generated method stub
	
}

@Override
public void resume() {
	// TODO Auto-generated method stub
	
}

@Override
public void hide() {
	// TODO Auto-generated method stub
	
}
}