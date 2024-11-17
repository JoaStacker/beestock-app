package com.api.crud.services.impl;


import com.api.crud.persistence.entities.*;
import com.api.crud.persistence.repositories.IClienteRepository;
import com.api.crud.persistence.repositories.ICondicionTributariaRepository;
import com.api.crud.persistence.repositories.ILocalidadRepository;
import com.api.crud.services.IClienteService;
import com.api.crud.services.models.dtos.ClienteDTO;
import com.api.crud.services.models.response.Cliente.ClienteResponseDTO;
import com.api.crud.services.models.response.ClientesResponseDTO;
import com.api.crud.services.models.response.CondicionTributaria.CondicionTributariaResponseDTO;
import com.api.crud.services.models.response.CondicionTributaria.CondicionesTributariasResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import com.api.crud.services.models.response.direccion.DireccionResponseDTO;
import com.api.crud.services.models.response.direccion.LocalidadResponseDTO;
import com.api.crud.services.models.response.direccion.PaisResponseDTO;
import com.api.crud.services.models.response.direccion.ProvinciaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private ILocalidadRepository localidadRepository;

    @Autowired
    private ICondicionTributariaRepository condicionTributariaRepository;

    public ResponseEntity<Object> createCliente(ClienteDTO body) throws Exception{
        try{

            // Validar existencias en base de datos.
            Optional<Cliente> cli = clienteRepository.findByCuit(body.getCuit());
            if (cli.isPresent()) {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "CUIT ya existe!");
            }

            Optional<Localidad> localidad = localidadRepository.findById(body.getLocalidadId());
            if(localidad.isEmpty()){
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Localidad id no existe!");
            }

            Optional<CondicionTributaria> condicionTributaria = condicionTributariaRepository.findById(body.getCondicionTributariaId());
            if(condicionTributaria.isEmpty()){
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Condicion Tributaria id no existe!");
            }


            Cliente nuevoCliente = new Cliente(body.getCuit(), body.getNombre(), body.getApellido(), body.getEmail(), body.getFechaNacimiento(), condicionTributaria.get(),
                    body.getCalle(), body.getNumero(), body.getPiso(), localidad.get());

            clienteRepository.save(nuevoCliente);

            ClienteResponseDTO response = new ClienteResponseDTO();
            response.setId(nuevoCliente.getId());
            response.setCuit(nuevoCliente.getCuit());
            response.setNombre(nuevoCliente.getNombre());
            response.setApellido(nuevoCliente.getApellido());
            response.setEmail(nuevoCliente.getEmail());
            response.setFechaNacimiento(nuevoCliente.getFechaNacimiento());

            CondicionTributaria condTributaria = nuevoCliente.getCondicionTributaria();
            CondicionTributariaResponseDTO cond = new CondicionTributariaResponseDTO(
                    condTributaria.getId(),
                    condTributaria.getTipo()
            );
            response.setCondicionTributaria(cond);

            Direccion direccion = nuevoCliente.getDireccion();
            DireccionResponseDTO dir = new DireccionResponseDTO(
                    direccion.getId(),
                    direccion.getCalle(),
                    direccion.getNumero(),
                    direccion.getPiso()
            );
            response.setDireccion(dir);

            return ResponseHandler.responseBuilder(HttpStatus.CREATED, "Cliente creado con éxito!", response);


        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    public ResponseEntity<Object> findAll() {
        try {
            List<Cliente> allClientes = clienteRepository.findAllNotDeleted();
            if (allClientes.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "No hay clientes disponibles");
            }

            ClientesResponseDTO response = new ClientesResponseDTO();
            List<ClienteResponseDTO> clientesList = new ArrayList<>();
            for(Cliente cliente: allClientes){
                ClienteResponseDTO cli = new ClienteResponseDTO();
                cli.setId(cliente.getId());
                cli.setCuit(cliente.getCuit());
                cli.setEmail(cliente.getEmail());
                cli.setNombre(cliente.getNombre());
                cli.setApellido(cliente.getApellido());
                cli.setFechaNacimiento(cliente.getFechaNacimiento());
                Direccion direccion = cliente.getDireccion();
                DireccionResponseDTO dir = new DireccionResponseDTO(
                        direccion.getId(),
                        direccion.getCalle(),
                        direccion.getNumero(),
                        direccion.getPiso()
                );
                cli.setDireccion(dir);
                clientesList.add(cli);
            }
            response.setClientes(clientesList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Clientes encontrados con exito", response);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.");
        }
    }

    public ResponseEntity<Object> findOne(Long id) throws Exception {
        try{
            Optional<Cliente> clienteFound = clienteRepository.findById(id);
            if(clienteFound.isPresent()){
                Cliente cliente = clienteFound.get();
                ClienteResponseDTO response = new ClienteResponseDTO();
                response.setId(cliente.getId());
                response.setCuit(cliente.getCuit());
                response.setEmail(cliente.getEmail());
                response.setNombre(cliente.getNombre());
                response.setApellido(cliente.getApellido());
                response.setFechaNacimiento(cliente.getFechaNacimiento());
                CondicionTributaria condicionTributaria = cliente.getCondicionTributaria();
                CondicionTributariaResponseDTO cond = new CondicionTributariaResponseDTO(condicionTributaria.getId(), condicionTributaria.getTipo());
                response.setCondicionTributaria(cond);

                Direccion direccion = cliente.getDireccion();
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

                return ResponseHandler.responseBuilder(HttpStatus.OK, "Cliente encontrado con exito", response);
            }else{
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "Cliente no existe");
            }
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.");
        }
    }

    public ResponseEntity<Object> updateCliente(Long id, ClienteDTO body) throws Exception {
        try{
            Optional<Cliente> optionalCliente = clienteRepository.findById(id);

            if (optionalCliente.isPresent()) {
                Cliente clienteToUpdate = optionalCliente.get();
                if(body.getCuit() != null){
                    clienteToUpdate.setCuit(body.getCuit());
                }
                if(body.getNombre() != null){
                    clienteToUpdate.setNombre(body.getNombre());
                }
                if(body.getApellido() != null){
                    clienteToUpdate.setApellido(body.getApellido());
                }
                if(body.getEmail() != null){
                    clienteToUpdate.setEmail(body.getEmail());
                }
                if(body.getFechaNacimiento() != null){
                    clienteToUpdate.setFechaNacimiento(body.getFechaNacimiento());
                }
                if(body.getCondicionTributariaId() != null){
                    Optional<CondicionTributaria> condicionTributaria = condicionTributariaRepository.findById(body.getCondicionTributariaId());
                    if(condicionTributaria.isEmpty()){
                        return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Condicion tributaria id no existe!", condicionTributaria);
                    }
                    clienteToUpdate.setCondicionTributaria(condicionTributaria.get());
                }
                if(body.getCalle() != null){
                    Direccion direccion = clienteToUpdate.getDireccion();
                    direccion.setCalle(body.getCalle());
                    clienteToUpdate.setDireccion(direccion);
                }
                if(body.getNumero() != null){
                    Direccion direccion = clienteToUpdate.getDireccion();
                    direccion.setNumero(body.getNumero());
                    clienteToUpdate.setDireccion(direccion);
                }
                if(body.getPiso() != null){
                    Direccion direccion = clienteToUpdate.getDireccion();
                    direccion.setPiso(body.getPiso());
                    clienteToUpdate.setDireccion(direccion);
                }
                if(body.getLocalidadId() != null){
                    Direccion direccion = clienteToUpdate.getDireccion();
                    Optional<Localidad> localidad = localidadRepository.findById(body.getLocalidadId());
                    if(localidad.isEmpty()){
                        return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Localidad id no existe!", localidad);
                    }
                    direccion.setLocalidad(localidad.get());
                    clienteToUpdate.setDireccion(direccion);
                }

                clienteRepository.save(clienteToUpdate);

                ClienteResponseDTO response = new ClienteResponseDTO();
                response.setId(clienteToUpdate.getId());
                response.setCuit(clienteToUpdate.getCuit());
                response.setNombre(clienteToUpdate.getNombre());
                response.setApellido(clienteToUpdate.getApellido());
                response.setFechaNacimiento(clienteToUpdate.getFechaNacimiento());
                Direccion direccion = clienteToUpdate.getDireccion();
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

                return ResponseHandler.responseBuilder(HttpStatus.OK, "Cliente actualizado con exito", response);
            } else {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Cliente no existe");
            }
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.");
        }
    }


    public ResponseEntity<Object> deleteCliente(Long id) throws Exception {

        try{
            Optional<Cliente> optionalCliente = clienteRepository.findById(id);

            if (optionalCliente.isPresent()) {
                Cliente clienteToDelete = optionalCliente.get();
                clienteToDelete.setBorrado(true);

                clienteRepository.save(clienteToDelete);

                ClienteResponseDTO response = new ClienteResponseDTO();
                response.setBorrado(clienteToDelete.getBorrado());
                response.setId(clienteToDelete.getId());
                response.setNombre(clienteToDelete.getNombre());
                response.setApellido(clienteToDelete.getApellido());


                return ResponseHandler.responseBuilder(HttpStatus.OK, "Cliente eliminado con exito", response);
            } else {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Cliente no existe");
            }
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.");
        }
    }

    public ResponseEntity<Object> findByMesNacimiento(String mes) throws Exception {
        try{
            Optional<Cliente> cliente = clienteRepository.findByFilter(mes);
            if(cliente.isPresent()){
                Cliente cli = cliente.get();
                ClienteResponseDTO response = new ClienteResponseDTO();
                response.setId(cli.getId());
                response.setCuit(cli.getCuit());
                response.setNombre(cli.getNombre());
                response.setApellido(cli.getApellido());
                response.setEmail(cli.getEmail());
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Cliente encontrado con exito", response);
            }else{
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "Cliente no existe");
            }
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.");
        }
    }

    public ResponseEntity<Object> findAllCondicionesTributarias() {
        try {
            List<CondicionTributaria> allCondiciones = condicionTributariaRepository.findAll();
            if (allCondiciones.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "No hay condiciones tributarias disponibles");
            }

            CondicionesTributariasResponseDTO response = new CondicionesTributariasResponseDTO();
            List<CondicionTributariaResponseDTO> condicionesList = new ArrayList<>();
            for(CondicionTributaria condicion: allCondiciones){
                CondicionTributariaResponseDTO cond = new CondicionTributariaResponseDTO(
                    condicion.getId(),
                    condicion.getTipo()
                );
                condicionesList.add(cond);
            }
            response.setCondicionesTributarias(condicionesList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Clientes encontrados con exito", response);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.");
        }
    }

}
