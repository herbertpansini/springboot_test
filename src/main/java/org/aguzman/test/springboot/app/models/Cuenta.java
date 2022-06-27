package org.aguzman.test.springboot.app.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aguzman.test.springboot.app.exceptions.DineroInsuficienteException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String persona;
    private BigDecimal saldo;

    public void debito(BigDecimal monto) {
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new DineroInsuficienteException("Dinero insuficiente en la cuenta");
        }
        this.saldo = nuevoSaldo;
    }

    public void credito(BigDecimal monto) {
        this.saldo = this.saldo.add(monto);
    }
}