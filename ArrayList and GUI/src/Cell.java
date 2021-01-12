import java.awt.Color;
import java.awt.Graphics;
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
	int targetX = 0;
	int targetY = 0;
	int cellID;
	long loopyTimey = 0;
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
	public void update(ArrayList<Cell> list) {
		getTarget(list);
		convertMassToRadius();
		Move();
	}
	public void Move() {
		double distX = targetX - x;
		double distY = targetY - y;
		getMaxSpeed();
		maxSpeed *= loopyTimey;
		double preferedSpeed = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		double ratio = maxSpeed/preferedSpeed;
		int moveX = (int)(Math.ceil(Math.abs((distX * ratio))) * Math.signum(distX));
		int moveY = (int)(Math.ceil(Math.abs((distY * ratio))) * Math.signum(distY));
		x += moveX;
		y += moveY;
	}
	public void getMaxSpeed() {
		maxSpeed = 1/Math.sqrt(mass);
	}
	public void convertMassToRadius() {
		radius = Math.sqrt((mass));
	}
	public void paint(Graphics g, ArrayList<Cell> list, int loopTime) {
		update(list);
		loopyTimey = loopTime;
		g.setColor(c);
		g.fillOval((int)(x-radius),(int)(y-radius),(int)(radius*2),(int)(radius*2));
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
		for (int i = 0; i < Math.min(2,list.size()); i ++) {
			if (cellID != i) {
				initialTargetX = list.get(i).getX();
				initialTargetY = list.get(i).getY();
				initialTargetMass =  list.get(i).getMass();
			}
		}
		double targetScore = initialTargetMass / Math.pow(getDistance(initialTargetX,initialTargetY)/radius,2);
		for (int i = 0 ; i < list.size(); i ++) {
			if (cellID != list.get(i).getCellID() && list.get(i).getMass()<mass && targetScore <= list.get(i).getMass()/Math.pow(getDistance(list.get(i).getX(),list.get(i).getY())/radius,2)) {
				targetX = list.get(i).getX(); initialTargetX = list.get(i).getX();
				targetY = list.get(i).getY(); initialTargetY = list.get(i).getY();
				targetScore = initialTargetMass / Math.pow(getDistance(initialTargetX,initialTargetY)/radius,2);
			}
		}
	}
}
