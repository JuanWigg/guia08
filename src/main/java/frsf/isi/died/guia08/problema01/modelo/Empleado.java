package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Empleado {

	public enum Tipo { CONTRATADO,EFECTIVO}; 
	
	private Integer cuil;
	private String nombre;
	private Tipo tipo;
	private Double costoHora;
	private List<Tarea> tareasAsignadas;
	
	private Function<Tarea, Double> calculoPagoPorTarea;		
	private Predicate<Tarea> puedeAsignarTarea;

	public Empleado(Integer cuil, String nombre, Double costoHora, Tipo tipo) {
		this.cuil = cuil;
		this.nombre = nombre;
		this.costoHora = costoHora;
		this.tipo = tipo;
		this.tareasAsignadas = new ArrayList<Tarea>();
		
		
		switch(this.tipo) {
		case CONTRATADO:
			puedeAsignarTarea = t -> this.tareasAsignadas.stream()
			.filter(tarea -> tarea.getFechaFin()!=null)
			.count() < 5;
			
			calculoPagoPorTarea = tarea -> tarea.getRetraso()>2?
					tarea.getDuracionEstimada()*(this.costoHora-(this.costoHora/4))
					:tarea.getDuracionEstimada()*(this.costoHora + ((this.costoHora*30)/100));
			
			break;
			
		case EFECTIVO:
			puedeAsignarTarea = t -> (this.tareasAsignadas.stream()
			.filter(tarea -> tarea.getFechaFin()!=null)
			.map(ta -> ta.getDuracionEstimada())
			.reduce(0, (a, b) -> a+b) + t.getDuracionEstimada()) <= 15;
			
			calculoPagoPorTarea = tarea -> tarea.getDuracionEstimada()*(this.costoHora + (this.costoHora/5));
			
			break;
		}
		}
	
	
	
	public Double salario() {
		// cargar todas las tareas no facturadas
		// calcular el costo
		// marcarlas como facturadas
		
		List<Tarea> tareasNoFacturadas = this.tareasAsignadas.stream()
				.filter(t -> !t.getFacturada())
				.collect(Collectors.toList());
		
		tareasNoFacturadas.stream().forEach(t -> t.setFacturada(true));
		Double resultado = tareasNoFacturadas.stream()
		.mapToDouble(t -> this.costoTarea(t))
		.sum();
		
		return resultado;
	}
	
	
	public Integer getCuil() {
		return this.cuil;
	}
	
	
	/**
	 * Si la tarea ya fue terminada nos indica cuaal es el monto según el algoritmo de calculoPagoPorTarea
	 * Si la tarea no fue terminada simplemente calcula el costo en base a lo estimado.
	 * @param t
	 * @return
	 */
	public Double costoTarea(Tarea t) {
		if(t.getFechaFin()==null) {
			   return t.getDuracionEstimada()*this.costoHora;
		}
		else {
				return this.calculoPagoPorTarea.apply(t);
		}
	}
		
	public Boolean asignarTarea(Tarea t) throws TareaAsignadaIncorrectaException {
		if(puedeAsignarTarea.test(t)) {
			try {
				t.asignarEmpleado(this);
				this.tareasAsignadas.add(t);
				return true;
			}
			catch(TareaAsignadaIncorrectaException e) {
				throw e;
			}
				
			
		}
		else
			return false;
		
	}
	
	public void comenzar(Integer idTarea) throws TareaNoExistenteException{
		if(!this.tareasAsignadas.stream().anyMatch(t -> t.getId().equals(idTarea))) {
			throw(new TareaNoExistenteException());
		}
		else{
			this.tareasAsignadas.stream().filter(t -> t.getId().equals(idTarea)).forEach(t -> t.setFechaInicio(LocalDateTime.now()));
		}
	}
	
	public void finalizar(Integer idTarea) throws TareaNoExistenteException {
		if(!this.tareasAsignadas.stream().anyMatch(t -> t.getId().equals(idTarea))) {
			throw(new TareaNoExistenteException());
		}
		else{
			this.tareasAsignadas.stream().filter(t -> t.getId().equals(idTarea)).forEach(t -> t.setFechaFin(LocalDateTime.now()));
		}
	}

	public void comenzar(Integer idTarea,String fecha) throws TareaNoExistenteException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyyy HH:mm");
		LocalDateTime fechaParaInicio = LocalDateTime.parse(fecha, formatter);
		
		if(!this.tareasAsignadas.stream().anyMatch(t -> t.getId().equals(idTarea))) {
			throw(new TareaNoExistenteException());
		}
		else{
			this.tareasAsignadas.stream().filter(t -> t.getId().equals(idTarea)).forEach(t -> t.setFechaInicio(fechaParaInicio));
		}
		
	}
	public List<Tarea> getTareasAsignadas() {
		return this.tareasAsignadas;
	}
	
	public String getNombre() {
		return this.getNombre();
	}
	public void finalizar(Integer idTarea,String fecha) throws TareaNoExistenteException{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime fechaParaFin = LocalDateTime.parse(fecha, formatter);
		
		
		if(!this.tareasAsignadas.stream().anyMatch(t -> t.getId().equals(idTarea))) {
			throw(new TareaNoExistenteException());
		}
		else{
			this.tareasAsignadas.stream().filter(t -> t.getId().equals(idTarea)).forEach(t -> t.setFechaFin(fechaParaFin));
		}
	}
}
