package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

@Data
public class RoomTemperatureRolloverValue extends Number {

	public RoomTemperatureRolloverValue(SupportedLine supportedLine) {
		super(supportedLine);
		setDpt("5.010");
	}

	public void addLine(dk.sublife.csv2items.ets.types.RoomTemperatureRolloverValue roomTemperatureRolloverValue){
		setValue(roomTemperatureRolloverValue);
	}

	@Override
	public String getLabel() {
		return super.getLabel()+" [MAP(rtr.map):%s]";
	}


}
