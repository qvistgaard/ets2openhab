package dk.sublife.csv2items.ets;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;

@Data
@ToString(callSuper = true)
abstract public class LineBase implements Line {
	private final String name;
	private final Address address;
	private final String description;
	private final String type;
	private final Settings settings;

	public LineBase(CSVRecord record) throws Exception {
		name = record.get(0);
		address = new Address(record.get(1));
		description = record.get(4);
		type = record.get(5);
		settings = new Settings(record.get(4));
	}

	@Override
	public String getRoom(){
		return name.replaceAll("-.*", "");
	}



	@Override
	public int compareTo(Line o) {
		return getName().compareTo(o.getName());
	}
}
