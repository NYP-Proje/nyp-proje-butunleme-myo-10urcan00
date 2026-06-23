package com.bank.model;

/**
 * KrediHesabi — Credit/loan account (Kredi Faiz Maliyeti).
 * Extends Hesap and overrides faizHesapla() with the annual loan interest formula.
 *
 * Formula: anaPara × (faizOrani / 100) × (vadeAy / 12.0)
 *
 * Note: Class name uses 'i' in place of the Turkish dotless-i (ı) for maximum
 * cross-platform filesystem compatibility while preserving the intended meaning.
 */
public class KrediHesabi extends Hesap {

    /**
     * Constructs a credit account.
     *
     * @param musteriAdi Customer full name
     * @param anaPara    Loan principal amount in TL
     * @param vadeAy     Loan term in months
     * @param faizOrani  Annual interest rate as a percentage
     */
    public KrediHesabi(String musteriAdi, double anaPara, int vadeAy, double faizOrani) {
        super(musteriAdi, anaPara, vadeAy, faizOrani);
    }

    /**
     * Calculates the total credit interest cost.
     * Formula: anaPara × (faizOrani / 100) × (vadeAy / 12.0)
     *
     * @return Total interest cost in TL
     */
    @Override
    public double faizHesapla() {
        return getAnaPara() * (getFaizOrani() / 100.0) * (getVadeAy() / 12.0);
    }

    @Override
    public String toString() {
        return String.format("KrediHesabi{musteriAdi='%s', anaPara=%.2f, vadeAy=%d, faizOrani=%.2f, faiz=%.2f}",
                getMusteriAdi(), getAnaPara(), getVadeAy(), getFaizOrani(), faizHesapla());
    }
}
