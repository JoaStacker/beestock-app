package com.api.crud.services.impl;


import com.api.crud.persistence.entities.*;
import com.api.crud.persistence.repositories.IEmpleadoRepository;
import com.api.crud.persistence.repositories.IVentaRepository;
import com.api.crud.services.IVentaService;
import com.api.crud.services.models.dtos.ProveedorDTO;
import com.api.crud.services.models.dtos.VentaDTO;
import com.api.crud.services.models.response.ResponseHandler;
import com.api.crud.services.models.response.direccion.DireccionResponseDTO;
import com.api.crud.services.models.response.direccion.LocalidadResponseDTO;
import com.api.crud.services.models.response.direccion.PaisResponseDTO;
import com.api.crud.services.models.response.direccion.ProvinciaResponseDTO;
import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;
import com.api.crud.services.models.response.proveedor.ProveedorResponseDTO;
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
public class VentaServiceImpl implements IVentaService {

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    public ResponseEntity<Object> getAll() throws Exception {
        try{
            List<Venta> allVentas = ventaRepository.findAll();
            if (allVentas.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "No hay ventas disponibles");
            }

            VentasResponseDTO response = new VentasResponseDTO();
            List<VentaResponseDTO> ventasList = new ArrayList<>();
            for(Venta venta: allVentas){
                VentaResponseDTO ven = new VentaResponseDTO();
                ven.setId(venta.getId());
                ven.setFechaVenta(venta.getFechaVenta());
                Empleado empleado = venta.getEmpleado();
                ven.setEmpleado(new EmpleadoResponseDTO(empleado.getId(), empleado.getDni(), empleado.getNombre(), empleado.getApellido()));
                ventasList.add(ven);
            }
            response.setVentas(ventasList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Ventas devueltas con exito", response);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al devolver ventas: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> createVenta(VentaDTO body) throws Exception {
        try{
            // Validar existencias en base de datos.
            Optional<Empleado> empleadoExistente = empleadoRepository.findById(body.getEmpleadoId());
            if(empleadoExistente.isEmpty()){
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "El Empleado no existe.");
            }

//              TODO
//            Optional<Cliente> clienteExistente = clienteRepository.findById(body.getClienteId());
//            if(clienteExistente.isEmpty()){
//                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "El Cliente no existe.");
//            }

            Empleado empleado = empleadoExistente.get();
//            Cliente cliente = clienteExistente.get();
            Venta venta = new Venta(
                    body.getFechaVenta(),
                    body.getMontoTotal(),
                    body.getCantidadCuotas(),
                    body.getEstado(),
                    empleado
            );

            // TODO Agregar detalles de venta

            ventaRepository.save(venta);

            VentaResponseDTO response = new VentaResponseDTO();
            response.setId(venta.getId());
            response.setFechaVenta(venta.getFechaVenta());
            response.setMontoTotal(venta.getMontoTotal());
            response.setCantidadCuotas(venta.getCantidadCuotas());
            response.setEstado(venta.getEstado());
            response.setEmpleado(new EmpleadoResponseDTO(empleado.getId(), empleado.getDni(), empleado.getNombre(), empleado.getApellido()));
            return ResponseHandler.responseBuilder(HttpStatus.CREATED, "Venta creada con éxito!", response);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear Venta: "
                    + e.getMessage());
        }
    }
}
