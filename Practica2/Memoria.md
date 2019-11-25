# Práctica 2: Máquina expendedora (cliente - servidor)

## Descripción de la aplicación, funcionalidad y actores que intervienen

Para realizar el ejercicio 5, hemos propuesto una máquina expendedora donde el usuario puede elegir qué comida o bebida quiere comprar. Además, al principio puede identificarse mediante su DNI para aplicar el descuento de estudiante. Tanto al servir el pedido o introducir un caracter especial, se vuelve al menú inicial.

Al principio, hay que pulsar la tecla `1` para comenzar el pedido, si se pulsa cualquier otra tecla vuelve a aparecer dicho mensaje.

A continuación, hay que pulsar la tecla `1` si eres estudiante y si no, se pulsa otra tecla. En el caso de que seamos estudiantes, se nos pedirá el DNI para comprobar que somos estudiantes. Si introducimos un DNI falso, se detectará y no se aplicará el descuento. Los DNIs válidos los hemos guardado en el fichero `log_estudiante.txt`.

Lo siguiente será elegir entre comida o bebida:

- **Comida**: disponemos del siguiente menú con sus respectivas teclas que hay que pulsar:
    - FRuta: `0`
    - Kit kot: `1`
    - Galletas Newton: `2`
    - Sandwich: `3`

    Sin embargo, si pulsamos una tecla que no aparece en el menú, se detectará y se volverá a mostrar el menú hasta que pulsemos la tecla correcta.
- **Bebida**: disponemos del siguiente menú con sus respectivas teclas que hay que pulsar:
    - Cafe: `0`
    - Poca-cola: `1`
    - Agua: `2`
    - FRanta naranja: `3`

    Sin embargo, si pulsamos una tecla que no aparece en el menú, se detectará y se volverá a mostrar el menú hasta que pulsemos la tecla correcta.

Por último, nos dará nuestro pedido y el importe que hay que pagar.


## Diagrama de estados del servidor

Los estados por los que pasa el servidor de acuerdo a los eventos que puedan surgir o los mensajes que recibe por parte de los clientes se muestra en la siguiente imagen.

> Insertar diagrama

## Mensajes que intervienen

| **Código** | **Cuerpo** | **Descripción** |
| :---------: | :--------: | :-------------: |
| | | |

> Completar tabla

## Capturas de pantalla de la aplicación

Primero, compilamos con `make`, luego ejecutamos `java MaquinaServidorIterativo` y por último ejecutamos en otra terminal `java MaquinaCliente`.

En la segunda terminal, nos aparece el siguiente mensaje:

```
Intentando abrir puerto...
Puerto abierto.
Estableciendo stream de datos...
Stream establecido.
Bienvenido a la máquina expendedora, pulsa 1 para comenzar
```

Si pulsamos cualquier tecla que no sea `1`, nos volverá a aparecer dicho mensaje. Si pulsamos `1` nos aparecerá:

```
1
Pulsa 1 si eres estudiante, si no, pulsa otra tecla

Recibidos 51 bytes: 
Teclee opcion
```

Si pulsamos cualquier tecla que no sea `1`, no se aplicará el descuento. Si pulsamos `1` nos aparecerá:

```
1
Introduzca su DNI

Recibidos 17 bytes: 
Teclee opcion
```

En este momento, tenemos que introducir nuestro DNI para que la máquina compruebe si somos estudiantes, y en este caso, nos aplique el descuento.

Si introducimos el DNI correcto (`12345678`) nos aparecerá:

```
12345678
DNI reconocido, se aplicará descuento.
Pulsa 1 si quieres bebida, 0 si quieres comida

Recibidos 84 bytes: 
Teclee opcion
```

Ahora nos pide elegir entre bebida (`1`) o comida (`0`). Si pulsamos bebida obtenemos:

```
1
BEBIDA:
Cafe: 0
Poca-cola: 1
Agua: 2
FRanta naranja: 3

Recibidos 50 bytes: 
Teclee opcion
```

Si pulsamos `0` para elegir café, obtendremos:

```
0
TIPOS DE CAFE:
Solo/Expresso: 0
Cortado: 1
Largo: 2
Con leche: 3
Bombón: 4
Capuchino: 5

Recibidos 81 bytes: 
Teclee opcion
```

Ahora pulsamos la tecla `8` y vemos que detecta que no existe y que nos vuelve a enviar el mismo menú:

```
8
TIPOS DE CAFE:
Solo/Expresso: 0
Cortado: 1
Largo: 2
Con leche: 3
Bombón: 4
Capuchino: 5

Recibidos 81 bytes: 
Teclee opcion
```

Por último, pulsamos la tecla `5` y obtenemos:

```
Precio: 0,30
***********Aqui tiene su pedido***********

Bienvenido a la máquina expendedora, pulsa 1 para comenzar
```

Nos da nuestro pedido y vuelve al menú principal.