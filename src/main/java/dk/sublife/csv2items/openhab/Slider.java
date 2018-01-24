package dk.sublife.csv2items.openhab;


import dk.sublife.csv2items.ets.SupportedLine;

public class Slider extends Switch {

	public Slider(SupportedLine supportedLine) {
		super(supportedLine);
		setIcon("qualityofservice");
	}
	public void addLine(dk.sublife.csv2items.ets.types.BlindsSunPos blindsSunPos){
		setOnOff(blindsSunPos);
	}

	@Override
	public String openhabSitemap() {
		return openhabSitemap("Slider", getName());
	}
}
