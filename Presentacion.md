---
title: BitTorrent
revealOptions:
    transition: 'Fade'
    theme: robot-lung


---

# BitTorrent

> Ana Buendía Ruiz-Azuaga

> Paula Villanueva Núñez

> DGIIM

---

## Introducción


---

## Peer-to-peer

---

### Aplicaciones


---

### Características


---

### Problemas


---

### Clasificación

---

#### Criterio de clasificación según grado de centralización


---

#### Criterio de clasificación según grado de anonimato


---

## BitTorrent

- Es un protocolo diseñado para el **intercambio de archivos P2P**.
- Transferencias de **archivos grandes**.
- Diseñado por Bram Cohen en 2001
- Usado por **170 millones de personas** cada mes.
- Mueve hasta el **40% del tráfico** mundial de Internet.
- Propia **criptomoneda**.

---

## BitTorrent

- Los usuarios se unen en un *enjambre* para descargar y subir archivos simultáneamente.
- Para subir un archivo hay que subir su **`torrent`**.
- Se crea un nodo BitTorrent (**semilla**).
- Para descargarlo creamos otro nodo (**cliente**).
- Un archivo se distribuye en **pequeñas partes**.

---

## BitTorrent

- La **semilla original** no tiene que enviar las partes a todos los usuarios.
- Las partes no se descargan secuencialmente. BitTorrent las **reordena** y comprueba cuáles se han descargados.
- Cada parte tiene el **mismo tamaño**.
- Cada parte se **transmite** de una sola vez.



---

## BitTorrent

- Podemos detener la descarga y reanudarla **sin perder información**.
- Cuando se descarga el archivo, el cliente se convierte en **semilla**.
- Cada parte del archivo está protegida por un **hash criptográfico**.
- Se pueden detectar las **modificaciones** gracias a la semilla original.

---

## BitTorrent

- Reducir costes de hardware y de ancho de banda.
- Redundancia ante problemas del sistema.
- Reduce dependencias con el distribuidor original.
- Fuentes de descarga transitorias.

---

## Programas clientes

<section style="text-align: left;">

Sirven para abrir el archivo `.torrent` y descargar su información. Hay dos tipos:

- **Múltiples descargas simultáneas**: Vuze, BitComet, KTorrent, $\mu$Torrent, Transmission...
- **Descarga única**: puede haber varios `.torrent` abiertos simultáneamente, pero solo se descarga uno. Ejemplos: BitTornado, navegador Opera...

---

## Estructura de una BitTorrent

- **Peers**: usuarios.
- **Leechers**: usuarios que están descargando un archivo o los usuarios que descargan archivos pero no los comparten.
- **Seeders**: usuarios que tienen el archivo completo.
- **Trackers**: servidor especial con información para que los peers se conecten entre sí.
- **Swarm**: usuarios que el tracker busca.

---

## Mecánica del funcionamiento de BitTorrent

1. Descargar un archivo `.torrent`.
2. Abrir el archivo `.torrent` con un **programa cliente**.
3. El **tracker** y el **peer** se comunican mediante una conexión **HTTP**. El tracker informa de los peers y seeders que tienen nuestro archivo, y se actualiza con la información del nuevo peer que ingresa.

---

## Mecánica del funcionamiento de BitTorrent

<section style="text-align: left;">


4.Cuando el **peer** sabe dónde tiene que buscar las partes, se comunica con otros mediante **sockets TCP o UDP**. El archivo comienza a **descargarse**. Cada parte descargada se comparte con otros peers.

---

## Archivos Torrent


---

## Trackers


---

### Clases


---

## Ventajas

- **No se saturan los servidores**: se ahorra ancho de banda.
- **Redundancia**: se puede acceder a un archivo aunque el servidor no esté disponible siempre que algún peer haya recibido el archivo completo.
- **Alta disponibilidad a la hora de descargar**: descargar archivos simultáneamente.

---

## Problemas

- **Errores**: al componer el archivo.
- **Trampas**: puede haber archivos falsos.
- **Dependencia de los peers**: si un usuario al descargar un archivo se desconecta o la velocidad está limitada.
- **Dependencia del tracker**: sin el tracker no se podrían hacer las transferencias.

---

## Referencias

- https://es.wikipedia.org/wiki/Peer-to-peer
- https://en.wikipedia.org/wiki/Peer-to-peer
- https://es.wikipedia.org/wiki/Archivo_Torrent
- https://es.wikipedia.org/wiki/Tracker_(BitTorrent)
- https://es.wikipedia.org/wiki/BitTorrent
- https://www.xatakamovil.com/conectividad/que-es-y-como-funciona-el--protocolo-bittorrent
