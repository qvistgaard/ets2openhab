package dk.sublife.csv2items.openhab;


import dk.sublife.csv2items.ets.SupportedLine;

public class StatusBoolean extends Contact {

	public StatusBoolean(SupportedLine supportedLine) {
		super(supportedLine);
		setIcon("switch");
	}
	public void addLine(dk.sublife.csv2items.ets.types.StatusBoolean statusBoolean){
		setOnOff(statusBoolean);
	}
	public void addLine(dk.sublife.csv2items.ets.types.Status16bit status16bit){
		setOnOff(status16bit);
	}
	public void addLine(dk.sublife.csv2items.ets.types.Status8bit status8bit){
		setOnOff(status8bit);
	}

	@Override
	public String openhabSitemap() {
		return openhabSitemap("Text", getName());
	}

	@Override
	public String getLabel() {
		return super.getLabel()+"[MAP(boolean.map):%s]";
	}
}
