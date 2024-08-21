package com.demo.alumnos.crud.service;

import com.demo.alumnos.crud.dto.AlumnoDTO;
import com.demo.alumnos.crud.dto.InfoResult;
import com.demo.alumnos.crud.exceptions.AlumnoException;
import com.demo.alumnos.crud.model.Alumno;
import com.demo.alumnos.crud.repository.AlumnoRepository;
import com.demo.alumnos.crud.util.AlumnoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AlumnoServiceTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private AlumnoUtils alumnoUtils;

    @InjectMocks
    private AlumnoService alumnoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearAlumno_Success() throws AlumnoException {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setNombre("Juan");
        Alumno alumno = new Alumno();
        alumno.setNombre("Juan");

        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);

        InfoResult<AlumnoDTO> result = alumnoService.crearAlumno(alumnoDTO, UUID.randomUUID());

        assertNotNull(result);
        assertEquals(201L, result.getStatus());
        assertEquals("success created student", result.getMessage());
        assertNotNull(result.getData());
        assertEquals("Juan", result.getData().getNombre());
    }

    @Test
    void testCrearAlumno_Failure_MissingName() throws AlumnoException {
        AlumnoDTO alumnoDTO = new AlumnoDTO();

        InfoResult<AlumnoDTO> result = alumnoService.crearAlumno(alumnoDTO, UUID.randomUUID());

        assertNotNull(result);
        assertEquals(400L, result.getStatus());
        assertEquals("name is required", result.getMessage());
    }

    @Test
    void testObtenerAlumnoPorId_Success() throws AlumnoException{
        Alumno alumno = new Alumno();
        alumno.setId(1L);
        alumno.setNombre("Juan");

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));

        InfoResult<AlumnoDTO> result = alumnoService.obtenerAlumnoPorId(1L, UUID.randomUUID());

        assertNotNull(result);
        assertEquals(200L, result.getStatus());
        assertEquals("Alumno Encontrado", result.getMessage());
        assertNotNull(result.getData());
        assertEquals("Juan", result.getData().getNombre());
    }

    @Test
    void testObtenerAlumnoPorId_NotFound() throws AlumnoException {
        when(alumnoRepository.findById(1L)).thenReturn(Optional.empty());

        InfoResult<AlumnoDTO> result = alumnoService.obtenerAlumnoPorId(1L, UUID.randomUUID());

        assertNotNull(result);
        assertEquals(404L, result.getStatus());
        assertEquals("Component Not Found", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testActualizarAlumno_Success() throws AlumnoException {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId(1L);
        alumnoDTO.setNombre("Juan");

        Alumno alumno = new Alumno();
        alumno.setId(1L);
        alumno.setNombre("Pedro");

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);

        InfoResult<AlumnoDTO> result = alumnoService.actualizarAlumno(alumnoDTO, UUID.randomUUID());

        assertNotNull(result);
        assertEquals(200L, result.getStatus());
        assertEquals("Alumno Actualizado correctamente", result.getMessage());
        assertNotNull(result.getData());
        assertEquals("Juan", result.getData().getNombre());
    }

    @Test
    void testActualizarAlumno_NotFound() throws AlumnoException {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId(1L);
        alumnoDTO.setNombre("Juan");

        when(alumnoRepository.findById(1L)).thenReturn(Optional.empty());

        InfoResult<AlumnoDTO> result = alumnoService.actualizarAlumno(alumnoDTO, UUID.randomUUID());

        assertNotNull(result);
        assertEquals(404L, result.getStatus());
        assertEquals("Alumno no encontrado", result.getMessage());
    }

    @Test
    void testEliminarAlumno_Success() throws AlumnoException {
        Alumno alumno = new Alumno();
        alumno.setId(1L);
        alumno.setStatus(1L);

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);

        InfoResult<Long> result = alumnoService.eliminarAlumno(1L, UUID.randomUUID());

        assertNotNull(result);
        assertEquals(202L, result.getStatus());
        assertEquals("Alumno desactivado correctamente", result.getMessage());
    }

    @Test
    void testEliminarAlumno_NotFound() throws AlumnoException {
        when(alumnoRepository.findById(1L)).thenReturn(Optional.empty());

        InfoResult<Long> result = alumnoService.eliminarAlumno(1L, UUID.randomUUID());

        assertNotNull(result);
        assertEquals(404L, result.getStatus());
        assertEquals("Alumno no desactivado", result.getMessage());
    }

    @Test
    void testObtenerTodosAlumnos_Success() throws AlumnoException {
        Alumno alumno1 = new Alumno();
        alumno1.setId(1L);
        alumno1.setNombre("Juan");
        Alumno alumno2 = new Alumno();
        alumno2.setId(2L);
        alumno2.setNombre("Pedro");

        List<Alumno> alumnoList = Arrays.asList(alumno1, alumno2);
        Page<Alumno> alumnosPage = new PageImpl<>(alumnoList);

        when(alumnoRepository.findByStatus(1L, PageRequest.of(0, 2, org.springframework.data.domain.Sort.Direction.ASC, "id")))
                .thenReturn(alumnosPage);

        InfoResult<Page<AlumnoDTO>> result = alumnoService.obtenerTodosAlumnos(0, 2, "ASC", UUID.randomUUID());

        assertNotNull(result);
        assertEquals(200L, result.getStatus());
        assertEquals("Alumnos Encontrados", result.getMessage());
        assertEquals(2, result.getData().getContent().size());
    }
}