import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Cell {
	public int mass;
	public double radius;
	public Color c;
	public int x;
	public int y;
	public double maxSpeed;
	public double vx;
	public double vy;
	public int screenX = 1000;
	public int screenY = 800;
	int targetX = 0;
	int targetY = 0;
	int cellID;
	long loopyTimey = 0;
	boolean amPlayer = false;
	
	public Cell (int X, int Y, int ID, int extraMass) {
		int red = (int)(Math.random() * 256);
		int green = (int)(Math.random() * 256);
		int blue = (int)(Math.random() * 256);
		c = new Color(red,green,blue);
		mass = extraMass;
		x = X;
		y = Y;
		targetX = x;
		targetY = y;
		
		cellID = ID;
	}
	
	public void setAmPlayer (boolean haveAI) {
		amPlayer = haveAI;
	}
	
	public void update(ArrayList<Cell> list) {
		if (! amPlayer) {
			getTarget(list);
		}
		convertMassToRadius();
		Move();
	}
	public void Move() {
		double distX = targetX - x;
		double distY = targetY - y;
		getMaxSpeed();
		maxSpeed *= loopyTimey/4;
		double preferedSpeed = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		double ratio = maxSpeed/preferedSpeed;
		int moveX = (int)(Math.ceil(Math.abs((distX * ratio))) * Math.signum(distX));
		int moveY = (int)(Math.ceil(Math.abs((distY * ratio))) * Math.signum(distY));
		x += moveX;
		y += moveY;
	}
	
	public void setY(int Y) {
		y = Y;
	}
	
	public void setX(int X) {
		x = X;
	}
	
	public void getMaxSpeed() {
		maxSpeed = 1/Math.sqrt(mass);
	}
	public void convertMassToRadius() {
		radius = Math.sqrt((mass));
	}
	public void paint(Graphics g, ArrayList<Cell> list, int loopTime, double Zoom, int extraX, int extraY) {
		update(list);
		loopyTimey = loopTime;
		Rectangle screen = new Rectangle(0,0,1000,900);
		Rectangle blob = new Rectangle((int)(Zoom * (x-radius - extraX ))+ (500),(int)(Zoom * (y-radius -extraY ))+(425),(int)(Zoom * radius*2),(int)(Zoom * radius*2));
		if (screen.intersects(blob)) {
			g.setColor(c);
			g.fillOval((int)(Zoom * (x-radius - extraX ))+ (500),(int)(Zoom * (y-radius -extraY ))+(425),(int)(Zoom * radius*2),(int)(Zoom * radius*2));
		}
	}
	
	public double getDistance(int TargetX, int TargetY) {
		return Math.pow(Math.pow((double)(TargetX-x),2) + Math.pow((double)(TargetY-y),2), 0.5);
	}
	public int getMass() {
		return mass;
	}
	public int getCellID() {
		return cellID;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public double getRadius() {
		return radius;
	}
	public void addMass(double extraMass) {
		mass += extraMass;
	}
	public void getTarget(ArrayList<Cell> list) {
		int initialTargetX = 0;
		int initialTargetY = 0;
		int initialTargetMass = 0;
		for (int i = 0; i < list.size(); i ++) {
			if (cellID != i && mass < list.get(i).getMass()) {
				initialTargetX = list.get(i).getX();
				initialTargetY = list.get(i).getY();
				initialTargetMass =  5;
			}
		}
		double targetScore = Math.pow(initialTargetMass,2) / Math.pow(getDistance(initialTargetX,initialTargetY),3);
		for (int i = 0 ; i < list.size(); i ++) {
			if (cellID != list.get(i).getCellID() && list.get(i).getMass()<mass && targetScore <= Math.pow(list.get(i).getMass(),2)/Math.pow(getDistance(list.get(i).getX(),list.get(i).getY()),3)) {
				targetX = list.get(i).getX(); initialTargetX = list.get(i).getX();
				targetY = list.get(i).getY(); initialTargetY = list.get(i).getY();
				initialTargetMass = list.get(i).getMass();
				targetScore = Math.pow(initialTargetMass,2) / Math.pow(getDistance(initialTargetX,initialTargetY),3);
			}
		}
		if (targetScore*10000 <= 1) {
			double dist = getDistance(initialTargetX,initialTargetY);
			for (int i = 0 ; i < list.size(); i ++) {
				if (cellID != list.get(i).getCellID() && list.get(i).getMass()<mass && dist >= getDistance(list.get(i).getX(),list.get(i).getY())) {
					targetX = list.get(i).getX(); initialTargetX = list.get(i).getX();
					targetY = list.get(i).getY(); initialTargetY = list.get(i).getY();
					dist = getDistance(initialTargetX,initialTargetY);
				}
			}
		}
	}
}
