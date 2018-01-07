package dk.sublife.csv2items.ets.types;

import dk.sublife.csv2items.ets.SupportedLine;
import dk.sublife.csv2items.ets.SupportedLinePattern;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

@ToString(callSuper = true)
public class LightDimmer extends SupportedLinePattern {

	private final static Pattern pattern = Pattern.compile("(.*?)-DIM$");

	public LightDimmer(CSVRecord record) throws Exception {
		super(record);
	}

	@Override
	public boolean isSupported() {
		return pattern.matcher(getName()).matches() && getType().equals("DPST-3-7");
	}

	@Override
	public Pattern getPattern() {
		return pattern;
	}
}
