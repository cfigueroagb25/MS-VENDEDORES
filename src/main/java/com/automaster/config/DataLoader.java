package com.automaster.config;

import com.automaster.model.Sucursal;
import com.automaster.model.Vendedor;
import com.automaster.repository.SucursalRepository;
import com.automaster.repository.VendedorRepository;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        log.info("Iniciando la carga de datos de prueba para Sucursales y Vendedores...");

        // 1. Crear Sucursales de prueba para poder asignarlas (ManyToOne)
        List<Sucursal> sucursales = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Sucursal sucursal = new Sucursal();

            // LÍNEA CORREGIDA: Asigna una dirección ficticia para cumplir con la restricción de la BD
            sucursal.setDireccion(faker.address().fullAddress());

            // Si tu entidad Sucursal también tiene un atributo "nombre", descomenta la línea de abajo:
            // sucursal.setNombre("Sucursal " + faker.address().cityName());

            sucursales.add(sucursalRepository.save(sucursal));
        }

        // 2. Crear Vendedores mapeando exactamente los atributos de tu imagen
        for (int i = 0; i < 30; i++) {
            Vendedor vendedor = new Vendedor();

            // Atributo: rut (String, unique=true)
            vendedor.setRut(faker.regexify("[0-9]{8}-[0-9kK]"));

            // Atributo: nombre (String)
            vendedor.setNombre(faker.name().fullName());

            // Atributo: metaVentas (Double)
            vendedor.setMetaVentas(faker.number().randomDouble(0, 1000000, 10000000));

            // Atributo: comisiones (Double)
            vendedor.setComisiones(faker.number().randomDouble(0, 0, 500000));

            // Atributo: sucursal (Relación ManyToOne)
            Sucursal sucursalAsignada = sucursales.get(random.nextInt(sucursales.size()));
            vendedor.setSucursal(sucursalAsignada);

            // Guardar vendedor
            vendedorRepository.save(vendedor);
        }

        log.info("¡Datos de prueba de Vendedores generados con éxito!");
    }
}
