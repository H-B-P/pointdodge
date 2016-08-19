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


public class GameScreen_2 implements Screen {
	
	final PointDodge game;
	OrthographicCamera camera;
	
   public GameScreen_2(final PointDodge gam, int minespeed, String topic, String level, String mode, boolean android) {

	 this.game = gam;
      
	 camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 480);
      
   }
   
   //---RENDER---
   @Override
   public void render(float delta) {
	   camera.update();
   }
   @Override
   
   //---END THE WORLD RESPONSIBLY---
   
   //(Still need to do this properly, but leaving most of the images etc
   //running doesn't appear to be causing any problems yet.)
   public void dispose() {
      // dispose of all the native resources

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