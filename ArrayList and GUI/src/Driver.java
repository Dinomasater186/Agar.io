import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Driver extends JPanel implements MouseListener, ActionListener {
	ArrayList<Cell> Enimy = new ArrayList<Cell>();
	Timer t;
	Long lastLoopTime = System.currentTimeMillis();
	int extraCellID = 100;
	int numPlayers = 0;
	Font big = new Font("Courier New", 1, 50);
	Font small = new Font("Courier New", 1, 30);
	Font biggest = new Font("Courier New", 1, 90);
	int z = 0;
	
	public void reset() {
		z = 0;
		Enimy.clear();
		for (int i = 0; i < 100; i ++) {
			Cell c = new Cell((int)(Math.random()*1000),(int)(Math.random()*1000),i, 50);
			Enimy.add(c);
		}
		
		for (int i = 0; i < 200; i ++)  {
			Cell c = new Cell((int)(Math.random()*1000),(int)(Math.random()*1000),extraCellID, 5);
			Enimy.add(c);
		}
	}
	
	public Driver() {
		JFrame frame = new JFrame("Agar.io");
		frame.setSize(1000, 1000);
		frame.add(this);
		
		reset();
		
		t = new Timer(10,this);
		t.start();
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public void paint(Graphics g) {
		numPlayers = 0;
		super.paintComponent(g);
		int loopTime = (int)(System.currentTimeMillis() - lastLoopTime);
		lastLoopTime = System.currentTimeMillis();
		
		double zoom = Math.max(600/(Math.pow((double)Enimy.get(0).getMass(),0.9)),1);
		
		g.drawRect((int)( zoom * -1 * Enimy.get(0).getX()) + 500, (int)(zoom * -1 * Enimy.get(0).getY()) + 425, (int)(1000*zoom), (int)(1000*zoom));
		
		for (Cell e : Enimy) {
			
			e.paint(g,Enimy,loopTime,zoom,Enimy.get(0).getX(),Enimy.get(0).getY());
			for (int i = 0; i < Enimy.size(); i ++) {
				Cell j = Enimy.get(i);
				if (e.getMass() > j.getMass() && e.getDistance(j.getX(), j.getY()) <= e.getRadius()) {
					e.addMass(j.getMass());
					Enimy.remove(i);
					i --;
				}
			}
			
			if (e.getCellID() != extraCellID) {
				numPlayers ++;
			}
			
			if (e.getX() + e.radius > 1000) {e.setX((int)(1000-e.radius -1));}
			if (e.getY() + e.radius > 1000) {e.setY((int)(1000-e.radius -1));}
			if (e.getX() - e.radius < 0) {e.setX((int)(e.radius + 1));}
			if (e.getY() - e.radius < 0) {e.setY((int)(e.radius + 1));}
			
		}
		if (Enimy.size() <= 10000) {
			for (int i = 0; i < 1; i ++)  {
				Cell c = new Cell((int)(Math.random()*1000),(int)(Math.random()*1000),extraCellID, 5);
				Enimy.add(c);
			}
		}
		
		if (numPlayers == 1) {
			z ++;
			g.setColor(Color.black);
			g.setFont(biggest);
			if (Enimy.get(0).getCellID() != 0) {
				g.drawString("Cell " + (Enimy.get(0).getCellID() + 1) + " Won", 220, 300);
			}
			else {
				g.drawString("You Won", 300, 300);
			}
		}
		if (z == 100) {
			reset();
		}

		g.setColor(Color.black);
		g.setFont(small);
		g.drawString("Cells Remaining " + numPlayers, 660, 50);
		g.setFont(small);
		if (Enimy.get(0).getCellID() != 0) {
			g.drawString("Spectating " + (Enimy.get(0).getCellID() + 1), 0, 900);
		}
		
	}
	public static void main(String[] args) {
		Driver drive = new Driver();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
