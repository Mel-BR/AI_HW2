package LevelEditor;

public class TestWindow2 implements Runnable {


	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 3;
	public static final String NAME = "Wargame";

	public static final int SIZE =1680;
	
	public boolean running = false;
	
	private int tickCount = 0;
	
	public static FrameLevelEditor frame;

	public static int windowW = SIZE;

	public static int windowH = SIZE*10/16;

	public static float scaleW = 1;
	public static float scaleH = 1;
	
	public GUI(){
		
		frame = new FrameLevelEditor();
		
	}
	
	public synchronized void start(){
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop(){
		running = false;
		
	}
	
	
	public void run() {
		long lastTime = System.nanoTime();
		double nanoPerTick = 1000000000D/60; //1/60 sec
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0D;
		
		while (running){
			long now = System.nanoTime();
			delta += (now - lastTime)/nanoPerTick;
			lastTime = now;
			boolean shouldRender = false;
					
			while (delta >= 1){
		    ticks++;
			tick();
			delta -= 1;
			shouldRender = true;
			} 
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (shouldRender){
			frames++;
			render();
			}
			
			/*if (System.currentTimeMillis()-lastTimer >= 1000){
				lastTimer += 1000;
				System.out.println(frames+", "+ticks);
				frames = 0;
				ticks = 0;
			}*/
			
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void tick(){
		//Do some shit here
		tickCount++;


	}

	public void render(){
		//paint some things you already know
		frame.repaint();
		//frame.panel.repaint();
		
	}
	
	
	public static void main(String[] args){
		new GUI().start();
	}
}
