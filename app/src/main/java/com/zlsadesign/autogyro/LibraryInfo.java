package com.zlsadesign.autogyro;

public class LibraryInfo {

  public String name = "";
  public String author = "";
  public String description = "";
  public String link = "";
  public String license = "";

  public LibraryInfo() {

  }

  public LibraryInfo setName(String name) {
    this.name = name;
    return this;
  }

  public LibraryInfo setAuthor(String author) {
    this.author = author;
    return this;
  }

  public LibraryInfo setDescription(String description) {
    this.description = description;
    return this;
  }

  public LibraryInfo setLink(String link) {
    this.link = link;
    return this;
  }

  public LibraryInfo setLicense(String license) {
    this.license = license;
    return this;
  }

}
