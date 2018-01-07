package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

@Data
public class Radiator extends Percentage {
	private dk.sublife.csv2items.ets.types.Radiator radiator;
	dk.sublife.csv2items.ets.types.RadiatorFeedback radiatorFeedback;

	public Radiator(SupportedLine supportedLine) {
		super(supportedLine);
	}

	public void addLine(dk.sublife.csv2items.ets.types.Radiator radiator){
		setValue(radiator);
	}

	public void addLine(dk.sublife.csv2items.ets.types.RadiatorFeedback radiatorFeedback){
		setValueFeedback(radiatorFeedback);
	}
}
