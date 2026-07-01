package com.bank.model;

/**
 * VadeliHesap — Time deposit account (Mevduat Vadeli Hesap).
 * Extends Hesap and overrides faizHesapla() with the monthly prorated interest formula.
 *
 * Formula: (anaPara × (faizOrani / 100) × vadeAy) / 12
 */
public class VadeliHesap extends Hesap {

    /**
     * Constructs a time deposit account.
     *
     * @param musteriAdi Customer full name
     * @param anaPara    Principal amount in TL
     * @param vadeAy     Term in months
     * @param faizOrani  Annual interest rate as a percentage
     */
    public VadeliHesap(String musteriAdi, double anaPara, int vadeAy, double faizOrani) {
        super(musteriAdi, anaPara, vadeAy, faizOrani);
    }

    /**
     * Calculates time deposit interest using the standard monthly prorated formula.
     * Formula: (anaPara × (faizOrani / 100) × vadeAy) / 12
     *
     * @return Total interest earned in TL
     */
    @Override
    public double faizHesapla() {
        return (getAnaPara() * (getFaizOrani() / 100.0) * getVadeAy()) / 12.0;
    }

    @Override
    public String toString() {
        return String.format("VadeliHesap{musteriAdi='%s', anaPara=%.2f, vadeAy=%d, faizOrani=%.2f, faiz=%.2f}",
                getMusteriAdi(), getAnaPara(), getVadeAy(), getFaizOrani(), faizHesapla());
    }
}
