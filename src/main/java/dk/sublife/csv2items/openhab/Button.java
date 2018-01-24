package dk.sublife.csv2items.openhab;


import dk.sublife.csv2items.ets.SupportedLine;

public class Button extends Switch {

	public Button(SupportedLine supportedLine) {
		super(supportedLine);
		setIcon("switch");
	}

	public void addLine(dk.sublife.csv2items.ets.types.BlindsReferenceMovement blindsReferenceMovement){
		setOnOff(blindsReferenceMovement);
		setIcon("settings");
	}

	@Override
	public String openhabSitemap() {
		return openhabSitemap("Switch", getName(), "mappings=[ON=Activate] visibility=["+getName()+"!=ON]\n")
				+ openhabSitemap("\t\t\t\tSwitch", getName(), "mappings=[OFF=Activate] visibility=["+getName()+"==ON]");
	}

	@Override
	public String getLabel() {
		return super.getLabel();
	}
}
