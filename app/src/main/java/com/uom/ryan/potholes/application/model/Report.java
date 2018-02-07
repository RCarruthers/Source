package com.uom.ryan.potholes.application.model;

/**
 * Created by Ryan on 04/11/2017.
 */

public class Report {

    public enum Severity {
        LOW, MEDIUM, HIGH;
    }

    public double lat;
    public double lon;
    public String severity;
    public String reportType;
    public String description;

    public Report() {};

    public Report(double lon, double lat, String reportType, Severity severity) {
        this.lat = lat;
        this.lon = lon;
        this.severity = severity.name();
        this.reportType = reportType;
        this.description = "";
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }






}
