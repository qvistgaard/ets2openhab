package dk.sublife.csv2items.ets;

import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

public abstract class SupportedLinePattern extends SupportedLine {
	public SupportedLinePattern(CSVRecord record) throws Exception {
		super(record);
	}

	abstract protected Pattern getPattern();

	@Override
	public boolean isSupported() {
		return getPattern().matcher(getName()).matches();
	}

	@Override
	public String getItemName() {
		return getPattern().matcher(getName()).replaceAll("$1");
	}
}
