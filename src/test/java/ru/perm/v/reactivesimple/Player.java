package ru.perm.v.reactivesimple;

public class Player {
	String name;
	String second_name;

	public Player(String name, String second_name) {
		this.name = name;
		this.second_name = second_name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Player)) return false;

		Player player = (Player) o;

		if (name != null ? !name.equals(player.name) : player.name != null) return false;
		return second_name != null ? second_name.equals(player.second_name) : player.second_name == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (second_name != null ? second_name.hashCode() : 0);
		return result;
	}
}
