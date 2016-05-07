import java.io.*;
import java.net.*;

public class QuoteClient {
	public static void main( String[] args ){

		if( args.length != 2 ){
			System.err.println( "Usage: java QuoteClient Server_IP Server_PORT" );
			System.exit( 1 );
		}

		try{
			/* Recover parameters */
			InetAddress server_IP = InetAddress.getByName( args[0] );
			int    server_PORT = Integer.parseInt( args[1] );
			System.out.println( "Args successfully readed" );

			/* Creating the datagram socket object */
			DatagramSocket datagramSocket = new DatagramSocket( );

			/* Creating the datagram packet to send */
			String request = new String( "CITATION_PLZ" );
			byte[] buffer  = request.getBytes( "UTF-8" );

			DatagramPacket packet = new DatagramPacket( buffer, buffer.length,  
														server_IP, server_PORT );
			/* Sending the packet through the datagram socket */
			datagramSocket.send( packet );
			System.out.println( "Datagram packet successfully sent through the network" );

			/* Preparing for a server response */
			/* Need to create another datagram packet for receivement */
			/* So, first we need a buffer that can hold the response */
			buffer = new byte[1024];
			packet = new DatagramPacket( buffer, buffer.length );

			/* Waiting for a server response */
			datagramSocket.receive( packet );
			System.out.println( "Datagram packet received" );

			String response = new String( packet.getData() );
			System.out.println("The citation:\n" + response );
		}

		catch( Exception e ){
            System.err.println( "Error:" + e );
            System.exit( 2 );
		}
	}
}