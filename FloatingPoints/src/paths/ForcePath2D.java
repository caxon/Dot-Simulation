package paths;

import java.util.ArrayList;
import forces.*;

import main.Particle;

public class ForcePath2D extends ParticlePath {

	ArrayList<Force2D> forces;
	public double fx;
	public double fy; // Net force x, y components
	
	public double vx;
	public double vy; // Velocity x, y components
	
	public double mass=1;
	
	public ForcePath2D(){
		forces = new ArrayList<Force2D>();
	}
	
	@Override
	public void updatePath(Particle particle) {
		fx=0; // Sums net x,y components
		fy=0;
		for (Force2D f:forces){
			f.calculateForce(particle);
			fx+=f.fx;
			fy+=f.fy;
		}
		vx+=fx/mass;
		vy+=fy/mass;
		
		particle.x+=vx;
		particle.y+=vy;
		}
	
	public void addForce(Force2D f){
		forces.add(f);
	}
	
	// Adds velocity in x, y directions
	public void push(double vx, double vy){
		this.vx+=vx;
		this.vy+=vy;
	}
	
	// Returns magnitude of velocity
	public double getSpeed(){
		return Math.hypot(vx, vy);
	}
	
	// Returns direction (theta) of velocity
	public double getDirection(){
		return Math.atan2(vy, vx);
	}
	
	public void setMass(double mass){
		this.mass=mass;
	}

}
