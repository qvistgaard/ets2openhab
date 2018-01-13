package dk.sublife.csv2items.ets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.csv.CSVRecord;

@Data
@ToString(callSuper = true)

abstract public class SupportedLine extends LineBase {

	public SupportedLine(CSVRecord record) throws Exception {
		super(record);
		if(!isSupported()){
			throw new Exception("Unsupported");
		}
	}

	abstract public String getItemName();
	public boolean isReadable(boolean defaultValue){
		return Boolean.valueOf(getSettings().getProperty("readable", String.valueOf(defaultValue)));
	}

/*
	public enum Type {
		LIGHT_SWITCH,
		LIGHT_SWITCH_FEEDBACK,
		LIGHT_DIMMER,
		LIGHT_DIMMER_VALUE,
		LIGHT_DIMMER_VALUE_FEEDBACK,
		BINARY_INPUT,
		RADIATOR,
		RADIATOR_FEEDBACK,
		ROOM_TEMPERATURE_ROLLOVER,
		ROOM_TEMPERATURE_ROLLOVER_VALUE,
		ROOM_TEMPERATURE,
		ROOM_TEMPERATURE_SETPOINT,
		ROOM_TEMPERATURE_SETPOINT_FEEDBACK,
		ROOM_TEMPERATURE_SETPOINT_VALUE,
		ROOM_TEMPERATURE_ROLLOVER_FEEDBACK
	}

	private final Map<Line.Type,Pattern> patterns = new TreeMap<Line.Type,Pattern>(){{
		put(Line.Type.LIGHT_SWITCH, Pattern.compile("(.*?)-SW$"));
		put(Line.Type.LIGHT_SWITCH_FEEDBACK, Pattern.compile("(.*?)-SW-FB$"));
		put(Line.Type.LIGHT_DIMMER, Pattern.compile("(.*?)-DIM$"));
		put(Line.Type.LIGHT_DIMMER_VALUE, Pattern.compile("(.*?)-DIM-VAL$"));
		put(Line.Type.LIGHT_DIMMER_VALUE_FEEDBACK, Pattern.compile("(.*?)-DIM-VFB$"));
		put(Line.Type.BINARY_INPUT, Pattern.compile("(.*?)-BI$"));
		put(Line.Type.RADIATOR, Pattern.compile("(.*?)-RAD$"));
		put(Line.Type.RADIATOR_FEEDBACK, Pattern.compile("(.*?)-RAD(.*?)-VFB$"));
		put(Line.Type.ROOM_TEMPERATURE_ROLLOVER, Pattern.compile("(.*?)-RTR$"));
		put(Line.Type.ROOM_TEMPERATURE_ROLLOVER_FEEDBACK, Pattern.compile("(.*?)-RTR-VFB$"));
		put(Line.Type.ROOM_TEMPERATURE_ROLLOVER_VALUE, Pattern.compile("(.*?)-RTR-VAL$"));
		put(Line.Type.ROOM_TEMPERATURE, Pattern.compile("(.*?)-RT$"));
		put(Line.Type.ROOM_TEMPERATURE_SETPOINT, Pattern.compile("(.*?)-RT-SP$"));
		put(Line.Type.ROOM_TEMPERATURE_SETPOINT_VALUE, Pattern.compile("(.*?)-RT-SP-VAL$"));
		put(Line.Type.ROOM_TEMPERATURE_SETPOINT_FEEDBACK, Pattern.compile("(.*?)-RT-SP-VFB$"));
	}};



	public String getRoom(){
		return name.replaceAll("-.*", "");
	}

	public String getItemName(){
		final Line.Type type = getTypeOld();
		String prefix = "";
		switch (type){
			case LIGHT_SWITCH:
				prefix = "iLight";
				break;
			case LIGHT_SWITCH_FEEDBACK:
				prefix = "iLight";
				break;
			case LIGHT_DIMMER:
				prefix = "iLight";
				break;
			case LIGHT_DIMMER_VALUE:
				prefix = "iLight";
				break;
			case LIGHT_DIMMER_VALUE_FEEDBACK:
				prefix = "iLight";
				break;
			case BINARY_INPUT:
				prefix = "iBin";
				break;
			case RADIATOR:
				prefix = "iRad";
				break;
			case RADIATOR_FEEDBACK:
				prefix = "iRad";
				break;
			case ROOM_TEMPERATURE_ROLLOVER:
				prefix = "iRTR";
				break;
			case ROOM_TEMPERATURE_ROLLOVER_FEEDBACK:
				prefix = "iRTR";
				break;
			case ROOM_TEMPERATURE_ROLLOVER_VALUE:
				prefix = "iRTRVal";
				break;
			case ROOM_TEMPERATURE:
				prefix = "iRT";
				break;
			case ROOM_TEMPERATURE_SETPOINT:
				prefix = "iSP";
				break;
			case ROOM_TEMPERATURE_SETPOINT_FEEDBACK:
				prefix = "iSP";
				break;
			case ROOM_TEMPERATURE_SETPOINT_VALUE:
				prefix = "iSPVal";
				break;
		}
		return String.format("%s %s", prefix, patterns.get(type).matcher(name).replaceAll("$1"));
	}

	public Line.Type getTypeOld(){
		final Optional<Map.Entry<Line.Type, Pattern>> first = patterns.entrySet().stream()
				.filter(p -> p.getValue().matcher(name).matches())
				.findFirst();
		if(first.isPresent()){
			return first.get().getKey();
		}
		return null;
	}*/
}
