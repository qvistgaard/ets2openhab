package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

@Data
public class DateTime extends Item {
	private SupportedLine date;
	private SupportedLine time;

	public DateTime(SupportedLine supportedLine) {
		super(supportedLine);
	}

	public void addLine(dk.sublife.csv2items.ets.types.DateTimeDate dateTimeDate){
		setDate(dateTimeDate);
	}
	public void addLine(dk.sublife.csv2items.ets.types.DateTimeTime dateTimeTime){
		setTime(dateTimeTime);
	}

	public String openhabSitemap() {
		return openhabSitemap("Text", getName());
	}

	@Override
	public String openhab() {
		StringBuilder sb = new StringBuilder();

		sb.append("11.001:"+getDate().getAddress());
		sb.append(", ");
		sb.append("10.001:"+getTime().getAddress());
		sb.append("\", channel=\"ntp:ntp:local:dateTime");


		return openhab("DateTime", getName(), getLabel(), getIcon(), getGroups(), getTags(), sb.toString());
	}

	@Override
	public String getLabel() {
		return super.getLabel()+" [%s]";
	}


}
