import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class World extends JPanel{
	
	// Array of all points
	ArrayList<Particle> particles;
	final String filename = "points.txt";
	int UPDATES_PER_SECOND = 60;
	
	// Generates points array from file points.txt
	@SuppressWarnings("unused")
	public void initData(String filename){
		particles = new ArrayList<Particle>();
		File file = new File(filename);
		
		if (file == null)
			System.out.println("CANNOT READ FILE");
		
		try (Scanner fileScanner = new Scanner(file)){
			
			while (fileScanner.hasNextLine()){
				String line = fileScanner.nextLine();
				if (line.matches("#.*")) // Ignore comments signified by #
					continue;
				Scanner scanner = new Scanner(line); // Scans token by token
				Particle p = new Particle(scanner.nextDouble(), scanner.nextDouble()); // First two entries are points
				while (scanner.hasNext()){
					// Flag for handling point data
					char flag = scanner.next().charAt(0);
					switch (flag){
					case 'c':
						p.setPath(new CirclePath2D(p, scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble()));
						break;
					case 'C':
						p.setColor(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
						break;
					case 's':
						p.setSize(scanner.nextInt());
					}
				}
				// Print string representation of p
				System.out.println(p);
				particles.add(p);
				scanner.close();
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void tick(){
		for (Particle p : particles)
			p.move();
	}
	
	public void clickMouse(MouseEvent e){
		Particle p =(new Particle(e.getX(), e.getY()));
		p.setSize(10 + (int)(Math.random()*20));
		p.setColor(Color.blue);
		p.setPath(new CirclePath2D(p, p.x+Math.random()*20-10, p.y+Math.random()*20-10, Math.random()*2+1));
		particles.add(p);
	}
	
	public World(){
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		System.out.println("Created!");
		initData(filename);
		new Timer(1000/UPDATES_PER_SECOND, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tick();
				repaint();
			}
		}).start();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				clickMouse(e);
			}
		});
	
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(400, 400);
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		for (Particle p : particles){
			if (p.color != null)
				g2.setColor(p.color);
			else 
				g2.setColor(Color.black);
			if (p.size>0)
				g2.drawOval((int) p.x - p.size, (int) p.y - p.size, p.size, p.size);
			else
				g2.drawLine((int)p.getX(), (int)p.getY(), (int)p.getX(), (int)p.getY());
		}
	}
	
}