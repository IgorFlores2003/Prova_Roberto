package com.example.converter;

import com.example.dto.*;

import com.example.Employe.*;

import org.springframework.stereotype.Component;

@Component
public class Converter {

    public Employeedto employeetodto(Employee employee) {
        return new Employeedto(
                employee.getCpf(),
                employee.getName(),
                employee.getLastTrainingDate(),
                employee.getExtraWorkedHours(),
                employee.getPayrollTotalValue()
        );
    }
    public Employee toEntity(Employeedto dto) {
		System.out.println("toEntity: " + dto);
		return new Employee( 
				   dto.getCpf(),
        		   dto.getName(),
        		   dto.getLastTrainingDate(),
        		   dto.getExtraWorkedHours(),
        		   dto.getPayrollTotalValue(), false
        		  );
	}
}
   
   