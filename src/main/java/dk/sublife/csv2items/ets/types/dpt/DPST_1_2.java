package dk.sublife.csv2items.ets.types.dpt;

import dk.sublife.csv2items.ets.SupportedLinePattern;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

@ToString(callSuper = true)
abstract public class DPST_1_2 extends SupportedLinePattern {

	public DPST_1_2(CSVRecord record) throws Exception {
		super(record);
	}

	@Override
	public boolean isSupported() {
		return super.isSupported() && (getType().equals("DPST-1-2"));
	}
}
