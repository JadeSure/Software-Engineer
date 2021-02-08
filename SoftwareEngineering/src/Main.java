
public class Main {
	static ObjectIO objectIO = new ObjectIO();
	
	public static void main(String[] args) {
	
		// initiate system
//		SupermarketSystem system = new SupermarketSystem();

		// Read objects from file
		SupermarketSystem system = (SupermarketSystem)objectIO.readObjectFromFile("files/System_objects");
		
		// Execute application
		system.run();
		
		// write objects to file
		objectIO.writeObjectToFile("files/System_objects", system);
		
		

	}

}
