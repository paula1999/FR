import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

public class ProcesadorMaquina {
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private Socket socketServicio;
	// stream de lectura (por aquí se recibe lo que envía el cliente)
	private BufferedReader inReader;
	// stream de escritura (por aquí se envía los datos al cliente)
	private PrintWriter outPrinter;
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;

	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorMaquina (Socket socketServicio) {
		this.socketServicio = socketServicio;
		random = new Random();
	}

	// Aquí es donde se realiza el procesamiento realmente:
	void procesa(){
		// Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
		String datosRecibidos = new String();
		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
		String datosEnviar = new String();

		try {
            String respuesta;
            boolean descuento = false, comida = false, bebida = false;
            int opcion;

            // Obtiene los flujos de escritura/lectura
			inReader = new BufferedReader (new InputStreamReader (socketServicio.getInputStream()));
            outPrinter = new PrintWriter (socketServicio.getOutputStream(), true);

			// Recibe la respuesta:
            datosRecibidos = inReader.readLine();
            respuesta = new String (datosRecibidos);
            opcion = Integer.parseInt(respuesta);

            while (opcion != 1){
                System.out.println("HE ENTRAO");
                datosEnviar = comienzo();

                outPrinter.flush();
                outPrinter.println(datosEnviar);
                outPrinter.flush();

                // Recibe la respuesta:
                datosRecibidos = inReader.readLine();
                respuesta = new String (datosRecibidos);
            }

            // Mensaje de descuento
            datosEnviar = estudiante();

            outPrinter.flush();
            outPrinter.println(datosEnviar);
            outPrinter.flush();

			// Recibe la respuesta:
            datosRecibidos = inReader.readLine();
            respuesta = new String (datosRecibidos);
            opcion = Integer.parseInt(respuesta);

            if (opcion == 1)
                descuento = true;
            
            // Mensaje de menu
            datosEnviar = menu();

            outPrinter.flush();
            outPrinter.println(datosEnviar);
            outPrinter.flush();

			// Recibe la respuesta:
            datosRecibidos = inReader.readLine();
            respuesta = new String (datosRecibidos);
            opcion = Integer.parseInt(respuesta);

            while (opcion != 0 && opcion != 1){
                datosRecibidos = inReader.readLine();
                respuesta = new String (datosRecibidos);
            }

            if (opcion == 1)
                bebida = true;
            else if (opcion == 0)
                comida = true;

            if (bebida){
                // Mensaje de menu bebida
                datosEnviar = menu_bebida();

                outPrinter.flush();
                outPrinter.println(datosEnviar);
                outPrinter.flush();

                // Recibe la respuesta:
                datosRecibidos = inReader.readLine();
                respuesta = new String (datosRecibidos);

                switch(respuesta){
                    case "0":
                        datosEnviar = tipo_cafe();

                        outPrinter.flush();
                        outPrinter.println(datosEnviar);
                        outPrinter.flush();

                        // Recibe la respuesta:
                        datosRecibidos = inReader.readLine();
                        respuesta = new String (datosRecibidos);
                        opcion = Integer.parseInt(respuesta);

                        while (opcion > 5){
                            datosEnviar = tipo_cafe();

                            outPrinter.flush();
                            outPrinter.println(datosEnviar);
                            outPrinter.flush();

                            // Recibe la respuesta:
                            datosRecibidos = inReader.readLine();
                            respuesta = new String (datosRecibidos);
                            opcion = Integer.parseInt(respuesta);
                        }

                        if (opcion <= 5){
                            datosEnviar = servir();
                            outPrinter.flush();
                            outPrinter.println(datosEnviar);
                            outPrinter.flush();
                        }

                }
            }
            else if (comida){
                // Mensaje de menu comida
                datosEnviar = menu_comida();

                outPrinter.flush();
                outPrinter.println(datosEnviar);
                outPrinter.flush();

                // Recibe la respuesta:
                datosRecibidos = inReader.readLine();
                respuesta = new String (datosRecibidos);
            }


		} catch (IOException e) {
			System.err.println("Error al obtener los flujos de entrada/salida.");
		}
	}

    // Funciones:
    private String comienzo(){
        return "Bienvenido a la máquina expendedora, pulsa 1 para comenzar";
    }

    private String estudiante(){
        return "Pulsa 1 si eres estudiante, si no, pulsa otra tecla";
    }

    private String menu(){
        return "Pulsa 1 si quieres bebida, 0 si quieres comida";
    }

    private String menu_bebida(){
        return "BEBIDA:\nCafe: 0\nPoca-cola: 1\nAgua: 2\nFRanta naranja: 3\n";
    }

    private String menu_comida(){
        return "COMIDA:\nFRuta: 0\nKit kot: 1\nGalletas Newton: 2\nSandwich: 3\n";
    }

    private String tipo_cafe(){
        return "TIPOS DE CAFE:\nSolo/Expresso: 0\nCortado: 1\nLargo: 2\nCon leche: 3\nBombón: 4\nCapuchino: 5\n";
    }

    private String servir(){
        return "Aqui tiene su pedido\n";
    }
    
}
