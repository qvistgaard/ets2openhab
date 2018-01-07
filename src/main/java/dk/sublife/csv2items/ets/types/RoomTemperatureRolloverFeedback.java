package dk.sublife.csv2items.ets.types;

import dk.sublife.csv2items.ets.Line;
import dk.sublife.csv2items.ets.SupportedLine;
import dk.sublife.csv2items.ets.SupportedLinePattern;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

@ToString(callSuper = true)
public class RoomTemperatureRolloverFeedback extends SupportedLinePattern {

	private final static Pattern supports = Pattern.compile("(.*?)-RTR-VFB$");

	public RoomTemperatureRolloverFeedback(CSVRecord record) throws Exception {
		super(record);
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("(.*?)-RTR-VFB$");
	}

	@Override
	public boolean isSupported() {
		return super.isSupported() && getType().equals("DPT-5");
	}

}
