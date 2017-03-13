package forces;

import main.Particle;


public class ConstantForce2D extends Force2D {

	public ConstantForce2D(double fx, double fy) {
		this.fx=fx;
		this.fy=fy;
	}
	
	@Override
	public void calculateForce(Particle particle) {
		
	}

}
