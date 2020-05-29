package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;

public class EmpleadoTest {

	// IMPORTANTE
	// ESTA CLASE ESTA ANOTADA COMO @IGNORE por lo que no ejecutará ningun test
	// hasta que no borre esa anotación.
	
	@Test
	public void testSalario() {
		Tarea t1 = new Tarea(1, "Una tarea", 4);
		Tarea t2 = new Tarea(2, "Hola", 5);
		
		t1.setFechaInicio(LocalDateTime.now());
		t2.setFechaInicio(LocalDateTime.now());
		
		t2.setFechaFin(LocalDateTime.now().plusDays(1));
		Empleado e1 = new Empleado(23, "CLAU", 3.00, Tipo.CONTRATADO);
		try {
			e1.asignarTarea(t1);
			e1.asignarTarea(t2);
		} catch (TareaAsignadaIncorrectaException e) {
			fail("No deberia tirar excepcion");
		}
		
		assertTrue(e1.salario().equals(31.5));
	}

	@Test
	public void testCostoTarea() {
		Tarea t1 = new Tarea(1, "Una tarea", 4);
		Tarea t2 = new Tarea(2, "Hola", 5);
		
		t1.setFechaInicio(LocalDateTime.now());
		t2.setFechaInicio(LocalDateTime.now());
		
		t2.setFechaFin(LocalDateTime.now().plusDays(1));
		Empleado e1 = new Empleado(23, "CLAU", 3.00, Tipo.CONTRATADO);
		
		assertTrue(e1.costoTarea(t1).equals(12.00));
		assertTrue(e1.costoTarea(t2).equals(19.5));
	}

	@Test
	public void testAsignarTarea() {
		Empleado e1 = new Empleado(23, "CLAU", 3.00, Tipo.CONTRATADO);
		Tarea t1 = new Tarea(1, "Hola", 5);
		Tarea t2 = new Tarea(2, "Hola", 5);
		Tarea t3 = new Tarea(3, "Hola", 5);
		Tarea t4 = new Tarea(4, "Hola", 5);
		Tarea t5 = new Tarea(5, "Hola", 5);
		Tarea t6 = new Tarea(6, "Hola", 5);
		
		t1.setFechaInicio(LocalDateTime.now());
		t2.setFechaInicio(LocalDateTime.now());
		t3.setFechaInicio(LocalDateTime.now());
		t4.setFechaInicio(LocalDateTime.now());
		t5.setFechaInicio(LocalDateTime.now());
		t6.setFechaInicio(LocalDateTime.now());
		
		
		t1.setFechaFin(LocalDateTime.now());
		t2.setFechaFin(LocalDateTime.now());
		t3.setFechaFin(LocalDateTime.now());
		t4.setFechaFin(LocalDateTime.now());
		t5.setFechaFin(LocalDateTime.now());
		t6.setFechaFin(LocalDateTime.now());
		
		try {
			assertTrue(e1.asignarTarea(t1));
			assertTrue(e1.asignarTarea(t2));
			assertTrue(e1.asignarTarea(t3));
			assertTrue(e1.asignarTarea(t4));
			assertTrue(e1.asignarTarea(t5));
			assertFalse(e1.asignarTarea(t6));
		} catch (TareaAsignadaIncorrectaException e) {
			fail("No deberia haber tirado excepcion");
		}
		
		
	}

	@Test
	public void testComenzarInteger() {
		Tarea t1 = new Tarea(1);
		Empleado e1 = new Empleado(23, "CLAU", 3.00, Tipo.CONTRATADO);
		try{
			e1.asignarTarea(t1);
		}
		catch(TareaAsignadaIncorrectaException e) {
			fail();
		}
		
		try {
			e1.comenzar(1);
			assertTrue(e1.getTareasAsignadas().contains(t1));
		} 
		catch (TareaNoExistenteException e) {
			fail("Deberia haber salido bien");
		}
		
		
	}
	
	@Test(expected = TareaNoExistenteException.class)
	public void testComenzarIntegerException() throws TareaNoExistenteException {
		Tarea t1 = new Tarea(1);
		Empleado e1 = new Empleado(23, "CLAU", 3.00, Tipo.CONTRATADO);
		
		e1.comenzar(1);
		
		
	}

	
	
	@Test
	public void testFinalizarInteger() {
		Tarea t1 = new Tarea(1, "HOla", 23);
		Empleado e1 = new Empleado(23, "CLAU", 3.00, Tipo.CONTRATADO);
		t1.setFechaInicio(LocalDateTime.now());
		
		try {
			e1.asignarTarea(t1);
		} catch (TareaAsignadaIncorrectaException e) {
			fail("No deberia haber fallado");
		}
		
		try {
			e1.finalizar(1);
			assertTrue(t1.getFechaFin()!=null);
		} catch (TareaNoExistenteException e) {
			fail("No deberia haber fallado");
		}
		
		
		
		
	}

	@Test(expected = TareaNoExistenteException.class)
	public void testFinalizarIntegerException() throws TareaNoExistenteException {
		Tarea t1 = new Tarea(1);
		Empleado e1 = new Empleado(23, "CLAU", 3.00, Tipo.CONTRATADO);
		e1.finalizar(1);
	}
	
	
	
	
	@Test
	public void testComenzarIntegerString() {
		Tarea t1 = new Tarea(1, "HOla", 23);
		Empleado e1 = new Empleado(23, "CLAU", 3.00, Tipo.CONTRATADO);
		
		try {
			e1.asignarTarea(t1);
		} catch (TareaAsignadaIncorrectaException e) {
			fail("No deberia fallar");
		}
		//Por alguna razón esto deberia funcionar y no funciona, no puede parsear el string
		String fecha = "29-06-2020 22:40";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime fechaParaComenzar = LocalDateTime.parse(fecha, formatter);
		
		try {
			e1.comenzar(1, fecha);
		} catch (TareaNoExistenteException e) {
			fail("No deberia haber lanzado excepcion");
		}
		assertTrue(t1.getFechaInicio().equals(fechaParaComenzar));
		
	}

	@Test
	public void testFinalizarIntegerString() {
		Tarea t1 = new Tarea(1, "HOla", 23);
		Empleado e1 = new Empleado(23, "CLAU", 3.00, Tipo.CONTRATADO);
		
		try {
			e1.asignarTarea(t1);
		} catch (TareaAsignadaIncorrectaException e) {
			fail("No deberia fallar");
		}
		t1.setFechaInicio(LocalDateTime.now());
		
		//Por alguna razón esto deberia funcionar y no funciona, no puede parsear el string
		String fecha = "24-06-2020 22:40";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime fechaParaTerminar = LocalDateTime.parse(fecha, formatter);
		
		try {
			e1.finalizar(1, fecha);
		} catch (TareaNoExistenteException e) {
			fail("No deberia haber lanzado excepcion");
		}
		assertTrue(t1.getFechaFin().equals(fechaParaTerminar));
	}

}
