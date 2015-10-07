import java.awt.Point
import java.util.Math

public class trilaterate{
	static sigDist = new float[3];
	public trilaterate(Point point1, Point point2, Point point3){
		Point p1, p2, p3, p4, off;
		this.p1 = point1;
		this.p2 = point2;
		this.p3 = point3;
		p4 = new Point();
		
		off = new Point(0,0) - p1;
		p1 = p1 + off;
		p2 = p2 + off;
		p3 = p3 + off;

		// TODO: Continue when empty constructor is finished
	}
	public trilaterate(){
		Point p1, p2, p3, p4, off;
		p1 = new Point(-1,1);
		p2 = new Point(1,1);
		p3 = new Point(-1,-1);
		p4 = new Point();
		off = new Point();

		static float[] sigDist = new float[3]

		off = new Point(0,0) - p1;
		p1 = p1 + off;
		p2 = p2 + off;
		p3 = p3 + off;

		p4 
	}
	
	void calculateDistances(float[] rssi, double freqMHz){
		// Free space path loss
		//   ((dB + 27.55-20log10(f))/20)
		// 10
		if(rssi.length != 3){
			System.out.println("Incorrect number of arguments");
		}
		for(int i=0;i<3;i++){
			double a = (rssi[i] + 27.55 - (20 * Math.Log10(freqMHz)))/20;
			sigDist[i] = Math.Exp(10,a);
		} 
	}
	
	void calculateP4(){
		p4.setX((Math.pow(sigDist[0],2)-Math.pow(sigDist[1],2)+Math.pow(p2.getX(),2))/(2 * p2.getX()));
		p4.setY((Math.pow(sigDist[0],2)-Math.pow(sigDist[2],2)+Math.pow(p3.getX(),2) + Math.pow(p3.getY(),2))/((2 * p3.getY())-(p4.getX() * (p3.getX()/p3.getY())));
		p4 = p4 + off;
	}
	
	
}

