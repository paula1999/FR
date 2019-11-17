import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MaquinaCliente {

	public static void main(String[] args) {
		
		String buferEnvio;
		String buferRecepcion;
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		
		// Socket para la conexión TCP
		Socket socketServicio=null;
		
		try {
			// Creamos un socket que se conecte a "hist" y "port":
			//////////////////////////////////////////////////////
            // socketServicio= ... (Completar)
            System.out.println("Intentando abrir puerto...");
            socketServicio = new Socket(host,port);
            System.out.println("Puerto abierto.");
			//////////////////////////////////////////////////////			
            
            System.out.println("Estableciendo stream de datos...");
			                        
            BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
            PrintWriter outPrint = new PrintWriter(socketServicio.getOutputStream(), true);

            System.out.println("Stream establecido.");
			// Si queremos enviar una cadena de caracteres por un OutputStream, hay que pasarla primero
            // a un array de bytes:
            
			//buferEnvio= new String("Al monte del volcan debes ir sin demora");
		
			//System.out.println("Enviando datos...");
                        
            //            outPrint.println(buferEnvio);
                        
            //            System.out.println("Datos enviados.");
                        
			System.out.println("Mostrando menu");
            
            int longitud;
            String menu;
            
            //while(true){
                longitud = 0;
                menu = "";
                while ((buferRecepcion = inReader.readLine()) != null){
                    longitud += buferRecepcion.length();
                    menu = menu.concat(buferRecepcion);
                }
                
                System.out.println(menu);
                System.out.println("Recibidos " + longitud + " bytes: ");
                System.out.println("Teclee opcion");

                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader buferteclado = new BufferedReader (isr);

                buferEnvio = buferteclado.readLine();

                outPrint.println(buferEnvio);
           //}
		
			socketServicio.close();
                        
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}