package forces;

import main.Particle;

public class ElasticForce2D extends Force2D{

	public Particle applier;
	public double distance_0;
	public double coefficient;
	
	public ElasticForce2D(Particle applier, double distance_0, double coefficient){
		this.applier=applier;
		this.distance_0=distance_0;
		this.coefficient=coefficient;
	}
	
	public ElasticForce2D(Particle this_particle, Particle applier, double coefficient){
		this(applier, Math.hypot(this_particle.getX()-applier.getX(), this_particle.getY()-applier.getY()), coefficient);
	}

	@Override
	public void calculateForce(Particle particle) {
		double dx = applier.getX()-particle.getX();
		double dy = applier.getY()-particle.getY();
		double dist = Math.hypot(dx, dy);
		double theta = Math.atan2(dy, dx);
		double delta_d = (dist-distance_0)/distance_0;
		fx = Math.cos(theta)*coefficient*delta_d;
		fy = Math.sin(theta)*coefficient*delta_d;
	}

}
