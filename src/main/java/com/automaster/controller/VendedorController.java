package com.automaster.controller;


import com.automaster.dto.VendedorRequestDTO;
import com.automaster.dto.VendedorResponseDTO;
import com.automaster.service.VendedorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/vendedores")
@Tag(name="Vendedores", description = "Todo lo relacionado con los vendedores")
public class VendedorController {

    @Autowired
    private VendedorServiceImpl vendedorService;

    @PostMapping
    @Operation(summary = "Registrar/Crear nuevo vendedor ", description = "Creacion de un nuevo vendedor  ")
    public ResponseEntity<VendedorResponseDTO> registrarVendedor(@Valid @RequestBody VendedorRequestDTO request) {
        log.info("Petición POST recibida para registrar vendedor");
        VendedorResponseDTO response = vendedorService.crearVendedor(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/rut/{rut}")
    @Operation(summary = "Obtener Vendedor por rut  ", description = "Obtiene el vendedor mediante RUT ")
    public ResponseEntity<VendedorResponseDTO> obtenerPorRut(@PathVariable String rut) {
        log.info("Petición GET recibida para buscar vendedor por RUT");
        VendedorResponseDTO response = vendedorService.buscarPorRut(rut);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // --- RUTA PARA LISTAR TODOS ---
    @GetMapping
    @Operation(summary = "Listar todos los vendedores", description = "Obtiene una lista completa de todos los vendedores registrados")
    public ResponseEntity<List<VendedorResponseDTO>> listarVendedores() {
        log.info("Petición REST GET entrante para listar todos los vendedores");
        List<VendedorResponseDTO> response = vendedorService.listarTodos();
        return new ResponseEntity<>(response, HttpStatus.OK); // Devuelve 200 OK
    }

    // --- RUTA PARA ELIMINAR ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar vendedor", description = "Elimina un vendedor del sistema usando su ID")
    public ResponseEntity<Void> eliminarVendedor(@PathVariable Long id) {
        log.info("Petición REST DELETE entrante para el ID: {}", id);
        vendedorService.eliminarVendedor(id);

        // HttpStatus.NO_CONTENT (204) es el estándar oficial cuando eliminas algo con éxito
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}