package com.automaster.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class VendedorRequestDTO {
    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "La meta de ventas es obligatoria")
    @Positive(message = "La meta de ventas debe ser mayor a cero")
    private Double metaVentas;

    @NotNull(message = "Las comisiones son obligatorias")
    @Min(value = 0, message = "Las comisiones no pueden ser negativas")
    private Double comisiones;

    @NotNull(message = "El ID de la sucursal es obligatorio")
    private Long idSucursal;

}
