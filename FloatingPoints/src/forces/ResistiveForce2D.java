package forces;

import main.Particle;
import paths.ForcePath2D;

public class ResistiveForce2D extends Force2D {

	double coefficient = 0.5;
	public ResistiveForce2D(double coefficient) {
		this.coefficient=coefficient;
	}
	
	@Override
	public void calculateForce(Particle particle) {

		ForcePath2D fp = (ForcePath2D)particle.getPath();
		double speed = fp.getSpeed();
		double theta = fp.getDirection();
		
		fx = -coefficient*Math.cos(theta)*speed;
		fy = -coefficient*Math.sin(theta)*speed;
		
		
	}

}
