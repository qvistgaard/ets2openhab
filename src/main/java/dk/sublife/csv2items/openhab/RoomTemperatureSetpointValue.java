package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

@Data
public class RoomTemperatureSetpointValue extends Temperature {

	public RoomTemperatureSetpointValue(SupportedLine supportedLine) {
		super(supportedLine);
	}

	public void addLine(dk.sublife.csv2items.ets.types.RoomTemperatureSetpointValue roomTemperatureSetpointValue){
		setValue(roomTemperatureSetpointValue);
	}

}
