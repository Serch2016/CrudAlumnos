package com.demo.alumnos.crud.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class InfoResult<T>{

    private Long status;
    private String message;
    private T data;
    private String error;
    private UUID code;

    public InfoResult(Long status, String message, T data){
        this.status=status;
        this.message=message;
        this.data=data;

    }

    public InfoResult(Long status, String message, String error){
        this.status=status;
        this.message=message;
        this.error=error;
        this.code=code;

    }


    public InfoResult(Long status, String message, String error, UUID code) {
        this.status=status;
        this.message=message;
        this.error=error;
        this.code=code;
    }

}
