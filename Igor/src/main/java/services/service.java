package services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.dto.*;
import com.example.suport.ProductException;
import com.example.Employe.*;
import repositori.Repositorio;
import com.example.converter.*;

@Service
public class service{
private Repositorio re;

@Autowired
private Converter converter;

@Autowired
public service(Repositorio repo) {
	this.re = repo;
}

public List<Employeedto> findAll() {
	return re.findAll().stream().map(converter::Employeedto)
			.collect(Collectors.toList());
}

public Employee findById(Integer code) {
	Optional<Employee> obj = repo.findById(code);
	Employee entity = obj.orElseThrow(() -> new ObjectNotFoundException("Object not found: " + code));
	return entity;
}

public List<Employeedto> findByActive(boolean b) {
	return re.findByActive(true).stream().map(converter::toDTO).collect(Collectors.toList());
}

public void createProduct(Employeedto product) {
	re.save(converter.toEntity(product));
}

public void updateProduct(Employeedto product, Integer code) {
	if (code == null || product == null || !code.equals(product.getCpf())) {
		throw new ProductException("Invalid product code.");
	}
	Employee existingObj = findById(code);
	updateData(existingObj, product);
	re.save(existingObj);
}

private void updateData(Employee existingObj, Employeedto newObj) {
	existingObj.setName(newObj.getName());
}

public void deleteProduct(Integer code) {
	if (code == null) {
		throw new ProductException("Product code can not be null.");
	}
	Employee obj = findById(code);
	try {
		re.delete(obj);
		
	} catch (DataIntegrityViolationException e) {
		throw new ProductException("Can not delete a Product with dependencies constraints.");
	}
}
}