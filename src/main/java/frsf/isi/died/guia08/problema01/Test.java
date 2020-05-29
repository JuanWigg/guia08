package frsf.isi.died.guia08.problema01;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {

	public static void main(String[] args) {
		DateTimeFormatter df  = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm");
		String fecha = "19-05-2020 12:34";
		LocalDateTime ldt = LocalDateTime.parse(fecha, df);
		System.out.println(ldt);

	}

}
