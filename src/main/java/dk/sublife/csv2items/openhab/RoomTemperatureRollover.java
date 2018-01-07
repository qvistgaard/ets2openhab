package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

@Data
public class RoomTemperatureRollover extends Number {

	public RoomTemperatureRollover(SupportedLine supportedLine) {
		super(supportedLine);
	}

	public void addLine(dk.sublife.csv2items.ets.types.RoomTemperatureRollover roomTemperatureRollover){
		setValue(roomTemperatureRollover);
	}

	public void addLine(dk.sublife.csv2items.ets.types.RoomTemperatureRolloverFeedback roomTemperatureRolloverFeedback){
		setValueFeedback(roomTemperatureRolloverFeedback);
	}
}
