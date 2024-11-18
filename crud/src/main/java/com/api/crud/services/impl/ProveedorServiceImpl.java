package com.api.crud.services.impl;


import com.api.crud.persistence.entities.*;
import com.api.crud.persistence.repositories.ILocalidadRepository;
import com.api.crud.persistence.repositories.IProveedorRepository;
import com.api.crud.persistence.repositories.ITipoServicioRepository;
import com.api.crud.services.IProveedorService;
import com.api.crud.services.models.dtos.EmpleadoDTO;
import com.api.crud.services.models.dtos.ProveedorDTO;
import com.api.crud.services.models.response.direccion.DireccionResponseDTO;
import com.api.crud.services.models.response.direccion.LocalidadResponseDTO;
import com.api.crud.services.models.response.direccion.PaisResponseDTO;
import com.api.crud.services.models.response.direccion.ProvinciaResponseDTO;
import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;
import com.api.crud.services.models.response.empleado.EmpleadosResponseDTO;
import com.api.crud.services.models.response.proveedor.ProveedorResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import com.api.crud.services.models.response.proveedor.ProveedoresResponseDTO;
import com.api.crud.services.models.response.tipoServicio.TipoServicioResponseDTO;
import com.api.crud.services.models.response.tipoServicio.TiposServicioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorServiceImpl implements IProveedorService {
    @Autowired
    private IProveedorRepository proveedorRepository;

    @Autowired
    private ITipoServicioRepository tipoServiciosRepository;

    @Autowired
    private ILocalidadRepository localidadRepository;

    public ResponseEntity<Object> createProveedor(ProveedorDTO body) throws Exception {
        try{
            // Validar existencias en base de datos.
            Optional<Proveedor> proveedorExistente = proveedorRepository.findByCuit(body.getCuit());
            if(proveedorExistente.isPresent()){
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "El CUIT ya pertenece a un proveedor.");
            }

            Optional<Localidad> localidad = localidadRepository.findById(body.getLocalidadId());
            if(localidad.isEmpty()){
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Localidad id no existe!", localidad);
            }//segun la logica. La localidad esta establecida en el front. Entonces siempre deberia de aparecer

            List<TipoServicio> allServicios = tipoServiciosRepository.findAll();
            List<Long> listaIds = body.getTipoServicios();
            //Este for verific que los servicios ingresados existen
            for (Long servicio_id : listaIds) {
                boolean exists = allServicios.stream()
                        .anyMatch(tipoServicio -> tipoServicio.getId().equals(servicio_id));

                if (!exists) {
                    return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Un servicio no existe.");
                }
            }

            List<TipoServicio> listaServicios = tipoServiciosRepository.findByFilter(listaIds);
            Proveedor nuevoProveedor = new Proveedor(body.getNombre(), body.getCuit(), body.getCorreo(),
                    body.getCalle(), body.getNumero(), body.getPiso(), localidad.get());
            nuevoProveedor.setTipoServicios(listaServicios);
            proveedorRepository.save(nuevoProveedor);

            ProveedorResponseDTO response = new ProveedorResponseDTO();
            response.setCuit(nuevoProveedor.getCuit());
            response.setNombre(nuevoProveedor.getNombre());
            response.setCorreo(nuevoProveedor.getCorreo());
            response.setTipoServicios(nuevoProveedor.getTipoServicios());
            Direccion dir = nuevoProveedor.getDireccion();
            Localidad loc = dir.getLocalidad();
            Provincia prov = loc.getProvincia();
            Pais pais = prov.getPais();
            response.setDireccion(new DireccionResponseDTO(dir.getId(), dir.getCalle(), dir.getNumero(), dir.getPiso()));
            response.setLocalidad(new LocalidadResponseDTO(loc.getId(), loc.getNombreLocalidad()));
            response.setProvincia(new ProvinciaResponseDTO(prov.getId(), prov.getNombreProvincia()));
            response.setPais(new PaisResponseDTO(pais.getId(), pais.getNombrePais()));

            return ResponseHandler.responseBuilder(HttpStatus.CREATED, "Proveedor creado con éxito!", response);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear Proveedor: "
                    + e.getMessage());
        }
    }

    public ResponseEntity<Object> deleteProveedorById(Long id) throws Exception {
            Optional<Proveedor> proveedorExistente = proveedorRepository.findById(id);
            if(proveedorExistente.isPresent()){
                Proveedor proveedor = proveedorExistente.get();
                proveedor.setEstado(false);
                proveedorRepository.save(proveedor);
            }else{
                return ResponseHandler.responseBuilder(HttpStatus.NOT_FOUND, "El proveedor no existe!");
            }
        return ResponseHandler.responseBuilder(HttpStatus.OK, "Proveedor eliminado!");
    }

    public ResponseEntity<Object> findAll() throws Exception {
        try{
            List<Proveedor> allProveedores = proveedorRepository.findAllNotDeleted();

            List<ProveedorResponseDTO> proveedoresList = new ArrayList<>();
            for(Proveedor pov : allProveedores){
                ProveedorResponseDTO proveedorResponseDTO = new ProveedorResponseDTO();
                proveedorResponseDTO.setId(pov.getId());
                proveedorResponseDTO.setCuit(pov.getCuit());
                proveedorResponseDTO.setNombre(pov.getNombre());
                proveedorResponseDTO.setCorreo(pov.getCorreo());
                proveedorResponseDTO.setTipoServicios(pov.getTipoServicios());
                Direccion direccion = pov.getDireccion();
                DireccionResponseDTO dir = new DireccionResponseDTO(
                        direccion.getId(),
                        direccion.getCalle(),
                        direccion.getNumero(),
                        direccion.getPiso()
                );
                proveedorResponseDTO.setDireccion(dir);
                proveedoresList.add(proveedorResponseDTO);
            }

            ProveedoresResponseDTO response = new ProveedoresResponseDTO();
            response.setProveedores(proveedoresList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Proveedores encontrados con exito", response);

        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener proveedores: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> findOne(Long id) throws Exception {
        try{
            Optional<Proveedor> proveedorFound = proveedorRepository.findById(id);
            if(proveedorFound.isPresent()){
                Proveedor proveedor = proveedorFound.get();
                ProveedorResponseDTO response = new ProveedorResponseDTO();
                response.setCuit(proveedor.getCuit());
                response.setNombre(proveedor.getNombre());
                response.setCorreo(proveedor.getCorreo());
                response.setTipoServicios(proveedor.getTipoServicios());

                Direccion direccion = proveedor.getDireccion();
                Localidad localidad = direccion.getLocalidad();
                Provincia provincia = localidad.getProvincia();
                Pais pais = provincia.getPais();

                response.setDireccion(new DireccionResponseDTO(
                        direccion.getId(),
                        direccion.getCalle(),
                        direccion.getNumero(),
                        direccion.getPiso()
                ));
                response.setLocalidad(new LocalidadResponseDTO(
                        localidad.getId(),
                        localidad.getNombreLocalidad()
                ));
                response.setProvincia(new ProvinciaResponseDTO(
                        provincia.getId(),
                        provincia.getNombreProvincia()
                ));
                response.setPais(new PaisResponseDTO(
                        pais.getId(),
                        pais.getNombrePais()
                ));

                return ResponseHandler.responseBuilder(HttpStatus.OK, "Proveedor encontrado con exito", response);
            }else{
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "Proveedor no existe");
            }
        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }

    public ResponseEntity<Object> updateProveedor(Long id, ProveedorDTO proveedorDTO) throws Exception {
        try{
            Optional<Proveedor> optionalProveedor = proveedorRepository.findById(id);

            if (optionalProveedor.isPresent()) {
                Proveedor proveedorToUpdate = optionalProveedor.get();
                if(proveedorDTO.getCuit() != null){
                    proveedorToUpdate.setCuit(proveedorDTO.getCuit());
                }
                if(proveedorDTO.getCorreo() != null){
                    proveedorToUpdate.setCorreo(proveedorDTO.getCorreo());
                }
                if(proveedorDTO.getNombre() != null){
                    proveedorToUpdate.setNombre(proveedorDTO.getNombre());
                }
                if(proveedorDTO.getTipoServicios() != null){
                    List<TipoServicio> allServicios = tipoServiciosRepository.findAll();
                    List<Long> listaIds = proveedorDTO.getTipoServicios();
                    for (Long servicio_id : listaIds) {
                        boolean exists = allServicios.stream()
                                .anyMatch(tipoServicio -> tipoServicio.getId().equals(servicio_id));

                        if (!exists) {
                            return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Un servicio no existe.");
                        }
                    }
                    List<TipoServicio> listaServicios = tipoServiciosRepository.findByFilter(listaIds);
                    proveedorToUpdate.setTipoServicios(listaServicios);
                }
                if(proveedorDTO.getCalle() != null){
                    Direccion direccion = proveedorToUpdate.getDireccion();
                    direccion.setCalle(proveedorDTO.getCalle());
                    proveedorToUpdate.setDireccion(direccion);
                }
                if(proveedorDTO.getNumero() != null){
                    Direccion direccion = proveedorToUpdate.getDireccion();
                    direccion.setNumero(proveedorDTO.getNumero());
                    proveedorToUpdate.setDireccion(direccion);
                }
                if(proveedorDTO.getPiso() != null){
                    Direccion direccion = proveedorToUpdate.getDireccion();
                    direccion.setPiso(proveedorDTO.getPiso());
                    proveedorToUpdate.setDireccion(direccion);
                }
                if(proveedorDTO.getLocalidadId() != null){
                    Direccion direccion = proveedorToUpdate.getDireccion();
                    Optional<Localidad> localidad = localidadRepository.findById(proveedorDTO.getLocalidadId());
                    if(localidad.isEmpty()){
                        return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Localidad id no existe!", localidad);
                    }
                    direccion.setLocalidad(localidad.get());
                    proveedorToUpdate.setDireccion(direccion);
                }

                proveedorRepository.save(proveedorToUpdate);

                //respuesta
                ProveedorResponseDTO response = new ProveedorResponseDTO();
                response.setCuit(proveedorToUpdate.getCuit());
                response.setCorreo(proveedorToUpdate.getCorreo());
                response.setNombre(proveedorToUpdate.getNombre());
                response.setTipoServicios(proveedorToUpdate.getTipoServicios());
                Direccion direccion = proveedorToUpdate.getDireccion();
                Localidad localidad = direccion.getLocalidad();
                Provincia provincia = localidad.getProvincia();
                Pais pais = provincia.getPais();

                response.setDireccion(new DireccionResponseDTO(
                        direccion.getId(),
                        direccion.getCalle(),
                        direccion.getNumero(),
                        direccion.getPiso()
                ));
                response.setLocalidad(new LocalidadResponseDTO(
                        localidad.getId(),
                        localidad.getNombreLocalidad()
                ));
                response.setProvincia(new ProvinciaResponseDTO(
                        provincia.getId(),
                        provincia.getNombreProvincia()
                ));
                response.setPais(new PaisResponseDTO(
                        pais.getId(),
                        pais.getNombrePais()
                ));

                return ResponseHandler.responseBuilder(HttpStatus.OK, "Proveedor actualizado con exito", response);
            } else {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Proveedor no existe");
            }
        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }

    public ResponseEntity<Object> findAllTiposServicios() throws Exception {
        try{
            List<TipoServicio> allServicios = tipoServiciosRepository.findAll();

            List<TipoServicioResponseDTO> serviciosList = new ArrayList<>();
            for(TipoServicio pov : allServicios){
                TipoServicioResponseDTO serv = new TipoServicioResponseDTO();
                serv.setId(pov.getId());
                serv.setNombre(pov.getNombre());
                serviciosList.add(serv);
            }

            TiposServicioResponseDTO response = new TiposServicioResponseDTO();
            response.setTiposServicio(serviciosList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Tipos de servicio encontrados con exito", response);

        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener tipos de servicio: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> getRankingIncidentes() throws Exception {
        try{
            List<Proveedor> allProveedores = proveedorRepository.findAllWithIncidentes();

            List<ProveedorResponseDTO> proveedoresList = new ArrayList<>();
            for(Proveedor pov : allProveedores){
                ProveedorResponseDTO proveedorResponseDTO = new ProveedorResponseDTO();

                proveedorResponseDTO.setId(pov.getId());
                proveedorResponseDTO.setCuit(pov.getCuit());
                proveedorResponseDTO.setNombre(pov.getNombre());
                proveedorResponseDTO.setCorreo(pov.getCorreo());
                proveedorResponseDTO.setTipoServicios(pov.getTipoServicios());
                proveedorResponseDTO.setTotalIncidentes((long) pov.getIncidentes().size());
                Direccion direccion = pov.getDireccion();
                DireccionResponseDTO dir = new DireccionResponseDTO(
                        direccion.getId(),
                        direccion.getCalle(),
                        direccion.getNumero(),
                        direccion.getPiso()
                );
                proveedorResponseDTO.setDireccion(dir);
                proveedoresList.add(proveedorResponseDTO);

            }

            // Sort proveedores by totalIncidentes in descending order and then by nombre alphabetically (ascending order) in case of a tie
            proveedoresList.sort((p1, p2) -> {
                // First, compare by totalIncidentes in descending order
                int incidentComparison = Long.compare(p1.getTotalIncidentes(), p2.getTotalIncidentes());

                // If totalIncidentes are the same, compare by nombre alphabetically (ascending order)
                if (incidentComparison == 0) {
                    return p1.getNombre().compareToIgnoreCase(p2.getNombre());
                }

                // Otherwise, return the result of the totalIncidentes comparison
                return incidentComparison;
            });

            // Assign ranking positions
            for (int i = 0; i < proveedoresList.size(); i++) {
                // Ranking position is the index + 1 (1-based index)
                proveedoresList.get(i).setRanking(i + 1);
            }

            ProveedoresResponseDTO response = new ProveedoresResponseDTO();
            response.setProveedores(proveedoresList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Proveedores encontrados con exito", response);

        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener proveedores: " + e.getMessage());
        }
    }
}
