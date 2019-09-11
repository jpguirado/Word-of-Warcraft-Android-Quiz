# Word-of-Warcraft-Android-Quiz
## Juego con persistencia y multimedia

```
Juan Pedro Guirado Sánchez
Ángel Luis Serrano González
```

## Contenido

- Descripción general del juego implementado
- Detalle de los requisitos contemplados
   - Básicos
      - Conjunto de preguntas
      - Configuración o ajustes
      - Información en pantalla durante el juego
      - Desarrollo del juego
      - Tabla de puntuaciones
- Aspectos importantes de la implementación de los desafíos
   - Plata
      - Diseño
      - Varios conjuntos de preguntas
- Justificación de técnicas elegidas para persistencia
   - JSON
   - SQLite
   - Preferencias...............................................................................................................................
- Conclusiones


## Descripción general del juego implementado

En esta práctica hemos optado por mejorar la aplicación de quiz que realizamos anteriormente
en la práctica 1, añadiéndole los requisitos correspondientes a los temas de persistencia y
multimedia de la asignatura. Nuestro juego consiste en un Quiz sobre el famoso videojuego
MMORPG World of Warcraft y el universo creado alrededor del mismo. Es por ello, que al juego
se le ha dotado de una estética similar a la del que se basa, con imágenes, sonidos y fuentes
usadas por el mismo.

El jugador deberá responder una serie de preguntas, configurables, relacionadas con distintos
aspectos del videojuego. Al acabar el videojuego se mostrará por pantalla la puntuación
obtenida por el jugador y si se encuentra entre las mejores, se incluirá en la tabla de
puntuaciones. Las preguntas pueden incluir imágenes, videos, sonidos y respuestas con
imágenes. En todo momento se mostrará el número de pregunta por la que va el jugador y el
ratio aciertos/fallos.

## Detalle de los requisitos contemplados

### Básicos

#### Conjunto de preguntas

Se han incluido 20 preguntas de diferentes tipos: solo texto, texto con imagen, texto y
respuestas con imágenes, video y audio. Se podrá elegir entre 5 o 10 preguntas y de distinta
dificultad: fácil o difícil.

#### Configuración o ajustes

Se podrá fijar el número de preguntas a 5 o 10, fijar la dificultad de estas a fácil o difícil y cambiar
el nombre del jugador. Todos esto parámetros son persistentes.

#### Información en pantalla durante el juego

Durante la partida se podrá ver el número de pregunta por la que va el jugador y como se
encuentra respecto del total escogido para la partida. También se muestra en pantalla la relación
aciertos/fallos.

#### Desarrollo del juego

Los fallos y aciertos se contabilizan en pantalla al mismo tiempo que el número de pregunta por
la que va el jugador. Al acabar la partida se muestra un mensaje con la puntuación total obtenida
y se pasa a la pantalla de puntuación. Si la puntuación obtenida se encuentra entre las mejores,
se añade a la tabla de clasificación

#### Tabla de puntuaciones

Se podrá ver una clasificación de las mejores puntuaciones obtenidas por lo jugadores en la
pantalla de puntuaciones, accesible desde el menú principal.

#### Captura Introducir Nombre
![Introducir nombre](https://i.imgur.com/KJuUwmP.jpg)#### Captura Menú principal
![Menu principal](https://i.imgur.com/Vr1yYyX.jpg)
#### Captura Menú opciones
![Opciones](https://i.imgur.com/O95GcVk.jpg)
#### Captura Pregunta multimedia
![Menu principal](https://i.imgur.com/WNBk111.jpg)
#### Captura Tabla puntuaciones
![Menu principal](https://i.imgur.com/dZWBALV.jpg)

## Aspectos importantes de la implementación de los desafíos

### Plata

#### Diseño

Se ha dotado al juego en general de una apariencia similar a la del videojuego en el que están
basadas las preguntas. Para ello se ha incluido: un video del videojuego como fondo del menú principal, botones y fuentes basados en la estética del videojuego, icono de la aplicación
personalizado con el logo de Word of Warcraft y por supuesto, las preguntas y multimedia que
las acompañan se basan en el videojuego.

#### Varios conjuntos de preguntas

Se han incluido 2 packs de preguntas de 10 elementos cada uno, diferenciados por su dificultad,
fácil o difícil. Esta dificultad puede ser cambiada en el menú de opciones, así como el número de
preguntas de la partida.

## Justificación de técnicas elegidas para persistencia

### JSON

Hemos decidido emplear archivos JSON para almacenar las preguntas. Hemos optado por esta
técnica debido a la facilidad de manejo de los archivos JSON, ya que nos permitían crear objetos
con distintos campos y recuperarlos de una forma sencilla. Esto nos resultaba perfecto para
almacenar nuestras preguntas, ya que contenían un gran número de campos con distinto
contenido. El archivo JSON se encuentra en los resources de la aplicación y cuando la aplicación
se inicia, lee las preguntas del archivo y procede a tratarlas para que el juego cumpla con su
funcionalidad.

### SQLite

Para almacenar las puntuaciones de los jugadores de forma persistente hemos optado por usar
SQLite. Esta decisión es debida a que nos ha parecido una forma sencilla y funcional de
almacenar estas puntuaciones de forma persistente. Hemos decidido usar una tabla con 2
columnas, nombre y puntuación.

### Preferencias...............................................................................................................................

Hemos usado las preferencias para almacenar esos valores sueltos que necesitábamos
mantener de forma persistente, como el nombre, dificultad y número de preguntas. Nos ha
parecido la mejor opción para almacenar estos valores sueltos debido a su sencillez y facilidad
de acceso en base a una etiqueta.

## Conclusiones

Esta práctica nos ha ayudado a conocer más en profundidad y comprender como funciona la
implementación de almacenamiento persistente en el desarrollo de aplicaciones para
dispositivos móviles. A la hora de desarrollar, el tema del almacenamiento puede ser bastante
confuso y poco intuitivo, pero consideramos que las herramientas utilizadas para esta práctica
han resultado ser sencillas y más fáciles de entender y aplicar que otras aprendidas a lo largo de
la carrera.

Por otro lado, en lo que se refiere al aspecto multimedia, consideramos que las herramientas
vistas en clase y posteriormente aplicadas en esta práctica han sido muy útiles y eficaces a la
hora de aplicar contenido multimedia de forma bastante sencilla a las preguntas del juego.
