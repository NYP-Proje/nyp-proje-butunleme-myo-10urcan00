package com.bank.model;

/**
 * Hesap — Base class representing a generic bank account.
 * Encapsulates customer and financial data with private fields.
 * Subclasses override faizHesapla() to apply specific interest logic (Polymorphism).
 */
public class Hesap {

    private String musteriAdi;
    private double anaPara;
    private int    vadeAy;
    private double faizOrani;

    /**
     * Full constructor.
     *
     * @param musteriAdi Customer full name
     * @param anaPara    Principal amount in TL
     * @param vadeAy     Term in months
     * @param faizOrani  Annual interest rate as a percentage (e.g., 25.5 for 25.5%)
     */
    public Hesap(String musteriAdi, double anaPara, int vadeAy, double faizOrani) {
        this.musteriAdi = musteriAdi;
        this.anaPara    = anaPara;
        this.vadeAy     = vadeAy;
        this.faizOrani  = faizOrani;
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public String getMusteriAdi() { return musteriAdi; }
    public double getAnaPara()    { return anaPara;    }
    public int    getVadeAy()     { return vadeAy;     }
    public double getFaizOrani()  { return faizOrani;  }

    // ── Setters ──────────────────────────────────────────────────────────────

    public void setMusteriAdi(String musteriAdi) { this.musteriAdi = musteriAdi; }
    public void setAnaPara(double anaPara)        { this.anaPara    = anaPara;    }
    public void setVadeAy(int vadeAy)             { this.vadeAy     = vadeAy;     }
    public void setFaizOrani(double faizOrani)    { this.faizOrani  = faizOrani;  }

    /**
     * Calculates the interest amount.
     * Base implementation returns 0.0; subclasses override with their formulas.
     *
     * @return Calculated interest amount in TL
     */
    public double faizHesapla() {
        return 0.0;
    }

    @Override
    public String toString() {
        return String.format("Hesap{musteriAdi='%s', anaPara=%.2f, vadeAy=%d, faizOrani=%.2f}",
                musteriAdi, anaPara, vadeAy, faizOrani);
    }
}
