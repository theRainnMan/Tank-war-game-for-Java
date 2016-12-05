import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Explode {
	int x, y;
	private boolean live = true;
	int step = 0;
	
	private static boolean init = false;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] imgs ={
		tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
	};
	
	private TankClient Tc;
	
	public Explode(int x, int y, TankClient Tc){
		this.x = x;
		this.y = y;
		this.Tc = Tc;
	}
	
	public void draw(Graphics g){
		if(!init){
			for (int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], 1000, 1000, null);
			}
			init = true;
		}
		
		if(!live){
			Tc.explodes.remove(this);
			return;
		}
		if(step == imgs.length){
			live = false;
			step = 0;
			return;
		}
		g.drawImage(imgs[step], x, y, null);
		step++;
	}
}


