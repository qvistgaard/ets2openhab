package dk.sublife.csv2items.openhab;


import dk.sublife.csv2items.ets.Line;
import dk.sublife.csv2items.ets.SupportedLine;

public class Window extends Switch {

	public Window(SupportedLine supportedLine) {
		super(supportedLine);
	}
/*	public Window(final String name, final Line line, final String... tags){
		setName(name);
		setGroups(new String[]{line.getRoom()});
		setTags(tags);
		setLabel(name);
		setIcon("window");
	}*/

	@Override
	public String openhabSitemap() {
		return openhabSitemap("Text", getName());
	}

	@Override
	public String getLabel() {
		return super.getLabel()+"[MAP(window.map):%s]";
	}
}
