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
	}

	public void addLine(LightSwitch lightSwitch){
		setOnOff(lightSwitch);
	}

	public void addLine(LightSwitchFeedback lightSwitchFeedback){
		setOnOffFeedback(lightSwitchFeedback);
	}

	public void addLine(LightDimmer lightDimmer){
		setIncreaseDecrease(lightDimmer);
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
			sb.append(getOnOff().toString());
			if (getOnOffFeedback() != null) {
				sb.append("+<" + getOnOffFeedback().toString());
			}
			if (getIncreaseDecrease() != null) {
				type = "Dimmer";
				sb.append(", " + getIncreaseDecrease());
			}
			if (getPercent() != null) {
				sb.append(", " + getPercent());
				if (getPercentFeedback() != null) {
					sb.append("+<" + getPercentFeedback());
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
			return openhabSitemap("Switch", getName()) + "\n" + super.openhabSitemap();
		}
	}
}
