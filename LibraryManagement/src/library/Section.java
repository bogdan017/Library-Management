package library;

import enums.SectionType;


public class Section {
    private String id;
    private SectionType type;

    public Section(String id, SectionType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public SectionType getType() {
        return type;
    }
}
