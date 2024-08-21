package com.demo.alumnos.crud.controller;

import com.demo.alumnos.crud.dto.AlumnoDTO;
import com.demo.alumnos.crud.dto.InfoResult;
import com.demo.alumnos.crud.exceptions.AlumnoException;
import com.demo.alumnos.crud.service.AlumnoService;
import com.demo.alumnos.crud.util.AlumnoConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.UUID;

@RestController
@RequestMapping(value = "/api/students")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    Logger logger = LoggerFactory.getLogger(AlumnoController.class);

    @PostMapping("/create")
    public InfoResult<AlumnoDTO> crearAlumno(@RequestBody AlumnoDTO alumnoDTO){
        UUID uuid = UUID.randomUUID();
        logger.info("createStudent {}", uuid);
        InfoResult<AlumnoDTO> infoResult;

        try {
            infoResult = alumnoService.crearAlumno(alumnoDTO, uuid);

        }catch (Exception e){
            infoResult = new InfoResult<>(500L, AlumnoConstants.ERROR_NOT_FOUND, e.getMessage());
        }


        return infoResult;
    }

    @GetMapping("/getId/{id}")
    public InfoResult<AlumnoDTO> obtenerAlumnoPorId(@PathVariable Long id) throws AlumnoException {
        UUID uuid = UUID.randomUUID();
        logger.info("obtenerAlumnoPorId {}", uuid);
        InfoResult<AlumnoDTO> infoResult;

        try {
            infoResult = alumnoService.obtenerAlumnoPorId(id, uuid);

        }catch (Exception e){
            infoResult = new InfoResult<>(500L, AlumnoConstants.ERROR_NOT_FOUND, e.getMessage());
        }

        return infoResult;
    }

    @PutMapping("/update")
    public InfoResult<AlumnoDTO> actualizarAlumno(@RequestBody AlumnoDTO alumnoDTO) throws AlumnoException {
        UUID uuid = UUID.randomUUID();
        logger.info("actualizarAlumno {}", uuid);
        InfoResult<AlumnoDTO> infoResult;

        try {
            infoResult = alumnoService.actualizarAlumno(alumnoDTO, uuid);

        }catch (Exception e){
            infoResult = new InfoResult<>(500L, AlumnoConstants.ERROR_NOT_FOUND, e.getMessage());
        }

        return infoResult;
    }
    @DeleteMapping("/delete/{id}")
    public InfoResult<Long> eliminarAlumno(@PathVariable long id) throws AlumnoException {
        UUID uuid = UUID.randomUUID();
        logger.info("eliminarAlumno {}", uuid);
        InfoResult<Long> infoResult;

        try {
            infoResult = alumnoService.eliminarAlumno(id, uuid);

        }catch (Exception e){
            infoResult = new InfoResult<>(500L, AlumnoConstants.ERROR_NOT_FOUND, e.getMessage());
        }

        return infoResult;
    }

    @GetMapping("/allStudents")
    public InfoResult<Page<AlumnoDTO>> obtenerTodosAlumnos(@RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size") int size,
                                                           @RequestParam(value = "sort", defaultValue = "ASC") String sort) throws AlumnoException{

        UUID uuid = UUID.randomUUID();
        logger.info("obtenerTodosAlumnos {}", uuid);
        InfoResult<Page<AlumnoDTO>> infoResult;

        try {
            infoResult = alumnoService.obtenerTodosAlumnos(page, size, sort, uuid);
            return infoResult;

        }catch (Exception e){

            infoResult = new InfoResult<>(500L, AlumnoConstants.ERROR_NOT_FOUND, e.getMessage(), uuid);
            return infoResult;

        }

    }

}
