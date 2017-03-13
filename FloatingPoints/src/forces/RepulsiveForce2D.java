package forces;

import main.Particle;

public class RepulsiveForce2D extends Force2D{

	double coefficient;
	Particle applier;
	public final double MIN_DISTANCE = 0.8;

	
	public RepulsiveForce2D(Particle applier, double coefficient) {
		this.applier = applier;
		this.coefficient=coefficient;
	}
	
	public Particle getApplier(){
		return applier;
	}

	@Override
	public void calculateForce(Particle p) {
		double dx = applier.getX()-p.getX();
		double dy = applier.getY()-p.getY();
		double distance = Math.hypot(dx, dy);
		if (MIN_DISTANCE >0 && distance<MIN_DISTANCE) // Prevents insane force values and divide by zero issues

		{
			fx=0; fy=0;
			return;
		}
		double fmagnitude = coefficient/Math.pow(distance, 2);
		double dxnorm = dx/distance;
		double dynorm = dy/distance;
		
		
		fx = -fmagnitude*dxnorm;
		fy = -fmagnitude*dynorm;
	}
	

}
