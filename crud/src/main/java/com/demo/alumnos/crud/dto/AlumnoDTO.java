package com.demo.alumnos.crud.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlumnoDTO {

    private Long id;

    private String nombre;

    private String apellido;

    private Integer edad;

    private String email;

    private Long status;
}
