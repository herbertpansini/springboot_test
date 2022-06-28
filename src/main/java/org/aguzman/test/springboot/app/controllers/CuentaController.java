package org.aguzman.test.springboot.app.controllers;

import lombok.RequiredArgsConstructor;
import org.aguzman.test.springboot.app.models.Cuenta;
import org.aguzman.test.springboot.app.services.CuentaService;
import org.aguzman.test.springboot.app.services.TransaccionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cuenta detalle(@PathVariable Long id) {
        return this.cuentaService.findById(id);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransaccionDto transaccionDto) {
        this.cuentaService.transferir(transaccionDto.getCuentaOrigenId(), transaccionDto.getCuentaDestinoId(), transaccionDto.getMonto(), transaccionDto.getBancoId());

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Transferencia realizada con éxito!");
        response.put("transaccion", transaccionDto);

        return ResponseEntity.ok(response);
    }
}