package br.edu.univas.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Employeedto{
    private long cpf;
    private String name;
    private Date lastTrainingDate;
    private float extraWorkedHours;
    private float payrollTotalValue;
    private boolean active;
}