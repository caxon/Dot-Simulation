package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

import builders.ParticlesBuilder;
import forces.*;
import paths.*;

@SuppressWarnings("serial")
public class World extends JPanel{
	
	// Array of all points
	ArrayList<Particle> particles;
	final String filename = "points.txt";
	int UPDATES_PER_SECOND = 400;
	int WINDOW_WIDTH = 800;
	int WINDOW_HEIGHT = 800;
	
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
						break;
					case 'l':
						p.setPath(new LinearPath2D(scanner.nextDouble(), scanner.nextDouble(),scanner.nextDouble()));
						break;
					case 'w':
						p.setPath(new WanderingPath2D(scanner.nextDouble()));
						break;
					case 'o': // Initiates orbitpath2D to particles(size - scanner[probably 1], speed);
						p.setPath(new OrbitPath2D(p, particles.get(particles.size()-Math.abs(scanner.nextInt())), scanner.nextDouble()));
						break;
					case 'h': // cHasepath()
						p.setPath(new ChasePath2D(particles.get(particles.size()-Math.abs(scanner.nextInt())), scanner.nextDouble()));
						break;
					case '0':
						p.setPath(new LinearPath2D(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble()));
						break;
					default:
						System.out.println("INCORRECT INPUT");
					}
				}
				System.out.println(p);
				particles.add(p);
				scanner.close();
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		/*
		Particle last = particles.get(particles.size()-1);
		Particle chaseTest = new Particle(30, 30);
		chaseTest.setPath(new ChasePath2D(last, 0.15));
		chaseTest.setColor(Color.RED);
		particles.add(chaseTest);*/
		
		
//		Particle heavy = new Particle(200, 200);
//		heavy.setColor(Color.DARK_GRAY);
//		heavy.setSize(20);
//		particles.add(heavy);
//		
//		Particle light = new Particle(150, 210);
//		light.setColor(Color.red);
//		light.setSize(5);
//		
//		ForcePath2D forcepath = new ForcePath2D();
//		Force2D repforce = new RepulsiveForce2D(heavy, 1);
//		Force2D atrforce = new AttractiveForce2D(heavy, 1);
//		
//		forcepath.addForce(repforce);
//		forcepath.push(0.5, 0);
//		light.setPath(forcepath);
//		
//		particles.add(light);

//		particles.addAll( new ParticlesBuilder().genRandom(50, 100, 200, 30).setRepulsiveForce(0.5).pushForce(2, 0).get()); 
		
		/*
		ParticlesBuilder pb_attr = new ParticlesBuilder().genGridGP(180, 180, 30, 30, 4).setColor(Color.blue).pushForce(0.2, 0.2);;
		ParticlesBuilder pb_repul = new ParticlesBuilder().genGridGP(230, 230, 30, 30, 5).setColor(Color.red);;
		pb_attr.setRepulsiveForceAgainst(pb_repul, 0.13);
		pb_repul.setRepulsiveForceAgainst(pb_attr,-0.1);
		
		particles.addAll(pb_attr.add(pb_repul).applyJitter(0.2).get());  */
		
		// PB GRID DEMO:
		/*
		ParticlesBuilder pb_grid = new ParticlesBuilder().genGridGP(-50, -50, 850, 850, 15).setColor(Color.blue).setSize(8);
		ParticlesBuilder pb_shotgun = new ParticlesBuilder().genRandom(400, 400, 50, 3).setColor(Color.RED)
				.setSize(6).shotgunForce(2, 0, 1);				;
//		Particle linear = new Particle(20, 160);
//		Particle orbit = new Particle(20, 200);

		/*
		linear.setPath(new LinearPath2D(3, 1.2, 0.5));
		linear.setVisible(false);
		orbit.setPath(new OrbitPath2D(orbit, linear, 2));
		pb_grid.setRepulsiveForceAgainst(new ParticlesBuilder(orbit), -0.8);
		
		pb_grid.setResistiveForce(0.01);
		pb_shotgun.setResistiveForce(0.00);
		pb_shotgun.pushForce(0.5, 0); 
		pb_grid.setRepulsiveForceAgainst(pb_shotgun, -2);
		pb_shotgun.setRepulsiveForceAgainst(pb_grid, -10);
		

//		particles.add(linear);
//		particles.add(orbit);
		particles.addAll(pb_grid.add(pb_shotgun).get());
		*/
		/*
		Particle p1 = new Particle(150, 150);
		ForcePath2D p1_f = new ForcePath2D();
		p1_f.push(1, 0);
		p1_f.addForce(new ResistiveForce2D(0.01));
		p1.setPath(p1_f);
		particles.add(p1);*/
		
//		RepulsiveForce grid test
//		/*
		ParticlesBuilder pb_grid = new ParticlesBuilder().genGridGP(-50, -50, 900, 900, 15).setColor(Color.blue).setSize(2);
		pb_grid.setResistiveForce(0.01);
		pb_grid.setElasticForceGrid(0.1);
		Particle circle = new Particle(400, 350);
		circle.setPath(new CirclePath2D(circle, 400, 400, 3));
		ParticlesBuilder pb_circle = new ParticlesBuilder(circle);
//		((ForcePath2D) (pb_grid.get().get(1826).getPath())).push(5, 5);
		pb_grid.setRepulsiveForceAgainst(pb_circle, -20);
		particles.addAll(pb_grid.add(pb_circle).get());
//		*/
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
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e){
				if (e.getKeyCode()==KeyEvent.VK_R)
					initData(filename);
				if (e.getKeyCode()==KeyEvent.VK_PLUS)
					UPDATES_PER_SECOND*=1.5;
				if (e.getKeyChar()==KeyEvent.VK_MINUS)
					UPDATES_PER_SECOND/=1.5;
				System.out.println("Key pressed.");
			}
		});
	
	}
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		for (Particle p : particles){
			if (p.getVisible()==false)
				continue;
			if (p.color != null)
				g2.setColor(p.color);
			else 
				g2.setColor(Color.black);
			if (p instanceof GridParticle){ // Draws gridparticles as connections
				for (Particle con : ((GridParticle)p).getShortConnections()){
					;//g2.drawLine((int)p.getX(), (int)p.getY(), (int)con.getX(), (int)con.getY());
				}
				if (p.size>0)
					g2.drawOval((int)( p.x - p.size/2.0), (int)( p.y - p.size/2.0), p.size, p.size);	

			}
			
			
			else if (p.size>0){
					g2.drawOval((int)( p.x - p.size/2.0), (int)( p.y - p.size/2.0), p.size, p.size);
					g2.fillOval((int)( p.x - p.size/2.0), (int)( p.y - p.size/2.0), p.size, p.size);}
			else
				g2.drawLine((int)p.getX(), (int)p.getY(), (int)p.getX(), (int)p.getY());
		}
	}
	
	
}