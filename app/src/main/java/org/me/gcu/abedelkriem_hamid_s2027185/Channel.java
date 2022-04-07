package org.me.gcu.abedelkriem_hamid_s2027185;

import java.util.ArrayList;
import java.util.List;

public class Channel {
    private String title;
    private String description;
    private String link;
    private String language;
    private String copyright;
    private String lastBuildDate;
    private String docs;
    private String generator;
    private String ttl;
    private String webMaster;

    private List<Item> items;

    public Channel() {
        items = new ArrayList<>();
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getWebMaster() {
        return webMaster;
    }

    public void setWebMaster(String webMaster) {
        this.webMaster = webMaster;
    }

}
