package dk.sublife.csv2items.ets.types;

import dk.sublife.csv2items.ets.SupportedLinePattern;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

@ToString(callSuper = true)
public class RoomTemperature extends SupportedLinePattern {

	public RoomTemperature(CSVRecord record) throws Exception {
		super(record);
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("(.*?)-RT$");
	}

	@Override
	public boolean isSupported() {
		return super.isSupported() && getType().equals("DPST-9-1");
	}

}
