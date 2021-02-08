import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class ObjectIO {
	public Object readObjectFromFile(String filePath) {
		try {
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			Object collectedObject = objectIn.readObject();
			System.out.println("The Object has been read from the file");
			objectIn.close();

			return collectedObject;

		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			System.out.println("class not fund!");
			return null;
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.out.println("IO exception occurred!");
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unknown exception occurred!");
			return null;
		}

	}

	public void writeObjectToFile(String filePath, Object serObj) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(serObj);
			objectOut.close();
			System.out.println("The Object  was succesfully written to a file");
			
		} catch(NotSerializableException nse){
			nse.printStackTrace();
			System.out.println("Object is not serializable!");
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.out.println("IO exception occurred!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unknown exception occurred!");
		}

	}
	
	public static void writeReportToFile(String filePath, String title,String content) {

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write("****************** Report: " + title + 
					"\n  Unicorn Market   Date:   " + SupermarketSystem.df.format(new Date()) + "\n******************\n\n");
			writer.write(content);
			writer.close(); 
			System.out.println("Report has been generated!");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
