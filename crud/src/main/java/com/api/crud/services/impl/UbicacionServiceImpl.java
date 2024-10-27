package com.api.crud.services.impl;


import com.api.crud.persistence.entities.*;
import com.api.crud.persistence.repositories.*;
import com.api.crud.services.IUbicacionService;
import com.api.crud.services.IVentaService;
import com.api.crud.services.models.dtos.VentaDTO;
import com.api.crud.services.models.response.Cliente.ClienteResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;
import com.api.crud.services.models.response.ubicaciones.*;
import com.api.crud.services.models.response.ventas.VentaResponseDTO;
import com.api.crud.services.models.response.ventas.VentasResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UbicacionServiceImpl implements IUbicacionService {

    @Autowired
    private IPaisRepository paisRepository;

    @Autowired
    private IProvinciaRepository provinciaRepository;

    @Autowired
    private ILocalidadRepository localidadRepository;

    public ResponseEntity<Object> getAllPaises() throws Exception {
        try{
            List<Pais> allPaises = paisRepository.findAll();
            if (allPaises.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "No hay paises disponibles");
            }

            PaisesResponseDTO response = new PaisesResponseDTO();
            List<PaisResponseDTO> paisesList = new ArrayList<>();
            for(Pais pais: allPaises){
                PaisResponseDTO pa = new PaisResponseDTO();
                pa.setId(pais.getId());
                pa.setNombre(pais.getNombrePais());
                paisesList.add(pa);
            }
            response.setPaises(paisesList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Paises listados con exito", response);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    public ResponseEntity<Object> getAllProvinciasByPais(Long id) throws Exception {
        try{
            List<Provincia> allProvincias = provinciaRepository.findAllByPais(id);
            if (allProvincias.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "No hay provincias disponibles");
            }

            ProvinciasResponseDTO response = new ProvinciasResponseDTO();
            List<ProvinciaResponseDTO> provinciasList = new ArrayList<>();
            for(Provincia provincia: allProvincias){
                ProvinciaResponseDTO prov = new ProvinciaResponseDTO();
                prov.setId(provincia.getId());
                prov.setNombre(provincia.getNombreProvincia());
                provinciasList.add(prov);
            }
            response.setProvincias(provinciasList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Provincias listadas con exito", response);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }


    public ResponseEntity<Object> getAllLocalidadesByProvincia(Long id) throws Exception {
        try{
            List<Localidad> allLocalidades = localidadRepository.findAllByProvincia(id);
            if (allLocalidades.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "No hay localidades disponibles");
            }

            LocalidadesResponseDTO response = new LocalidadesResponseDTO();
            List<LocalidadResponseDTO> localidadesList = new ArrayList<>();
            for(Localidad localidad: allLocalidades){
                LocalidadResponseDTO loc = new LocalidadResponseDTO();
                loc.setId(localidad.getId());
                loc.setNombre(localidad.getNombreLocalidad());
                localidadesList.add(loc);
            }
            response.setLocalidades(localidadesList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Localidades listadas con exito", response);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }



}
