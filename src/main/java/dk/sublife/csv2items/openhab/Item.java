package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public abstract class Item {
	private String name;
	private String label;
	private String[] groups;
	private String[] tags;
	private String icon = "sensor";
	private SupportedLine item;

	public Item(SupportedLine supportedLine) {
		setName(supportedLine.getItemName());
		setLabel(supportedLine.getName());
	}

	public abstract String openhab();
	public String openhabSitemap() {
		return openhabSitemap("Default", getName());
	}
	public String openhab(String itemType, String itemName, String label, String icon, String[] groups, String[] tags, String bindingConfig){
		return String.format("%s %s \"%s\" <%s> (%s) [%s] { knx=\"%s\" }", itemType, itemName.replaceAll("\\s|_|/|-", ""), label, icon,
				Stream.of(groups).map(s -> "g"+s.replaceAll("\\s|/|-|_", "")).collect(Collectors.joining(", ")),
				Stream.of(tags).map(s -> "\""+s+"\"").collect(Collectors.joining(", ")),
				bindingConfig);
	}
	public String openhabSitemap(String type, String item){
		return String.format("\t\t%s item=%s", type, item.replaceAll("\\s|/|-|_", ""));
	}
}
