package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.Line;
import dk.sublife.csv2items.ets.SupportedLine;

public class RTR extends Number {
	public RTR(SupportedLine supportedLine) {
		super(supportedLine);
	}
/*	public RTR(final String name, final Line line, final String dpt, final String... tags) {
		setName(name);
		setGroups(new String[]{line.getRoom()});
		setTags(tags);
		setLabel(name);
		setDpt(dpt);
		setIcon("heating");
	}*/

	@Override
	public String getLabel() {
		return super.getLabel()+" [MAP(rtr.map):%s]";
	}
}
