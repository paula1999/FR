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

            // Obtiene los flujos de escritura/lectura
			inReader = new BufferedReader (new InputStreamReader (socketServicio.getInputStream()));
            outPrinter = new PrintWriter (socketServicio.getOutputStream(), true);

            // Comienza el funcionamiento de la maquina
            while (!fin){
                // Inicializamos las variables
                descuento = false;
                comida = false;
                bebida = false;

                // Recibe la respuesta
                datosRecibidos = inReader.readLine();
                respuesta = new String (datosRecibidos);

                // Mientras no quiera comenzar
                while (!respuesta.equals("1") && !respuesta.equals(fin_oper)){ 
                    datosEnviar = comienzo();

                    outPrinter.flush();
                    outPrinter.println("1");
                    outPrinter.flush();
                    outPrinter.println(datosEnviar);
                    outPrinter.flush();

                    // Recibe la respuesta:
                    datosRecibidos = inReader.readLine();
                    respuesta = new String (datosRecibidos);
                }
                
                if (respuesta.equals(fin_oper)){
                    fin = true;
                    datosEnviar = "0";
                    outPrinter.flush();
                    outPrinter.println(datosEnviar);
                    outPrinter.flush();
                }

                if (!fin){

                    // Mensaje para ver si es estudiante o no
                    datosEnviar = estudiante();

                    outPrinter.flush();
                    outPrinter.println("1");
                    outPrinter.flush();
                    outPrinter.println(datosEnviar);
                    outPrinter.flush();

                    // Recibe la respuesta:
                    datosRecibidos = inReader.readLine();
                    respuesta = new String (datosRecibidos);

                    // Si es estudiante
                    if (respuesta.equals("1")){ 
                        // Enviamos mensaje para comprobar DNI
                        datosEnviar = estudiante2();

                        outPrinter.flush();
                        outPrinter.println("1");
                        outPrinter.flush();
                        outPrinter.println(datosEnviar);
                        outPrinter.flush();

                        // Recibimos la respuesta
                        datosRecibidos = inReader.readLine();
                        respuesta = new String (datosRecibidos);

                        // Inicializamos variables para leer en fichero
                        BufferedReader file_reader = new BufferedReader(new FileReader("./log_estudiante.txt"));
                        String datos_leidos = new String();
                        String comp = "";
                        boolean encontrado = false;
                        
                        // Leemos el fichero de los DNIs y comparamos
                        while (((datos_leidos = file_reader.readLine()) != null) && !encontrado){
                            comp = new String(datos_leidos);

                            if (comp.equals(respuesta)){
                                descuento = true;
                                encontrado = true;

                                datosEnviar = DNIaceptado();

                                outPrinter.flush();
                                outPrinter.println("2");
                                outPrinter.flush();
                                outPrinter.println(datosEnviar);
                                outPrinter.flush();
                            }
                        }

                        // Si no se ha encontrado el DNI en el fichero
                        if (!descuento){
                            datosEnviar = DNInoaceptado();

                            outPrinter.flush();
                            outPrinter.println("2");
                            outPrinter.flush();
                            outPrinter.println(datosEnviar);
                            outPrinter.flush();
                        }
                    } else{
                        outPrinter.flush();
                        outPrinter.println("1");
                        outPrinter.flush();
                    }
                    
                    // Mensaje de menu
                    datosEnviar = menu();

                    outPrinter.flush();
                    outPrinter.println(datosEnviar);
                    outPrinter.flush();

                    // Recibe la respuesta:
                    datosRecibidos = inReader.readLine();
                    respuesta = new String (datosRecibidos);

                    // Ni comida ni bebida, número erróneo
                    while (!respuesta.equals("0") && !respuesta.equals("1")){ 
                        // Volvemos a enviar el mensaje
                        datosEnviar = menu();

                        outPrinter.flush();
                        outPrinter.println("1");
                        outPrinter.flush();
                        outPrinter.println(datosEnviar);
                        outPrinter.flush();

                        // Recibimos respuesta
                        datosRecibidos = inReader.readLine();
                        respuesta = new String (datosRecibidos);
                    }

                    if (respuesta.equals("1")) // Bebida
                        bebida = true;
                    else if (respuesta.equals("0")) // Comida
                        comida = true;

                    // Si ha elegido bebida
                    if (bebida){
                        // Mensaje de menu bebida
                        datosEnviar = menu_bebida();

                        outPrinter.flush();
                        outPrinter.println("5");
                        outPrinter.flush();
                        outPrinter.println(datosEnviar);
                        outPrinter.flush();

                        // Recibe la respuesta:
                        datosRecibidos = inReader.readLine();
                        respuesta = new String (datosRecibidos);

                        seleccion = true;

                        do{
                            switch (respuesta){
                                case "0": // Cafe
                                    seleccion = true;

                                    // Eviamos menu de los distintos tipos de cafe
                                    datosEnviar = tipo_cafe();

                                    outPrinter.flush();
                                    outPrinter.println("7");
                                    outPrinter.flush();
                                    outPrinter.println(datosEnviar);
                                    outPrinter.flush();

                                    // Recibe la respuesta:
                                    datosRecibidos = inReader.readLine();
                                    respuesta = new String (datosRecibidos);
                                    
                                    do{
                                        switch (respuesta){
                                            case "0": 
                                                seleccion_cafe = true;

                                                break;

                                            case "1":
                                                seleccion_cafe = true;

                                                break;
                                            
                                            case "2":
                                                seleccion_cafe = true;

                                                break;

                                            case "3":
                                                seleccion_cafe = true;

                                                break;

                                            case "4":
                                                seleccion_cafe = true;

                                                break;
                                            
                                            case "5":
                                                seleccion_cafe = true;

                                                break;
                                            
                                            default:
                                                seleccion_cafe = false;
                                                datosEnviar = tipo_cafe();

                                                outPrinter.flush();
                                                outPrinter.println("7");
                                                outPrinter.flush();
                                                outPrinter.println(datosEnviar);
                                                outPrinter.flush();

                                                // Recibe la respuesta:
                                                datosRecibidos = inReader.readLine();
                                                respuesta = new String (datosRecibidos);

                                        }
                                    }while(!seleccion_cafe);

                                    // Calculamos el precio
                                    datosEnviar = coste(1, descuento);

                                    break;
                                case "1": // Poca-cola
                                    seleccion = true;

                                    // Calculamos el precio
                                    datosEnviar = coste(2, descuento);

                                    break;
                                case "2": // Agua
                                    seleccion = true;

                                    // Calculamos el precio
                                    datosEnviar = coste(0.5, descuento);

                                    break;
                                case "3": // FRanta naranja
                                    seleccion = true;

                                    // Calculamos el precio
                                    datosEnviar = coste(1.75, descuento);

                                    break;
                                default: // Opción errónea
                                    seleccion = false;

                                    // Mensaje de menu bebida
                                    datosEnviar = menu_bebida();

                                    outPrinter.flush();
                                    outPrinter.println("5");
                                    outPrinter.flush();
                                    outPrinter.println(datosEnviar);
                                    outPrinter.flush();

                                    // Recibe la respuesta:
                                    datosRecibidos = inReader.readLine();
                                    respuesta = new String (datosRecibidos);
                            }
                        }while(!seleccion);

                        // Enviamos precio
                        outPrinter.flush();
                        outPrinter.println("2");
                        outPrinter.flush();
                        outPrinter.println(datosEnviar);
                        outPrinter.flush();

                        datosEnviar = servir();     

                        // Enviamos la finalizacion del pedido
                        outPrinter.flush();
                        outPrinter.println(datosEnviar);
                        outPrinter.flush();
                    }
                    else if (comida){
                        // Mensaje de menu comida
                        datosEnviar = menu_comida();

                        outPrinter.flush();
                        outPrinter.println("5");
                        outPrinter.flush();
                        outPrinter.println(datosEnviar);
                        outPrinter.flush();

                        // Recibe la respuesta:
                        datosRecibidos = inReader.readLine();
                        respuesta = new String (datosRecibidos);

                        seleccion = true;

                        do{
                            switch (respuesta){
                                case "0": // FRuta
                                    seleccion = true;

                                    // Calculamos el precio
                                    datosEnviar = coste(0.20, descuento);

                                    break;
                                case "1": // Kit-kot
                                    seleccion = true;

                                    // Calculamos el precio
                                    datosEnviar = coste(1.25, descuento);

                                    break;
                                case "2": // Galletas Newton 
                                    seleccion = true;

                                    // Calculamos el precio
                                    datosEnviar = coste(2.25, descuento);

                                    break;
                                case "3": // Sandwich
                                    seleccion = true;

                                    // Calculamos el precio
                                    datosEnviar = coste(1.5, descuento);

                                    break;
                                default: // Opción errónea
                                    seleccion = false;
                                    // Mensaje de menu comida
                                    datosEnviar = menu_comida();

                                    outPrinter.flush();
                                    outPrinter.println("5");
                                    outPrinter.flush();
                                    outPrinter.println(datosEnviar);
                                    outPrinter.flush();

                                    // Recibe la respuesta:
                                    datosRecibidos = inReader.readLine();
                                    respuesta = new String (datosRecibidos);
                            }
                        }while(!seleccion); // Mientras elija la opción errónea

                        // Enviamos coste del pedido
                        outPrinter.flush();
                        outPrinter.println("2");
                        outPrinter.flush();
                        outPrinter.println(datosEnviar);
                        outPrinter.flush();

                        // Enviamos finalizacion del pedido
                        datosEnviar = servir();     
                        
                        outPrinter.flush();
                        outPrinter.println(datosEnviar);
                        outPrinter.flush();
                    }
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
