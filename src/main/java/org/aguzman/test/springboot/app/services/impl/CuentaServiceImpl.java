package org.aguzman.test.springboot.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.aguzman.test.springboot.app.models.Banco;
import org.aguzman.test.springboot.app.models.Cuenta;
import org.aguzman.test.springboot.app.repositories.BancoRepository;
import org.aguzman.test.springboot.app.repositories.CuentaRepository;
import org.aguzman.test.springboot.app.services.CuentaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class CuentaServiceImpl implements CuentaService {
    private final CuentaRepository cuentaRepository;
    private final BancoRepository bancoRepository;

    @Override
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return this.cuentaRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public int revisarTotalTransferencias(Long bancoId) {
        return this.bancoRepository.findById(bancoId).orElseThrow().getTotalTransferencias();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal revisarSaldo(Long cuentaId) {
        return this.findById(cuentaId).getSaldo();
    }

    @Override
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {
        Cuenta cuentaOrigen = this.findById(numCuentaOrigen);
        cuentaOrigen.debito(monto);
        this.cuentaRepository.save(cuentaOrigen);

        Cuenta cuentaDestino = this.findById(numCuentaDestino);
        cuentaDestino.credito(monto);
        this.cuentaRepository.save(cuentaDestino);

        Banco banco = this.bancoRepository.findById(bancoId).orElseThrow();
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        this.bancoRepository.save(banco);
    }
}