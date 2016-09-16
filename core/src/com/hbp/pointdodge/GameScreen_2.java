package com.hbp.pointdodge;

import java.util.Iterator;
import java.math.*;
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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Preferences;
import com.hbp.pointdodge.Kaboom;
import com.hbp.pointdodge.Dot;


public class GameScreen_2 implements Screen {
	
	final PointDodge game;
	OrthographicCamera camera;
	private Texture pod_t;
	private TextureRegion pod_tr;
	private Rectangle pod_r;
	
	private Rectangle pod_r_vert;
	private Rectangle pod_r_horz;
	private Rectangle pod_r_horzvert;
	
	private Rectangle asterisk_r;
	private Texture asterisk_t;
	
	private Texture statusbar_t;
	
	private Texture poncho_t;
	private Texture window_t;
	
	private Texture grid_t;
	
	private Array<Rectangle> pods_r;
	private Polygon pod_poly;
	private Array<Polygon> pods_poly;
	private float pod_x;
	private float pod_y;
	private float pod_ydot;
	private float pod_xdot;
	private float pod_xdotdot;
	private float pod_ydotdot;
	private float pod_xdotdotdot;
	private float pod_ydotdotdot;
	
	private float pod_ydash;
	private float pod_ydashdash;
	private float pod_ydotdash;
	private float pod_ydashdot;
	
	private FitViewport viewport;
	private int GAMESPEED;
	private String TOPIC;
	private String LEVEL;
	private String MODE;
	
	private float input_x;
	private float input_y;
	
	private float effective_delta;
	
	private Array<Dot> dots;
	private Array<Kaboom> explosions;
	
	private Texture dot_t_r;
	private Texture dot_t_n;
	private Texture dot_t_p;
	private Texture dot_t_b;
	private Texture dot_t_g;
	private Texture dot_t_y;
	private Texture dot_t_t;
	private Texture dot_t_c;
	
	private Texture explosion_t;
	
	private int score;
	
	private SpriteBatch batch;
	
	private float total_time;
	private int seconds;
	
	private float[] pp_input;
	
	private int UNIT_LENGTH_IN_PIXELS;
	
	private int SHIP_DISPLACEMENT_IN_PIXELS;
	
	private float SHIP_BOUNDARY_DIST;
	
	private boolean HAVE_WE_EXPLODED;
	
	private boolean ANDROID;
	
	private String ypon;
	private BitmapFont font;
	
