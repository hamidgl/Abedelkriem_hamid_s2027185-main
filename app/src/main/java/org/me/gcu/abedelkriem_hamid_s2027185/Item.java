package org.me.gcu.abedelkriem_hamid_s2027185;

import java.util.Date;

public class Item {

    private String title;
    private String description;
    private String link;
    private String pubDate;

    public Item(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public Item(String title, String description, String link, String pubDate) {
        this(title, description, link);
        this.pubDate = pubDate;
    }

    public Item() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
