package main;

public class User {
    private String nume;
    private String prenume;
    private String username;//Nickname
    private String email;
    private String parola;
    private String cnp;
    private String iban;
    private String nrContract;
    private String nrTelefon;
    private String nivelAccesibilitate;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getNrContract() {
        return nrContract;
    }

    public void setNrContract(String nrContract) {
        this.nrContract = nrContract;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    public String getNivelAccesibilitate() {
        return nivelAccesibilitate;
    }

    public void setNivelAccesibilitate(String nivelAccesibilitate) {
        this.nivelAccesibilitate = nivelAccesibilitate;
    }

    public String getAdresa() {
        return this.adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    private String adresa;
}
