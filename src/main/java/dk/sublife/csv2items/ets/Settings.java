package dk.sublife.csv2items.ets;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class Settings extends Properties {

	public Settings(final String description) throws IOException {
		final StringBuilder properties = new StringBuilder();
		Boolean inSettings = false;
		for (String s : description.split("\n")) {
			if(s.toLowerCase().startsWith("openhab:")){
				inSettings = true;
			} else if(inSettings && s.trim().length() == 0){
				break;
			} else if(inSettings){
				properties.append(s).append("\n");
			}
		}
		load(new StringReader(properties.toString()));
	}
}
