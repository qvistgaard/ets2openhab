package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public abstract class Item {
	private String name;
	private String label;
	private String[] groups = new String[0];
	private String[] tags = new String[0];
	private String icon = null;
	private SupportedLine item;

	public Item(SupportedLine supportedLine) {
		setName(supportedLine.getItemName());
		setLabel(supportedLine.getName());
		setGroups(new String[] { supportedLine.getRoom() });
	}

	public String getName(){
		return name + getClass().getSimpleName();
	}

	public abstract String openhab();
	public String openhabSitemap() {
		return openhabSitemap("Default", getName());
	}
	public String openhab(String itemType, String itemName, String label, String icon, String[] groups, String[] tags, String bindingConfig){
		final StringBuilder sb = new StringBuilder(itemType).append(" ")
				.append(itemName.replaceAll("\\s|_|/|-", "")).append(" ")
				.append("\"").append(label).append("\" ");
		if(icon != null){
			sb.append("<").append(icon).append("> ");
		}
		if(groups.length > 0){
			sb.append(Stream.of(groups)
					.map(s -> "g"+s.replaceAll("\\s|/|-|_", ""))
					.collect(Collectors.joining(", ", "(", ") ")));
		}
		if(tags.length > 0){
			sb.append(Stream.of(groups)
					.map(s -> "g"+s.replaceAll("\\s|/|-|_", ""))
					.collect(Collectors.joining(", ", "[", "] ")));
		}
		sb.append("{ knx=\"").append(bindingConfig).append("\" }");
		return sb.toString();
	}

	public String openhabSitemap(String type, String item){
		return String.format("\t\t%s item=%s", type, item.replaceAll("\\s|/|-|_", ""));
	}
}
