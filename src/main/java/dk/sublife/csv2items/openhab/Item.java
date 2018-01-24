package dk.sublife.csv2items.openhab;

import dk.sublife.csv2items.ets.SupportedLine;
import lombok.Data;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public abstract class Item {
	private String name;
	private String label;
	private ArrayList<String> groups = new ArrayList<>();
	private String[] tags = new String[0];
	private String icon = null;
	private SupportedLine item;

	public Item(SupportedLine supportedLine) {
		setName(supportedLine.getItemName());
		setLabel(supportedLine.getName());
		getGroups().add(supportedLine.getRoom());
	}

	public String getGrouping(){
		return getClass().getSimpleName();
	}

	public String getName(){
		return (name + getClass().getSimpleName()).replaceAll("\\s|_|/|-|&|\\.|#|\'|,", "");
	}

	public abstract String openhab();

	public String openhabSitemap() {
		return openhabSitemap("Default", getName());
	}
	public String openhab(String itemType, String itemName, String label, String icon, ArrayList<String> groups, String[] tags, String bindingConfig){
		final StringBuilder sb = new StringBuilder(String.format("%1$-15s", itemType)).append(" ")
				.append(itemName).append(" ")
				.append("\"").append(label).append("\" ");
		if(icon != null){
			sb.append("<").append(icon).append("> ");
		}
		if(groups.size() > 0){
			sb.append(groups.stream()
					.map(s -> "g"+s.replaceAll("\\s|/|-|_", ""))
					.collect(Collectors.joining(", ", "(", ") ")));
		}
		if(tags.length > 0){
			sb.append(Stream.of(tags)
					.map(s -> "g"+s.replaceAll("\\s|/|-|_", ""))
					.collect(Collectors.joining(", ", "[", "] ")));
		}
		sb.append("{ knx=\"").append(bindingConfig).append("\" } ");
		return sb.toString();
	}

	public String openhabSitemap(String type, String item){
		return String.format("%s item=%s", type, item.replaceAll("\\s|/|-|_", ""));
	}

	public String openhabSitemap(String type, String item, String extraSettings){
		return String.format("%s item=%s %s", type, item.replaceAll("\\s|/|-|_", ""), extraSettings);
	}

}
