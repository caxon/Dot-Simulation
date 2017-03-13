package forces;

import main.Particle;

abstract public class Force2D {

	public double fx;
	public double fy;
	
	public double getMagnitude(){
		return Math.hypot(fx, fy);
	}
	
	public abstract void calculateForce(Particle particle);
	
}
