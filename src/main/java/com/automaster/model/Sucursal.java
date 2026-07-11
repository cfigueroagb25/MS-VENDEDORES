package com.automaster.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sucursales")
@Data
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;
}
