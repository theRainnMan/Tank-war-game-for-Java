import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tank {
	private int x;
	private int y;
	private boolean bL = false, bU = false, bR = false, bD = false;
	
	private Direction dir = Direction.Stop;
	private Direction barrelDir = Direction.D;
	public static final int xSpeed = 5;
	public static final int ySpeed = 5;
	public static final int Width = 30;
	public static final int Height = 30;
	private boolean isEnemy;
	private boolean live = true;
	private int step = r.nextInt(12) + 3;
	private int oldX, oldY;
	private int life = 100;
	private bloodBar bb = new bloodBar();
	//private boolean good;
	
	private static Random r = new Random();
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	
	TankClient Tc;
	private static Image[] tankImages = null;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	static {
		tankImages = new Image[]{ 
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankL.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankLD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankLU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankR.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankRD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankRU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/tankU.gif")),
		};
		imgs.put("D", tankImages[0]);
		imgs.put("L", tankImages[1]);
		imgs.put("LD", tankImages[2]);
		imgs.put("LU", tankImages[3]);
		imgs.put("R", tankImages[4]);
		imgs.put("RD", tankImages[5]);
		imgs.put("RU", tankImages[6]);
		imgs.put("U", tankImages[7]);
	}
	
	public Tank(int x, int y, boolean isEnemy) {
		this.x = x;
		this.y = y;
		this.isEnemy = isEnemy;
	}
	
	public Tank(int x, int y, boolean isEnemy, Direction dir, TankClient Tc){
		this(x, y, isEnemy);
		//this.oldX = oldX;
		//this.oldY = oldY;
		this.dir = dir;
		this.Tc = Tc;
	}
	
	void move(){
		this.oldX = x;
		this.oldY = y;
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
		if(this.dir != Direction.Stop){
			this.barrelDir = this.dir;
		}
		if(x < 0) x = 0;
		if(y < 26) y = 26;
		if(x + Tank.Width > TankClient.GameWidth) x = TankClient.GameWidth - Tank.Width;
		if(y + Tank.Height > TankClient.GameHight) y = TankClient.GameHight - Tank.Height;
		
		if(!isEnemy){
			Direction[] dirs = Direction.values();
			if(step == 0){
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			step--;
			
			if(r.nextInt(40) > 38) this.fire();
		}
	}
	
	public void stay(){
		x = oldX;
		y = oldY;
	}
	
	public void draw(Graphics g){
		if(!live){
			if(!isEnemy){
				Tc.tanks.remove(this);
			}
			return;
		}
		if(isEnemy){
			Color c = g.getColor();
			g.setColor(Color.red);
			bb.draw(g);
			g.setColor(c);
		}
		
		switch(barrelDir){
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		}
		move();
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_F2:
			if(!this.live){
				this.live = true;
				this.life = 100;
			}
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();
	}
	
	void locateDirection(){
		if(bL && !bD && !bR && !bU) dir = Direction.L;
		else if(bL && !bD && !bR && bU) dir = Direction.LU;
		else if(!bL && !bD && !bR && bU) dir = Direction.U;
		else if(!bL && !bD && bR && bU) dir = Direction.RU;
		else if(!bL && !bD && bR && !bU) dir = Direction.R;
		else if(!bL && bD && bR && !bU) dir = Direction.RD;
		else if(!bL && bD && !bR && !bU) dir = Direction.D;
		else if(bL && bD && !bR && !bU) dir = Direction.LD;
		else if(!bL && !bD && !bR && !bU) dir = Direction.Stop;
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_W:
			superfire();
			break;
		case KeyEvent.VK_A:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDirection();
	}

	public Missile fire() {
		if(!live) return null;
		int x = this.x + Tank.Width/2 - Missile.Width/2;
		int y = this.y + Tank.Height/2 - Missile.Height/2;
		Missile m = new Missile(x, y, isEnemy, barrelDir, this.Tc);
		Tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir){
		if(!live) return null;
		int x = this.x + Tank.Width/2 - Missile.Width/2;
		int y = this.y + Tank.Height/2 - Missile.Height/2;
		Missile m = new Missile(x, y, isEnemy, dir, this.Tc);
		Tc.missiles.add(m);
		return m;
	}
	
	public Rectangle getRec(){
		return new Rectangle(x, y, Width, Height);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isEnemy() {
		return isEnemy;
	}
	
	public boolean collideTheWall(Wall w){
		if(this.getRec().intersects(w.getRec())){
			this.stay();
			return true;
		}
		else{
			return false;
		}
	}
	
	private void superfire(){
		Direction[] dirs = Direction.values();
		for(int i = 0; i < 8; i++){
			fire(dirs[i]);
		}
	}
	
	public boolean collidesWithTanks(List<Tank> tanks){
		for(int i = 0; i < tanks.size(); i++){
			Tank t = tanks.get(i);
			if(this != t){
				if(this.live && t.isLive() && this.getRec().intersects(t.getRec())){
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	public boolean collideBlood(Blood b){
		if(this.live && b.isLive() && this.getRec().intersects(b.getRec())){
			b.setLive(false);
			return true;
		}
		else{
			return false;
		}
	}
	
	public class bloodBar{
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y - 10, Width, 10);
			int w = Width * life/100;
			g.fillRect(x, y - 10, w, 10);
			g.setColor(c);
		}
	}
}



