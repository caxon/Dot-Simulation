package forces;

import main.Particle;
@Deprecated
public class AttractiveForce2D extends Force2D{

	double coefficient;
	Particle applier;
	public final double MIN_DISTANCE = 0.1;

	
	public AttractiveForce2D(Particle applier, double coefficient) {
		this.applier = applier;
		this.coefficient=coefficient;
	}

	@Override
	public void calculateForce(Particle p) {
		double dx = applier.getX()-p.getX();
		double dy = applier.getY()-p.getY();
		double distance = Math.hypot(dx, dy);

		if (MIN_DISTANCE >0 && distance<MIN_DISTANCE) // Prevents insane force values and divide by zero issues
			distance = MIN_DISTANCE;
		double fmagnitude = coefficient/Math.pow(distance, 2);
		double dxnorm = dx/distance;
		double dynorm = dy/distance;
		
		
		fx = fmagnitude*dxnorm;
		fy = fmagnitude*dynorm;
		
	}
	

}
