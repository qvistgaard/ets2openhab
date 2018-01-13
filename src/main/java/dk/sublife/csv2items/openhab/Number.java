package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

@Data
public class Number extends Item {
	private SupportedLine value;
	private SupportedLine valueFeedback;
	private String dpt;

	public Number(SupportedLine supportedLine) {
		super(supportedLine);
	}

	@Override
	public String openhab() {

		// 9.001
		StringBuilder sb = new StringBuilder();
		if (getValue() != null) {
			if (getValueFeedback() == null) {
				sb.append("<");
			}
			if (getDpt() != null) {
				sb.append(getDpt() + ":");
			}
			sb.append(getValue().getAddress());
		}
		if(getValueFeedback() != null){
			if(getValue() != null) {
				sb.append("+");
			}
			sb.append("<");
			if(getDpt() != null){
				sb.append(getDpt()+":");
			}

			sb.append(getValueFeedback().getAddress());
		}
		return openhab("Number", getName(), getLabel(), getIcon(), getGroups(), getTags(), sb.toString());
	}
}
