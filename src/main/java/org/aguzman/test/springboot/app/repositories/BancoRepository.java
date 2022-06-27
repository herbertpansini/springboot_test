package org.aguzman.test.springboot.app.repositories;

import org.aguzman.test.springboot.app.models.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {
}