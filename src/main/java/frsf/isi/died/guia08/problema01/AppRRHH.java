package frsf.isi.died.guia08.problema01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.modelo.Tarea;
import frsf.isi.died.guia08.problema01.modelo.TareaAsignadaIncorrectaException;
import frsf.isi.died.guia08.problema01.modelo.TareaNoExistenteException;

public class AppRRHH {

	private List<Empleado> empleados;
	
	public void agregarEmpleadoContratado(Integer cuil,String nombre,Double costoHora) {
		Empleado e1 = new Empleado(cuil, nombre, costoHora, Tipo.CONTRATADO);
		empleados.add(e1);
	}
	
	public void agregarEmpleadoEfectivo(Integer cuil,String nombre,Double costoHora) {
		Empleado e1 = new Empleado(cuil, nombre, costoHora, Tipo.EFECTIVO);
		empleados.add(e1);	
	}
	
	public void asignarTarea(Integer cuil,Integer idTarea,String descripcion,Integer duracionEstimada) throws EmpleadoNoEncontradoException {
		Predicate<Empleado> p = emp -> emp.getCuil().equals(cuil);
		if(this.buscarEmpleado(p).isPresent()) {
			Tarea t = new Tarea(idTarea, descripcion, duracionEstimada);
			try{
				if(this.buscarEmpleado(p).get().asignarTarea(t))
					System.out.println("Tarea asignada al empleado con exito");
				else {
					System.out.println("El empleado no puede ser asignado a la tarea");
				}
				
			}
			catch(TareaAsignadaIncorrectaException e) {
				System.out.println("La tarea ya fue asignada a otro empleado, o ya tiene fecha de fin estipulada");
			}
		}
		else {
			throw(new EmpleadoNoEncontradoException());
		}
	}
	
	public void empezarTarea(Integer cuil,Integer idTarea) throws EmpleadoNoEncontradoException{
		Predicate<Empleado> p = emp -> emp.getCuil().equals(cuil);
		if(this.buscarEmpleado(p).isPresent()) {
			
			try{
				this.buscarEmpleado(p).get().comenzar(idTarea);
			}
			catch(TareaNoExistenteException e) {
				System.out.println("La tarea que intenta empezar no esta asignada a este empleado");
			}
			
		}
		else {
			throw(new EmpleadoNoEncontradoException());
		}
	}
	
	public void terminarTarea(Integer cuil,Integer idTarea) throws EmpleadoNoEncontradoException{
		Predicate<Empleado> p = emp -> emp.getCuil().equals(cuil);
		if(this.buscarEmpleado(p).isPresent()) {
			
			try{
				this.buscarEmpleado(p).get().finalizar(idTarea);
			}
			catch(TareaNoExistenteException e) {
				System.out.println("La tarea que intenta finalizar no esta asignada a este empleado");
			}
			
		}
		else {
			throw(new EmpleadoNoEncontradoException());
		}	
	}

	public void cargarEmpleadosContratadosCSV(String nombreArchivo) {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado
		
		try {
			FileReader archivo = new FileReader(nombreArchivo);
			BufferedReader lectorBuff = new BufferedReader(archivo);
			String linea;
			
			while((linea = lectorBuff.readLine()) != null) {
				String[] partes = linea.split(";");
				agregarEmpleadoContratado(Integer.parseInt(partes[0]),partes[1], Double.parseDouble(partes[2]));
			}
			
			archivo.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No se encontró el archivo especificado en " + nombreArchivo);
		}
		catch (IOException e) {
			System.out.println("No se pudo leer el archivo");
		}	
	}

	public void cargarEmpleadosEfectivosCSV(String nombreArchivo) {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado	
		try {
			FileReader archivo = new FileReader(nombreArchivo);
			BufferedReader lectorBuff = new BufferedReader(archivo);
			String linea;
			
			while((linea = lectorBuff.readLine()) != null) {
				String[] partes = linea.split(";");
				agregarEmpleadoEfectivo(Integer.parseInt(partes[0]),partes[1], Double.parseDouble(partes[2]));
			}
			
			archivo.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No se encontró el archivo especificado en " + nombreArchivo);
		}
		catch (IOException e) {
			System.out.println("No se pudo leer el archivo");
		}
	}

	public void cargarTareasCSV(String nombreArchivo) {
		// leer datos del archivo
		// cada fila del archivo tendrá:
		// cuil del empleado asignado, numero de la taera, descripcion y duración estimada en horas.
		try {
			FileReader archivo = new FileReader(nombreArchivo);
			BufferedReader lectorBuff = new BufferedReader(archivo);
			String linea;
			
			while((linea = lectorBuff.readLine()) != null) {
				String[] partes = linea.split(";");
				try {
					this.asignarTarea(Integer.parseInt(partes[3])
							,Integer.parseInt(partes[0])
							,partes[1]
							,Integer.parseInt(partes[2]));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EmpleadoNoEncontradoException e) {
					// TODO Auto-generated catch block
					System.out.println("No se encontró ningún empleado con el CUIL especificado");
				}
			}
			
			archivo.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No se encontró el archivo especificado en " + nombreArchivo);
		}
		catch (IOException e) {
			System.out.println("No se pudo leer el archivo");
		}
		
	}
	
	private void guardarTareasTerminadasCSV() {
		// guarda una lista con los datos de la tarea que fueron terminadas
		// y todavía no fueron facturadas
		// y el nombre y cuil del empleado que la finalizó en formato CSV 
		
		List<List<Tarea>> todasLasTareas = this.empleados.stream().map(e -> e.getTareasAsignadas()).collect(Collectors.toList());
		List<Tarea> tareasGuardar = todasLasTareas.stream()
				.flatMap(List::stream)
				.filter(t -> t.getFechaFin()!=null && !t.getFacturada())
				.collect(Collectors.toList());
		
		File archivoGuardado = new File("TareasTerminadas.csv");
		if (!archivoGuardado.exists()) {
            try {
				archivoGuardado.createNewFile();
			} catch (IOException e1) {
				System.out.println("El archivo para guardar las tareas no pudo ser creado");
			}
        }
		
		
		FileWriter fw;
		try {
			fw = new FileWriter(archivoGuardado);
			BufferedWriter bw = new BufferedWriter(fw);
			for(Tarea t: tareasGuardar) {
				String lineaT = t.getId() + ";" 
						+ t.getDescripcion() + ";" 
						+ t.getFechaFin().toString() + ";" 
						+ t.getEmpleadoAsignado().getCuil() + ";" 
						+ t.getEmpleadoAsignado().getNombre();
				bw.write(lineaT);
				bw.newLine();
			} 
			
			fw.close();
			
		} catch (IOException e1) {
			System.out.println("Hubo un error al intentar escribir en el archivo");
		}
		
		
		
	}
	
	private Optional<Empleado> buscarEmpleado(Predicate<Empleado> p){
		return this.empleados.stream().filter(p).findFirst();
	}

	public Double facturar() {
		this.guardarTareasTerminadasCSV();
		return this.empleados.stream()				
				.mapToDouble(e -> e.salario())
				.sum();
	}
}
