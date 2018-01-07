package dk.sublife.csv2items;

import dk.sublife.csv2items.ets.Line;
import dk.sublife.csv2items.ets.SupportedLine;
import dk.sublife.csv2items.openhab.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.StreamSupport;

public class Parser {

	public static void main(final String[] args) throws IOException {
		// final Map<String,Item> items = new TreeMap<>();
		final Map<String,Room> rooms = new TreeMap<>();
		final InputStream resourceAsStream = Parser.class.getResourceAsStream("/test.csv");
		CSVParser parser = CSVParser.parse(resourceAsStream, Charset.forName("UTF-8"), CSVFormat.RFC4180);
		StreamSupport.stream(parser.spliterator(), false)
				.skip(1)
				.map(Line::create)
				.sorted()
				.filter(l -> SupportedLine.class.isAssignableFrom(l.getClass()))
				.map(l -> (SupportedLine) l)
				.forEach(l -> rooms.computeIfAbsent(l.getRoom(), Room::new).addLine(l));


		System.out.println(rooms);
		/*
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
							item = items.computeIfAbsent(l.getItemName(), v -> new Light(v, l));
							((Light)item).setOnOff(l.getAddress());
							break;
						case LIGHT_SWITCH_FEEDBACK:
							item = items.computeIfAbsent(l.getItemName(), v -> new Light(v, l));
							((Light)item).setOnOffFeedback(l.getAddress());
							break;
						case LIGHT_DIMMER:
							item = items.computeIfAbsent(l.getItemName(), v -> new Light(v, l));
							((Light)item).setIncreaseDecrease(l.getAddress());
							break;
						case LIGHT_DIMMER_VALUE:
							item = items.computeIfAbsent(l.getItemName(), v -> new Light(v, l));
							((Light)item).setPercent(l.getAddress());
							break;
						case LIGHT_DIMMER_VALUE_FEEDBACK:
							item = items.computeIfAbsent(l.getItemName(), v -> new Light(v, l));
							((Light)item).setPercentFeedback(l.getAddress());
							break;
						case BINARY_INPUT:
							item = items.computeIfAbsent(l.getItemName(), v -> new Window(v, l, "Binary"));
							((Switch)item).setOnOff(l.getAddress());
							break;
						case RADIATOR:
							item = items.computeIfAbsent(l.getItemName(), v -> new Percentage(v, l, "5.001", "Radiator", "HVAC"));
							((dk.sublife.csv2items.openhab.Number)item).setValue(l.getAddress());
							break;
						case RADIATOR_FEEDBACK:
							item = items.computeIfAbsent(l.getItemName(), v -> new Percentage(v, l, "5.001", "Radiator", "HVAC"));
							((dk.sublife.csv2items.openhab.Number)item).setValueFeedback(l.getAddress());
							break;
						case ROOM_TEMPERATURE_ROLLOVER:
							item = items.computeIfAbsent(l.getItemName(), v -> new RTR(v, l, "5.010","Room Temperature Rollover", "HVAC"));
							((dk.sublife.csv2items.openhab.Number)item).setValue(l.getAddress());
							break;
						case ROOM_TEMPERATURE_ROLLOVER_FEEDBACK:
							item = items.computeIfAbsent(l.getItemName(), v -> new RTR(v, l, "5.010", "Room Temperature Rollover", "HVAC"));
							((dk.sublife.csv2items.openhab.Number)item).setValueFeedback(l.getAddress());
							break;
						case ROOM_TEMPERATURE_ROLLOVER_VALUE:
							item = items.computeIfAbsent(l.getItemName(), v -> new RTR(v, l, "5.010", "Room Temperature Rollover", "HVAC"));
							((dk.sublife.csv2items.openhab.Number)item).setValue(l.getAddress());
							break;
						case ROOM_TEMPERATURE:
							item = items.computeIfAbsent(l.getItemName(), v -> new Temperature(v, l, "9.001", "Room Temperature", "HVAC"));
							((dk.sublife.csv2items.openhab.Number)item).setValue(l.getAddress());
							break;
						case ROOM_TEMPERATURE_SETPOINT:
							item = items.computeIfAbsent(l.getItemName(), v -> new Temperature(v, l, "9.001","Room Temperature Setpoint", "HVAC"));
							((dk.sublife.csv2items.openhab.Number)item).setValue(l.getAddress());
							break;
						case ROOM_TEMPERATURE_SETPOINT_FEEDBACK:
							item = items.computeIfAbsent(l.getItemName(), v -> new Temperature(v, l, "9.001","Room Temperature Setpoint", "HVAC"));
							((dk.sublife.csv2items.openhab.Number)item).setValueFeedback(l.getAddress());
							break;
						case ROOM_TEMPERATURE_SETPOINT_VALUE:
							item = items.computeIfAbsent(l.getItemName(), v -> new Temperature(v, l, "9.001", "Room Temperature Setpoint", "HVAC"));
							((dk.sublife.csv2items.openhab.Number)item).setValue(l.getAddress());
							break;
					}

				});
*/



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