package dk.sublife.csv2items.ets.types;

import dk.sublife.csv2items.ets.SupportedLinePattern;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

@ToString(callSuper = true)
public class BlindsSlatsStep extends SupportedLinePattern {

	public BlindsSlatsStep(CSVRecord record) throws Exception {
		super(record);
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("(.*?)-SLATS-STEP");
	}

	@Override
	public boolean isSupported() {
		return super.isSupported() && (getType().equals("DPST-1-7"));
	}
}
