package paths;

import main.Particle;

public class OrbitPath2D extends CirclePath2D {

	Particle target;
	
	public OrbitPath2D(Particle p, Particle target, double speed) {
		super(p, target.getX(), target.getY(), speed);
		this.target=target;
	}
	
	@Override
	public void updatePath(Particle p) {
		center_x=target.getX();
		center_y=target.getY();
		super.updatePath(p);
	}

}
