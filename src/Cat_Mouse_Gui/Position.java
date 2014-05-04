package Cat_Mouse_Gui;
public class Position {

	private static final double TWOPI = 2 * Math.PI;
	private double myRadius;
	private double myAngle;

	/**
	 * Represent a position (radius, angle) in polar coordinates. All angles are
	 * in radians. The internal representation of an angle is always at least 0
	 * and less than 2 * PI. Also, the radius is always at least 1.
	 */
	
	/**
	 * Constructors.
	 */
	public Position() {
		myRadius = 0;
		myAngle = 0;
	}

	public Position(Position p) {
		myRadius = p.myRadius;
		myAngle = p.myAngle;
	}

	public Position(double r, double theta) {
		myRadius = r;
		myAngle = theta;
	}

	/**
	 * Return a printable version of the position.
	 */
	public String toString() {
		String outString = String.format("(%.2f,%.2f)", myRadius, getAngle(myAngle));
		return outString;
	}
	
	public double getAngle(double angle) {
		return angle % TWOPI;
	}

	/**
	 * Update the current position according to the given increments.
	 * Preconditions: thetaChange is less than 2 * PI and greater than -2 * PI;
	 * one of rChange and thetaChange is 0.
	 * 
	 * @param rChange a number indicate the change in cat's radius.
	 * 			if rChange is negative,  this means cat moves inwardly.
	 * @param thetaChange
	 */

	public void update(double rChange, double thetaChange) {
		// You fill this in.
		
		//if cat's radius is less than 1m (gets to the statue).
		if ((myRadius + rChange) < 1) {
			myRadius = 1;
		} else {
			myRadius = rChange + myRadius;
		}
		myAngle = (thetaChange + myAngle) % TWOPI;
	}

	/**
	 * getter method for cat.
	 * @return	cat's position's radius.
	 */
	public double getMyRadius() {
		return myRadius;
	}

	/**
	 * getter method for cat.
	 * @return	cat's position's angle.
	 */
	public double getMyAngle() {
		return myAngle;
	}

	/**
	 * The cat catches the mouse when it (the cat) moves past the mouse while at the base of the statue, 
	 * i.e. when the cat radius is 1.0 and the mouse angle lies between the old cat angle and the new cat angle. 
	 * An angle B is between angles A and C in the following circumstances:
	 * cos (B – A) > cos (C – A), and
	 * cos (C – B) > cos (C – A).
	 * @param cat new position of cat.
	 * @param mouse	new position of mouse.
	 * @return whether cat catches the mouse before moving into this new position.
	 */
	public static boolean isCatching(Position cat, Position mouse) {
		
		if (cat.getMyRadius() == 1) {
			double B = mouse.getMyAngle();
			double A = cat.getMyAngle();
			double C = cat.getMyAngle() + 1.25;
			if (Math.cos(B - A) > Math.cos(C - A) || Math.cos(C - B) > Math.cos(C - A)) {
				return true;
			}
		}
		return false;
		
		
//		double catAngleChanged = TWOPI * (1.25 / (TWOPI * cat.getMyRadius()));
//		
//		if ((cat.getMyAngle() < mouse.getMyAngle()) 
//				& ((cat.getMyAngle() + catAngleChanged) > mouse.getMyAngle())) {
//			return true;
//		} else {
//			return false;
//		}
	}
}