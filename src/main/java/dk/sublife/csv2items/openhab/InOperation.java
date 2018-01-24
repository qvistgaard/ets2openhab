package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import dk.sublife.csv2items.ets.types.BlindsInPosition;

public class InOperation extends Contact {

	public InOperation(SupportedLine supportedLine) {
		super(supportedLine);
		setIcon("switch");
	}

	public void addLine(dk.sublife.csv2items.ets.types.InOperation inOperation){
		setOnOff(inOperation);
	}

	@Override
	public String openhab() {
		StringBuilder sb = new StringBuilder("<"+getOnOff().getAddress());
		sb.append("\", expire=\"610s");

		return openhab("Contact", getName(), getLabel(), getIcon(), getGroups(), getTags(), sb.toString());
	}

	@Override
	public String openhabSitemap() {
		return openhabSitemap("Text", getName(), " valuecolor=[0=\"red\", 1=\"green\", OPEN=\"green\"]");
	}

	@Override
	public String getLabel() {
		return super.getLabel()+" [MAP(in-operation.map):%s]";
	}
}
