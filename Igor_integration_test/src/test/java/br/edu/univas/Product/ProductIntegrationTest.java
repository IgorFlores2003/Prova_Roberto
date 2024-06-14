package br.edu.univas.Product;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.univas.dto.Employeedto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class ProductIntegrationTest {

    private ObjectMapper mapper = new ObjectMapper();
    private final String employeeURL = "http://localhost:8084/api/employee";

    @Test
    public void testGetEmployeeById_Success() {
        long employeeCpf = 12345678910L;
        try {
            Response resp = RestAssured.get(employeeURL + "/" + employeeCpf);
            assertEquals(HttpStatus.OK.value(), resp.getStatusCode());

            String jsonBody = resp.getBody().asString();
            Employeedto employee = mapper.readValue(jsonBody, Employeedto.class);

            assertNotNull(employee);
            assertEquals(employeeCpf, employee.getCpf());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Fail to get employee by id: " + employeeCpf, e);
        }
    }

    @Test
    public void testPostEmployee_Success() {
        Employeedto employee = new Employeedto(12345678911L, "vicente", new Date(), 10.5f, 2000.0f, false);
        Response resp = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(employee)
                .post(employeeURL);
        assertEquals(HttpStatus.CREATED.value(), resp.getStatusCode());
    }

    @Test
    public void testPostEmployee_AlreadyExists() {
    
        Employeedto existingEmployee = new Employeedto(12345678910L, "Existing Employee", new Date(), 10.5f, 2000.0f, false);
        Response resp = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(existingEmployee)
                .post(employeeURL);
        assertEquals(HttpStatus.CONFLICT.value(), resp.getStatusCode());
    }

    @Test
    public void testPostEmployee_InvalidData() {
        Employeedto invalidEmployee = new Employeedto(); 
        Response resp = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(invalidEmployee)
                .post(employeeURL);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resp.getStatusCode());
    }

    @Test
    public void testUpdateEmployee_Activate_Success() {
        long employeeCpf = 12345678910L; 
        try {
        
            Response respGet = RestAssured.get(employeeURL + "/" + employeeCpf);
            assertEquals(HttpStatus.OK.value(), respGet.getStatusCode());

        
            Employeedto employee = mapper.readValue(respGet.getBody().asString(), Employeedto.class);
            employee.setActive(true);

            Response respUpdate = RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .body(employee)
                    .put(employeeURL + "/" + employeeCpf);
            assertEquals(HttpStatus.OK.value(), respUpdate.getStatusCode());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail("Fail to update employee activation status with cpf: " + employeeCpf, e);
        }
    }

    @Test
    public void testUpdateEmployee_Activate_NotExists() throws JsonProcessingException {
        long employeeCpf = 98765432100L; 
        Response respGet = RestAssured.get(employeeURL + "/" + employeeCpf);
		assertEquals(HttpStatus.NOT_FOUND.value(), respGet.getStatusCode());

		Employeedto employee = new Employeedto(employeeCpf, "Non Existent", new Date(), 0.0f, 0.0f, false);
		employee.setActive(true);

		Response respUpdate = RestAssured
		        .given()
		        .contentType(ContentType.JSON)
		        .body(employee)
		        .put(employeeURL + "/" + employeeCpf);
		assertEquals(HttpStatus.NOT_FOUND.value(), respUpdate.getStatusCode());
    }
}
