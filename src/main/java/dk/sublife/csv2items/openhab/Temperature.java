package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.Line;
import dk.sublife.csv2items.ets.SupportedLine;

public class Temperature extends Number {
	public Temperature(SupportedLine supportedLine) {
		super(supportedLine);
		setIcon("temperature");
		setDpt("9.001");
		getGroups().add("Temperatures");
	}

	@Override
	public String getLabel() {
		return super.getLabel()+" [%.2f °C]";
	}
}
