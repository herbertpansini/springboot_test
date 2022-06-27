package org.aguzman.test.springboot.app.services.impl;

import org.aguzman.test.springboot.app.models.Banco;
import org.aguzman.test.springboot.app.models.Cuenta;
import org.aguzman.test.springboot.app.repositories.BancoRepository;
import org.aguzman.test.springboot.app.repositories.CuentaRepository;
import org.aguzman.test.springboot.app.services.CuentaService;

import java.math.BigDecimal;

public class CuentaServiceImpl implements CuentaService {
    private CuentaRepository cuentaRepository;
    private BancoRepository bancoRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    public Cuenta findById(Long id) {
        return this.cuentaRepository.findById(id);
    }

    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        return this.bancoRepository.findById(bancoId).getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        return this.findById(cuentaId).getSaldo();
    }

    @Override
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {
        Cuenta cuentaOrigen = this.findById(numCuentaOrigen);
        cuentaOrigen.debito(monto);
        this.cuentaRepository.update(cuentaOrigen);

        Cuenta cuentaDestino = this.findById(numCuentaDestino);
        cuentaDestino.credito(monto);
        this.cuentaRepository.update(cuentaDestino);

        Banco banco = this.bancoRepository.findById(bancoId);
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        this.bancoRepository.update(banco);
    }
}