# Sistema de Chat TCP Multiproceso (DAM - Servicios y Procesos)

## 1. Descripción
Sistema de chat distribuido Cliente-Servidor con sockets TCP.  
Cada grupo de chat se ejecuta como proceso independiente, gestionado con `ProcessBuilder`.  
Permite múltiples usuarios conectados simultáneamente y mensajes en tiempo real.

## 2. Requisitos
- **Java:** 21
- **MySQL:** 8.0 o superior
- **Librería JDBC:** `mysql-connector-java-9.1.0.jar` (añadir al Build Path de Eclipse)

## 3. Instrucciones para ejecutar
1. Clonar el repositorio: https://github.com/fbrocio/AppMensajeria
2. Crear la base de datos MySQL ejecutando: mysql -u usuario -p < database/appmensajeria.sql
3. Ajustar usuario y contraseña en 'ConexionBD.java'.
4. Ejecutar primero ServidorApp
5. Ejecutar después ClienteApp