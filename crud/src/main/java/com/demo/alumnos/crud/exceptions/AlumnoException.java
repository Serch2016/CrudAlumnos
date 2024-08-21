package com.demo.alumnos.crud.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import javax.naming.CommunicationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.UUID;

public class AlumnoException extends Exception{

    static final Logger logger = LoggerFactory.getLogger(AlumnoException.class);

    private final UUID uuid;

    private final String stack;

    public AlumnoException(String message, UUID uuid, String stack) {
        super(message);
        this.uuid = uuid;
        this.stack=stack;
    }

    public UUID getUuid() { return uuid; }

    public String getStack() { return stack; }

    public static String getErrorMessage(Exception e){
        if (e instanceof CommunicationException){
            return "Error de comunicacion en los servidores";
        } else if (e instanceof IOException){
            return "Error de operacion de archivos";
        } else if(e instanceof DataAccessException){
            return "Error al acceder a los datos";
        } else if (e instanceof SQLException sqlException){
            return "Error de Base de datos: " + sqlException.getMessage() + " (codigo de error: " + sqlException.getErrorCode() +")";
        } else if (e instanceof NoSuchElementException){
            return "Elemento no encontrado";
        } else {
            return "Error interno del servidor" + e.getMessage();
        }

    }

}
