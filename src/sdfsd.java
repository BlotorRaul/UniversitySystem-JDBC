import main.ProfessorData;

public class sdfsd {
    private ProfessorData profesorData;

    public sdfsd(ProfessorData profesorData) {
        this.profesorData = profesorData;
    }
    public void doSomethingWithProfesorData() {
        int idUser = profesorData.getIdUser();
        int idProfesor = profesorData.getIdProfesor();

        // Acum poți utiliza idUser și idProfesor în logica ta
        System.out.println("IdUser: " + idUser);
        System.out.println("IdProfesor: " + idProfesor);
    }
}
