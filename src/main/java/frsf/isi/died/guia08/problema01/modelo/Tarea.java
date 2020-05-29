package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Tarea {

	private Integer id;
	private String descripcion;
	private Integer duracionEstimada;
	private Empleado empleadoAsignado;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private Boolean facturada;
	private Integer retraso;
	
	public Tarea(Integer id) {
		this.id = id;
		this.facturada = false;
	}
	public Tarea(Integer id, String descripcion, Integer duracionEstimada) {
		this.id = id;
		this.descripcion = descripcion;
		this.duracionEstimada = duracionEstimada;
		this.facturada = false;
	}
	
	public void asignarEmpleado(Empleado e) throws TareaAsignadaIncorrectaException {
		if(this.empleadoAsignado != null && this.fechaFin!=null)
			throw(new TareaAsignadaIncorrectaException());
		else
			this.empleadoAsignado = e;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracionEstimada() {
		return duracionEstimada;
	}

	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
		LocalDateTime fechaFinEstipulada = fechaInicio.plusDays(this.duracionEstimada/4);
		long retraso = fechaFinEstipulada.until(fechaInicio, ChronoUnit.DAYS);
		this.retraso = (int) retraso;
	}
	
	public Integer getRetraso() {
		return this.retraso;
	}
	public Boolean getFacturada() {
		return facturada;
	}

	public void setFacturada(Boolean facturada) {
		this.facturada = facturada;
	}

	public Empleado getEmpleadoAsignado() {
		return empleadoAsignado;
	}
	
	
	
}
