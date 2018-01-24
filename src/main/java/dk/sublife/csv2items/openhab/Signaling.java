package dk.sublife.csv2items.openhab;


import dk.sublife.csv2items.ets.SupportedLine;

public class Signaling extends Switch {

	public Signaling(SupportedLine supportedLine) {
		super(supportedLine);
		getGroups().add("Signaling");
	}
	public void addLine(dk.sublife.csv2items.ets.types.Signaling signaling){
		setOnOff(signaling);
	}
}
