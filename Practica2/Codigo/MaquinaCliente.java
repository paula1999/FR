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
        String fin_oper = "FIN_OPERACION";
		
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
                        
            int longitud;
            String menu;
            boolean operacion_acabada, parar = false;
            String fin_lectura = "***********Aqui tiene su pedido***********";
            int num_lineas = 0;
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader buferteclado = new BufferedReader (isr);

            while (!parar){
                operacion_acabada = false;

                outPrint.flush();
                System.out.println("Bienvenido a la máquina expendedora, pulsa 1 para comenzar");
                outPrint.flush();

                buferEnvio = buferteclado.readLine();

                outPrint.flush();
                outPrint.println(buferEnvio);
                outPrint.flush();
            
                while(!operacion_acabada){
                    longitud = 0;
                    menu = "";

                    buferRecepcion = inReader.readLine();
                    String respuesta = new String (buferRecepcion);
                    num_lineas = Integer.parseInt(respuesta);
            
                    for (int i = 0; i < num_lineas; ++i){
                        buferRecepcion = inReader.readLine();
                        respuesta = new String (buferRecepcion);
                        longitud += buferRecepcion.length();
                        menu = menu.concat(respuesta);
                        menu = menu.concat("\n");
                    }
                    
                    // Si ha acabado el pedido, volver al inicio
                    if (respuesta.equals(fin_lectura)){
                        System.out.println(menu);
                        operacion_acabada = true;
                    }
                    else{
                        System.out.println(menu);
                        System.out.println("Recibidos " + longitud + " bytes: ");
                        System.out.println("Teclee opcion");

                        isr = new InputStreamReader(System.in);
                        buferteclado = new BufferedReader (isr);

                        buferEnvio = buferteclado.readLine();

                        outPrint.flush();
                        outPrint.println(buferEnvio);
                        outPrint.flush();
                    }
                }
            }

			socketServicio.close();
                        
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}