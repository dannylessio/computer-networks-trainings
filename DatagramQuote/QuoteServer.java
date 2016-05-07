import java.io.*;
import java.net.*;

public class QuoteServer {
	public static void main( String args[] ){

		if( args.length != 1 ){
			System.err.println( "Usage: java QuoteServer PORT" );
			System.exit( 1 );
		}

		try {
			/* get the parameters */
			int PORT = Integer.parseInt( args[0] );

			/* Creating the quotations array */
			String[] quotations = { "Chea vaca de chea to santoea",
							  	  "Il budino ha il caramello in bocca",
							  	  "Meglio un pigiama rotto oggi che una camicia domani",
							  	  "Failing to plan is planning to fail",
							  	  "Always made thank you notes",
							  	  "Life is a buisquit but if it rains it loosens"
								};

			/* Creating the datagram socket */
			DatagramSocket datagramSocket = new DatagramSocket( PORT );
			/* Prepare a buffer before starting receiving data */
			byte[] buffer = new byte[1024];
			/* Create the datagram packet */
			DatagramPacket packet = new DatagramPacket( buffer, buffer.length );

			int currentQuotation = 0;
			while( true ){
				/* First receive a packet from a client */
				datagramSocket.receive( packet );
				System.out.println("Received a datagram packet from a client");

				/* Store client IP and PORT */
				InetAddress client_IP   = packet.getAddress();
				int         client_PORT = packet.getPort();

				/* Parse the request */
				String request = new String( packet.getData() );
				System.out.println(request);

				/* deleting blank spaces */
				request = request.trim();
				
				if( request.equals("CITATION_PLZ") ){
					String response  = quotations[ currentQuotation ];
					buffer           = response.getBytes( "UTF-8" );

					/* create a datagram packet for the response */
					packet = new DatagramPacket( buffer, buffer.length, client_IP, client_PORT );

					/* send the response to the client */
					datagramSocket.send( packet );
					System.out.println("Quotation successfully sent to the network.");

					/* update the circular counter */
					currentQuotation = ( currentQuotation + 1 ) % quotations.length;
				}
				else {
					System.out.println( "Received a non undestanded request." );
				}


				/* must recover overwritten objects for the next request */
				buffer = new byte[1024];
				packet = new DatagramPacket( buffer, buffer.length );
			} /* end while( true )*/

		}

		catch( Exception e ){
            System.err.println( "Error:" + e );
            System.exit( 2 );
		}
	}
}