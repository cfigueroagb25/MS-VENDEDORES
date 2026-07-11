package com.automaster.config;

import com.automaster.model.Sucursal;
import com.automaster.model.Vendedor;
import com.automaster.repository.SucursalRepository;
import com.automaster.repository.VendedorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


    @Slf4j
    @Component
    public class DataInitializer implements CommandLineRunner {

        @Autowired
        private SucursalRepository sucursalRepository;

        @Autowired
        private VendedorRepository vendedorRepository;

        @Override
        public void run(String... args) throws Exception {
            if (sucursalRepository.count() == 0) {
                Sucursal s1 = new Sucursal();
                s1.setNombre("Casa Matriz");
                s1.setDireccion("Avenida Principal 123");
                sucursalRepository.save(s1);
                log.info("Sucursal base creada.");
            }

            if (vendedorRepository.count() == 0) {
                Sucursal sucursalBase = sucursalRepository.findAll().get(0);

                Vendedor v1 = new Vendedor();
                v1.setRut("98.765.432-1");
                v1.setNombre("Diego Silva");
                v1.setMetaVentas(50000000.0);
                v1.setComisiones(0.0);
                v1.setSucursal(sucursalBase);
                vendedorRepository.save(v1);

                log.info("Vendedor de prueba creado con éxito.");
            }
        }
    }


