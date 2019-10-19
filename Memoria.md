# Introducción

# Peer-to-peer

Una **red peer-to-peer** (P2P) es una red de ordenadores en la que todos o algunos aspectos funcionan sin clientes ni servidores, sino una serie de nodos que son iguales entre sí, esto es, actúan simultáneamente como clientes y servidores entre ellos.

Las redes P2P permiten el intercambio directo de información entre dos ordenadores interconectados.

Además, las conexiones P2P aprovechan, administran y optimizan el uso del ancho de banda de los demás usuarios de la red por la conectividad de los mismos, obteniendo así más rendimiento en las conexiones y transferencias que con algunos métodos centralizados convencionales.

## Aplicaciones

- Compartir, intercambiar y buscar archivos de cualquier tipo.
- Telefonía VoIP para hacer más eficiente la transmisión de datos en tiempo real, como Skype.
- Sistemas de ficheros distribuidos, como CFS o Freenet.
- Sistemas para garantizar anonimato, como i2p.
- Cálculos científicos que procesen enormes bases de datos.
- Monedas virtuales.
- Grabadores de sistemas de CCTV.

## Características

- **Escalabilidad**: Lo deseable es que cuantos más nodos estén conectados a una red P2P, mejor será su funcionamiento. Así, cuando los nodos se conectan y comparten sus propios recursos, los recursos totales del sistema P2P aumentan, lo cual difiere del diseño tradicional de cliente-servidor.
- **Robustez**: Al ser distribuidas las redes P2P incrementan la robustez en caso de fallos  en la réplica de datos a varios destinos.
- **Descentralización**: Son descentralizadas por definición y no existen nodos especiales o distintos de otros, por ello ningún nodo es indispensable para que la red funcione.
- **Distribución de costes entre los usuarios**: Se comparten recursos a cambio de recursos.
- **Anonimato**: Es deseable que quede anónimo el creador o autor del contenido, así como el editor, lector, servidor donde se encuentra y la petición para encontrarlo, siempre que los usuarios lo requieran.
- **Seguridad**: Sería conveniente poder identificar y evitar los nodos maliciosos, contenido infectado, espionaje de comunicaciones entre nodos, protección de recursos en la red... Esta característica es la menos implementada y tiene aún varios mecanismos en desarrollo, como cifrado multiclave.

## Problemas
Tenemos dos problemas principales:
- **Cómo encontrar un nodo conectado**: Se suele solucionar haciendo una conexión a un servidor, que mantiene una lista de las IPs conectadas.
- **Cómo conectar dos nodos sin dirección IP pública**: Los nodos se conectan a través de otro nodo que funciona como proxy. Cualquier nodo con IP pública puede ser escogido como proxy.

## Clasificación
Existen varios criterios para clasificar las redes P2P, ya sea según el grado de centralización, su estructuración, generación a la que pertenezcan, grado de protección de nuestra identidad y enrutamiento y descubrimiento de recursos.

### Criterio de clasificación según grado de centralización:
- **Centralizadas**: Se basa en una arquitectura monolítica en la que todas las transacciones se hacen a través de un único servidor que sirve de enlace entre dos nodos y almacena y distribuye los nodos donde se almacenan contenidos.
- **Mixtas**: Hay interacción entre un servidor central que administra los recursos de banda ancha, enrutamiento y comunicación entre nodos, pero sin saber la identidad de cada nodo ni almacenar información.
- **Puras**: Son las más comunes, no requiere usar un servidor central, por lo que se opta por los mismos usuarios como nodos de esas conexiones y almacenadores de  la información. Todas las comunicaciones son directamente de usuario a usuario con ayuda de otro nodo (otro usuario).

### Criterio de clasificación según grado de anonimato
- **Sin características de anonimato**.
- **Pseudónimo**.
- **Red P2P Privada**: Solo IPs de confianza pueden conectarse.
- **Frient-to-friend**: Solo te conectas con "amigos", solo admite direcciones IP donde confías.

# Archivos Torrent
Los **archivos torrent** almacenan metadatos sobre archivos y carpetas que se van a distribuir y ser usadaos por un cliente de BitTorrent.

Un **torrent** es información acerca de un archivo de destino, aunque no contiene información acerca del contenido del mismo. Funcionan dividiendo el archivo de destino en pequeños fragmentos de información localizados en hosts diferentes. Así, los torrents son capaces de descargar archivos grandes rápidamente.

Cuando un cliente inicia una descarga por torrent, los fragmentos del archivo de destino necesarios pueden ser encontrados sin dificultad y, luego, el cliente puede unirlos, de forma que sean usables.

Un archivo torrent contiene las URL de distintos trackers y la integridad de los metadatos de todos los fragmentos, además de metadatos adicionales.

# Trackers
Un **tracker** de BitTorrent es un servidor especial que contiene la información necesaria para que los peers se conecten con otros peers asistiendo la comunicación entre ellos usando el protocolo bitTorrent. Los trackers son el único punto de encuentro al cual los clientes necesitan conectarse para comunicarse o comenzar una descarga.

Los trackers coordinan la comunicación y distribución de datos, mantienen las estadísticas y la información de verificación para cada torrent.

El tracker es el único que sabe dónde se encuentra cada peer dentro de un enjambre, por lo que es indispensable para poder comunicarse con el resto de usuarios, al menos hasta haberse conectado al enjambre.

## Clases
Los trackers se dividen en dos clases:

- **Privados**: Requieren que los peers sean usuarios registrados en un sitio web.
- **Públicos**: Cualquiera puede comunicarse con ellos.

# Referencias
- [https://es.wikipedia.org/wiki/Peer-to-peer](https://es.wikipedia.org/wiki/Peer-to-peer)
- [https://en.wikipedia.org/wiki/Peer-to-peer](https://en.wikipedia.org/wiki/Peer-to-peer)
- [https://es.wikipedia.org/wiki/Archivo_Torrent](https://es.wikipedia.org/wiki/Archivo_Torrent)
- [https://es.wikipedia.org/wiki/Tracker_(BitTorrent)](https://es.wikipedia.org/wiki/Tracker_(BitTorrent))


