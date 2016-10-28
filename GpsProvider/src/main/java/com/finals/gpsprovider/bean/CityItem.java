package com.finals.gpsprovider.bean;

public class CityItem {

	public CityItem(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String name = "";

	public int id;

	String pinyin = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Override
	public String toString() {
		return pinyin;
	}

}
