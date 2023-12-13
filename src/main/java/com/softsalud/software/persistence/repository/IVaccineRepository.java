package com.softsalud.software.persistence.repository;

import com.softsalud.software.persistence.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gonzalez Ismael
 */
@Repository
public interface IVaccineRepository extends JpaRepository<Vaccine, Long>{
    
}
