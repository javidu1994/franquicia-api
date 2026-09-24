# Proyecto Franquicia API

Proyecto implementado en Java 25, Spring Boot y Spring WebFlux que permite gestionar franquicias, sucursales y productos, este proyecto hace uso del plugin de Bancolombia llamado Scaffold Clean Architecture que permite una creacion rapida de un proyecto basado en una arquitectura limpia, que aisla el modelo de negocio de la infraestructura o tecnologias especificas. En la siguiente imagen se muestra el diagrama de arquitectura de este tipo de arquitecturas.

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

# Instrucciones de ejecución
1. Se debe crear una carpeta en el equipo
2. Dentro de la carpeta se deben agregar los 2 archivos adjuntos que están en la carpeta **franquicia-app.zip adjunto dentro del correo** llamados **docker-compose.yml** y **.env**
3. Abrir la terminal o cmd, ubicarse en la ruta de la carpeta y ejecutar el comando: "docker compose up"
4. Una vez la aplicación haya arrancado abrir la documentación de los endpoints en Swagger OpenApi en la ruta: http://localhost:8080/franquicia-api/swagger-ui/index.html
5. Usar Postman para la ejecución de los endpoints

# Configuraciones necesarias
1. Se debe tener instalado el Daemon de Docker
2. Instalar Postman

# Consideraciones técnicas relevantes
1. Framework: se hizo uso del framework Spring Web Flux que permite programación reactiva no bloqueante para los flujos y consultas de datos.
2. Base de datos Postgresql en AWS: se hizo uso de esta base de datos porque esta optimizada y se integra mucho mejor el driver reactivo r2dbc con este motor de base de datos.
3. Se pueden realizar mejoras para mantener las variables de entorno con aws secrets.

# Endpoints expuestos
1.	Exponer endpoint para agregar una nueva franquicia **POST http://localhost:8080/api/v1/franquicias/**
3.	Exponer endpoint para agregar una nueva sucursal a la franquicia **POST http://localhost:8080/api/v1/sucursales/**
4.	Exponer endpoint para agregar un nuevo producto a la sucursal **POST http://localhost:8080/api/v1/productos/**
5.	Exponer endpoint para eliminar un nuevo producto a una sucursal **DELETE http://localhost:8080/api/v1/productos/{idProducto}**
6.	Exponer endpoint para modificar un Stock de un nuevo producto **PUT http://localhost:8080/api/v1/productos/stock**
7.	Exponer endpoint para agregar que permita mostrar cual es el producto que más stock tiene por sucursal para una franquicia puntual. Debe retoma un listado de productos que indiquen a que sucursal pertenece. **GET http://localhost:8080/api/v1/productos/franquicia/{idFranquicia}/stock-mayor**

Puntos extra:
1. Plus si se expone endpoint que permita actualizar el nombre de la franquicia. **PUT http://localhost:8080/api/v1/franquicias/{idFranquicia}**
2. Plus si se expone endpoint que permita actualizar el nombre de la sucursal.  **PUT http://localhost:8080/api/v1/sucursales/{idSucursal}**
3. Plus si se expone endpoint que permita actualizar el nombre del producto. **PUT http://localhost:8080/api/v1/productos/{idProducto}**

**Nota: ver documentación en Swagger para más información de los datos que se debe enviar a cada petición.**


