package com.softsalud.software.persistence.repository;

import com.softsalud.software.persistence.entity.DoseHistory;
import com.softsalud.software.persistence.entity.config.PersonVaccineId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gonzalez Ismael
 */
@Repository
public interface IDoseHistoryRepository extends JpaRepository<DoseHistory,PersonVaccineId>{
    //CONSULTAS PERSONLAIZADAS
    @Query("SELECT dh FROM DoseHistory dh WHERE dh.number_doses = 1")
    List<DoseHistory> findPeopleWithOneDose();
}
