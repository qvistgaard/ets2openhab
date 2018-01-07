package dk.sublife.csv2items.ets.types;

import dk.sublife.csv2items.ets.Line;
import dk.sublife.csv2items.ets.SupportedLine;
import dk.sublife.csv2items.ets.SupportedLinePattern;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

@ToString(callSuper = true)
public class Contact extends SupportedLinePattern {

	public Contact(CSVRecord record) throws Exception {
		super(record);
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("(.*?)-CONTACT$");
	}

	@Override
	public boolean isSupported() {
		return super.isSupported() && (getType().equals("DPST-1-2") || getType().equals("DPST-1-1"));
	}
}
