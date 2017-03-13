package builders;

import java.awt.Color;
import java.util.ArrayList;

import paths.*;
import forces.*;
import main.GridParticle;
import main.Particle;

public class ParticlesBuilder {

	ArrayList<Particle> particles;
	
	public ParticlesBuilder(){
		particles = new ArrayList<Particle>();
	}
	
	public ParticlesBuilder genRandom(double center_x, double center_y, double range, int num_particles){
		for (int p_i = 0; p_i< num_particles; p_i++){ // p_i = particle iterator 
			Particle p = new Particle(center_x + range * (Math.random()-0.5), center_y + range * (Math.random()-0.5));
			p.setColor(Color.BLUE);
			p.setSize(5);
			
			particles.add(p);
		}
		return this;
	}
	
	// Generates grid of particles width x height with spacing space between particles horizontally and vertically
	public ParticlesBuilder genGrid(double center_x, double center_y, double width, double height, double spacing){
		System.out.format("Generating %s particles in grid.\n", (int)(width*height/spacing/spacing));
		particles = new ArrayList<Particle>();
		int num_horiz = (int) ( width/spacing);
		int num_verti = (int) ( height/spacing);
		for (int ix = 0; ix < num_horiz; ix++)
			for (int iy = 0; iy < num_verti; iy++)
				particles.add(new Particle(center_x+ix*spacing, center_y+iy*spacing));
		return this;
	}
	public ParticlesBuilder genCircle(double center_x, double center_y, double radius, double num_particles, double theta_offset){

		System.out.format("Generating %s particles in circle.\n", (int)(num_particles));
		particles = new ArrayList<Particle>();
		double theta = Math.PI*2*theta_offset;
		double theta_incr = Math.PI*2/num_particles;
		for (int i = 0; i<num_particles;i++){
			particles.add(new Particle(center_x+Math.cos(theta)*radius,
					center_y + Math.sin(theta)*radius));
			theta+=theta_incr;
		}
		
		return this;
	}
	public ParticlesBuilder genCircle(double center_x, double center_y, double radius, double num_particles){
		return genCircle(center_x, center_y, radius, num_particles, 0);
	}
	
	// Fills particles<> with GridParticles (as connections instead of particles)
	public ParticlesBuilder genGridGP(double center_x, double center_y, double width, double height, double spacing){
		System.out.format("Generating %s particles in grid.\n", (int)(width*height/spacing/spacing));
		particles = new ArrayList<Particle>();
		int num_horiz = (int) ( width/spacing);
		int num_verti = (int) ( height/spacing);
		for (int ix = 0; ix < num_horiz; ix++){
			for (int iy = 0; iy < num_verti; iy++){
				particles.add(new GridParticle(center_x+ix*spacing, center_y+iy*spacing));
			}
		}
		for (int ix = 0; ix < num_horiz; ix++){
			for (int iy = 0; iy < num_verti; iy++){
				GridParticle gp = (GridParticle) particles.get(ix*num_horiz+iy); 
				// Checks four cases surrounding GP and connects them to the GP. Safety around the edges.
				
				if (ix<num_horiz - 1)
					gp.connect(particles.get((ix+1)*num_horiz + iy));
				
				if (iy<num_verti - 1)
					gp.connect(particles.get(ix*num_horiz + iy + 1));
			}
		}
		return this;
	}
	
