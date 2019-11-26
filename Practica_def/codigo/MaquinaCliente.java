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
        String TRY_LOGIN = "try_login";
        String LOGIN_SUCCESS = "login_success";
        String LOGIN_FAIL = "login_fail";
        String EXIT_MENU = "exit_menu";
        String CONT_MENU = "cont_menu";
		
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

            String menu;
            String respuesta;
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader buferteclado = new BufferedReader (isr);
            boolean salir = false;
            boolean descuento = false;

            do{
                System.out.println("Bienvenido a la máquina expendedora, pulsa 1 para comenzar o 0 para salir");
                respuesta = buferteclado.readLine();
            } while(!respuesta.equals("0") && !respuesta.equals("1"));

            if (respuesta.equals("0")){
                buferEnvio = EXIT_MENU;
                salir = true;
            }
            else if (respuesta.equals("1")){
                buferEnvio = CONT_MENU;
                salir = false;
            }
            else{
                buferEnvio = "";
            }

            outPrint.println(buferEnvio);
            outPrint.flush();
            if (!salir){
                do{
                    System.out.println("Pulse 0 si es estudiante, 1 si no");
                    respuesta = buferteclado.readLine();
                }while(!respuesta.equals("0") && !respuesta.equals("1"));
                
                if (respuesta.equals("0")){
                    System.out.println("Introduzca su dni");
                    respuesta = buferteclado.readLine();
                    buferEnvio = TRY_LOGIN; 
                    outPrint.println(buferEnvio);
                    outPrint.flush();
                    buferEnvio = respuesta;
                    outPrint.println(buferEnvio);
                    outPrint.flush();

                    buferRecepcion = inReader.readLine();

                    if (buferRecepcion.equals(LOGIN_SUCCESS)){
                        System.out.println("DNI reconocido, se aplicará descuento");
                        descuento = true;
                    }
                    else if (buferRecepcion.equals(LOGIN_FAIL)){
                        System.out.println("DNI no reconocido, no se aplicará descuento");
                        descuento = false;
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