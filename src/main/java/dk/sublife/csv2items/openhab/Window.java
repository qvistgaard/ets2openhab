package dk.sublife.csv2items.openhab;


import dk.sublife.csv2items.ets.SupportedLine;

public class Window extends Contact {

	public Window(SupportedLine supportedLine) {
		super(supportedLine);
		setIcon("window");
	}
	public void addLine(dk.sublife.csv2items.ets.types.Window window){
		setOnOff(window);
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
