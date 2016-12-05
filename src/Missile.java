import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Missile {
	public static final int xSpeed = 10;
	public static final int ySpeed = 10;
	int x;
	int y;
	public static final int Width = 10;
	public static final int Height = 10;
	private boolean live = true;
	Direction dir;
	private TankClient Tc;
	private boolean good;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] missileImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	
	static{
		missileImages = new Image[] {
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileD.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileL.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileLD.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileLU.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileR.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileRD.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileRU.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileU.gif")),
		};
		
		imgs.put("D", missileImages[0]);
		imgs.put("L", missileImages[1]);
		imgs.put("LD", missileImages[2]);
		imgs.put("LU", missileImages[3]);
		imgs.put("R", missileImages[4]);
		imgs.put("RD", missileImages[5]);
		imgs.put("RU", missileImages[6]);
		imgs.put("U", missileImages[7]);
	}
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, boolean good, Direction dir, TankClient Tc) {
		this(x, y, dir);
		this.good = good;
		this.Tc = Tc;
	}
	
	public void draw(Graphics g){
		if(!live){
			Tc.missiles.remove(this);
			return;
		}
		switch(dir){
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		}
		move();
	}
	
	private void move(){
		switch(dir){
		case L:
			x -= xSpeed;
			break;
		case LU:
			x -= xSpeed;
			y -= ySpeed;
			break;
		case U:
			y -= ySpeed;
			break;
		case RU:
			x += xSpeed;
			y -= ySpeed;
			break;
		case R:
			x += xSpeed;
			break;
		case RD:
			x += xSpeed;
			y += ySpeed;
			break;
		case D:
			y += ySpeed;
			break;
		case LD:
			x -= xSpeed;
			y += ySpeed;
			break;
		case Stop:
			break;
		}
		if(x < 0 || y < 0 || x > TankClient.GameWidth || y > TankClient.GameHight){
			live = false;
		}
	}

	public boolean isLive() {
		return live;
	}
	
	public Rectangle getRec(){
		return new Rectangle(x, y, Width, Height);
	}
	
	public boolean hitTank(Tank t){
		if(this.getRec().intersects(t.getRec()) && t.isLive() && this.good != t.isEnemy() && this.live){
			if(t.isEnemy()){
				t.setLife(t.getLife() - 20);
				if(t.getLife() <= 0){
					t.setLive(false);
				}
			}
			else{
				t.setLive(false);
			}
			//t.setLive(false);
			this.live = false;
			Explode e = new Explode(x, y, Tc);
			Tc.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks){
		for(int i = 0; i < tanks.size(); i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall w){
		if(this.live && this.getRec().intersects(w.getRec())){
			this.live = false;
			return true;
		}
		else{
			return false;
		}
	}
}