   public GameScreen_2(final PointDodge gam, int gamespeed, String topic, String level, String mode, boolean android) {
	   
	   GAMESPEED=gamespeed;
	   TOPIC=topic;
	   LEVEL=level;
	   MODE=mode;
	   ANDROID=android;
	 this.game = gam;
      
	 dots=new Array<Dot>();
	 explosions = new Array<Kaboom>();
	 
	 dot_t_n=new Texture(Gdx.files.internal("sniperdot.png"));
	 
	 dot_t_y=new Texture(Gdx.files.internal("dots/dot_yellow.png"));
	 dot_t_g=new Texture(Gdx.files.internal("dots/dot_green.png"));
	 dot_t_t=new Texture(Gdx.files.internal("dots/dot_tucker.png"));
	 
	 dot_t_r=new Texture(Gdx.files.internal("dots/dot_red.png"));
	 dot_t_c=new Texture(Gdx.files.internal("dots/dot_cyan.png"));
	 dot_t_p=new Texture(Gdx.files.internal("dots/dot_purple.png"));
	 
	 explosion_t = new Texture(Gdx.files.internal("explosion.png"));
	 
	 ypon="";
	 
	 pod_x=0;
	 pod_y=0;
	 pod_xdot=0;
	 pod_ydot=0;
	 pod_xdotdot=0;
	 pod_ydotdot=0;
	 pod_xdotdotdot=0;
	 pod_ydotdotdot=0;
	 
	 pod_ydash=0;
	 pod_ydashdash=0;
	 pod_ydashdot=0;
	 pod_ydotdash=0;
	 
	 score=0;
	
	 statusbar_t=new Texture(Gdx.files.internal("statusbar.png"));
	poncho_t= new Texture(Gdx.files.internal("blackbar_poncho.png"));
	if (TOPIC=="NONE"){
		window_t=new Texture(Gdx.files.internal("window_normal.png"));
	}
	else{
		window_t=new Texture(Gdx.files.internal("window_normal.png"));
	}
	
	if (TOPIC=="CARTESIAN"){
		pod_t= new Texture(Gdx.files.internal("cartesian_dodger.png"));
		grid_t= new Texture(Gdx.files.internal("grid_II.png"));
	}
	else{
		pod_t= new Texture(Gdx.files.internal("base_dodger.png"));
		grid_t= new Texture(Gdx.files.internal("grid_II.png"));
	}
	pod_tr= new TextureRegion(pod_t);
	
	pod_r= new Rectangle();
	pods_r= new Array<Rectangle>();
	pod_r.width=40;
	pod_r.height=40;
	pod_r.x=pod_x*80+160-20;
	pod_r.y=pod_y*80+320-20;
	
	if (TOPIC=="NONE" || TOPIC=="CARTESIAN"){
		pod_r_horz= new Rectangle();
		pod_r_vert= new Rectangle();
		pod_r_horzvert= new Rectangle();
		pod_r_horz.width=40;
		pod_r_vert.width=40;
		pod_r_horzvert.width=40;
		pod_r_horz.height=40;
		pod_r_vert.height=40;
		pod_r_horzvert.height=40;
		pod_r_horz.x= 160-20;
		pod_r_vert.x=160-20;
		pod_r_horzvert.x=160-20;
		pod_r_horz.y=320-20;
		pod_r_vert.y=320-20;
		pod_r_horzvert.y=320-20;
		
		pods_r.add(pod_r);
		pods_r.add(pod_r_horz);
		pods_r.add(pod_r_vert);
		pods_r.add(pod_r_horzvert);
	}
	
	pp_input=new float[]{pod_r.x, pod_r.y, pod_r.x+pod_r.width, pod_r.y, pod_r.x+pod_r.width, pod_r.y+pod_r.height, pod_r.x, pod_r.y+pod_r.height};
	pod_poly= new Polygon(pp_input);
	
	pods_poly= new Array<Polygon>();
	
	pods_poly.add(pod_poly);
	
	
	camera = new OrthographicCamera();
	camera.setToOrtho(false, 320, 480);
	viewport = new FitViewport(320, 480, camera);
    batch = new SpriteBatch();
    
    total_time=0;
    seconds=0;
    
    UNIT_LENGTH_IN_PIXELS=80;
    
    HAVE_WE_EXPLODED=false;
    
    SHIP_BOUNDARY_DIST=1.5f;
    
    asterisk_r=new Rectangle();
    asterisk_r.x=160-20+plusorminus()*80;
    asterisk_r.y=240-20+plusorminus()*80;
    asterisk_r.width=40;
    asterisk_r.height=40;
    asterisk_t=new Texture(Gdx.files.internal("asterisk2.png"));
    
    font = new BitmapFont();
    font.setColor(Color.BLACK);
   }
   
   //---FUNCTIONS---
   
   private int plusorminus(){
	   int coin=MathUtils.random(0,1);
	   return coin*2-1;
   }
   
   private int tri(){
	   int coin=MathUtils.random(0,2);
	   return coin-1;
   }
   
   private float pent(){
	   int coin=MathUtils.random(0,4);
	   return ((float)coin-2)/2;
   }
   
   private float sept(){
	   int coin=MathUtils.random(0,6);
	   return ((float)coin-3)/2;
   }
   
   //--Collisions between geometric shapes. Why is this not already part of libgdx?
   
   private boolean Rectangle_collides_with_Polygon(Rectangle rec, Polygon pol) {
	   float[] rp_input=new float[]{rec.x, rec.y, rec.x+rec.width, rec.y, rec.x+rec.width, rec.y+rec.height, rec.x, rec.y+rec.height};
	   Polygon recpol=new Polygon(rp_input);
	   //System.out.println(recpol.getVertices()[0]+", "+recpol.getVertices()[1]);
	   //System.out.println(recpol.getVertices()[2]+", "+recpol.getVertices()[3]);
	   //System.out.println(recpol.getVertices()[4]+", "+recpol.getVertices()[5]);
	   //System.out.println(recpol.getVertices()[6]+", "+recpol.getVertices()[7]);
	   //System.out.println("WAAAAAAAAA");
	   return Intersector.overlapConvexPolygons(pol, recpol);
   }
   
