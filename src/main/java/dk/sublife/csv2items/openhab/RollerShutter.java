package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import dk.sublife.csv2items.ets.types.BlindsOpenClose;
import dk.sublife.csv2items.ets.types.BlindsPos;
import dk.sublife.csv2items.ets.types.BlindsPosFeedback;
import dk.sublife.csv2items.ets.types.BlindsSlatsStep;
import lombok.Data;

@Data
public class RollerShutter extends Switch {
	private BlindsOpenClose blindsOpenClose;
	private BlindsSlatsStep blindsStop;
	private BlindsPos blindsPos;
	private BlindsPosFeedback blindsPosFeedback;

	public void addLine(dk.sublife.csv2items.ets.types.BlindsOpenClose blindsOpenClose){
		setBlindsOpenClose(blindsOpenClose);
	}

	public void addLine(dk.sublife.csv2items.ets.types.BlindsPos blindsPos){
		setBlindsPos(blindsPos);
	}

	public void addLine(dk.sublife.csv2items.ets.types.BlindsPosFeedback blindsPosFeedback){
		setBlindsPosFeedback(blindsPosFeedback);
	}

	public void addLine(BlindsSlatsStep blindsSlatsStep){
		setBlindsStop(blindsSlatsStep);
	}


	public RollerShutter(SupportedLine supportedLine) {
		super(supportedLine);
		setIcon("rollershutter");
	}

	@Override
	public String openhab() {

		/* Rollershutters  Up/Down, Stop/Move, Position */
		// Rollershutter Shutter_GF_Living "Shutter" (GF_Living, Shutters) { knx="4/2/10, 4/2/11, 4/2/12" }


		StringBuilder sb = new StringBuilder();

		if (getBlindsOpenClose() != null) {
			sb.append(getBlindsOpenClose().getAddress());
			if(getBlindsStop() != null){
				sb.append(", ").append(getBlindsStop().getAddress());
			} else {
				sb.append(", ").append("1/10/255");
			}

			if(getBlindsPos() != null){
				sb.append(", ").append(getBlindsPos().getAddress());
				if(getBlindsPosFeedback() != null){
					sb.append("+<").append(getBlindsPosFeedback().getAddress());
				}
			}
			return openhab("RollerShutter", getName(), getLabel(), getIcon(), getGroups(), getTags(), sb.toString());
		} else if(getBlindsPos() != null){
			if(getBlindsPos() != null){
				sb.append(getBlindsPos().getAddress());
				if(getBlindsPosFeedback() != null){
					sb.append("+<").append(getBlindsPosFeedback().getAddress());
				}
			}
			return openhab("Number", getName(), getLabel(), getIcon(), getGroups(), getTags(), sb.toString());
		}
		throw new RuntimeException("Missing fields for Rollerblinds");
	}

	@Override
	public String openhabSitemap() {
		StringBuilder sb = new StringBuilder();
		if (getBlindsOpenClose() != null) {
			if (getBlindsPos() == null) {
				return super.openhabSitemap();
			} else {
				return openhabSitemap("Slider", getName()) + "\n\t\t\t\t" + super.openhabSitemap();
			}
		} else if(getBlindsPos() != null){
			return openhabSitemap("Slider", getName());
		}
		throw new RuntimeException("Missing fields for Rollerblinds");
	}

}
