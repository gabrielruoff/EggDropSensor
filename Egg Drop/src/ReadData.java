import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class ReadData {

	static InputStream in = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		boolean loop = false;
		
		double input = 0;
		
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
			
			input = ((DataInputStream) in).readDouble();
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		//Print out the input data
		System.out.println(input);
		
		}
		
		
	}

	void connect (String portName) throws Exception {

		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if ( portIdentifier.isCurrentlyOwned() )
		{
		System.out.println("Error: Port is currently in use");
		}
		else
		{
		CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

		if ( commPort instanceof SerialPort )
		{
		SerialPort serialPort = (SerialPort) commPort;
		serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,
		SerialPort.PARITY_NONE);
		in = new DataInputStream(serialPort.getInputStream());
		}
		else
		{
		System.out.println("Error: Only serial ports are handled by this example.");
		}
		}
		}
	
}
