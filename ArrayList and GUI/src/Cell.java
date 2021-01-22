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
	public double dX;
	public double dY;
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
		int red = (int)(Math.random() * 100) + 50;
		int green = (int)(Math.random() * 200) + 50;
		int blue = (int)(Math.random() * 200) + 50;
		c = new Color(red,green,blue);
		mass = extraMass;
		x = X; dX = X;
		y = Y; dY = Y;
		targetX = x;
		targetY = y;
		
		cellID = ID;
	}
	
	public void setAmPlayer (boolean notHaveAI) {
		amPlayer = notHaveAI;
	}
	
	public void setTarget(int X, int Y) {
		targetX = X;
		targetY = Y;
	}
	
	public void update(ArrayList<Cell> list) {
		
		convertMassToRadius();
		if (!amPlayer) {
			getTarget(list);
			Move();
		}
	}
	public void Move() {
		double distX = targetX - x;
		double distY = targetY - y;
		getMaxSpeed();
		maxSpeed *= ((double)loopyTimey/1000);//System.out.println(maxSpeed + " " + ((double)loopyTimey/1000) + " " + loopyTimey);
		double preferedSpeed = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		double ratio = maxSpeed/preferedSpeed;
		double moveX =(distX * ratio);
		double moveY = (distY * ratio);
		
		dX += moveX;
		dY += moveY;
		
		x = (int)dX;
		y = (int)dY;
	}
	
	public void setY(int Y) {
		y = Y; dY = Y;
	}
	
	public void setX(int X) {
		x = X; dX = X;
	}
	
	public void getMaxSpeed() {
		maxSpeed = Math.ceil(400/radius) + 50;
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
