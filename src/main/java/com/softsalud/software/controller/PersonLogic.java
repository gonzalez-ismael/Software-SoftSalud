package com.softsalud.software.controller;

import com.softsalud.software.persistence.entity.Address;
import com.softsalud.software.persistence.entity.Person;
import com.softsalud.software.persistence.service.interfaces.IAddressService;
import com.softsalud.software.persistence.service.interfaces.IPersonService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gonzalez Ismael
 */
public class PersonLogic {

    private final IPersonService personServi;
    private final IAddressService addressServi;

    public PersonLogic(IPersonService personService, IAddressService addressService) {
        this.personServi = personService;
        this.addressServi = addressService;
    }

    public void listPerson(JTable tableFrame) {
        DefaultTableModel table = (DefaultTableModel) tableFrame.getModel();

        table.setRowCount(0);

        List<Person> lista = personServi.getPeople();
//        List<Address> lista1 = addressServi.getAddresses();
//        List<Phone> lista2 = phoneServi.getPhones();

        Object[] object = new Object[8];

        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getDni();
            object[1] = lista.get(i).getName();
            object[2] = lista.get(i).getLast_name();
            object[3] = lista.get(i).getBirthdate();
            object[4] = lista.get(i).getAge();
            object[5] = lista.get(i).isHas_covid();
            object[6] = lista.get(i).isHas_transplants();
            object[7] = lista.get(i).getRisk_factor();
            table.addRow(object);
        }
    }

    public boolean addPerson(JRootPane rootPane, Long dni, String lastName, String name, String birthdate, Long phone,
            Long optional_phone, String neibor, String street, Integer houseNum,
            boolean covid, boolean transplants, String riskFactor) {
        boolean operationFlag = false;
        if (!emptyFields(dni, lastName, name, birthdate, phone, neibor, street, houseNum, riskFactor)) {
            try {
                LocalDate datePerson = convertToDate(birthdate);
                int age = calculateAge(datePerson);
                Address address = addAddress(neibor, street, houseNum);

                Person p = new Person();
                p.setDni(dni);
                p.setName(name);
                p.setLast_name(lastName);
                p.setBirthdate(datePerson);
                p.setAge(age);
                p.setRisk_factor(riskFactor);
                p.setHas_covid(covid);
                p.setHas_transplants(transplants);
                p.setPhone_number(phone);
                if(optional_phone != null){
                    p.setOptional_phone_number(optional_phone);
                }
                p.setAddress(address);
                
                personServi.savePerson(p);
                operationFlag = true;
            } catch (ParseException ex) {
                Logger.getLogger(PersonLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane,
                    "Exiten campos vacíos. Revise e intente de nuevo.",
                    "Ups",
                    JOptionPane.ERROR_MESSAGE);
        }
        return operationFlag;
    }

    private Address addAddress(String neibor, String street, Integer houseNum) {
        return addressServi.saveAddress2(neibor, street, houseNum);
    }

    private boolean emptyFields(Long dni, String lastName, String name, String birthdate, Long phone,
            String neibor, String street, Integer houseNum, String riskFactor) {
        // Verifica que ninguna de las variables esté vacía o nula
        return (Objects.equals(dni, "") || "".equals(lastName) || "".equals(name) || "".equals(birthdate) || Objects.equals(phone, "")
                || "".equals(neibor) || "".equals(street) || "".equals(houseNum)
                || "".equals(riskFactor));
    }

    private LocalDate convertToDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateString);
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    private int calculateAge(LocalDate birthdate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthdate, currentDate).getYears();
    }
}
