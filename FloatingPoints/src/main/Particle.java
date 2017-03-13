package main;
import java.awt.Color;

import paths.*;

public class Particle {

	public double x;
	public double y;
	public double z;
	
	public ParticlePath path;
	public Color color;
	public int size = 3;
	
	public boolean visible = true;
	
	
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getZ(){
		return z;
	}
	
	Particle(double x, double y, double z){
		this.x=x; this.y=y; this.z=z;
	}
	
	public Particle(double x, double y){
		this.x=x;this.y=y;
	}
	public void setPath(ParticlePath path){
		this.path = path;
	}
	public ParticlePath getPath(){
		return this.path;
	}
	public void setColor(int r, int g, int b){
		this.color = new Color(r, g, b);
		System.out.println(this.color);
	}
	public void setSize(int size){
		this.size=size;
	}
	
	public void move(){
		if (path!=null)
			path.updatePath(this);
	}
	
	public void setVisible(boolean visible){
		this.visible=visible;
	}
	public boolean getVisible(){
		return visible;
	}
	@Override
	public String toString(){
		return String.format("P: (%.1f, %.1f)", x, y) + ((path!=null) ? "\n  " +path : "");
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
