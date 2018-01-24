package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import dk.sublife.csv2items.ets.types.BlindsSlatsStep;
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
	public void addLine(dk.sublife.csv2items.ets.types.BlindsAutomatic blindsAutomatic){
		setOnOff(blindsAutomatic);
		setIcon("switch");
	}
	public void addLine(dk.sublife.csv2items.ets.types.BlindsPresence blindsPresence){
		setOnOff(blindsPresence);
		setIcon("switch");
	}
	public void addLine(dk.sublife.csv2items.ets.types.BlindsHeating blindsHeating){
		setOnOff(blindsHeating);
		setIcon("switch");
	}
	public void addLine(BlindsSlatsStep blindsSlatsStep){
		setOnOff(blindsSlatsStep);
		setIcon("switch");
	}
	public void addLine(dk.sublife.csv2items.ets.types.BlindsCooling blindsCooling){
		setOnOff(blindsCooling);
		setIcon("switch");
	}
	public void addLine(dk.sublife.csv2items.ets.types.BlindsSun blindsSun){
		setOnOff(blindsSun);
		setIcon("switch");
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

	@Override
	public String openhabSitemap() {
		return openhabSitemap("Switch", getName());
	}
}
