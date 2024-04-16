package com.example.dto;

	import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	public class Employeedto {
		private long cpf;
		private String name;
		private Date LastTrainingDate;
		private float extraWorkedHours;
		private float payrollTotalValue;
	

		
	}

