package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

@Data
public class RoomTemperature extends Temperature {

	public RoomTemperature(SupportedLine supportedLine) {
		super(supportedLine);
		getGroups().add("RoomTemperatures");

	}

	public void addLine(dk.sublife.csv2items.ets.types.RoomTemperature roomTemperature){
		setValue(roomTemperature);
	}

}
