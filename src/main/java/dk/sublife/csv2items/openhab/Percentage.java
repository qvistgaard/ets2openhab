package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.Line;
import dk.sublife.csv2items.ets.SupportedLine;

public class Percentage extends Number {
	public Percentage(SupportedLine supportedLine) {
		super(supportedLine);
	}

/*	public Percentage(final String name, final Line line, final String dpt, final String... tags) {
		setName(name);
		setGroups(new String[]{line.getRoom()});
		setTags(tags);
		setLabel(name);
		setIcon("heating");
		setDpt(dpt);
	}*/


	@Override
	public String getLabel() {
		return super.getLabel()+" [%d %%]";
	}
}
