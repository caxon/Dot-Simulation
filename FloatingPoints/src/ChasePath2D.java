
public class ChasePath2D extends ParticlePath{

	Particle target;
	double speed;
	
	public ChasePath2D(Particle target, double speed) {
		this.target = target;
		this.speed = speed;
	}
	@Override
	void updatePath(Particle particle) {
		double dx; double dy;
		double distance = Math.hypot(target.getX()-particle.getX(), target.getY()-particle.getY());
		dx = target.getX()-particle.getX();
	}

	
}
