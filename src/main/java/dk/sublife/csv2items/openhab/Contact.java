package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

public class Contact extends Switch {

	public Contact(SupportedLine supportedLine) {
		super(supportedLine);
	}

	public void addLine(dk.sublife.csv2items.ets.types.Contact contact){
		setOnOff(contact);
	}

	@Override
	public String openhab() {
		StringBuilder sb = new StringBuilder("<"+getOnOff().getAddress().toString());
		return openhab("Contact", getName(), getLabel(), getIcon(), getGroups(), getTags(), sb.toString());
	}

	@Override
	public String openhabSitemap() {
		return openhabSitemap("Text", getName());
	}

}
