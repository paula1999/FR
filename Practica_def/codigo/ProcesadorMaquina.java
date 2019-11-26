import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.text.NumberFormat;

public class ProcesadorMaquina {
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private Socket socketServicio;
	// stream de lectura (por aquí se recibe lo que envía el cliente)
	private BufferedReader inReader;
	// stream de escritura (por aquí se envía los datos al cliente)
	private PrintWriter outPrinter;
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
    private Random random;
    // Fin operacion
    private String fin_oper = "0";
    private boolean fin = false;

    String TRY_LOGIN = "try_login";
    String LOGIN_SUCCESS = "login_success";
    String LOGIN_FAIL = "login_fail";
    String EXIT_MENU = "exit_menu";
    String CONT_MENU = "cont_menu";

	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorMaquina (Socket socketServicio) {
		this.socketServicio = socketServicio;
		random = new Random();
	}

	// Aquí es donde se realiza el procesamiento
	void procesa(){
		// Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
		String datosRecibidos = new String();
		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarla:
		String datosEnviar = new String();

		try {
            String respuesta;
            boolean descuento = false, comida, bebida, seleccion = true, seleccion_cafe = true;
            int opcion;
            fin = false;

            // Obtiene los flujos de escritura/lectura
			inReader = new BufferedReader (new InputStreamReader (socketServicio.getInputStream()));
            outPrinter = new PrintWriter (socketServicio.getOutputStream(), true);

            // Comienza el funcionamiento de la maquina
            while (!fin){

                // Recibe la respuesta
                
                datosRecibidos = inReader.readLine();
                respuesta = new String (datosRecibidos);

                if (respuesta.equals(CONT_MENU)){
                    fin = false;
                }
                else if (respuesta.equals(EXIT_MENU)){
                    fin = true;
                }
                else if (respuesta.equals(TRY_LOGIN)){
                    datosRecibidos = inReader.readLine();
                    respuesta = new String (datosRecibidos);
                              
                    BufferedReader file_reader = new BufferedReader(new FileReader("./log_estudiante.txt"));
                    String datos_leidos = new String();
                    String comp = "";
                    boolean encontrado = false;
                    
                    // Leemos el fichero de los DNIs y comparamos
                    while (((datos_leidos = file_reader.readLine()) != null) && !encontrado){
                        comp = new String(datos_leidos);

                        if (comp.equals(respuesta)){
                            encontrado = true;

                            datosEnviar = LOGIN_SUCCESS;
                            outPrinter.println(datosEnviar);
                            outPrinter.flush();
                        }
                    }
                    if (!encontrado){
                        datosEnviar = LOGIN_FAIL;
                        outPrinter.println(datosEnviar);
                        outPrinter.flush();
                    }

                    fin = true; //Quitar cuando haya mas cosas despues del login
                }
            }

		} catch (IOException e) {
			System.err.println("Error al obtener los flujos de entrada/salida.");
		}
	}

    //////////////////////////////////////////////////
    // Funciones
    //////////////////////////////////////////////////

    // Mensaje de bienvenida
    private String comienzo(){
        return "Bienvenido a la máquina expendedora, pulsa 1 para comenzar o 0 para salir";
    }

    // Mensaje para ver si es estudiante o no
    private String estudiante(){
        return "Pulsa 1 si eres estudiante, si no, pulsa otra tecla";
    }

    // Mensaje para introducir el DNI
    private String estudiante2(){
        return "Introduzca su DNI";
    }

    // Mensaje para DNIS reconocidos
    private String DNIaceptado(){
        return "DNI reconocido, se aplicará descuento.";
    }

    // Mensaje para DNIs no reconocidos
    private String DNInoaceptado(){
        return "DNI no reconocido, no se aplicará descuento.";
    }

    // Mensaje para elegir comida o bebida
    private String menu(){
        return "Pulsa 1 si quieres bebida, 0 si quieres comida";
    }

    // Mensaje para el menu de bebida
    private String menu_bebida(){
        return "BEBIDA:\nCafe: 0\nPoca-cola: 1\nAgua: 2\nFRanta naranja: 3";
    }

    // Mensaje para el menu de comida
    private String menu_comida(){
        return "COMIDA:\nFRuta: 0\nKit kot: 1\nGalletas Newton: 2\nSandwich: 3";
    }

    // Mensaje para el menu de los tipos de cafe
    private String tipo_cafe(){
        return "TIPOS DE CAFE:\nSolo/Expresso: 0\nCortado: 1\nLargo: 2\nCon leche: 3\nBombón: 4\nCapuchino: 5";
    }

    // Mensaje para servir el pedido
    private String servir (){
        return "***********Aqui tiene su pedido***********";
    }
    
    private String coste (double precio, boolean descuento){
        NumberFormat money = NumberFormat.getCurrencyInstance(); // Formato de dinero
        double precio_final = precio, porcentaje = (double) 30/100;
        String pedido = new String();

        // Aplicamos el descuento, si lo hay
        if (descuento){
            precio_final = porcentaje*precio;
        }

        pedido = "Precio: " + money.format(precio_final); // Ponemos el precio en formato de dinero

        return pedido;
    }
}
