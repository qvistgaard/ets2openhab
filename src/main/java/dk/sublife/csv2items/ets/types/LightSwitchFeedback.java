package dk.sublife.csv2items.ets.types;

import dk.sublife.csv2items.ets.Line;
import dk.sublife.csv2items.ets.SupportedLine;
import dk.sublife.csv2items.ets.SupportedLinePattern;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

@ToString(callSuper = true)
public class LightSwitchFeedback extends SupportedLinePattern {

	public LightSwitchFeedback(CSVRecord record) throws Exception {
		super(record);
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("(.*?)-SW-FB$");
	}

	@Override
	public boolean isSupported() {
		return super.isSupported() && getType().equals("DPST-1-1");
	}
}
