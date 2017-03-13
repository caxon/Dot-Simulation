package paths;

import main.Particle;

public class WanderingPath2D extends ParticlePath{

	Destination dest = null;
	Double range = 30d;
	Double FOV = Math.toRadians(240);
	Double theta = Math.toRadians(360*Math.random());
	
	public WanderingPath2D(double speed){
		dest = null;
		this.speed = speed;
	}
	
	public void setRange(double range){
		this.range =range;
	}
	
	public Destination newDestination(Particle p){
		double x = p.getX();
		double y = p.getY();
		double r = range/2*(Math.random() + 1);
		theta = theta + FOV*(Math.random()-0.5);
		Destination ret = new Destination(r*Math.cos(theta)+x, r*Math.sin(theta)+y);
		System.out.println(ret + "theta: " + Math.toDegrees(theta));
		return ret;
	}
	
	@Override
	public void updatePath(Particle p) {
		if (dest==null){
			dest = newDestination(p);
		}
		double deltaX = dest.x-p.getX();
		double deltaY = dest.y-p.getY();
		double hypot = Math.hypot(deltaX, deltaY);
//		System.out.println(hypot);
		if (hypot < speed){
			dest = newDestination(p);
		}
		else{
			p.x += speed*Math.cos(theta);
			p.y += speed*Math.sin(theta);
		}
	}
	
}

class Destination{
	double x, y;
	
	Destination(double x, double y){
		this.x=x;this.y=y;
	}
	
	@Override
	public String toString(){
		return String.format("WanderingPath: x: %.1f, y:%.1f", x, y);
	}
}
