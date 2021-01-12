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
	
	public Driver() {
		JFrame frame = new JFrame("Agar.io");
		frame.setSize(1000, 1000);
		frame.add(this);
		
		for (int i = 0; i < 40; i ++) {
			Cell c = new Cell((int)(Math.random()*1000),(int)(Math.random()*1000),i, 200);
			Enimy.add(c);
		}
		
		for (int i = 0; i < 500; i ++)  {
			Cell c = new Cell((int)(Math.random()*1000),(int)(Math.random()*1000),extraCellID, 50);
			Enimy.add(c);
		}
		t = new Timer(10,this);
		t.start();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public void paint(Graphics g) {
		super.paintComponent(g);
		int loopTime = (int)(System.currentTimeMillis() - lastLoopTime);
		System.out.println(loopTime);
		lastLoopTime = System.currentTimeMillis();
		for (Cell e : Enimy) {
			e.paint(g,Enimy,loopTime);
			for (int i = 0; i < Enimy.size(); i ++) {
				Cell j = Enimy.get(i);
				if (e.getMass() > j.getMass() && e.getDistance(j.getX(), j.getY()) <= e.getRadius()) {
					e.addMass(j.getMass());
					Enimy.remove(i);
					i --;
				}
			}
		}
		
		for (int i = 0; i < 1; i ++)  {
			Cell c = new Cell((int)(Math.random()*1000),(int)(Math.random()*1000),extraCellID, 50);
			Enimy.add(c);
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
