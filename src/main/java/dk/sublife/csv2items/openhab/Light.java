package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import dk.sublife.csv2items.ets.types.*;
import lombok.Data;

@Data
public class Light extends Switch {
	private LightDimmer increaseDecrease;
	private LightDimmerValue percent;
	private LightDimmerValueFeedback percentFeedback;

	public Light(SupportedLine supportedLine) {
		super(supportedLine);
		setIcon("light");
		getGroups().add("Lights");
	}

	public void addLine(LightSwitch lightSwitch){
		setOnOff(lightSwitch);
	}
	public void addLine(LightSwitchFeedback lightSwitchFeedback){
		setOnOffFeedback(lightSwitchFeedback);
	}
	public void addLine(LightDimmer lightDimmer){
		setIncreaseDecrease(lightDimmer);
		getGroups().add("DimmableLights");
	}
	public void addLine(LightDimmerValue lightDimmerValue){
		setPercent(lightDimmerValue);
	}
	public void addLine(LightDimmerValueFeedback lightDimmerValueFeedback){
		setPercentFeedback(lightDimmerValueFeedback);
	}

	@Override
	public String openhab() {
		try {
			String type = "Switch";
			StringBuilder sb = new StringBuilder();
			if (getOnOffFeedback() == null) {
				sb.append("<");
			}
			sb.append(getOnOff().getAddress());
			if (getOnOffFeedback() != null) {
				sb.append("+<" + getOnOffFeedback().getAddress());
			}
			if (getIncreaseDecrease() != null) {
				type = "Dimmer";
				sb.append(", " + getIncreaseDecrease().getAddress());
			}
			if (getPercent() != null) {
				sb.append(", " + getPercent().getAddress());
				if (getPercentFeedback() != null) {
					sb.append("+<" + getPercentFeedback().getAddress());
				}
			}
			return openhab(type, getName(), getLabel(), getIcon(), getGroups(), getTags(), sb.toString());
		} catch (NullPointerException e){
			System.out.println(this);
			throw e;
		}

	}

	@Override
	public String openhabSitemap() {
		StringBuilder sb = new StringBuilder();
		if(getIncreaseDecrease() == null){
			return super.openhabSitemap();
		} else {
			return openhabSitemap("Switch", getName()) + "\n\t\t\t\t" + openhabSitemap("Slider", getName());
		}
	}
}
