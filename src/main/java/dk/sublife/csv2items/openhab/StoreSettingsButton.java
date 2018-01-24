package dk.sublife.csv2items.openhab;


import dk.sublife.csv2items.ets.SupportedLine;

public class StoreSettingsButton extends Button {

	public StoreSettingsButton(SupportedLine supportedLine) {
		super(supportedLine);
		setIcon("settings");
	}

	@Override
	public String openhabSitemap() {
		return openhabSitemap("Switch", getName(), "mappings=[ON=Set] visibility=["+getName()+"!=ON]\n")
				+ openhabSitemap("\t\t\t\tSwitch", getName(), "mappings=[OFF=Set] visibility=["+getName()+"==ON]");
	}

	@Override
	public String getLabel() {
		return super.getLabel();
	}
}
