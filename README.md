# EclipsingBinariesLightCurveGenerator

#Description
A new version from the old program: https://github.com/FunkyNoodles/EclipsingBinariesProgram/releases

The new calculation will use spherical coordinates rather than cartesian coordinates to divide the spheres, 
which is easier and slightly more accurate than "slices and polygons" before. The eclipse algorithms is more efficient this time since it is set in a coordinate system.
Instead of dividing a sphere into slices and slices into polygons, this updated program will divide the hemisphere (seen in two dimensions) to rings and sectors. This is easier because each ring will have the same magnitude of brightness, and outer rings will be dimmer (limb-darkening effect).

When one star is in front of another, the program will determine if each polar sector of the covered star is behind the front star, if so, the brightness value of that specific sector is subtracted from the entire brightness.

This program will also work for elliptical orbits, and hopefully feature perspectives from various angles.

Some goals:

	Work out the light curve for elliptic orbits.
	Work out the light curve from tilted perspective, that is, the centers of the stars do not necessarily cross.
	Update GUI from swing to JavaFX.

#Authors

(Originally coded at COSMOS UCSC 2014)

Original Java Code by Louis Lu (nickasds@hotmail.com)

Debug and Revised by Andrew Zhang (andrewmzhang12345@gmail.com)

Maintained by Louis Lu and Andrew Zhang.
 
#Acknowlegements
Eclipsing Binaries Group:

	Darragh Hettrick
	Rohit Chopra
	Andrew Zhang
	Louis Lu
	
Instructors:

	Professor of Astronomy and Astrophysics, Puragra "Raja" Guhathakurta
	Professor of Computer Engineering Tracy Larrabee
	Meredith Muller
	Shivaram Yellamilli
	Jane Li
	John Wright
	Astronomers of the Lick Observatory

COSMOS UCSC 2014 Cluster 7: Astronomy:

	Sumin You
	Eric Wu
	Emily Villa
	Joseph Engelhart
	Jessica Lee
	Irene Duarte
	Timothy Mitchell Chue
	Gina Condotti
	Tiffany Madruga
	Kristi Richter
	Sasha Ruszczyk
	Anooshree Sengupta
	Hariharan Sezhiyan
	Kirby Choy
	Emily Shiang
	Justin Sidhu
	Alyssandra Valenzuela
	Jake Velez
	Andrew Cardenas

	Raffelina Grano
	Dustin Serrano

#Known Bugs

There are some minor issues in the math, forgot to take into account one dimension in the sliced sphere.
It should be a sliced dodecahedron-like structure.

Browse button does not work for MAC OSX

If you find a bug, or have questions, please 
email andrewmzhang12345@gmail.com or nickasds@hotmail.com

#BUG FIXES

8/6/14: Fixed Graph generating negative percent when run twice

8/6/14: Fixed Graph generating the different graph upon hitting generate twice

8/7/14: Fixed Mathematical Error that caused incorrect slice height generation




