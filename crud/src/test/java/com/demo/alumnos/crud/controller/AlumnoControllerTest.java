package com.demo.alumnos.crud.controller;

import com.demo.alumnos.crud.dto.AlumnoDTO;
import com.demo.alumnos.crud.dto.InfoResult;
import com.demo.alumnos.crud.exceptions.AlumnoException;
import com.demo.alumnos.crud.service.AlumnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class AlumnoControllerTest {
    @InjectMocks
    private AlumnoController alumnoController;

    @Mock
    private AlumnoService alumnoService;

    private AlumnoDTO alumnoDTO;
    private UUID uuid;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId(1L);
        alumnoDTO.setNombre("Juan");
        alumnoDTO.setApellido("Perez");
        alumnoDTO.setEdad(20);
        alumnoDTO.setEmail("juan.perez@example.com");
        uuid = UUID.randomUUID();
    }

    @Test
    void testCrearAlumno() throws AlumnoException {
        InfoResult<AlumnoDTO> expectedInfoResult = new InfoResult<>(201L, "success created student", alumnoDTO);
        when(alumnoService.crearAlumno(any(AlumnoDTO.class), any(UUID.class))).thenReturn(expectedInfoResult);

        InfoResult<AlumnoDTO> actualInfoResult = alumnoController.crearAlumno(alumnoDTO);

        assertEquals(expectedInfoResult.getCode(), actualInfoResult.getCode());
        assertEquals(expectedInfoResult.getMessage(), actualInfoResult.getMessage());
        assertEquals(expectedInfoResult.getData(), actualInfoResult.getData());
    }

    @Test
    void testObtenerAlumnoPorId() throws AlumnoException {
        InfoResult<AlumnoDTO> expectedInfoResult = new InfoResult<>(200L, "Alumno Encontrado", alumnoDTO);
        when(alumnoService.obtenerAlumnoPorId(anyLong(), any(UUID.class))).thenReturn(expectedInfoResult);

        InfoResult<AlumnoDTO> actualInfoResult = alumnoController.obtenerAlumnoPorId(1L);

        assertEquals(expectedInfoResult.getCode(), actualInfoResult.getCode());
        assertEquals(expectedInfoResult.getMessage(), actualInfoResult.getMessage());
        assertEquals(expectedInfoResult.getData(), actualInfoResult.getData());
    }
    @Test
    void testActualizarAlumno() throws AlumnoException {
        InfoResult<AlumnoDTO> expectedInfoResult = new InfoResult<>(200L, "Alumno Actualizado correctamente", alumnoDTO);
        when(alumnoService.actualizarAlumno(any(AlumnoDTO.class), any(UUID.class))).thenReturn(expectedInfoResult);

        InfoResult<AlumnoDTO> actualInfoResult = alumnoController.actualizarAlumno(alumnoDTO);

        assertEquals(expectedInfoResult.getCode(), actualInfoResult.getCode());
        assertEquals(expectedInfoResult.getMessage(), actualInfoResult.getMessage());
        assertEquals(expectedInfoResult.getData(), actualInfoResult.getData());
    }

    @Test
    void testEliminarAlumno() throws AlumnoException {
        InfoResult<Long> expectedInfoResult = new InfoResult<>(202L, "Alumno desactivado correctamente", 1L);
        when(alumnoService.eliminarAlumno(anyLong(), any(UUID.class))).thenReturn(expectedInfoResult);

        InfoResult<Long> actualInfoResult = alumnoController.eliminarAlumno(1L);

        assertEquals(expectedInfoResult.getCode(), actualInfoResult.getCode());
        assertEquals(expectedInfoResult.getMessage(), actualInfoResult.getMessage());
        assertEquals(expectedInfoResult.getData(), actualInfoResult.getData());
    }

    @Test
    void testObtenerTodosAlumnos() throws AlumnoException {
        Page<AlumnoDTO> page = new PageImpl<>(Collections.singletonList(alumnoDTO));
        InfoResult<Page<AlumnoDTO>> expectedInfoResult = new InfoResult<>(200L, "Alumnos Encontrados", page);
        when(alumnoService.obtenerTodosAlumnos(any(int.class), any(int.class), any(String.class), any(UUID.class)))
                .thenReturn(expectedInfoResult);

        InfoResult<Page<AlumnoDTO>> actualInfoResult = alumnoController.obtenerTodosAlumnos(0, 10, "ASC");

        assertEquals(expectedInfoResult.getCode(), actualInfoResult.getCode());
        assertEquals(expectedInfoResult.getMessage(), actualInfoResult.getMessage());
        assertEquals(expectedInfoResult.getData(), actualInfoResult.getData());
    }

}