   private boolean Circle_contains_Rectangle(Circle cir, Rectangle rec) {
	   float[] verts= new float[]{rec.x, rec.y, rec.x+rec.width, rec.y, rec.x+rec.width, rec.y+rec.height, rec.x, rec.y+rec.height};
	   if (cir.contains(verts[0], verts[1]) && cir.contains(verts[2], verts[3]) && cir.contains(verts[4], verts[5]) && cir.contains(verts[6], verts[7])){
		   return true;
	   }
	   else{
		   return false;
	   }
   }
   
   private boolean Circle_intersects_Rectangle(Circle cir, Rectangle rec){
	   if (Intersector.overlaps(cir, rec) && !Circle_contains_Rectangle(cir, rec)){
		   return true;
	   }
	   else{
		   return false;
	   }
   }
   
   private boolean Circle_contains_Polygon(Circle cir, Polygon pol){
	   float[] verts= pol.getVertices();
	   int vlen=verts.length;
	   int vplace=0;
	   while (vplace<vlen){
		   if (!cir.contains(verts[vplace], verts[vplace+1])){
			   return false;
		   }
		   vplace+=2;
	   }
	   return true;
   }
   
   //----
   
   private void spawnExplosion(float X, float Y){
	   Kaboom boom = new Kaboom();
	   boom.rect= new Rectangle();
	   boom.birthtime=total_time;
	   boom.rect.x= X;
	   boom.rect.y= Y;
	   explosions.add(boom);
   }
   
