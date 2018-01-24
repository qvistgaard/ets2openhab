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
		// final InputStream resourceAsStream = Parser.class.getResourceAsStream("/test.csv");
		final FileInputStream inputStream = new FileInputStream(new File(args[0]));
		CSVParser parser = CSVParser.parse(inputStream, Charset.forName("UTF-8"), CSVFormat.RFC4180);
		StreamSupport.stream(parser.spliterator(), false)
				.skip(1)
				.map(Line::create)
				.sorted()
				.filter(l -> SupportedLine.class.isAssignableFrom(l.getClass()))
				.map(l -> (SupportedLine) l)
				.forEach(l -> rooms.computeIfAbsent(l.getRoom(), Room::new).addLine(l));


		rooms.forEach((key, value) -> {
			System.out.println(value.openhabItemConfig()+"\n\n");
		});

		final File itemsDirectory = new File(args[1]);
		if(!itemsDirectory.isDirectory()){
			throw new RuntimeException("File not found: "+itemsDirectory);
		}
		rooms.forEach((key, value) -> {
			try {
				File items = new File(args[1], "items/"+value.filename()+"-ets2openhab.items");
				final FileOutputStream fileOutputStream = new FileOutputStream(items);
				fileOutputStream.write(value.openhabItemConfig().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		File sitemap = new File(args[1], "sitemaps/ets2openhab.sitemap");
		final FileOutputStream fileOutputStream = new FileOutputStream(sitemap);
		fileOutputStream.write("sitemap ets2openhab label=\"ets2openhab generated\" {\n".getBytes());
		fileOutputStream.write("\tFrame {\n".getBytes());
		rooms.forEach((key, value) -> {
			try {
				fileOutputStream.write((value.openhabSitemapConfig()+"\n").getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		fileOutputStream.write("\t}\n}\n".getBytes());

		/*
		System.out.println("sitemap generated label=\"Generated\" {");
		rooms.forEach((key, value) -> System.out.println(value.openhabSitemapConfig()));
		System.out.println("}");
*/
		// rooms.forEach((key, value) -> System.out.println(value.openhabSitemap()));

	}
}