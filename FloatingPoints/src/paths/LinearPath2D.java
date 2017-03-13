package paths;
import main.Particle;

public class LinearPath2D extends ParticlePath {

	public double dx, dy;
	
	public LinearPath2D(double dx, double dy, double speed){

		this.speed=speed;
		
		double magnitude = Math.hypot(dx, dy);
		this.dx=dx/magnitude;
		this.dy=dy/magnitude;
	}
	
	@Override
	public
	void updatePath(Particle particle) {
		particle.x+=dx*speed;
		particle.y+=dy*speed;
	}
	
}
