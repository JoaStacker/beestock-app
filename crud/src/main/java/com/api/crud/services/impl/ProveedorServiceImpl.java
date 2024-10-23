package com.api.crud.services.impl;


import com.api.crud.persistence.entities.*;
import com.api.crud.persistence.repositories.ILocalidadRepository;
import com.api.crud.persistence.repositories.IProveedorRepository;
import com.api.crud.persistence.repositories.ITipoServicioRepository;
import com.api.crud.services.IProveedorService;
import com.api.crud.services.models.dtos.ProveedorDTO;
import com.api.crud.services.models.response.direccion.DireccionResponseDTO;
import com.api.crud.services.models.response.direccion.LocalidadResponseDTO;
import com.api.crud.services.models.response.direccion.PaisResponseDTO;
import com.api.crud.services.models.response.direccion.ProvinciaResponseDTO;
import com.api.crud.services.models.response.proveedor.ProveedorResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
            }

            List<TipoServicio> allServicios = tipoServiciosRepository.findAll();
            List<Long> listaIds = body.getTipoServicios();
            for (Long servicio_id : listaIds) {
                boolean exists = allServicios.stream()
                        .anyMatch(tipoServicio -> tipoServicio.getId().equals(servicio_id));

                if (!exists) {
                    return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Un servicio no existe.");
                }
            }

            // Realizar la operacion principal.
            List<TipoServicio> listaServicios = tipoServiciosRepository.findByFilter(listaIds);
            Proveedor nuevoProveedor = new Proveedor(body.getNombre(), body.getCuit(), body.getCorreo(),
                    body.getCalle(), body.getNumero(), body.getPiso(), localidad.get());
            nuevoProveedor.setTipoServicios(listaServicios);
            proveedorRepository.save(nuevoProveedor);

            // Construir el DTO de respuesta.
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

}
