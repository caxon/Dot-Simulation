package main;

import java.util.ArrayList;

public class GridParticle extends Particle {

	ArrayList<Particle> connections;

	double MAX_DISTANCE = 1000;
	
	public GridParticle(double x, double y) {
		super(x, y);
		connections = new ArrayList<>();
	}
	
	public void connect(Particle con_particle){
		connections.add(con_particle);
	}
	
	// Returns only connections that are less than MIN_DISTANCE
	public ArrayList<Particle> getShortConnections(){
		
		ArrayList<Particle> ret_connections = new ArrayList<>();
		for (Particle p : connections)
			if (Math.hypot(x-p.x, y-p.y)<MAX_DISTANCE)
				ret_connections.add(p);
		return ret_connections;
	}

	public ArrayList<Particle> getConnections(){
		return connections;
	}
}
