package cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.IParameterSplitter;

import benchmark.dataset.AttrDataset;
import benchmark.dataset.SDataset;
import io.AttributedGraphSetReader;

public class KCommon {

	public class RangeConverter implements IStringConverter<List<String>> {

		@Override
		public List<String> convert(String value) {
			return null;
		}
	}

	
	public static class RangeSplitter implements IParameterSplitter {
		public List<String> split(String value) {
			List<String> r = new ArrayList<>();
			if (value.startsWith("[") || value.startsWith("{")) {
				value = value.substring(1);
			}
			if (value.endsWith("]") || value.endsWith("]")) {
				value = value.substring(0, value.length()-1);
			}
			String[] values;
			if (value.contains(",")) {
				values = value.split(",");
			} else if (value.contains(";")) {
				values = value.split(";");
			} else {
				values = value.split(" ");
			}
			if (values.length==1) {
				if (values[0].contains(":")) {
					String[] lu = values[0].split(":");
					if (lu.length != 2) throw new ParameterException("Failed to parse '"+value+"'.");
					int lower = Integer.valueOf(lu[0]);
					int upper = Integer.valueOf(lu[1]);
					if (lower>upper) throw new ParameterException("Failed to parse '"+value+"'.");
					for (int i=lower; i<=upper; i++) {
						r.add(String.valueOf(i));
					}
					return r;
				}
			}

			for (String s : values) {
				r.add(s);
			}

			return r;

		}
	}
	
	static class CommandMain {
		@Parameter(names = { "-v", "--verbose" }, description = "Enable high level of verbosity")
		boolean verbose = false;
		
		@Parameter(names = { "-h", "--help" }, description = "Show help screen", help = true) 
		boolean help;
	}
	
	static ArrayList<AttrDataset> load(List<String> names, File dataDir) throws IOException {
		ArrayList<AttrDataset> ds = new ArrayList<AttrDataset>();
		for (String d : names) { ds.add(load(d, dataDir)); }
		return ds;
	}
	
	static AttrDataset load(String name, File dataDir) throws IOException {
		String data = name+"/"+name;
		AttrDataset ds = new AttrDataset(name);
		AttributedGraphSetReader agr = new AttributedGraphSetReader();
		ds.addAll(agr.read(dataDir.getAbsolutePath()+"/"+data));
		return ds;
	}

	static void runCommand(String command, boolean verbose) throws IOException, InterruptedException {
		Process proc = Runtime.getRuntime().exec(command);
		BufferedReader br= new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		
		String line = null;
		while ((line = br.readLine()) != null) {
			if (verbose) System.out.println(line);
		}
		br.close();

		int exitVal = proc.waitFor();
		if (exitVal != 0) {
			System.out.println("Error!");
		} else {
			System.out.println("Success!");
		}
	}
	
	static List<String> getDatasets(File dataDir) {
		List<String> bk = new LinkedList<>();
		for (File f : dataDir.listFiles()) {
			if (!f.isDirectory()) continue;
			bk.add(f.getName());
		}
		return bk;		
	}
}
