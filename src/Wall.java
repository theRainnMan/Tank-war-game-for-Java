import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {
	
	int x, y, w, h;
	TankClient Tc;
	
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.green);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}

	public Wall(int x, int y, int w, int h, TankClient Tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.Tc = Tc;
	}
	
	public Rectangle getRec(){
		return new Rectangle(x, y, w, h);
	}
	
}










