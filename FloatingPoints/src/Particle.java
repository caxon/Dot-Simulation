import java.awt.Color;

public class Particle {

	double x;
	double y;
	double z;
	
	double dx;
	double dy;
	double dz;
	
	ParticlePath path;
	Color color;
	int size = 3;
	
	
	double getX(){
		return x;
	}
	double getY(){
		return y;
	}
	double getZ(){
		return z;
	}
	
	Particle(double x, double y, double z){
		this.x=x; this.y=y; this.z=z;
	}
	
	Particle(double x, double y){
		this.x=x;this.y=y;
	}
	public void setPath(ParticlePath path){
		this.path = path;
	}
	public void setColor(int r, int g, int b){
		this.color = new Color(r, g, b);
		System.out.println(this.color);
	}
	public void setSize(int size){
		this.size=size;
	}
	
	public void move(){
		if (path!=null)
			path.updatePath(this);
	}
	
	@Override
	public String toString(){
		return String.format("P: (%.1f, %.1f)", x, y) + ((path!=null) ? "\n  " +path : "");
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
