package com.automaster.dto;

import lombok.Data;

@Data
public class VendedorResponseDTO {
        private Long id;
        private String rut;
        private String nombre;
        private Double metaVentas;
        private Double comisiones;
        private Long idSucursal;
        private String nombreSucursal;
}
