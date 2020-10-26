import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//
// YodafyServidorConcurrente
// (CC) jjramos, 2012
//
public class YodafyServidorConcurrente {
	public static void main(String[] args) {
		// Puerto de escucha
		int port = 8989;
		// array de bytes auxiliar para recibir o enviar datos.
		byte[] buffer = new byte[256];
		// Número de bytes leídos
		int bytesLeidos = 0;

		try {
			// Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				System.out.println("Error: no se pudo atender en el puerto " + port);
			}

			// Mientras ... siempre!
			do {
				Socket socketServicio = serverSocket.accept();

				// Creamos un objeto de la clase ProcesadorYodafy, pasándole como
				// argumento el nuevo socket, para que realice el procesamiento
				// Este esquema permite que se puedan usar hebras más fácilmente.
				ProcesadorYodafy procesador = new ProcesadorYodafy(socketServicio);
				procesador.start();
			} while (true);

		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto " + port);
		}
	}
}
