package dk.sublife.csv2items.ets;

import lombok.ToString;
import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

@ToString(callSuper = true)
public class LineIgnore extends LineBase {

	public LineIgnore(CSVRecord record) throws Exception {
		super(record);
	}

	@Override
	public boolean isSupported() {
		return false;
	}
}
