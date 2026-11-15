package ar.edu.utn.frc.dsi.bolsines.repository;

import ar.edu.utn.frc.dsi.bolsines.domain.Bolsin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BolsinRepository extends JpaRepository<Bolsin, Long> {}
