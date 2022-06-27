package org.aguzman.test.springboot.app;

import org.aguzman.test.springboot.app.repositories.BancoRepository;
import org.aguzman.test.springboot.app.repositories.CuentaRepository;
import org.aguzman.test.springboot.app.services.CuentaService;
import org.aguzman.test.springboot.app.services.impl.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringbootTestApplicationTests {

	CuentaRepository cuentaRepository;
	BancoRepository bancoRepository;
	CuentaService cuentaService;

	@BeforeEach
	void setup() {
		cuentaRepository = mock(CuentaRepository.class);
		bancoRepository = mock(BancoRepository.class);
		cuentaService = new CuentaServiceImpl(cuentaRepository, bancoRepository);
	}

	@Test
	void contextLoads() {
		when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
		when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
		when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);

		BigDecimal saldoOrigen = cuentaService.revisarSaldo(1L);
		BigDecimal saldoDestino = cuentaService.revisarSaldo(2L);

		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		cuentaService.transferir(1L, 2L, new BigDecimal("100"), 1L);
		saldoOrigen = cuentaService.revisarSaldo(1L);
		saldoDestino = cuentaService.revisarSaldo(2L);

		assertEquals("900", saldoOrigen.toPlainString());
		assertEquals("2100", saldoDestino.toPlainString());
	}
}