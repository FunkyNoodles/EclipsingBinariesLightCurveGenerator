package eblcg;
//polar coordinates this time (r, theta)
public class Star {
	//some properties
	int rings = 0; //number of rings to divide into, will be initialized
	double dr = 0;
	int sectors = 0; //number of sectors to divide into, will be initialized
	double dth = 0; //d-theta
	double radius = 0;
	double temperature = 0;
	double starBrightness = 0; //the hemisphere brightness will be obtained from Stefan-Boltzmann Law
	double area = 0;
	
	double component[][];
	
	double sumTemp = 0; //This is only for testing
	
	//Interface (percentage output)
	long percentCounter = 0;
	long totalCounter = 0;
	double percentage = 0;
	
	public Star(int rings, int sectors, int radius, int temperature){
		this.rings = rings;
		this.sectors = sectors;
		this.radius = radius;
		this.temperature = temperature;
		
		totalCounter = 2*rings*sectors;
		System.out.println(totalCounter);
		
		initStar();
	}
	
	public void initStar(){
		component = new double[rings][sectors];
		dr = radius/rings;
		dth = 2*Math.PI/sectors;
		//System.out.println(dth + "\t" + dr);
		area = radius*radius*Math.PI;
		//Stefan-Boltzmann law and dived by two because only seeing half of the star
		starBrightness = Math.pow(temperature, 4)*4*Math.PI*radius*radius*Math.pow(5.670373, -8)/2; 
		double ringBrightness = 0;
		//Because the ring gets larger as radius increases, the brightness assigned to the rings need to be proportional
		for(int initRingCounter=0; initRingCounter<rings; initRingCounter++){
			ringBrightness = starBrightness*(Math.pow(dr*(initRingCounter+1),2)*Math.PI - Math.pow(dr*(initRingCounter),2)*Math.PI)/area/sectors;
			for(int initSectorCounter=0; initSectorCounter<sectors; initSectorCounter++){
				component[initRingCounter][initSectorCounter] = ringBrightness;
				percentCounter++;
				percentage = (double)percentCounter/totalCounter;
			}
		}
		
		//Now modify the initialized array as gradient vector needs to be taken account
		//This is easier because every ring has the same gradient of brightness!
		//Math is based on spherical coordinates theta = arctan(y/Ring radius)
		//theta is angle to x from the tip of each ring
		//positive y is front
		double theta = 0;
		double y = 0;
		double rRing = 0;
		
		for(int modRingCounter=0; modRingCounter<rings; modRingCounter++){
			rRing = dr*(modRingCounter+1);
			y = Math.sqrt(radius*radius-rRing*rRing);
			theta = Math.abs(Math.atan(y/rRing));
			for(int modSectorCounter=0; modSectorCounter<sectors; modSectorCounter++){
				component[modRingCounter][modSectorCounter] *= Math.sin(theta);
				sumTemp += component[modRingCounter][modSectorCounter];
				percentCounter++;
				percentage = (double)percentCounter/totalCounter;
			}
		}
		System.out.println(starBrightness);
		System.out.println(sumTemp);
	}
}
