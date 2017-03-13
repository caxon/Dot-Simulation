package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class FloatingPoints {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowGUI();
			}
		});
	}
	
	static void createAndShowGUI(){
		JFrame f = new JFrame("Test Frame");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		World w = new World();
		w.setFocusable(true);
		f.add((JPanel) w);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		
	}
	
}


