package main;

public class ProfessorData {
    private int idUser;
    private int idProfesor;

    public ProfessorData(int idUser, int idProfesor) {
        this.idUser = idUser;
        this.idProfesor = idProfesor;
    }

    public ProfessorData() {
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }
}
