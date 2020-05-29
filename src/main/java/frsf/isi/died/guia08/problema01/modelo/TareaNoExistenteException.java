/**
 * 
 */
package frsf.isi.died.guia08.problema01.modelo;

/**
 * @author juanwigg
 *
 */
public class TareaNoExistenteException extends Exception{
	public TareaNoExistenteException() {
		super("La tarea a comenzar no esta en la lista de tareas asignadas al empleado");
	}
}