   //----
   
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
	    	  dot.rect.setCenter(-10, posn*UNIT_LENGTH_IN_PIXELS+240f);
	      }
	      if (speed<0){
	    	  dot.rect.setCenter(330, posn*UNIT_LENGTH_IN_PIXELS+240f);
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
	    	  dot.rect.setCenter(posn*UNIT_LENGTH_IN_PIXELS+160f, 70);
	      }
	      if (speed<0){
	    	  dot.rect.setCenter(posn*UNIT_LENGTH_IN_PIXELS+160f, 410);
	      }
	      
	      dot.vert_speed = speed;
	      dot.horz_speed = 0;
	      dot.vert_accel = 0;
	      dot.horz_accel = 0;
	      
	      dot.colour="yellow";
	      
	      dots.add(dot);
	   }
   
   private void spawnCartesianWall_vert(float speed){
		   spawnCartesianDot_vert(-1.75f,speed);
		   spawnCartesianDot_vert(-1.25f,speed);
		   spawnCartesianDot_vert(-0.75f,speed);
		   spawnCartesianDot_vert(-0.25f,speed);
		   spawnCartesianDot_vert(0.25f,speed);
		   spawnCartesianDot_vert(0.75f,speed);
		   spawnCartesianDot_vert(1.25f,speed);
		   spawnCartesianDot_vert(1.75f,speed);
   }
   
   private void spawnCartesianGapWall_vert(int gap, float speed){
	   spawnCartesianDot_vert(-1.75f,speed);
	   if (gap!=-1){
		   spawnCartesianDot_vert(-1.25f,speed);
		   spawnCartesianDot_vert(-0.75f,speed);
	   }
	   if (gap!=0){
		   spawnCartesianDot_vert(-0.25f,speed);
		   spawnCartesianDot_vert(0.25f,speed);
	   }
	   if (gap!=1){
		   spawnCartesianDot_vert(0.75f,speed);
		   spawnCartesianDot_vert(1.25f,speed);
	   }
	   spawnCartesianDot_vert(1.75f,speed);
}
   
   private void spawnCartesianWall_horz(float speed){
	   spawnCartesianDot_horz(-1.75f,speed);
	   spawnCartesianDot_horz(-1.25f,speed);
	   spawnCartesianDot_horz(-0.75f,speed);
	   spawnCartesianDot_horz(-0.25f,speed);
	   spawnCartesianDot_horz(0.25f,speed);
	   spawnCartesianDot_horz(0.75f,speed);
	   spawnCartesianDot_horz(1.25f,speed);
	   spawnCartesianDot_horz(1.75f,speed);
}
   
   private void spawnCartesianGapWall_horz(int gap, float speed){
	   spawnCartesianDot_horz(-1.75f,speed);
	   if (gap!=-1){
		   spawnCartesianDot_horz(-1.25f,speed);
		   spawnCartesianDot_horz(-0.75f,speed);
	   }
	   if (gap!=0){
		   spawnCartesianDot_horz(-0.25f,speed);
		   spawnCartesianDot_horz(0.25f,speed);
	   }
	   if (gap!=1){
		   spawnCartesianDot_horz(0.75f,speed);
		   spawnCartesianDot_horz(1.25f,speed);
	   }
	   spawnCartesianDot_horz(1.75f,speed);
}
   
   private void spawnCartesianSlash_vert(int st, float speed){
	  if (seconds==st){
 		  spawnCartesianDot_vert(-1.5f,speed);
 	  }
	  if (seconds==(st+2)){
 		  spawnCartesianDot_vert(-1.0f,speed);
 	  }
	  if (seconds==(st+4)){
 		  spawnCartesianDot_vert(-0.5f,speed);
 	  }
	  if (seconds==(st+6)){
 		  spawnCartesianDot_vert(0.0f,speed);
 	  }
	  if (seconds==(st+8)){
 		  spawnCartesianDot_vert(0.5f,speed);
 	  }
	  if (seconds==(st+10)){
 		  spawnCartesianDot_vert(1.0f,speed);
 	  }
	  if (seconds==(st+12)){
 		  spawnCartesianDot_vert(1.5f,speed);
 	  }
   }
   
   private String double_formatted(double doub){
	   double a=Math.round(doub*10.0)/10.0;
	   Float b=(Float)(float)a;
	   return b.toString();
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
	   //batch.draw(pod_t, pod_r.x-10, pod_r.y-10);
	   
	   if (TOPIC!="NONE"){
		   //batch.draw(grid_t, 0, 0);
	   }
	   
	   if (HAVE_WE_EXPLODED){
		   for(Kaboom boom: explosions) {
			   batch.draw(explosion_t, boom.rect.x-20, boom.rect.y-20);
		   }
	   }
	   else{
		   batch.draw(pod_tr, pod_r.x-10, pod_r.y-10, 30, 30, 60, 60, 1, 1, 0);
		   batch.draw(pod_tr, pod_r_horz.x-10, pod_r_horz.y-10, 30, 30, 60, 60, 1, 1, 0);
		   batch.draw(pod_tr, pod_r_vert.x-10, pod_r_vert.y-10, 30, 30, 60, 60, 1, 1, 0);
		   batch.draw(pod_tr, pod_r_horzvert.x-10, pod_r_horzvert.y-10, 30, 30, 60, 60, 1, 1, 0);
		   
	   }
	   
	   batch.draw(poncho_t, -800+160, -1200+240);
	   batch.draw(window_t, 0, 80);
	   
	   for(Dot dot: dots) {
		   if (dot.colour=="yellow"){
			   batch.draw(dot_t_y, dot.rect.x, dot.rect.y);
		   }
		   else{
			   batch.draw(dot_t_n, dot.rect.x, dot.rect.y);
		   }
	   }
	   
	   batch.draw(asterisk_t, asterisk_r.x, asterisk_r.y);
	   
	   //batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
	   
	   batch.draw(statusbar_t, 0, 0);
	   batch.draw(statusbar_t, 0, 400);
	   if (!HAVE_WE_EXPLODED){
		   if (TOPIC.equals("CARTESIAN")){
			   if (LEVEL.startsWith("xdotdotdot")){
				   font.draw(batch, double_formatted(pod_xdotdotdot), 30, 470);
				   font.draw(batch, double_formatted(pod_xdotdot), 30, 445);
				   font.draw(batch, double_formatted(pod_xdot), 30, 420);
				   if (LEVEL.equals("xdotdotdotydotdotdot")){
					   font.draw(batch, double_formatted(pod_ydotdotdot), 90, 470);
					   font.draw(batch, double_formatted(pod_ydotdot), 90, 445);
					   font.draw(batch, double_formatted(pod_ydot), 90, 420);
				   }
				   else{
					   font.draw(batch, double_formatted(pod_y), 90, 420);
				   }
			   }
			   else{
				   font.draw(batch, double_formatted(pod_x), 30, 420);
				   font.draw(batch, double_formatted(pod_y), 90, 420);
				   if(LEVEL.startsWith("xdot")){
					   font.draw(batch, double_formatted(pod_xdot), 30, 445);
				   }
				   if(LEVEL.startsWith("xdotdot")){
					   font.draw(batch, double_formatted(pod_xdotdot), 30, 470);
				   }
				   if (LEVEL.split("y").length>1){
					   ypon=(LEVEL.split("y"))[1];
					   if(ypon.startsWith("dot")){
						   font.draw(batch, double_formatted(pod_ydot), 90, 445);
					   }
					   if(ypon.startsWith("dash")){
						   font.draw(batch, double_formatted(pod_ydash), 90, 445);
					   }
					   if(ypon.startsWith("dotdot")){
						   font.draw(batch, double_formatted(pod_ydotdot), 90, 470);
					   }
					   if(ypon.startsWith("dashdot")){
						   font.draw(batch, double_formatted(pod_ydashdot), 90, 470);
					   }
					   if(ypon.startsWith("dotdash")){
						   font.draw(batch, double_formatted(pod_ydotdash), 90, 470);
					   }
					   if(ypon.startsWith("dashdash")){
						   font.draw(batch, double_formatted(pod_ydashdash), 90, 470);
					   }
				   }
			   }
		   }
	   }
	   
	   font.draw(batch, "Score: "+score, 150, 445);
	   
	   batch.end();
	   if (MODE.equals("gen")){
		   if((seconds+1)<(total_time)){
			  System.out.println(seconds);
	    	  seconds+=1;
	    	  
	    	  if (seconds==5){
	    		  spawnCartesianDot_vert(0,-0.4f);
	    	  }
	    	  
	    	  if (seconds==15){
	    		  spawnCartesianDot_horz(Math.round(pod_y),0.8f);
	    	  }
	    	  if (seconds==22){
	    		  spawnCartesianDot_vert(Math.round(pod_x),-0.8f);
	    	  }
	    	  
	    	  if (seconds>30 && seconds<100){
	    		  if (seconds%10==0){
	    			  spawnCartesianDot_horz(Math.round(pod_y*2)/2.0f,plusorminus()*0.8f);
	    		  }
	    		  if (seconds%10==5){
	    			  spawnCartesianDot_vert(Math.round(pod_x*2)/2.0f,plusorminus()*0.8f);
	    			  //spawnCartesianDot_vert(pent(),-0.4f);
	    		  }
	    	  }
	    	  
	    	  if (seconds==110){
	    		  spawnCartesianGapWall_horz(tri(),-0.4f);
	    	  }
	    	  
	    	  if (seconds==120){
	    		  spawnCartesianGapWall_vert(tri(),0.4f);
	    	  }
	    	  
	    	  if (seconds==130){
	    		  int a=tri();
	    		  spawnCartesianGapWall_horz(a,0.4f);
	    		  spawnCartesianGapWall_horz(a,-0.4f);
	    	  }
	    	  
	    	  if (seconds==145){
	    		  spawnCartesianWall_vert(0.4f);
	    	  }
	    	  
	    	  if (seconds==160){
	    		  spawnCartesianWall_horz(0.4f);
	    	  }
	    	  
	    	  if (seconds==175){
	    		  spawnCartesianWall_horz(-0.4f);
	    		  spawnCartesianWall_vert(plusorminus()*0.4f);
	    	  }
	    	  
	    	  if (seconds==190){
	    		  spawnCartesianWall_horz(0.4f);
	    		  spawnCartesianWall_vert(plusorminus()*0.4f);
	    	  }
	    	  
	    	  if (seconds==205){
	    		  spawnCartesianWall_horz(plusorminus()*0.4f);
	    		  spawnCartesianWall_vert(plusorminus()*0.4f);
	    	  }
		   }
	   }
	   if (Gdx.input.isKeyPressed(Keys.ESCAPE)){
		   game.setScreen(new LevelSelectScreen(game, "NONE", GAMESPEED, MODE, ANDROID));
		   dispose();
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
	   if (TOPIC=="CARTESIAN"){
		   if (LEVEL=="xdotydot"){
			   pod_xdot=input_x;
			   pod_ydot=input_y;
		   }
		   if (LEVEL=="xy"){
			   pod_x=input_x;
			   pod_y=input_y;
		   }
		   if (LEVEL=="xdotdotydotdot"){
			   pod_xdotdot=input_x;
			   pod_ydotdot=input_y;
		   }
		   if (LEVEL=="xdoty"){
			   pod_xdot=input_x;
			   pod_y=input_y;
		   }
		   if (LEVEL=="xdotdoty"){
			   pod_xdotdot=input_x;
			   pod_y=input_y;
		   }
		   if (LEVEL=="xdotdotydot"){
			   pod_xdotdot=input_x;
			   pod_ydot=input_y;
		   }
		   if (LEVEL=="xdotdotdoty"){
			   pod_xdotdotdot=input_x;
			   pod_y=input_y;
		   }
		   if (LEVEL=="xdotdotdotydotdotdot"){
			   pod_xdotdotdot=input_x;
			   pod_ydotdotdot=input_y;
		   }
		   if (LEVEL=="xdotydash"){
			   pod_xdot=input_x;
			   pod_ydash=input_y;
		   }
		   if (LEVEL=="xdotydashdash"){
			   pod_xdot=input_x;
			   pod_ydashdash=input_y;
		   }
		   if (LEVEL=="xdotydashdot"){
			   pod_xdot=input_x;
			   pod_ydashdot=input_y;
		   }
		   if (LEVEL=="xdotydotdash"){
			   pod_xdot=input_x;
			   pod_ydotdash=input_y;
		   }
	   }
	   
	   if (pod_xdotdot>=1){
		   pod_xdotdot=1;
	   }
	   if (pod_xdotdot<=-1){
		   pod_xdotdot=-1;
	   }
	   if (pod_ydotdot>=1){
		   pod_ydotdot=1;
	   }
	   if (pod_ydotdot<=-1){
		   pod_ydotdot=-1;
	   }
	   
	   if (pod_ydash>=1){
		   pod_ydash=1;
	   }
	   if (pod_ydash<=-1){
		   pod_ydash=-1;
	   }
	   
	   if (pod_xdot>=1){
		   pod_xdot=1;
	   }
	   if (pod_xdot<=-1){
		   pod_xdot=-1;
	   }
	   if (pod_ydot>=1){
		   pod_ydot=1;
	   }
	   if (pod_ydot<=-1){
		   pod_ydot=-1;
	   }
	   
	   if (Gdx.input.isKeyPressed(Keys.SPACE)){
		   pod_xdot=0;
		   pod_ydot=0;
	   }
	   
	   
	   pod_xdotdot+=pod_xdotdotdot*effective_delta;
	   pod_ydotdot+=pod_ydotdotdot*effective_delta;
	   
	   
	   pod_xdot+=pod_xdotdot*effective_delta;
	   pod_ydot+=pod_ydotdot*effective_delta;
	   
	   pod_ydash+=pod_ydashdash*pod_xdot*effective_delta;
	   pod_ydot+=pod_ydotdash*pod_xdot*effective_delta;
	   pod_ydash+=pod_ydashdot*effective_delta;
	   pod_y+=pod_ydash*pod_xdot*effective_delta;
	   
	   
	   pod_x+=pod_xdot*effective_delta;
	   pod_y+=pod_ydot*effective_delta;
	   
	   //System.out.println(pod_x);
	   //System.out.println(pod_y);
	   
	   if (TOPIC=="NONE" || TOPIC=="CARTESIAN"){
	   
		   if (pod_x>SHIP_BOUNDARY_DIST){
			   pod_x-=2*SHIP_BOUNDARY_DIST;
		   }
		   if (pod_x<-SHIP_BOUNDARY_DIST){
			   pod_x+=2*SHIP_BOUNDARY_DIST;
		   }
		   if (pod_y>SHIP_BOUNDARY_DIST){
			   pod_y-=2*SHIP_BOUNDARY_DIST;
		   }
		   if (pod_y<-SHIP_BOUNDARY_DIST){
			   pod_y+=2*SHIP_BOUNDARY_DIST;
		   }
		   
		   
		   
		   
	   }
	   
	   pod_r.setCenter(pod_x*UNIT_LENGTH_IN_PIXELS+160, pod_y*UNIT_LENGTH_IN_PIXELS+240);
	   
	   if (TOPIC=="NONE" || TOPIC=="CARTESIAN"){
		   pod_r_horz.y=pod_r.y;
		   pod_r_vert.x=pod_r.x;
		   if (pod_x<0){
			   pod_r_horz.x=pod_r.x+SHIP_BOUNDARY_DIST*2*UNIT_LENGTH_IN_PIXELS;
			   pod_r_horzvert.x=pod_r.x+SHIP_BOUNDARY_DIST*2*UNIT_LENGTH_IN_PIXELS;
		   }
		   else{
			   pod_r_horz.x=pod_r.x-SHIP_BOUNDARY_DIST*2*UNIT_LENGTH_IN_PIXELS;
			   pod_r_horzvert.x=pod_r.x-SHIP_BOUNDARY_DIST*2*UNIT_LENGTH_IN_PIXELS;
		   }
		   if (pod_y<0){
			   pod_r_vert.y=pod_r.y+SHIP_BOUNDARY_DIST*2*UNIT_LENGTH_IN_PIXELS;
			   pod_r_horzvert.y=pod_r.y+SHIP_BOUNDARY_DIST*2*UNIT_LENGTH_IN_PIXELS;
		   }
		   else{
			   pod_r_vert.y=pod_r.y-SHIP_BOUNDARY_DIST*2*UNIT_LENGTH_IN_PIXELS;
			   pod_r_horzvert.y=pod_r.y-SHIP_BOUNDARY_DIST*2*UNIT_LENGTH_IN_PIXELS;
		   }
	   }
	   
	   
	   
	   //pp_input=new float[]{-pod_r.width/2, -pod_r.height/2, pod_r.width/2, -pod_r.height/2, pod_r.width/2, pod_r.height/2, -pod_r.width/2, pod_r.height/2};
	   pp_input=new float[]{pod_r.x, pod_r.y, pod_r.x+pod_r.width, pod_r.y, pod_r.x+pod_r.width, pod_r.y+pod_r.height, pod_r.x, pod_r.y+pod_r.height};
	   pod_poly= new Polygon(pp_input);
	   pod_poly.setOrigin(pod_r.x+20,pod_r.y+20);
	   pod_poly.setRotation(0);
	   
	   Iterator<Dot> iter = dots.iterator();
	   
	   Iterator<Kaboom> iterk = explosions.iterator();
	      while(iterk.hasNext()) {
	    	  Kaboom boom = iterk.next();
	    	  if(total_time - boom.birthtime > 0.25) iterk.remove();
	      }
	   
	  while(iter.hasNext()) {
	     Dot dot = iter.next();
	     dot.rect.y += dot.vert_speed * effective_delta*UNIT_LENGTH_IN_PIXELS;
	     dot.rect.x += dot.horz_speed * effective_delta*UNIT_LENGTH_IN_PIXELS;
	     if((dot.rect.x+dot.rect.width/2)>360 || (dot.rect.x+dot.rect.width/2)<-40 || (dot.rect.y+dot.rect.height/2)>420 || (dot.rect.y+dot.rect.height/2)<60) iter.remove();
	     if(Rectangle_collides_with_Polygon(dot.rect,pod_poly) && !HAVE_WE_EXPLODED){
	    	 System.out.println("YES");
	    	 HAVE_WE_EXPLODED=true;
	    	 
	    	 spawnExplosion(pod_r.x,pod_r.y);
	    	 
	    	 Iterator<Rectangle> iterdie = pods_r.iterator();
	    	 while (iterdie.hasNext()){
	    		 Rectangle a_pod=iterdie.next();
	    		 spawnExplosion(a_pod.x, a_pod.y);
	    	 }
	    	 
	     }
	     else{
	    	 //System.out.println("NO");
	     }
	     //System.out.println(dot.rect.x + ", " + dot.rect.y);
	     //System.out.println(pod_poly.getTransformedVertices()[0]+", "+pod_poly.getTransformedVertices()[1]);
	     //System.out.println(pod_poly.getTransformedVertices()[2]+", "+pod_poly.getTransformedVertices()[3]);
	     //System.out.println(pod_poly.getTransformedVertices()[4]+", "+pod_poly.getTransformedVertices()[5]);
	     //System.out.println(pod_poly.getTransformedVertices()[6]+", "+pod_poly.getTransformedVertices()[7]);
	  }
	  
	  if ((!HAVE_WE_EXPLODED)&&(asterisk_r.overlaps(pod_r) || asterisk_r.overlaps(pod_r_horz) || asterisk_r.overlaps(pod_r_vert) || asterisk_r.overlaps(pod_r_horzvert))){
		  score+=1;
		  asterisk_r.x+=80*MathUtils.random(1, 2);
		  asterisk_r.y+=80*MathUtils.random(1, 2);
		  if (asterisk_r.x>(160-20+80)){
			  asterisk_r.x-=240;
		  }
		  
		  if (asterisk_r.y>(240-20+80)){
			  asterisk_r.y-=240;
		  }
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