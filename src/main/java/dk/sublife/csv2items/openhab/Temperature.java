package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.Line;
import dk.sublife.csv2items.ets.SupportedLine;

public class Temperature extends Number {
	public Temperature(SupportedLine supportedLine) {
		super(supportedLine);
	}

/*	public Temperature(final String name, final Line line, final String dpt, final String... tags) {
		setName(name);
		setGroups(new String[]{line.getRoom()});
		setTags(tags);
		setLabel(name);
		setDpt(dpt);
		setIcon("temperature");
	}*/

	@Override
	public String getLabel() {
		return super.getLabel()+" [%.2f °C]";
	}
}
