import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;

public class MaquinaCliente {
	public static void main(String[] args) {
		String buferEnvio;
        String buferRecepcion;
        String TRY_LOGIN = "try_login";
        String LOGIN_SUCCESS = "login_success";
        String LOGIN_FAIL = "login_fail";
        String EXIT_MENU = "exit_menu";
        String CONT_MENU = "cont_menu";
        String REQUEST_FOOD = "request_food";
        String REQUEST_DRINK = "request_drink";
        String REQUEST_PRICE;
		// Nombre del host donde se ejecuta el servidor:
		String host = "localhost";
		// Puerto en el que espera el servidor:
		int port = 8989;
		// Socket para la conexión TCP
		Socket socketServicio = null;
		
		try {
			// Creamos un socket que se conecte a "hist" y "port":
            System.out.println("Intentando abrir puerto...");
            socketServicio = new Socket(host,port);
            System.out.println("Puerto abierto.");			
            
            System.out.println("Estableciendo stream de datos...");
			                        
            BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
            PrintWriter outPrint = new PrintWriter(socketServicio.getOutputStream(), true);

            System.out.println("Stream establecido.");

            int longitud, opcion, num_lineas;
            String menu, respuesta, menu_precio;
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader buferteclado = new BufferedReader (isr);
            boolean salir = false, descuento = false, parsable = true;

            while (!salir){
                ///////////////////////////////////////////////
                // COMENZAR                                  //
                ///////////////////////////////////////////////

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

                // Enviamos opción de comenzar o salir
                outPrint.println(buferEnvio);
                outPrint.flush();

                ///////////////////////////////////////////////
                // ESTUDIANTE                                //
                ///////////////////////////////////////////////

                if (!salir){
                    do{
                        System.out.println("Pulse 0 si es estudiante, 1 si no");
                        respuesta = buferteclado.readLine();
                    }while(!respuesta.equals("0") && !respuesta.equals("1"));
                    
                    // Comprobamos que es estudiante
                    if (respuesta.equals("0")){
                        // Leemos DNI
                        System.out.println("Introduzca su dni");

                        respuesta = buferteclado.readLine();
                        buferEnvio = TRY_LOGIN; 

                        // Enviamos DNI para comprobar
                        outPrint.println(buferEnvio);
                        outPrint.flush();

                        buferEnvio = respuesta;

                        outPrint.println(buferEnvio);
                        outPrint.flush();

                        // Leemos la comprobación
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

                    ///////////////////////////////////////////////
                    // ELEGIMOS MENU COMIDA O BEBIDA             //
                    ///////////////////////////////////////////////

                    do{
                        System.out.println("Pulsa 1 si quieres bebida, 0 si quieres comida");
        
                        // Elige opcion mediante el teclado
                        buferEnvio = buferteclado.readLine();
                    } while(!(buferEnvio.equals("1") || buferEnvio.equals("0")));
        
                    menu_precio = buferEnvio;
        
                    // Si quiere bebida
                    if (buferEnvio.equals("1")){
                        outPrint.flush();
                        outPrint.println(REQUEST_DRINK);
                        outPrint.flush();
                    }
                    // Si quiere comida
                    else{
                        outPrint.flush();
                        outPrint.println(REQUEST_FOOD);
                        outPrint.flush();
                    }
        
                    // Recibimos el menu correspondiente
                    buferRecepcion = inReader.readLine();
                    respuesta = new String (buferRecepcion);
                    longitud = 0;
                    menu = "";

                    num_lineas = Integer.parseInt(respuesta);
                
                    for (int i = 0; i < num_lineas; ++i){
                        buferRecepcion = inReader.readLine();
                        respuesta = new String (buferRecepcion);
                        longitud += buferRecepcion.length();
                        menu = menu.concat(respuesta);
                        menu = menu.concat("\n");
                    }

                    System.out.println(menu);
        
                    ///////////////////////////////////////////////
                    // ELEGIMOS TIPO ESPECÍFICO DEL MENU         //
                    ///////////////////////////////////////////////
        
                    parsable = true;
                    opcion = -1;
        
                    do{
                        // Elige opción
                        buferEnvio = buferteclado.readLine();

                        try{
                            Integer.parseInt(buferEnvio);
                            parsable = true;
                        }catch(NumberFormatException e){
                            parsable = false;
                        }

                        if (parsable)
                            opcion = Integer.parseInt(buferEnvio);
                        else{
                            parsable =  false;
                            //System.out.println("Error, vuelve a elegir la opcion");
                        }
                        if (!parsable || (opcion > 3) || (opcion < 0) || (buferEnvio.length() > 1)) {
                            parsable =  false;
                            System.out.println("Error, vuelve a elegir la opcion");
                        }
                        
        
                    } while (!parsable || (opcion > 3) || (opcion < 0) || (buferEnvio.length() > 1));
        
                    // Concatenamos con el menu de antes
                    menu_precio += buferEnvio;
                                
                    // Concatenamos con el descuento
                    if (descuento)
                        menu_precio += "1";
                    else
                        menu_precio += "0";

                    REQUEST_PRICE = "request_price";
                    
                    // Enviamos el pedido
                    outPrint.flush();
                    outPrint.println(REQUEST_PRICE);
                    outPrint.flush();
                    outPrint.println(menu_precio);
                    outPrint.flush();

                    // Recibimos el precio correspondiente
                    buferRecepcion = inReader.readLine();
                    respuesta = new String (buferRecepcion);

                    System.out.println(respuesta);
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