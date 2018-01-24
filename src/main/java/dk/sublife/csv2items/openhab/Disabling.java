package dk.sublife.csv2items.openhab;


import dk.sublife.csv2items.ets.SupportedLine;

public class Disabling extends Switch {

	public Disabling(SupportedLine supportedLine) {
		super(supportedLine);
		getGroups().add("Disabling");
	}
	public void addLine(dk.sublife.csv2items.ets.types.Disabling disabling){
		setOnOff(disabling);
	}


}
