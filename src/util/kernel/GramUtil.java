package util.kernel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;



public class GramUtil {
	
	public static void writeLibSVMFile(double[][] m, String[] classes, String fileName) throws IOException {
		FileWriter fw = new FileWriter(fileName, false);
		BufferedWriter bw = new BufferedWriter(fw);
		GramUtil.writeLibSVMString(m, classes, bw);
		bw.close();
	}
	
	public static void writeLibSVMFile(GramMatrix gram, String fileName) throws IOException {
		GramUtil.writeLibSVMFile(gram.gram, gram.classes, fileName);
	}
	
	public static void writeLibSVMFile(GramMatrix gram, String fileName, ArrayList<Integer> rows, ArrayList<Integer> columns) throws IOException {
		FileWriter fw = new FileWriter(fileName, false);
		BufferedWriter bw = new BufferedWriter(fw);
		writeLibSVMString(gram.gram, gram.classes, rows, columns, bw);
		bw.close();
	}
	
	/**
	 * Writes data in LibSVM format.
	 * @param m the matrix of quadratic size
	 * @param classes the class labels
	 * @param w the output writer 
	 * @throws IOException
	 */
	private static void writeLibSVMString(double[][] m, String[] classes, Writer w) throws IOException {
		ArrayList<Integer> rc = new ArrayList<>();
		for (int i=0;i<m.length; i++) {
			rc.add(i);
		}
		writeLibSVMString(m, classes, rc, rc, w);
	}

	/**
	 * Writes data in LibSVM format.
	 * @param m the matrix
	 * @param classes the class labels
	 * @param rows restrict to these rows 
	 * @param columns restrict to these columns
	 * @param w the output writer 
	 * @throws IOException
	 */
	private static void writeLibSVMString(double[][] m, String[] classes, ArrayList<Integer> rows, ArrayList<Integer> columns, Writer w) throws IOException {
		int idRow=1;
		for (int i : rows) {
			w.append(classes[i]);
			w.append(" 0:"+idRow++);
			int idColumn=1;
			for (int j : columns) {
				double val = m[i][j];
				// such small values result in an error with strtod()
				// used by libsvm when parsing the input file.
				// it should be safe to set the value to zero here!
				if (val > 0 && val <= 1E-300) {
					System.out.println("Warning: "+val+" was set to 0");
					val = 0d;
				}
				w.append(" "+(idColumn++)+":"+val);
			}
			w.append("\n");
		}
	}

}
