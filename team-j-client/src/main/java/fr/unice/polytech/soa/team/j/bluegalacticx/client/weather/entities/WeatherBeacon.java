package fr.unice.polytech.soa.team.j.bluegalacticx.client.weather.entities;

import java.util.Objects;

public class WeatherBeacon {

    /**
     * beacon name
     */
    private String label;
    /**
     * average pressure station level (hPa)
     */
    private double pstatm;
    /**
     * mean sea-level pressure (hPa)
     */
    private double pmerm;
    /**
     * average of average temperatures (°C)
     */
    private double tmmoy;
    /**
     * average temperature standard deviation (°C)
     */
    private double tmsigma;
    /**
     * average maximum temperature (°C)
     */
    private double txmoy;
    /**
     * average min temperature (°C)
     */
    private double tnmoy;
    /**
     * average vapour pressure (hPa)
     */
    private double tsvmoy;
    /**
     * cumulative rainfall (in mm)
     */
    private double rr;

    public WeatherBeacon() {
    }

    public WeatherBeacon(String label, double pstatm, double pmerm, double tmmoy, double tmsigma, double txmoy,
            double tnmoy, double tsvmoy, double rr) {
        this.label = label;
        this.pstatm = pstatm;
        this.pmerm = pmerm;
        this.tmmoy = tmmoy;
        this.tmsigma = tmsigma;
        this.txmoy = txmoy;
        this.tnmoy = tnmoy;
        this.tsvmoy = tsvmoy;
        this.rr = rr;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPstatm() {
        return this.pstatm;
    }

    public void setPstatm(double pstatm) {
        this.pstatm = pstatm;
    }

    public double getPmerm() {
        return this.pmerm;
    }

    public void setPmerm(double pmerm) {
        this.pmerm = pmerm;
    }

    public double getTmmoy() {
        return this.tmmoy;
    }

    public void setTmmoy(double tmmoy) {
        this.tmmoy = tmmoy;
    }

    public double getTmsigma() {
        return this.tmsigma;
    }

    public void setTmsigma(double tmsigma) {
        this.tmsigma = tmsigma;
    }

    public double getTxmoy() {
        return this.txmoy;
    }

    public void setTxmoy(double txmoy) {
        this.txmoy = txmoy;
    }

    public double getTnmoy() {
        return this.tnmoy;
    }

    public void setTnmoy(double tnmoy) {
        this.tnmoy = tnmoy;
    }

    public double getTsvmoy() {
        return this.tsvmoy;
    }

    public void setTsvmoy(double tsvmoy) {
        this.tsvmoy = tsvmoy;
    }

    public double getRr() {
        return this.rr;
    }

    public void setRr(double rr) {
        this.rr = rr;
    }

    public WeatherBeacon label(String label) {
        this.label = label;
        return this;
    }

    public WeatherBeacon pstatm(double pstatm) {
        this.pstatm = pstatm;
        return this;
    }

    public WeatherBeacon pmerm(double pmerm) {
        this.pmerm = pmerm;
        return this;
    }

    public WeatherBeacon tmmoy(double tmmoy) {
        this.tmmoy = tmmoy;
        return this;
    }

    public WeatherBeacon tmsigma(double tmsigma) {
        this.tmsigma = tmsigma;
        return this;
    }

    public WeatherBeacon txmoy(double txmoy) {
        this.txmoy = txmoy;
        return this;
    }

    public WeatherBeacon tnmoy(double tnmoy) {
        this.tnmoy = tnmoy;
        return this;
    }

    public WeatherBeacon tsvmoy(double tsvmoy) {
        this.tsvmoy = tsvmoy;
        return this;
    }

    public WeatherBeacon rr(double rr) {
        this.rr = rr;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WeatherBeacon)) {
            return false;
        }
        WeatherBeacon weatherBeacon = (WeatherBeacon) o;
        return Objects.equals(label, weatherBeacon.label) && pstatm == weatherBeacon.pstatm
                && pmerm == weatherBeacon.pmerm && tmmoy == weatherBeacon.tmmoy && tmsigma == weatherBeacon.tmsigma
                && txmoy == weatherBeacon.txmoy && tnmoy == weatherBeacon.tnmoy && tsvmoy == weatherBeacon.tsvmoy
                && rr == weatherBeacon.rr;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, pstatm, pmerm, tmmoy, tmsigma, txmoy, tnmoy, tsvmoy, rr);
    }

    @Override
    public String toString() {
        return "{" + " label='" + getLabel() + "'" + ", pstatm='" + getPstatm() + "'" + ", pmerm='" + getPmerm() + "'"
                + ", tmmoy='" + getTmmoy() + "'" + ", tmsigma='" + getTmsigma() + "'" + ", txmoy='" + getTxmoy() + "'"
                + ", tnmoy='" + getTnmoy() + "'" + ", tsvmoy='" + getTsvmoy() + "'" + ", rr='" + getRr() + "'" + "}";
    }

}
