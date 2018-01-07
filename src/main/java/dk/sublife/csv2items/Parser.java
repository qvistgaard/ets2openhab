package dk.sublife.csv2items;

import com.sun.deploy.util.OrderedHashSet;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Parser {

	public static void main(final String[] args) throws IOException {
		// final Map<String,Item> items = new TreeMap<>();
		final Map<String,Room> rooms = new TreeMap<>();
		final InputStream resourceAsStream = Parser.class.getResourceAsStream("/groups.csv");
		CSVParser parser = CSVParser.parse(resourceAsStream, Charset.forName("UTF-8"), CSVFormat.RFC4180);
		StreamSupport.stream(parser.spliterator(), false)
				.map(l -> new Line(l.get(2), new Address(l.get(1)), l.get(3)))
				.sorted()
				.filter(l -> l.getType() != null)
				.forEach(l -> {
					final Room room = rooms.computeIfAbsent(l.getRoom(), Room::new);
					final Map<String, Item> items = room.getItems();
					Item item;
					switch (l.getType()){
						case LIGHT_SWITCH:
							item = items.computeIfAbsent(l.getItemName(), v -> Light.create(v, l));
							((Light)item).setOnOff(l.getAddress());
							break;
						case LIGHT_SWITCH_FEEDBACK:
							item = items.computeIfAbsent(l.getItemName(), v -> Light.create(v, l));
							((Light)item).setOnOffFeedback(l.getAddress());
							break;
						case LIGHT_DIMMER:
							item = items.computeIfAbsent(l.getItemName(), v -> Light.create(v, l));
							((Light)item).setIncreaseDecrease(l.getAddress());
							break;
						case LIGHT_DIMMER_VALUE:
							item = items.computeIfAbsent(l.getItemName(), v -> Light.create(v, l));
							((Light)item).setPercent(l.getAddress());
							break;
						case LIGHT_DIMMER_VALUE_FEEDBACK:
							item = items.computeIfAbsent(l.getItemName(), v -> Light.create(v, l));
							((Light)item).setPercentFeedback(l.getAddress());
							break;
						case BINARY_INPUT:
							item = items.computeIfAbsent(l.getItemName(), v -> new Window(v, l, "Binary"));
							((Switch)item).setOnOff(l.getAddress());
							break;
						case RADIATOR:
							item = items.computeIfAbsent(l.getItemName(), v -> Percentage.create(v, l, "5.001", "Radiator", "HVAC"));
							((Number)item).setValue(l.getAddress());
							break;
						case RADIATOR_FEEDBACK:
							item = items.computeIfAbsent(l.getItemName(), v -> Percentage.create(v, l, "5.001", "Radiator", "HVAC"));
							((Number)item).setValueFeedback(l.getAddress());
							break;
						case ROOM_TEMPERATURE_ROLLOVER:
							item = items.computeIfAbsent(l.getItemName(), v -> new RTR(v, l, "5.010","Room Temperature Rollover", "HVAC"));
							((Number)item).setValue(l.getAddress());
							break;
						case ROOM_TEMPERATURE_ROLLOVER_FEEDBACK:
							item = items.computeIfAbsent(l.getItemName(), v -> new RTR(v, l, "5.010", "Room Temperature Rollover", "HVAC"));
							((Number)item).setValueFeedback(l.getAddress());
							break;
						case ROOM_TEMPERATURE_ROLLOVER_VALUE:
							item = items.computeIfAbsent(l.getItemName(), v -> new RTR(v, l, "5.010", "Room Temperature Rollover", "HVAC"));
							((Number)item).setValue(l.getAddress());
							break;
						case ROOM_TEMPERATURE:
							item = items.computeIfAbsent(l.getItemName(), v -> Temperature.create(v, l, "9.001", "Room Temperature", "HVAC"));
							((Number)item).setValue(l.getAddress());
							break;
						case ROOM_TEMPERATURE_SETPOINT:
							item = items.computeIfAbsent(l.getItemName(), v -> Temperature.create(v, l, "9.001","Room Temperature Setpoint", "HVAC"));
							((Number)item).setValue(l.getAddress());
							break;
						case ROOM_TEMPERATURE_SETPOINT_FEEDBACK:
							item = items.computeIfAbsent(l.getItemName(), v -> Temperature.create(v, l, "9.001","Room Temperature Setpoint", "HVAC"));
							((Number)item).setValueFeedback(l.getAddress());
							break;
						case ROOM_TEMPERATURE_SETPOINT_VALUE:
							item = items.computeIfAbsent(l.getItemName(), v -> Temperature.create(v, l, "9.001", "Room Temperature Setpoint", "HVAC"));
							((Number)item).setValue(l.getAddress());
							break;
					}

				});



		final File itemsDirectory = new File(args[0]);
		final File sitemap = new File(args[1]);
		if(!itemsDirectory.isDirectory()){
			throw new RuntimeException("File not found: "+itemsDirectory);
		}
		rooms.forEach((key, value) -> {
			try {
				File items = new File(args[0], value.filename()+".items");
				final FileOutputStream fileOutputStream = new FileOutputStream(items);
				fileOutputStream.write(value.openhabItemConfig().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		final FileOutputStream fileOutputStream = new FileOutputStream(sitemap);
		fileOutputStream.write("sitemap generated label=\"Generated\" {\n".getBytes());
		fileOutputStream.write("Frame {\n".getBytes());
		rooms.forEach((key, value) -> {
			try {
				fileOutputStream.write((value.openhabSitemapConfig()+"\n").getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		fileOutputStream.write("} }\n".getBytes());

		/*
		System.out.println("sitemap generated label=\"Generated\" {");
		rooms.forEach((key, value) -> System.out.println(value.openhabSitemapConfig()));
		System.out.println("}");
*/
		// rooms.forEach((key, value) -> System.out.println(value.openhabSitemap()));

	}
}

@Data
abstract class Item {
	private String name;
	private String label;
	private String[] groups;
	private String[] tags;
	private String icon = "sensor";

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

@Data
class Switch extends Item {
	private Address onOff;
	private Address onOffFeedback;

	static Switch create(final String name, final Line line, final String... tags){
		Switch i = new Switch();
		i.setName(name);
		i.setGroups(new String[]{line.getRoom()});
		i.setTags(tags);
		i.setLabel(name);
		i.setIcon("window");
		return i;
	}

	@Override
	public String openhab() {
		StringBuilder sb = new StringBuilder(getOnOff().toString());
		if(getOnOffFeedback() != null){
			sb.append("+<"+getOnOffFeedback().toString());
		}
		return openhab("Switch", getName(), getLabel(), getIcon(), getGroups(), getTags(), sb.toString());
	}

}

class Window extends Switch {
	public Window(final String name, final Line line, final String... tags){
		setName(name);
		setGroups(new String[]{line.getRoom()});
		setTags(tags);
		setLabel(name);
		setIcon("window");
	}

	@Override
	public String openhabSitemap() {
		return openhabSitemap("Text", getName());
	}

	@Override
	public String getLabel() {
		return super.getLabel()+"[MAP(window.map):%s]";
	}
}


@Data
class Light extends Switch {
	private Address increaseDecrease;
	private Address percent;
	private Address percentFeedback;

	static Light create(final String name, final Line line){
		Light i = new Light();
		i.setName(name);
		i.setGroups(new String[]{line.getRoom()});
		i.setTags(new String[]{"Lightning"});
		i.setLabel(name);
		i.setIcon("lightbulb");
		return i;
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

@Data
class Number extends Item {
	private Address value;
	private Address valueFeedback;
	private String dpt;

	static Number create(final String name, final Line line, final String dpt, final String... tags){
		Number i = new Number();
		i.setName(name);
		i.setGroups(new String[]{line.getRoom()});
		i.setTags(tags);
		i.setLabel(name);
		i.setDpt(dpt);
		return i;
	}

	@Override
	public String openhab() {
		StringBuilder sb = new StringBuilder();
		if(getValueFeedback() == null) {
			sb.append("<");
		}
		if(getDpt() != null){
			sb.append(getDpt()+":");
		}
		sb.append(getValue().toString());
		if(getValueFeedback() != null){
			sb.append("+<");
			if(getDpt() != null){
				sb.append(getDpt()+":");
			}

			sb.append(getValueFeedback().toString());
		}
		return openhab("Number", getName(), getLabel(), getIcon(), getGroups(), getTags(), sb.toString());
	}
}


class Percentage extends Number {
	static Percentage create(final String name, final Line line, final String dpt, final String... tags){
		Percentage i = new Percentage();
		i.setName(name);
		i.setGroups(new String[]{line.getRoom()});
		i.setTags(tags);
		i.setLabel(name);
		i.setIcon("heating");
		i.setDpt(dpt);
		return i;
	}

	@Override
	public String getLabel() {
		return super.getLabel()+" [%d %%]";
	}
}

class Temperature extends Number {

	static Temperature create(final String name, final Line line, final String dpt, final String... tags){
		Temperature i = new Temperature();
		i.setName(name);
		i.setGroups(new String[]{line.getRoom()});
		i.setTags(tags);
		i.setLabel(name);
		i.setDpt(dpt);
		i.setIcon("temperature");
		return i;
	}

	@Override
	public String getLabel() {
		return super.getLabel()+" [%.2f °C]";
	}
}

class RTR extends Number {
	public RTR(final String name, final Line line, final String dpt, final String... tags) {
		setName(name);
		setGroups(new String[]{line.getRoom()});
		setTags(tags);
		setLabel(name);
		setDpt(dpt);
		setIcon("heating");
	}

	@Override
	public String getLabel() {
		return super.getLabel()+" [MAP(rtr.map):%s]";
	}
}



@Data
@AllArgsConstructor
class Line implements Comparable<Line> {
	@Override
	public int compareTo(Line o) {
		return getName().compareTo(o.getName());
	}

	enum Type {
		LIGHT_SWITCH,
		LIGHT_SWITCH_FEEDBACK,
		LIGHT_DIMMER,
		LIGHT_DIMMER_VALUE,
		LIGHT_DIMMER_VALUE_FEEDBACK,
		BINARY_INPUT,
		RADIATOR,
		RADIATOR_FEEDBACK,
		ROOM_TEMPERATURE_ROLLOVER,
		ROOM_TEMPERATURE_ROLLOVER_VALUE,
		ROOM_TEMPERATURE,
		ROOM_TEMPERATURE_SETPOINT,
		ROOM_TEMPERATURE_SETPOINT_FEEDBACK,
		ROOM_TEMPERATURE_SETPOINT_VALUE,
		ROOM_TEMPERATURE_ROLLOVER_FEEDBACK
	}
	
	private final Map<Type,Pattern> patterns = new TreeMap<Type,Pattern>(){{
		put(Type.LIGHT_SWITCH, Pattern.compile("(.*?)-SW$"));
		put(Type.LIGHT_SWITCH_FEEDBACK, Pattern.compile("(.*?)-SW-FB$"));
		put(Type.LIGHT_DIMMER, Pattern.compile("(.*?)-DIM$"));
		put(Type.LIGHT_DIMMER_VALUE, Pattern.compile("(.*?)-DIM-VAL$"));
		put(Type.LIGHT_DIMMER_VALUE_FEEDBACK, Pattern.compile("(.*?)-DIM-VFB$"));
		put(Type.BINARY_INPUT, Pattern.compile("(.*?)-BI$"));
		put(Type.RADIATOR, Pattern.compile("(.*?)-RAD$"));
		put(Type.RADIATOR_FEEDBACK, Pattern.compile("(.*?)-RAD(.*?)-VFB$"));
		put(Type.ROOM_TEMPERATURE_ROLLOVER, Pattern.compile("(.*?)-RTR$"));
		put(Type.ROOM_TEMPERATURE_ROLLOVER_FEEDBACK, Pattern.compile("(.*?)-RTR-VFB$"));
		put(Type.ROOM_TEMPERATURE_ROLLOVER_VALUE, Pattern.compile("(.*?)-RTR-VAL$"));
		put(Type.ROOM_TEMPERATURE, Pattern.compile("(.*?)-RT$"));
		put(Type.ROOM_TEMPERATURE_SETPOINT, Pattern.compile("(.*?)-RT-SP$"));
		put(Type.ROOM_TEMPERATURE_SETPOINT_VALUE, Pattern.compile("(.*?)-RT-SP-VAL$"));
		put(Type.ROOM_TEMPERATURE_SETPOINT_FEEDBACK, Pattern.compile("(.*?)-RT-SP-VFB$"));
	}};
	
	private final String name;
	private final Address address;
	private final String description;

	public String getRoom(){
		return name.replaceAll("-.*", "");
	}

	public String getItemName(){
		final Type type = getType();
		String prefix = "";
		switch (type){
			case LIGHT_SWITCH:
				prefix = "iLight";
				break;
			case LIGHT_SWITCH_FEEDBACK:
				prefix = "iLight";
				break;
			case LIGHT_DIMMER:
				prefix = "iLight";
				break;
			case LIGHT_DIMMER_VALUE:
				prefix = "iLight";
				break;
			case LIGHT_DIMMER_VALUE_FEEDBACK:
				prefix = "iLight";
				break;
			case BINARY_INPUT:
				prefix = "iBin";
				break;
			case RADIATOR:
				prefix = "iRad";
				break;
			case RADIATOR_FEEDBACK:
				prefix = "iRad";
				break;
			case ROOM_TEMPERATURE_ROLLOVER:
				prefix = "iRTR";
				break;
			case ROOM_TEMPERATURE_ROLLOVER_FEEDBACK:
				prefix = "iRTR";
				break;
			case ROOM_TEMPERATURE_ROLLOVER_VALUE:
				prefix = "iRTRVal";
				break;
			case ROOM_TEMPERATURE:
				prefix = "iRT";
				break;
			case ROOM_TEMPERATURE_SETPOINT:
				prefix = "iSP";
				break;
			case ROOM_TEMPERATURE_SETPOINT_FEEDBACK:
				prefix = "iSP";
				break;
			case ROOM_TEMPERATURE_SETPOINT_VALUE:
				prefix = "iSPVal";
				break;
		}
		return String.format("%s %s", prefix, patterns.get(type).matcher(name).replaceAll("$1"));
	}

	public Type getType(){
		final Optional<Map.Entry<Type, Pattern>> first = patterns.entrySet().stream()
				.filter(p -> p.getValue().matcher(name).matches())
				.findFirst();
		if(first.isPresent()){
			return first.get().getKey();
		}
		return null;
	}
}

@Data
class Address {
	private final Integer mainGa;
	private final Integer subGa;
	private final Integer group;

	public Address(final String address) {
		final String[] split = address.split("/");
		this.mainGa = Integer.valueOf(split[0]);
		this.subGa = Integer.valueOf(split[1]);
		this.group = Integer.valueOf(split[2]);
	}

	@Override
	public String toString() {
		return String.format("%d/%d/%d", mainGa, subGa, group);
	}
}

@Data
class Room {
	private final String name;
	private final Map<String,Item> items = new TreeMap<>();

	public Room(String name) {
		this.name = name;
	}

	public String filename(){
		return name.replaceAll(" ", "");
	}

	public String openhabItemConfig(){
		StringBuilder sb = new StringBuilder("// #### ROOM CONFIG: ").append(name).append("\n");
		sb.append(String.format("Group %s \"%s\"\n", getName().replaceAll("\\s", ""), getName()));
		return sb.append(items.entrySet().stream().map(o -> o.getValue().openhab()).collect(Collectors.joining("\n"))).toString();
	}

	public String openhabSitemapConfig(){
		StringBuilder sb = new StringBuilder("\n\n\tGroup label=\"").append(name).append("\"");
		sb.append(" item=").append(items.entrySet().stream().findFirst().get().getValue().getName().replaceAll("\\s|/|-|_", ""));
		sb.append(" { \n");
		return sb.append(items.entrySet().stream().map(o -> o.getValue().openhabSitemap()).collect(Collectors.joining("\n"))).append("\n\t}\n").toString();
	}
}
