package io.github.funkynoodles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LightCurveGenerator {

	public static Star star1 = new Star();
	public static Star star2 = new Star();

	// for eclipse

	static double timeIntervel = 1000; // will be customizable
	static double timeIncrement = 0;

	// Progress
	private static double progressPercentage = 0;

	// Variables
	static double star1Rings = 0;
	static double star1Sectors = 0;
	static double star2Rings = 0;
	static double star2Sectors = 0;
	static double systemEccentricity = 0;
	static double separationDistance = 0;

	static int imgWidth = 1280;
	static int imgHeight = 720;

	public static volatile boolean isGenerating = false;
	public static volatile boolean graphCreating = false;
	public static volatile boolean graphCreated = false;
	public static volatile boolean isRunning = false;

	// ArrayLists
	static ArrayList<Double> plotPoints = new ArrayList<Double>();
	static ArrayList<Double> derivative = new ArrayList<Double>();

	// GUI
	static GUI go = new GUI();

	// Orbit
	static double totalMass = 0;
	static double distance = 0;

	// Constants
	private final static double G = 6.671 * Math.pow(10, -11);

	// Indirect Variables
	static File imgDirMain;

	public static void main(String[] args) {
		go.init(args);
	}

	public static void beginGraph() {
		// Reset arraylists
		plotPoints.clear();
		derivative.clear();

		isGenerating = true;
		initOrbit(systemEccentricity, separationDistance);
		progress(); // Longest step
		if (!isRunning) {
			isGenerating = false;
			return;
		}
		generateGraph();
		isGenerating = false;
	}

	public static void progress() {
		timeIncrement = star1.period / timeIntervel;
		double currentAngle = 0;
		while (currentAngle <= 2 * Math.PI && isRunning) {
			// for star1, theta=0 points towards center, so use negative for
			// ellipse in polar coordinates
			star1.currentRadius = star1.semiMajorAxis * (1 - Math.pow(star1.eccentricity, 2)) / (1 - star1.eccentricity * Math.cos(currentAngle));
			star2.currentRadius = star2.semiMajorAxis * (1 - Math.pow(star2.eccentricity, 2)) / (1 + star2.eccentricity * Math.cos(currentAngle));
			star1.yPerspective = star1.currentRadius * Math.cos(currentAngle);
			star2.yPerspective = star2.currentRadius * Math.cos(currentAngle + Math.PI);
			star1.xPerspective = star1.currentRadius * Math.sin(currentAngle);
			star2.xPerspective = star2.currentRadius * Math.sin(currentAngle + Math.PI);
			currentAngle += 2 * Math.PI * star1.semiMajorAxis * star1.semiMinorAxis * timeIncrement / star1.period / Math.pow(star1.currentRadius, 2);
			progressPercentage = currentAngle / 2 / Math.PI;
			// System.out.println(star1.currentRadius +"\t" + star2.currentRadius + "\t" + star1.yPerspective + "\t" + star2.yPerspective);
			//System.out.println(getProgressPercentage());
			if (Math.abs(star1.yPerspective) + Math.abs(star2.yPerspective) < star1.radius + star2.radius) {
				if (star1.xPerspective > star2.xPerspective) {
					plotPoints.add(eclipse(1));
				} else if (star1.xPerspective < star2.xPerspective) {
					plotPoints.add(eclipse(2));
				}
			} else {
				plotPoints.add(star1.starBrightness + star2.starBrightness);
			}
		}
	}

	public static double eclipse(int starInFront) {
		/*
		 * At this time, the back star's center is set as the origin in a
		 * Cartesian coordinate system and the front star's center is exactly
		 * star1 radius + star2 radius away from the back star At this point,
		 * the front star is assumed to start at the x-axis and only follow the
		 * x-axis, this hopefully will be more generalized as the development
		 * goes on
		 */
		/*
		 * the calculation is significantly easier than the previous
		 * "slice and polygon" method, for each sector's position (x,y) if the
		 * distance between (x,y) and front star's center is smaller than front
		 * star's radius, then that specific sector is covered and its
		 * brightness is subtracted from the system brightness.
		 */
		double systemBrightness = star1.starBrightness + star2.starBrightness;
		if (starInFront == 2) {
			// if star 2 is in front
			for (int ringCounter = 0; ringCounter < star1.rings; ringCounter++) {
				for (int sectorCounter = 0; sectorCounter < star1.sectors; sectorCounter++) {
					star1.xSector = ringCounter * star1.dr * Math.cos(sectorCounter * star1.dth);
					star1.ySector = ringCounter * star1.dr * Math.sin(sectorCounter * star1.dth);
					star2.x = Math.abs(star2.yPerspective) + Math.abs(star1.yPerspective);
					if (Math.sqrt(Math.pow(star1.xSector - star2.x, 2)
							+ Math.pow(star1.ySector - star2.y, 2)) < star2.radius) {
						systemBrightness -= star1.component[ringCounter][sectorCounter];
					}
				}
			}
		}
		if (starInFront == 1) {
			// if star 1 is in front
			for (int ringCounter = 0; ringCounter < star2.rings; ringCounter++) {
				for (int sectorCounter = 0; sectorCounter < star2.sectors; sectorCounter++) {
					star2.xSector = ringCounter * star2.dr * Math.cos(sectorCounter * star2.dth);
					star2.ySector = ringCounter * star2.dr * Math.sin(sectorCounter * star2.dth);
					star1.x = Math.abs(star2.yPerspective) + Math.abs(star1.yPerspective);
					if (Math.sqrt(Math.pow(star2.xSector - star1.x, 2)
							+ Math.pow(star2.ySector - star1.y, 2)) < star1.radius) {
						systemBrightness -= star2.component[ringCounter][sectorCounter];
					}
				}
			}
		}
		return systemBrightness;
	}

	public static void initOrbit(double eccentricity, double distanceIn) {
		star1.initStar();
		star2.initStar();
		// Assigning stars' orbital properties
		totalMass = star1.mass + star2.mass;
		distance = distanceIn * 1.495978707 * Math.pow(10, 11); // AU
		star1.apoapsis = distance * star2.mass / (totalMass);
		star2.apoapsis = distance - star1.apoapsis;
		star1.eccentricity = eccentricity;
		star2.eccentricity = eccentricity;
		star1.semiMajorAxis = star1.apoapsis / (1 + star1.eccentricity);
		star2.semiMajorAxis = star2.apoapsis / (1 + star2.eccentricity);
		star1.semiMinorAxis = star1.semiMajorAxis * Math.sqrt(1 - Math.pow(star1.eccentricity, 2));
		star2.semiMinorAxis = star2.semiMajorAxis * Math.sqrt(1 - Math.pow(star2.eccentricity, 2));
		star1.focus = Math.sqrt(Math.pow(star1.semiMajorAxis, 2) - Math.pow(star1.semiMinorAxis, 2));
		star2.focus = Math.sqrt(Math.pow(star2.semiMajorAxis, 2) - Math.pow(star2.semiMinorAxis, 2));
		star1.periapsis = star1.semiMajorAxis * (1 - star1.eccentricity);
		star2.periapsis = star2.semiMajorAxis * (1 - star2.eccentricity);
		star1.period = 2 * Math.PI
				* Math.sqrt((Math.pow(star1.semiMajorAxis + star2.semiMajorAxis, 3) / (G * totalMass)));
		star2.period = star1.period;
	}

	public static void generateGraph() {
		XYSeries series = new XYSeries("XYGrapsh");
		for (int graphCounter = 0; graphCounter < plotPoints.size(); graphCounter++) {
			series.add(graphCounter, plotPoints.get(graphCounter));
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		JFreeChart chart = ChartFactory.createXYLineChart("Luminous Intensity vs. Phase", // Title
				"Phase", // x-axis Label
				"Luminous Intensity", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
		);
		try {
			System.out.println("Creating Graph...");
			graphCreating = true;
			// Create the chart
			ChartUtilities.saveChartAsJPEG(imgDirMain, chart, imgWidth, imgHeight);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error: Check save location");
			return;
		}
		graphCreated = true;
	}

	public static double getProgressPercentage() {
		return progressPercentage;
	}
}
