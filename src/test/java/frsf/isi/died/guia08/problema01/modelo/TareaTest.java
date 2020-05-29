package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;



public class TareaTest {
	Empleado e1;
	Empleado e2;
	Tarea t1;
	
	
	@Before
	public void init() {
		t1 = new Tarea(1, "Una tarea", 4);
		e1 = new Empleado(1, "Domingo", 3.0, Tipo.CONTRATADO);
		e2 = new Empleado(2222, "Fidel Comiso", 500.0, Tipo.EFECTIVO);
	}
	
	
	@Test
	public void asignarEmpleadoCorrectoTest() {
		
		
		try{
			t1.asignarEmpleado(e1);
		}
		catch(TareaAsignadaIncorrectaException e) {
			
			fail();
		}
		
		
		assertEquals(e1, t1.getEmpleadoAsignado());
		
	}
	
	
	@Test(expected = TareaAsignadaIncorrectaException.class)
	public void asignarEmpleadoExcepcionTest() throws TareaAsignadaIncorrectaException{
		t1.asignarEmpleado(e1);
		t1.setFechaInicio(LocalDateTime.now());
		t1.setFechaFin(LocalDateTime.now());
		t1.asignarEmpleado(e2);
	}

}


