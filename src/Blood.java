import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Blood {
	int w = 20, h = 20;
	private static Random r = new Random();
	private int x = r.nextInt(800 - 20) + 20;
	private int y = r.nextInt(600 - 20) + 20;
	private boolean live = true;
	
	TankClient Tc;
	
	public void draw(Graphics g){
		if(!live){
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.magenta);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	
/*	public Blood(int x, int y, int w, int h, TankClient Tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		Tc = Tc;
	}
*/
	public Rectangle getRec(){
		return new Rectangle(x, y, w, h);
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
}
