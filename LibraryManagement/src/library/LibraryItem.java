package library;

import enums.SectionType;


public abstract class LibraryItem {
    private String id;
    private String title;
    private SectionType section;

    public LibraryItem(String id, String title, SectionType section) {
        this.id = id;
        this.title = title;
        this.section = section;
    }

    public String getId() { 
        return id;
    }

    public String getTitle() {
        return title;
    }

    public SectionType getSection() {
        return section;
    }
}