	public ParticlesBuilder setRepulsiveForce(double forcemod){
		for (int p_i = 0; p_i<particles.size();p_i++){
			ParticlePath pp = particles.get(p_i).getPath();
			ForcePath2D fp;
			if (pp instanceof ForcePath2D)
				fp = (ForcePath2D) pp;
			else
				fp = new ForcePath2D();
			fp.setMass(1);
			
			for (int fp_i = 0; fp_i<particles.size(); fp_i ++){
				if (p_i!=fp_i)
					fp.addForce(new RepulsiveForce2D(particles.get(fp_i), forcemod));
			}
			particles.get(p_i).setPath(fp);
		}
		return this;
	}
	public ParticlesBuilder setRepulsiveForce(){
		return setRepulsiveForce(1.0);
	}
	public ParticlesBuilder setAttractiveForce(){
		return setRepulsiveForce(-1.0);
	}
	public ParticlesBuilder setAttractiveForce(double forcemod){
		return setRepulsiveForce(-forcemod);
	}
	public ParticlesBuilder setRepulsiveForceAgainst(ParticlesBuilder applier_pb, double forcemod){
		ArrayList<Particle> applier_particles = applier_pb.get();
		for (int p_i = 0; p_i<particles.size();p_i++){
			ParticlePath pp = particles.get(p_i).getPath();
			ForcePath2D fp;
			if (pp instanceof ForcePath2D)
				fp = (ForcePath2D) pp;
			else
				fp = new ForcePath2D();
			fp.setMass(1);
			
			for (int fp_i = 0; fp_i<applier_particles.size(); fp_i ++){
				if (p_i!=fp_i)
					fp.addForce(new RepulsiveForce2D(applier_particles.get(fp_i), forcemod));
			}
			particles.get(p_i).setPath(fp);
		}
		
		return this;	
	}
	public ParticlesBuilder setResistiveForce(double coefficient){
		for (int p_i = 0; p_i<particles.size();p_i++){
			ParticlePath pp = particles.get(p_i).getPath();
			ForcePath2D fp;
			if (pp instanceof ForcePath2D)
				fp = (ForcePath2D) pp;
			else
				fp = new ForcePath2D();
		
			fp.addForce(new ResistiveForce2D(coefficient));
			particles.get(p_i).setPath(fp);
		}
		return this;
	}
	// Requires gridparticles
	public ParticlesBuilder setElasticForceGrid(double coefficient){
		if (!(particles.get(0) instanceof GridParticle)){
			System.out.println("ERROR: requires gridparticles");
			return null;
		}
		
		for (Particle p: particles){
			GridParticle gp = (GridParticle) p;
			ParticlePath pp =p.getPath();
			ForcePath2D fp;
			if (pp instanceof ForcePath2D)
				fp = (ForcePath2D) pp;
			else 
				fp = new ForcePath2D();
			ArrayList<Particle> connections = gp.getConnections();
			for (Particle p_con : connections){
				fp.addForce(new ElasticForce2D(p, p_con, coefficient));
				ParticlePath pp2 = p_con.getPath();
				ForcePath2D fp2;
				if (pp2 instanceof ForcePath2D)
					fp2 = (ForcePath2D)pp2;
				else
					fp2=new ForcePath2D();				
				fp2.addForce( new ElasticForce2D(p_con, p, coefficient));
			}
				
		}
			
		return this;	
	}
	public ParticlesBuilder pushForce(double vx, double vy){
		
		for (int p_i = 0; p_i < particles.size(); p_i++){
			ForcePath2D fp = (ForcePath2D) particles.get(p_i).getPath();
			if (fp == null){
				fp = new ForcePath2D();
				particles.get(p_i).setPath(fp);
			}
			fp.push(vx, vy);
		}
		return this;
	}
	
	public ParticlesBuilder shotgunForce(double vx, double vy, double rand_range){
		for (int p_i = 0; p_i < particles.size(); p_i++){
			ForcePath2D fp = (ForcePath2D) particles.get(p_i).getPath();
			if (fp == null){
				fp = new ForcePath2D();
				particles.get(p_i).setPath(fp);
			}
			fp.push(vx+ rand_range*(0.5-Math.random()), vy + rand_range*(0.5-Math.random()));
		}
		return this;
	}
	
	public ParticlesBuilder setColor(Color color){
		for (Particle p: particles)
			p.setColor(color);
		return this;
	}
	public ParticlesBuilder setRandomColor(){
		for (Particle p : particles){
			float hue = (float)(Math.random());
			float sat = (float)(Math.random()*0.5 + 0.1);
			float lum = 0.8f;
			p.setColor(Color.getHSBColor(hue, sat, lum));
			p.setColor(new Color((int)(Math.random()*0x1000000)));
		}
		return this;
	}
	public ParticlesBuilder applyJitter(double magnitude){
		for (Particle p : particles){
			p.x += magnitude * (Math.random() - 0.5);
			p.x += magnitude * (Math.random() - 0.5);
		}
		return this;
	}
	
	public ParticlesBuilder (ArrayList<Particle> particles){
		this();
		this.particles=particles;
	}
	public ParticlesBuilder(Particle p){
		this();
		particles.add(p);
	}

	public ParticlesBuilder setSize(int size){
		for (Particle p : particles)
			p.setSize(size);
		return this;
	}
	
	public ArrayList<Particle> get(){
		return particles;
	}
	public ParticlesBuilder add(ParticlesBuilder pb){
		particles.addAll(pb.get());
		return this;
	}
	
}
