package com.demo.alumnos.crud.service;

import com.demo.alumnos.crud.dto.AlumnoDTO;
import com.demo.alumnos.crud.dto.InfoResult;
import com.demo.alumnos.crud.exceptions.AlumnoException;
import com.demo.alumnos.crud.model.Alumno;
import com.demo.alumnos.crud.repository.AlumnoRepository;
import com.demo.alumnos.crud.util.AlumnoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlumnoService {

    Logger logger = LoggerFactory.getLogger(AlumnoService.class);

    private final AlumnoRepository alumnoRepository;

    private final AlumnoUtils alumnoUtils;

    @Autowired
    public AlumnoService(AlumnoRepository alumnoRepository, AlumnoUtils alumnoUtils) {
        this.alumnoRepository = alumnoRepository;
        this.alumnoUtils = alumnoUtils;
    }

    public InfoResult<AlumnoDTO> crearAlumno(AlumnoDTO alumnoDTO, UUID uuid) throws AlumnoException {
        try {
            Alumno alumno = new Alumno();
            if (alumnoDTO.getNombre() == null || alumnoDTO.getNombre().isEmpty() || alumnoDTO.getNombre().equalsIgnoreCase("null"))
                return new InfoResult<>(400L, "name is required", alumnoDTO.getNombre());

            alumno.setNombre(alumnoDTO.getNombre());
            alumno.setApellido(alumnoDTO.getApellido() != null ? alumnoDTO.getApellido() : null);
            alumno.setEdad(alumnoDTO.getEdad() != null ? alumnoDTO.getEdad() : null);
            alumno.setEmail(alumnoDTO.getEmail() != null ? alumnoDTO.getEmail() : null);
            alumno.setStatus(1L);

            Alumno alumnoSave = alumnoRepository.save(alumno);
            AlumnoDTO response = convertToAlumnoDTO(alumnoSave);
            return new InfoResult<>(201L, "success created student", response);
        } catch (Exception e) {
            String message = AlumnoException.getErrorMessage(e);
            String stack = alumnoUtils.getStackTrace(e.getStackTrace().clone());
            throw new AlumnoException(message, uuid, stack);
        }
    }

    public InfoResult<AlumnoDTO> obtenerAlumnoPorId(Long id, UUID uuid) throws AlumnoException{
        try {
            Optional<Alumno> alumnoOptional = alumnoRepository.findById(id);
            return alumnoOptional.map(alumno ->  new InfoResult<>(200L, "Alumno Encontrado", convertToAlumnoDTO(alumno))).orElseGet(() -> new InfoResult<>(404L, "Component Not Found", null));

        }catch (Exception e){
            String message = AlumnoException.getErrorMessage(e);
            String stack = alumnoUtils.getStackTrace(e.getStackTrace().clone());
            throw new AlumnoException(message, uuid, stack);
        }

    }

    public InfoResult<AlumnoDTO> actualizarAlumno(AlumnoDTO alumnoDTO, UUID uuid) throws AlumnoException{
        try {

            Optional<Alumno> alumnoOptional = alumnoRepository.findById(alumnoDTO.getId());
            if (alumnoOptional.isPresent()){

                Alumno alumno = alumnoOptional.get();
                if (alumnoDTO.getNombre() == null || alumnoDTO.getNombre().isEmpty() || alumnoDTO.getNombre().equalsIgnoreCase("null"))
                    return new InfoResult<>(400L, "name is required", alumnoDTO.getNombre());

                alumno.setNombre(alumnoDTO.getNombre());
                alumno.setApellido(alumnoDTO.getApellido() != null ? alumnoDTO.getApellido() : null);
                alumno.setEdad(alumnoDTO.getEdad() != null ? alumnoDTO.getEdad() : null);
                alumno.setEmail(alumnoDTO.getEmail() != null ? alumnoDTO.getEmail() : null);
                alumno.setStatus(alumnoDTO.getStatus());

                alumnoRepository.save(alumno);

                return new InfoResult<>(200L, "Alumno Actualizado correctamente",convertToAlumnoDTO(alumno));
            } else {
                return new InfoResult<>(404L,"Alumno no encontrado",alumnoDTO.getNombre());
            }
        }catch (Exception e){
            String message = AlumnoException.getErrorMessage(e);
            String stack = alumnoUtils.getStackTrace(e.getStackTrace().clone());
            throw new AlumnoException(message, uuid, stack);
        }

    }

    public InfoResult<Long> eliminarAlumno(Long id, UUID uuid) throws AlumnoException{
        try {
            Optional<Alumno> alumnoOptional = alumnoRepository.findById(id);
            if (alumnoOptional.isPresent()){
                alumnoOptional.get().setStatus(0L);
                alumnoRepository.save(alumnoOptional.get());
                return new InfoResult<>(202L, "Alumno desactivado correctamente", alumnoOptional.get().getId());
            } else {
                return new InfoResult<>(404L, "Alumno no desactivado", id);
            }

        }catch (Exception e){
            String message = AlumnoException.getErrorMessage(e);
            String stack = alumnoUtils.getStackTrace(e.getStackTrace().clone());
            throw new AlumnoException(message, uuid, stack);

        }

    }

    public InfoResult<Page<AlumnoDTO>> obtenerTodosAlumnos(int page, int size, String sort, UUID uuid) throws  AlumnoException{

        Sort.Direction direction;
        direction = (!ObjectUtils.isEmpty(sort) && "DESC".equalsIgnoreCase(sort.trim())) ? Sort.Direction.DESC : Sort.Direction.ASC;
        List<AlumnoDTO> alumnoDTOS;
        Page<Alumno> alumnos;

        try {

            alumnos = alumnoRepository.findByStatus(1L, PageRequest.of(page, size, direction, "id"));
            alumnoDTOS = alumnos.getContent().stream().map(this::convertToAlumnoDTO).toList();
            return new InfoResult<>(200L, "Alumnos Encontrados", new PageImpl<>(alumnoDTOS, alumnos.getPageable(), alumnos.getTotalElements()));

        }catch (Exception e){
            String message = AlumnoException.getErrorMessage(e);
            String stack = alumnoUtils.getStackTrace(e.getStackTrace().clone());
            throw new AlumnoException(message, uuid, stack);

        }
    }



    private AlumnoDTO convertToAlumnoDTO(Alumno alumno) {
        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId(alumno.getId());
        alumnoDTO.setNombre(alumno.getNombre() != null ? alumno.getNombre() : null);
        alumnoDTO.setApellido(alumno.getApellido() != null ? alumno.getApellido() : null);
        alumnoDTO.setEdad(alumno.getEdad() != null ? alumno.getEdad() : null);
        alumnoDTO.setEmail(alumno.getEmail() != null ? alumno.getEmail() : null);

        return alumnoDTO;
    }
}
