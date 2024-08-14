# Malas prácticas
* código espagueti: El código original es incomprensive.
* no aplica el principio de responsabilidad única (Solid): El controlador se encarga de todo, validaciones, lógicas de negocio, almacenamiento de datos, etc...
* no usa inyección de dependencias: se inicializa el servicio de base de datos manualmente.
* el nombre de las variables no concuerda con lo que realiza.
* se realizan concatenaciones con + entre los strings.
* valores quemados en código, al momento de evaluar el nivel de riesgo.
* al evaluar el nivel de riesgo cuando es twitter se usan variables de facebook.
* no se evaluó que la entrada puede ser nula.

# Cómo se corrige
* Aplico una arquitectura de código limpia lo que incluye la aplicación de los principios SOLID.
* Se aplica inyección de dependencias.
* Se aplica el patrón de diseño factory, lo que permite pensar en agregar nuevas redes sociales al actual esquema de una manera simple.
* En el actual código no está pero crearía servicio rest por red social y crearía objetos tipos input para cada red social, esto evita validar cosas como esta if (nonNull(socialMention.getFacebookAccount())) en el controlador, el controlador únicamente debería invocar el caso de uso.
* Se crean constantes para los valores quemados.
* Se evalúa que la entrada no sea nula.

