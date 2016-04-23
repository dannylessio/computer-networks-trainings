import java.io.*;
import java.net.*;

public class RemoteHeadClient {
    public static void main( String[] args ) {

        if (args.length != 3) {
            System.err.println("Usage: java RemoteHeadClient server_IP server_PORT file_name");
            System.exit(1);
        }

        try
        {
            char response;
            String server_IP = args[0];
            int server_PORT = Integer.parseInt( args[1] );
            String file_name = args[2];

            Socket client_socket = new Socket( server_IP, server_PORT );
	          System.out.println( "Socket successfully started." );

            /* Handle UTF-8 stream instead of byte stream */
            InputStream input_byte_stream = client_socket.getInputStream();
            InputStreamReader input_char_stream = new InputStreamReader( input_byte_stream, "UTF-8" );
            BufferedReader input_stream = new BufferedReader( input_char_stream );

            OutputStream output_byte_stream = client_socket.getOutputStream();
            OutputStreamWriter output_char_stream = new OutputStreamWriter( output_byte_stream, "UTF-8" );
            BufferedWriter output_stream = new BufferedWriter( output_char_stream );

            /* Sending file_name to the server */
      	    System.out.println( "Requesting " + file_name + " from server..." );
            output_stream.write( file_name + "\n" );
            output_stream.flush( );

            /* Reading results from server */
            String line;
      	    System.out.println( "Response :" );

            line = input_stream.readLine( );
            while ( line != null ){
                System.out.println( line );
                line = input_stream.readLine( );
            }
            client_socket.close( );
        }
        catch( IOException e ){
            System.err.println( "Errore:" + e );
            System.exit( 2 );
        }
    }
}
