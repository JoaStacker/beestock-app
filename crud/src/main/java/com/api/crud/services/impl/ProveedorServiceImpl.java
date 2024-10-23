package com.api.crud.services.impl;


import com.api.crud.persistence.entities.*;
import com.api.crud.persistence.repositories.IProveedorRepository;
import com.api.crud.services.IProveedorService;
import com.api.crud.services.models.dtos.ProveedorDTO;
import com.api.crud.services.models.response.ProveedoresResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProveedorServiceImpl implements IProveedorService {
    @Autowired
    private IProveedorRepository proveedorRepository;

    public ResponseEntity<Object> createProveedor(ProveedorDTO proveedorDTO) throws Exception{
        try{
            // Check if the required fields are present
            if (proveedorDTO.getCUIT() == null) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'CUIT'");
            }
            if (proveedorDTO.getNombre() == null) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'nombre'");
            }
            if (proveedorDTO.getCorreo() == null) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'correo'");
            }
            if (proveedorDTO.getCalle() == null) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'calle'");
            }
            if (proveedorDTO.getNumero() == null) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'numero'");
            }
            if (proveedorDTO.getLocalidad() == null) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'localidad'");
            }
            if (proveedorDTO.getProvincia() == null) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'provincia'");
            }
            if (proveedorDTO.getPais() == null) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'pais'");
            }

            // Create a NUEVADIRECCION
            Pais pais = new Pais(proveedorDTO.getPais());
            Provincia provincia = new Provincia(proveedorDTO.getProvincia(),pais);
            Localidad localidad = new Localidad(proveedorDTO.getLocalidad(),provincia);
            Direccion nuevaDireccion = new Direccion(proveedorDTO.getCalle(),proveedorDTO.getNumero(),
                    proveedorDTO.getPiso(), localidad);


            // Save the new PROVEEDOR
            Proveedor proveedor = new Proveedor(proveedorDTO.getNombre(), proveedorDTO.getCUIT(), proveedorDTO.getCorreo(),
                    proveedorDTO.getTipoServicio(), nuevaDireccion);
            proveedorRepository.save(proveedor);//ESTOY SEGURO QUE FALTA UN TRYCATCH

            //esta linea realmente no se iria puesto que la entidad proveedor es la que se esta tratanto pero al devolverla
            //deberia suponer que se envia como un responseDto
//            ProveedoresResponseDTO proveedoresResponseDTO = new ProveedoresResponseDTO( proveedorDTO.getCorreo(),proveedorDTO.getNombre(),
//                    proveedorDTO.getCUIT(), nuevaDireccion, proveedorDTO.getTipoServicio());


            return ResponseHandler.responseBuilder(HttpStatus.CREATED, "Proveedor creado con éxito!", proveedor);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear Proveedor: "
                    + e.getMessage());
        }
    }

}
