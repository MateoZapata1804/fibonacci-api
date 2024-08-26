# fibonacci-api
Prueba técnica para Proteccion

El proyecto es un web service (API Rest) construído en lenguaje Java bajo el framework Spring. Se usó Java Persistence Api (JPA) para el mapeo de los campos de la base de datos.

Para subirlo local, se debe ejecutar desde el IDE de su preferencia, y tener una base de datos MySQL local con un esquema llamado "test_proteccion". Al ejecutar el proyecto,
las tablas se crearán de manera automática.


> Para el envío de correos electrónicos, se debe crear un archivo llamado "email.properties" en la carpeta de resources. Y agregarle las propiedades email.address y email.password.
	Dichas propiedades corresponden a la dirección y contraseña de correo electrónico que será el remitente de los correos. (Se debe tener contraseña de aplicaciones en caso de ser
	Gmail).