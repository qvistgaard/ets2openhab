package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.Address;
import dk.sublife.csv2items.ets.Line;
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

/*	static Number create(final String name, final Line line, final String dpt, final String... tags){
		Number i = new Number();
		i.setName(name);
		i.setGroups(new String[]{line.getRoom()});
		i.setTags(tags);
		i.setLabel(name);
		i.setDpt(dpt);
		return i;
	}*/

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
