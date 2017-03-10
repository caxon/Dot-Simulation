import java.awt.geom.Point2D;

public class CirclePath2D extends ParticlePath{

	double center_x;
	double center_y;
	double speed=1;
	double radius;
	double time_0;
	
	// initializes a circular path around point cent_x, cent_y with a velocity of speed
	public CirclePath2D(Particle p, double center_x, double center_y, double speed){
		this.center_x = center_x;
		this.center_y = center_y;
 		
		double dispX= p.getX()-center_x;
		double dispY= p.getY()-center_y;
		
	
		radius = Math.hypot(dispX, dispY);
		time_0 = Math.atan2(dispX, dispY);
		time = time_0;
		
	}
	
	@Override
	void updatePath(Particle p) {
		time+=Math.PI/2/100*speed;
		p.x=Math.sin(time)*radius + center_x;
		p.y=Math.cos(time)*radius + center_y ;
	}
	
	@Override
	public String toString()
	{
		return String.format("Center: (%.1f,%.1f), Radius: %.2f, T_0: %.3f rad", center_x, center_y, radius, time_0);
		
	}

	
	
}
