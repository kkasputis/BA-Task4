package ba.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "imones")
public class Imones {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
private int id;

private int lookupId;
private int code;
private long jarCode;
private String name;
private String shortname;
private int month;
private long avgWage;
private long avgWage2;
private int numInsured;
private int numInsured2;
private float tax;
private String ecoActName;
private String ecoActCode;
private String municipality;


public Imones() {}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getLookupId() {
	return lookupId;
}
public void setLookupId(int lookupId) {
	this.lookupId = lookupId;
}

public int getCode() {
	return code;
}
public void setCode(int code) {
	this.code = code;
}

public long getJarCode() {
	return jarCode;
}

public void setJarCode(long jarCode) {
	this.jarCode = jarCode;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getShortname() {
	return shortname;
}
public void setShortname(String shortname) {
	this.shortname = shortname;
}
public int getMonth() {
	return month;
}
public void setMonth(int month) {
	this.month = month;
}

public long getAvgWage() {
	return avgWage;
}

public void setAvgWage(long avgWage) {
	this.avgWage = avgWage;
}

public long getAvgWage2() {
	return avgWage2;
}

public void setAvgWage2(long avgWage2) {
	this.avgWage2 = avgWage2;
}

public int getNumInsured() {
	return numInsured;
}
public void setNumInsured(int numInsured) {
	this.numInsured = numInsured;
}
public int getNumInsured2() {
	return numInsured2;
}
public void setNumInsured2(int numInsured2) {
	this.numInsured2 = numInsured2;
}
public float getTax() {
	return tax;
}
public void setTax(float tax) {
	this.tax = tax;
}
public String getEcoActName() {
	return ecoActName;
}
public void setEcoActName(String ecoActName) {
	this.ecoActName = ecoActName;
}
public String getEcoActCode() {
	return ecoActCode;
}
public void setEcoActCode(String ecoActCode) {
	this.ecoActCode = ecoActCode;
}
public String getMunicipality() {
	return municipality;
}
public void setMunicipality(String municipality) {
	this.municipality = municipality;
}


}
