package dk.sublife.csv2items.ets;

import lombok.Data;

@Data
public class Address {
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