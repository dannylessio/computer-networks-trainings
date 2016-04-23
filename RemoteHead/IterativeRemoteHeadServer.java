import java.io.*;
import java.net.*;

public class IterativeRemoteHeadServer {
    static final int LINES = 5;

    public static void main( String args[] ) {

        int port;

        if ( args.length != 1 ) {
            System.err.println( "Uso: java IterativeRemoteHeadServer porta" );
            System.exit( 1 );
        }

        try {
            port = Integer.parseInt( args[0] );
            ServerSocket server_socket = new ServerSocket( port );
            System.out.println("Waiting for connection on port " + port );

            while( true ) {
                /* Remember that the accept method is blocking */
                Socket client_socket = server_socket.accept();
                System.out.println( "Request received." );

                /* Handle UTF-8 stream instead of byte stream */
                InputStream input_byte_stream = client_socket.getInputStream();
                InputStreamReader input_char_stream = new InputStreamReader( input_byte_stream, "UTF-8" );
                BufferedReader client_stream_reader = new BufferedReader( input_char_stream );

                OutputStream output_byte_stream = client_socket.getOutputStream();
                OutputStreamWriter output_char_stream = new OutputStreamWriter( output_byte_stream, "UTF-8" );
                BufferedWriter client_stream_writer = new BufferedWriter( output_char_stream );

                /* Reading the file name from the client_stream_reader */
                String file_to_read = client_stream_reader.readLine();

                /* Read the file */
                File file = new File( file_to_read );
                if( file.exists() ) {
            	      System.out.println("The file exsists");
                    BufferedReader file_stream_reader = new BufferedReader( new FileReader( file ) );

                    for( int i = 0; i < LINES; ++i ) {
                        client_stream_writer.write( file_stream_reader.readLine() + "\n" );
            			      client_stream_writer.flush();
                    }
                }
                /* Close the comunication */
                client_socket.close();
            } // while
        }
        catch( IOException e ) {
          System.err.println( e );
          System.exit( 2 );
        }
    }
}
