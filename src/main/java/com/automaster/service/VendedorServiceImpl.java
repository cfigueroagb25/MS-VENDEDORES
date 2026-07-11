package com.automaster.service;

import com.automaster.dto.VendedorRequestDTO;
import com.automaster.dto.VendedorResponseDTO;
import com.automaster.model.Sucursal;
import com.automaster.model.Vendedor;
import com.automaster.repository.SucursalRepository;
import com.automaster.repository.VendedorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VendedorServiceImpl {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    public VendedorResponseDTO crearVendedor(VendedorRequestDTO request) {
        log.info("Iniciando creación de vendedor con RUT: {}", request.getRut());

        if (vendedorRepository.existsByRut(request.getRut())) {
            log.error("El vendedor con RUT {} ya existe.", request.getRut());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El RUT ya está registrado.");
        }

        Sucursal sucursal = sucursalRepository.findById(request.getIdSucursal())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La sucursal indicada no existe."));

        Vendedor vendedor = new Vendedor();
        vendedor.setRut(request.getRut());
        vendedor.setNombre(request.getNombre());
        vendedor.setMetaVentas(request.getMetaVentas());
        vendedor.setComisiones(request.getComisiones());
        vendedor.setSucursal(sucursal);

        Vendedor guardado = vendedorRepository.save(vendedor);
        log.info("Vendedor creado con éxito. ID: {}", guardado.getId());

        return mapearADTO(guardado);
    }

    public VendedorResponseDTO buscarPorRut(String rut) {
        log.info("Buscando vendedor por RUT: {}", rut);
        Vendedor vendedor = vendedorRepository.findByRut(rut)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendedor no encontrado."));
        return mapearADTO(vendedor);
    }

    private VendedorResponseDTO mapearADTO(Vendedor vendedor) {
        VendedorResponseDTO dto = new VendedorResponseDTO();
        dto.setId(vendedor.getId());
        dto.setRut(vendedor.getRut());
        dto.setNombre(vendedor.getNombre());
        dto.setMetaVentas(vendedor.getMetaVentas());
        dto.setComisiones(vendedor.getComisiones());
        dto.setIdSucursal(vendedor.getSucursal().getId());
        dto.setNombreSucursal(vendedor.getSucursal().getNombre());
        return dto;
    }

    // --- MÉTODO PARA LISTAR TODOS ---
    public List<VendedorResponseDTO> listarTodos() {
        log.info("Consultando la base de datos para listar todos los vendedores");

        // findAll() ya viene incluido en JpaRepository
        List<Vendedor> vendedores = vendedorRepository.findAll();

        // Convertimos la lista de Entidades a una lista de DTOs
        return vendedores.stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    // --- MÉTODO PARA ELIMINAR ---
    public void eliminarVendedor(Long id) {
        log.info("Iniciando proceso para eliminar vendedor con ID: {}", id);

        // Validamos si existe antes de intentar borrarlo
        if (!vendedorRepository.existsById(id)) {
            log.error("Error al eliminar: No se encontró vendedor con ID {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El vendedor con el ID indicado no existe.");
        }

        // Si existe, lo eliminamos
        vendedorRepository.deleteById(id);
        log.info("Vendedor con ID {} eliminado exitosamente", id);
    }
}
