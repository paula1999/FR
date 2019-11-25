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
        String fin_oper = "0";
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		
		// Socket para la conexión TCP
		Socket socketServicio=null;
		
		try {
			// Creamos un socket que se conecte a "hist" y "port":
            System.out.println("Intentando abrir puerto...");
            socketServicio = new Socket(host,port);
            System.out.println("Puerto abierto.");			
            
            System.out.println("Estableciendo stream de datos...");
			                        
            BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
            PrintWriter outPrint = new PrintWriter(socketServicio.getOutputStream(), true);

            System.out.println("Stream establecido.");
                        
            int longitud, num_lineas = 0;
            String menu;
            boolean operacion_acabada, parar = false;
            String fin_lectura = "***********Aqui tiene su pedido***********", respuesta;
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader buferteclado = new BufferedReader (isr);

            while (!parar){
                operacion_acabada = false;

                outPrint.flush();
                System.out.println("Bienvenido a la máquina expendedora, pulsa 1 para comenzar o 0 para salir");
                outPrint.flush();

                buferEnvio = buferteclado.readLine();

                if (buferEnvio.equals(fin_oper)){
                    parar = true;
                    operacion_acabada = true;
                }

                outPrint.flush();
                outPrint.println(buferEnvio);
                outPrint.flush();

                if (parar){
                    buferRecepcion = inReader.readLine();
                    respuesta = new String(buferRecepcion);
                    System.out.println(respuesta);
                }
            
                while(!operacion_acabada){
                    longitud = 0;
                    menu = "";

                    buferRecepcion = inReader.readLine();
                    respuesta = new String (buferRecepcion);
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
                    // Si quiere volver al menú principal
                    // COMPLETAR
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