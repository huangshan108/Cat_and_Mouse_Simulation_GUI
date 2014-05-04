public class Cat {

	private Position myPosition;

	/**
	 * Constructors.
	 */
	public Cat() {
		myPosition = new Position();
	}

	public Cat(Position p) {
		myPosition = p;
	}

	/**
	 * An access function.
	 * 
	 * @return
	 */
	public Position getPosition() {
		return myPosition;
	}
	
	public void setPosition(Position p) {
		myPosition = p;
	}

	/**
	 * Move the cat around the statue: one meter toward the statue if the cat
	 * sees the mouse (or up to the statue if the cat is closer to the statue
	 * than one meter away), or 1.25 meters counterclockwise around the statue
	 * if the cat doesn't see the mouse. Return true if the cat eats the mouse
	 * during the move, false otherwise.
	 * 
	 * @param mousePosition 
	 * @return Return true if the cat eats the mouse during the move, false otherwise.
	 */

	@SuppressWarnings("static-access")
	public boolean move(Position mousePosition) {
		double mouseAng = mousePosition.getMyAngle();
		double catRad = myPosition.getMyRadius();
		double catAng = myPosition.getMyAngle();
		
		/**
		 * The cat sees the mouse if (cat radius) * cos (cat angle ��� mouse angle) is at least 1.0.
		 */
		
		
//		System.out.println(catAng);
//		System.out.println((catRad > 1) & ((catRad * Math.cos(catAng - mouseAng)) >= 1));
		
		if ((catRad > 1) & ((catRad * Math.cos(catAng - mouseAng)) >= 1)) {	//cat can see the mouse. it just move inwardly by 1m.
			myPosition.update(-1, 0);
			return false;
		} else if ((catRad == 1) & (myPosition.isCatching(myPosition, mousePosition))) {	//cat catches the mouse.
			myPosition.update(0, (2 * Math.PI) * (1.25 / (2 * Math.PI * catRad)));
			return true;
		} else {
			myPosition.update(0, (2 * Math.PI) * (1.25 / (2 * Math.PI * catRad)));	//cat can't see the mouse. it just move counterclockwise 1.25m.
			return false;
		}
	}
}
