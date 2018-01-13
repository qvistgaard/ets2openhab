package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

public class Radiator extends Percentage {
	public Radiator(SupportedLine supportedLine) {
		super(supportedLine);
		setIcon("radiator");
		setDpt("5.001");
	}

	public void addLine(dk.sublife.csv2items.ets.types.Radiator radiator){
		setValue(radiator);
	}

	public void addLine(dk.sublife.csv2items.ets.types.RadiatorFeedback radiatorFeedback){
		setValueFeedback(radiatorFeedback);
	}
}
