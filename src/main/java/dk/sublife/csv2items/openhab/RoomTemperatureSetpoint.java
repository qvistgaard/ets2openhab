package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

@Data
public class RoomTemperatureSetpoint extends Temperature {

	public RoomTemperatureSetpoint(SupportedLine supportedLine) {
		super(supportedLine);
	}

	public void addLine(dk.sublife.csv2items.ets.types.RoomTemperatureSetpoint roomTemperatureSetpoint){
		setValue(roomTemperatureSetpoint);
	}

	public void addLine(dk.sublife.csv2items.ets.types.RoomTemperatureSetpointFeedback roomTemperatureSetpointFeedback){
		setValueFeedback(roomTemperatureSetpointFeedback);
	}

	@Override
	public String openhabSitemap() {
		return openhabSitemap("Setpoint", getName(), " minValue=7 maxValue=30 step=0.5");
	}
}
