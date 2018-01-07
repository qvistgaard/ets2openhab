package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

@Data
public class RoomTemperatureRolloverValue extends Temperature {

	public RoomTemperatureRolloverValue(SupportedLine supportedLine) {
		super(supportedLine);
	}

	public void addLine(dk.sublife.csv2items.ets.types.RoomTemperatureRolloverValue roomTemperatureRolloverValue){
		setValue(roomTemperatureRolloverValue);
	}

}
