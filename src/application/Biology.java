package application;

public enum Biology {
	Domain(0, "域"), Kingdom(1, "界"), Phylum(2, "门"), Class(3, "纲"), Order(4, "目"), Family(5, "科"), Genus(6,
			"属"), Species(7, "种");

	private final int level;
	private final String localName;

	private Biology(int level, String localName) {
		this.level = level;
		this.localName = localName;
	}

	public int getLevel() {
		return level;
	}

	public String getLocalName() {
		return localName;
	}

}

