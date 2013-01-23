package Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class CreateFile {

	
	
	public static void main(String[] args) throws IOException {
		String inputFile = "D:/workspace/segaDentalSist/Data/prueba.txt";
		
		BufferedWriter writer;
		writer = new BufferedWriter(new FileWriter(inputFile));
		writer.write("Reporte con datos que están fallando");
		writer.newLine();
		writer.close();
		System.out.println("Archivo creado");
	}
	
}
