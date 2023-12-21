package com.softsalud.software.persistence.repository;

import com.softsalud.software.persistence.entity.DoseHistory;
import com.softsalud.software.persistence.entity.config.PersonVaccineId;
//import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gonzalez Ismael
 */
@Repository
public interface IDoseHistoryRepository extends JpaRepository<DoseHistory, PersonVaccineId> {

//    //CONSULTAS PERSONLAIZADAS
//    @Query("SELECT dh.person.dni, dh.person.last_name, dh.person.name, dh.vaccination_date, dh.vaccination_place, dh.vaccine_lot "
//            + "FROM DoseHistory dh "
//            + "INNER JOIN vaccine as v on (dh.getId().getVaccine().getCode() = v.code) "
//            + "WHERE v.nameVaccine = :nameVaccine")
//    List<Object[]> listPeoplePerVaccine(@Param("nameVaccine") String nameVaccine);

}
