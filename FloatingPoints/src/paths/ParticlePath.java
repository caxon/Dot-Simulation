package paths;
import main.*;

public abstract class ParticlePath {

	double time;
	double speed;
	
	public abstract void updatePath(Particle particle);
}
