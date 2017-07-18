/*Egg Drop Client Side Code by Gabriel Ruoff
 * This program connects to the bluetooth egg and recieves data from it.
 * The data is then ouputted to a text file that can be put into a spreadsheet/graphing software
 * I will soon be adding a GUI utility that allows the user to graph the acceration/time of the egg and
 * compare it to the maximun withstandable acceleration of the egg.
 */

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class ReadData {

	static InputStream in = null;
	
	static DataInputStream inputStream;
	
	static ArrayList<Double> toFile;
	
	//Data array
	static double[] data = new double[100];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		boolean loop = false;
		
		double input = 0.0;
		
		//Connect to arduino
		try {
			//Device
			
			System.out.println("connecting...");
			
		(new ReadData()).connect("/dev/cu.HC-06-DevB");
		
		System.out.println("connected");
		
		}
		
		catch ( Exception e ) {
			
		e.printStackTrace();
		
		System.exit(0);
		
		}
		
		while(!loop) {
		
		//Read inputStream data
		try {
			
			input = inputStream.readDouble();
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		//Save the data to an array
		while(dataIsAvailable(inputStream)) {
			
			int i = 0;
			
			try {
				
				//Leaving this array because it's easier for GUI
				data[i] = inputStream.readDouble();
				
				toFile.add(data[i]);
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
			i++;
			
		}
		
		//Print out the input data
		System.out.println(input);
		
		}
		
		//Write acceleration values to text file

		try {
			
			FileWriter writer = new FileWriter("outputData.txt");
			
			writer.write("Acceleration Values:" + System.getProperty("line.separator"));
			
			for(int i = 0; i < toFile.size(); i++) {
				
				System.out.println(toFile.get(i));
				
				writer.write(toFile.get(i) + System.getProperty("line.separator"));
				
			}
			
			writer.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

	void connect (String portName) throws Exception {

		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		   if ( portIdentifier.isCurrentlyOwned() ) {
			
			   System.out.println("Error: Port is currently in use");
		
		   }
		
		   else {
			
			  CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

		   if ( commPort instanceof SerialPort ) {
			
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
		
				DataInputStream inputStream = new DataInputStream(serialPort.getInputStream());
		
			}
		
			else {
			
				System.out.println("Error: Only serial ports are handled by this example.");
			
			}
		}
	}
	
	//see if data is available
	public static boolean dataIsAvailable(DataInputStream stream) {
		
		try {
			
			if(stream.readDouble() != 0) {
				
				return true;
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	
}