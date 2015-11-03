package com.example.rossch.gv_maps;

import android.content.Context;
import android.graphics.Point;
import java.lang.Math;
import android.net.wifi.WifiManager;
import android.net.wifi.ScanResult;
import android.os.Bundle;


public class trilaterate{
	float[] sigDist = new float[3];
    Point p1, p2, p3, p4, off;


    public trilaterate(Point point1, Point point2, Point point3){

		p1 = point1;
		p2 = point2;
		p3 = point3;
		p4 = new Point();

        off = new Point();
        off.set((0-p1.x),(0-p1.y));
        p1.set((p1.x + off.x), (p1.y + off.y));
        p2.set((p2.x + off.x), (p2.y + off.y));
        p3.set((p3.x + off.x), (p3.y + off.y));
//		off = new Point(0,0) - p1;
//		p1 = p1 + off;
//		p2 = p2 + off;
//		p3 = p3 + off;

		// TODO: Continue when empty constructor is finished
	}
	public trilaterate(){
		Point p1, p2, p3, p4, off;


		p1 = new Point(-1,1);
		p2 = new Point(1,1);
		p3 = new Point(-1,-1);
		p4 = new Point();
		off = new Point();
        off.set((0-p1.x),(0-p1.y));
        p1.set((p1.x + off.x), (p1.y + off.y));
        p2.set((p2.x + off.x), (p2.y + off.y));
        p3.set((p3.x + off.x), (p3.y + off.y));
		//off = new Point(0,0) - p1;
        //p1 = p1 + off;
        //p2 = p2 + off;
        //p3 = p3 + off;

		//p4
	}

	void calculateDistances(float[] rssi, double freqMHz){
		// Free space path loss
		//   ((dB + 27.55-20log10(f))/20)
		// 10
		if(rssi.length != 3){
			System.out.println("Incorrect number of arguments");
		}
		for(int i=0;i<3;i++){
			float a = (float) ((rssi[i] + 27.55 - (20 * Math.log10(freqMHz)))/20);
			sigDist[i] = (float) Math.pow(10, a);
		} 
	}
	
	void calculateP4(){
		p4.set((int)(((Math.pow(sigDist[0], 2) - Math.pow(sigDist[1], 2)) + Math.pow(p2.x, 2)) / (2 * p2.x)),
                (int)((Math.pow(sigDist[0], 2) - Math.pow(sigDist[2], 2) + Math.pow(p3.x, 2) + Math.pow(p3.y, 2)) / ((2 * p3.y) - (p4.x * (p3.x / p3.x)))));
		p4.set((p4.x + off.x),(p4.y + off.y));
	}
	
	
}
