package dk.sublife.csv2items.ets.types;

import dk.sublife.csv2items.ets.types.dpt.DPST_1_2;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

@ToString(callSuper = true)
public class BlindsPresence extends DPST_1_2 {

	public BlindsPresence(CSVRecord record) throws Exception {
		super(record);
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("(.*?-PRESENCE)-SW");
	}
}
