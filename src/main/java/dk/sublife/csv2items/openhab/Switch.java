package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import dk.sublife.csv2items.ets.types.LightSwitch;
import dk.sublife.csv2items.ets.types.LightSwitchFeedback;
import lombok.Data;

@Data
public class Switch extends Item {
	private SupportedLine onOff;
	private SupportedLine onOffFeedback;

	public Switch(SupportedLine supportedLine) {
		super(supportedLine);
	}


/*
	static Switch create(final String name, final Line line, final String... tags){
		Switch i = new Switch();
		i.setName(name);
		i.setGroups(new String[]{line.getRoom()});
		i.setTags(tags);
		i.setLabel(name);
		i.setIcon("window");
		return i;
	}
*/

	public void addLine(dk.sublife.csv2items.ets.types.SettingEnable settingEnable){
		setOnOff(settingEnable);
	}

	@Override
	public String openhab() {
		StringBuilder sb = new StringBuilder();

		if(getOnOffFeedback() == null) {
			if (onOff.isReadable(true)) {
				sb.append("<");
			}
			sb.append(getOnOff().getAddress());
		} else {
			if (onOff.isReadable(false)) {
				sb.append("<");
			}
			sb.append(getOnOff().getAddress());
			sb.append("+");
			if(onOffFeedback.isReadable(true)){
				sb.append("<");
			}
			sb.append(onOffFeedback.getAddress());
		}

		return openhab("Switch", getName(), getLabel(), getIcon(), getGroups(), getTags(), sb.toString());
	}

}
