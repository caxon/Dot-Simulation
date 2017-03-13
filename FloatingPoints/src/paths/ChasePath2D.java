package paths;

import main.*;

public class ChasePath2D extends ParticlePath{

	Particle target;
	double speed;
	
	public ChasePath2D(Particle target, double speed) {
		this.target = target;
		this.speed = speed;
	}
	@Override
	public
	void updatePath(Particle p) {
		double deltaX = target.getX()-p.getX();
		double deltaY = target.getY()-p.getY();
		double hypot = Math.hypot(deltaX, deltaY);
		if (hypot < speed){
			p.x=target.x;
			p.y=target.y;
		}
		else{
			p.x += deltaX*speed/hypot;
			p.y += deltaY*speed/hypot;
		}
	}

	
}
