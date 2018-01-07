package dk.sublife.csv2items.ets;

import org.apache.commons.csv.CSVRecord;
import org.reflections.Reflections;

import java.util.regex.Pattern;

public interface Line extends Comparable<Line> {
	static Line create(CSVRecord record) {
		try {
			final Settings settings = new Settings(record.get(4));
			String ignore = (String) settings.getOrDefault("ignore", "false");
			if (ignore.equals("true")){
				return new LineIgnore(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		final Reflections reflections = new Reflections("dk.sublife.csv2items.ets.types");
		for (Class<? extends Line> aClass : reflections.getSubTypesOf(Line.class)) {
			try {
				return aClass.getConstructor(CSVRecord.class).newInstance(record);
			} catch (Exception ignored) { }
		}
		throw new RuntimeException("Unknown record type: "+record.toString());
	}

	String getRoom();

	boolean isSupported();


	@Override
	int compareTo(Line o);

	String getName();
	Address getAddress();
	String getDescription();
	String getType();
	Settings getSettings();
}
