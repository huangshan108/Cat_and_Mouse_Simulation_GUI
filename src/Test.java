public class Test {

	public static Mouse mouse;
	public static Cat cat;
	public static Position position;
	public static CatMouseSimulation run;

	public Test() {
		//testing create mouse object
		mouse = new Mouse();
		//testing create cat object
		cat = new Cat();
		//testing create postion object
		position = new Position();
		//testing run the program
		run = new CatMouseSimulation();
	}

	public static void main(String[] args) {
		Test test = new Test();
		//testing Cat.move()
		boolean testMove = cat.move(mouse.getPosition());
		System.out.println("should be false: " + testMove);
		if (testMove == false) {
			System.out.println("testMove passed!\n");
		}
		
		//testing Position.update(), special case when cat goes "into" the status
		position.update(-2.5, 0);
		double testUpdate = position.getMyRadius();
		System.out.println("should be 1.0: " + testUpdate);
		if (testUpdate == 1) {
			System.out.println("testUpdate passed!\n");
		}
		
		//testing Position.isCatching()
		boolean testIsCatching = position.isCatching(cat.getPosition(), mouse.getPosition());
		System.out.println("should be false: " + testIsCatching);
		if (testIsCatching == false) {
			System.out.println("testIsCatching passed!\n");
		}
	}

}