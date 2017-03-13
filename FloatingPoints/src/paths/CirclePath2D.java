package paths;
import java.awt.geom.Point2D;
import main.*;

import main.Particle;

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
		this.speed = speed;
 		
		double dispX= center_x-p.getX();
		double dispY= center_y-p.getY();
		
	
		radius = Math.hypot(dispX, dispY);
		time_0 = Math.atan2(dispY, dispX);
		time = time_0;
		
	}
	
	@Override
	public
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
