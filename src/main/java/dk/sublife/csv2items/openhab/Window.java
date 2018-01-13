package dk.sublife.csv2items.openhab;


import dk.sublife.csv2items.ets.SupportedLine;

public class Window extends Switch {

	public Window(SupportedLine supportedLine) {
		super(supportedLine);
	}

	@Override
	public String openhabSitemap() {
		return openhabSitemap("Text", getName());
	}

	@Override
	public String getLabel() {
		return super.getLabel()+"[MAP(window.map):%s]";
	}
}
