import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;

public class TankClient extends Frame{
	public final static int GameWidth = 800;
	public final static int GameHight = 600;
	
	Tank myTank = new Tank(700, 600, true, Direction.Stop, this);
	Wall w1 = new Wall(200, 300, 20, 250, this);
	Wall w2 = new Wall(300, 500, 250, 20, this);
	
	Blood b = new Blood();
	
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<Tank> tanks = new ArrayList<Tank>();
	
	Image offScreenImage = null;	
	
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.green);
		g.drawString("missiles: "+missiles.size(), 60, 60);
		g.drawString("explodes: " +explodes.size(), 60, 80);
		g.drawString("tanks: " +tanks.size(), 60, 100);
		g.drawString("myBlood: " + myTank.getLife(), 60, 120);
		g.setColor(c);
	
		b.draw(g);
		w1.draw(g);
		w2.draw(g);
		
		if(tanks.size() == 0){
			for(int i = 0; i < Integer.parseInt(PropertyMgr.getProperty("reProduceTankCount")); i++){
				tanks.add(new Tank(50 + 40*(i + 1), 50, false, Direction.D, this));
			}
		}
		
		if(myTank.collideBlood(b)){
			myTank.setLife(100);
			b.draw(g);
		}
		
		for(int i = 0; i < missiles.size(); i++){
			
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
			/*if(!m.isLive()){
				missiles.remove(m);
			}
			else{
				m.draw(g);
			}*/
		}
		
		for(int i = 0; i < explodes.size(); i++){
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
		for(int i = 0; i < tanks.size(); i++){
			Tank t = tanks.get(i);
			t.collideTheWall(w1);
			t.collideTheWall(w2);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}
		
		myTank.draw(g);
	}
	
	public void update(Graphics g){
		if(offScreenImage == null){
			offScreenImage = this.createImage(GameWidth, GameHight);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.black);
		gOffScreen.fillRect(0, 0, GameWidth, GameHight);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	public void launchFrame(){
		
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int initTankCount = Integer.parseInt(PropertyMgr.getProperty("initTankCount"));
		
		for(int i = 0; i < initTankCount; i++){
			tanks.add(new Tank(50 + 40*(i+1), 50, false, Direction.D, this));
		}
		
		this.setLocation(400, 300);
		this.setSize(GameWidth, GameHight);
		this.setVisible(true);
		this.setTitle("TankWar");
		this.setBackground(Color.BLACK);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				setVisible(false);
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start();
	}
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
	
	private class PaintThread implements Runnable{
		
		public void run(){
			while(true){
				repaint();
				try{
					Thread.sleep(50);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyMonitor extends KeyAdapter{

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
	}
}












