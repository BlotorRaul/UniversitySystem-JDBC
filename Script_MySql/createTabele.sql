-- -----------------------------------------------------
-- create database proiect
-- -----------------------------------------------------
create database if not exists proiect;
use proiect;

-- -----------------------------------------------------
-- Table `login`
-- -----------------------------------------------------
create table if not exists login(
idUser int not null unique,
username varchar(30) NULL,
password varchar(30) NULL
);
-- ALTER TABLE login DROP FOREIGN KEY FK_login_user;
alter table login add constraint FK_login_user foreign key(idUser) references users(idUser) ON DELETE CASCADE ON UPDATE CASCADE;

-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------

create table if not exists users(
idUser int not null auto_increment PRIMARY KEY,
tipUser varchar(30) null,
CNP varchar(13) not null unique,
nume varchar(30) null,
prenume varchar(30) null,
adresa text null,
numarTelefon varchar(10) not null,
email varchar(30) not null unique,
IBAN  varchar(24) not null unique,
numarContract varchar(30) not null unique

);
ALTER TABLE users
ADD COLUMN username varchar(30) NOT NULL;
ALTER TABLE users
ADD COLUMN parola varchar(30) NOT NULL;
-- -----------------------------------------------------
-- Table `administrator`
-- -----------------------------------------------------
create table if not exists administrator(
idAdministrator int not null auto_increment PRIMARY KEY,
idUser int null,
superAdministrator int null
);

alter table administrator add constraint fk_administrator_user foreign key (idUser) references users(idUser) ON DELETE CASCADE ON UPDATE CASCADE;

-- -----------------------------------------------------
-- Table `profesor`
-- -----------------------------------------------------
create table if not exists profesor(
idProfesor int not null auto_increment PRIMARY KEY,
idUser int null,
minimOre int null,
maximOre int null,
departament varchar(30)
);
alter table profesor add constraint fk_profesor_user foreign key (idUser) references users(idUser) ON DELETE CASCADE ON UPDATE CASCADE;

-- -----------------------------------------------------
-- Table `student`
-- -----------------------------------------------------
create table if not exists student(
idStudent int not null auto_increment PRIMARY KEY,
idUser int null,
oreSustinute int null,
anInvatamant int null
);
alter table student add constraint fk_student_user foreign key (idUser) references users(idUser) ON DELETE CASCADE ON UPDATE CASCADE;

-- -----------------------------------------------------
-- Table `calendar`
-- -----------------------------------------------------
create table if not exists calendar(
idProfesor int null,
idMaterie int null,
numeMaterie varchar(30) null,
nrMaxStudenti int null,
dataDorita datetime null
);
alter table calendar add constraint fk_calendar_profesor foreign key(idProfesor) references profesor(idProfesor)ON DELETE CASCADE ON UPDATE CASCADE;


-- -----------------------------------------------------
-- Table `profesor_materie`
-- -----------------------------------------------------
create table if not exists profesor_materie(
idProfesor int null,
idMaterie int null
);
alter table profesor_materie add constraint fk_profesor_materie_profesor foreign key(idProfesor) references profesor(idProfesor)ON DELETE CASCADE ON UPDATE CASCADE;
alter table profesor_materie add constraint fk_profesor_materie_materie foreign key(idMaterie) references materie(idMaterie)ON DELETE CASCADE ON UPDATE CASCADE;

-- -----------------------------------------------------
-- Table `materie`
-- -----------------------------------------------------
create table if not exists materie(
idMaterie int not null auto_increment PRIMARY KEY,
descriere text null,
numeMaterie varchar(30) null,
dataIncepere date null,
dataSfarsire date null
);

-- -----------------------------------------------------
-- Table `activitate`
-- -----------------------------------------------------
create table if not exists activitate(
tipActivitate int not null auto_increment PRIMARY KEY,
numeActivitate varchar(30) null
);

-- -----------------------------------------------------
-- Table `activitati_materie`
-- -----------------------------------------------------
create table if not exists activitati_materie(
idActivitateMaterie int not null auto_increment PRIMARY KEY,
tipActivitate int null,
idMaterie int null,
idProfesor int null,
nrParticipantiMax int null,
pondereNota int null
);

alter table activitati_materie add constraint fk_activitati_materie_materie foreign key(idMaterie) references materie(idMaterie)ON DELETE CASCADE ON UPDATE CASCADE;
alter table activitati_materie add constraint fk_activitati_materie_activitate foreign key(tipActivitate) references activitate(tipActivitate)ON DELETE CASCADE ON UPDATE CASCADE;

-- -----------------------------------------------------
-- Table `note`
-- -----------------------------------------------------
create table if not exists note(
idActivitateMaterie int null,
idStudent int null,
nota float null
);

ALTER TABLE note
ADD COLUMN numeActivitate varchar(30);
alter table note add constraint fk_note_activitati_materie foreign key(idActivitateMaterie) references activitati_materie(idActivitateMaterie)ON DELETE CASCADE ON UPDATE CASCADE;

-- -----------------------------------------------------
-- Table `fiecareSaptamana`
-- -----------------------------------------------------
create table if not exists fiecareSaptamana(
idActivitateMaterie int null,
ziua varchar(30) null,
oraInceput time null, 
oraSfarsit time null
);
alter table fiecareSaptamana add constraint fk_fiecareSaptamana_activitati_materie foreign key(idActivitateMaterie) references activitati_materie(idActivitateMaterie)ON DELETE CASCADE ON UPDATE CASCADE;

-- -----------------------------------------------------
-- Table `student_materie`
-- -----------------------------------------------------
create table if not exists student_materie(
idStudent int null,
idMaterie int null,
idProfesor int null,
dataInscriere date null,
nota float null
);
alter table student_materie add constraint fk_student_materie_student foreign key(idStudent) references student(idStudent) ON DELETE CASCADE ON UPDATE CASCADE;
alter table student_materie add constraint fk_student_materie_materie foreign key(idMaterie) references materie(idMaterie) ON DELETE CASCADE ON UPDATE CASCADE;

-- -----------------------------------------------------
-- Table `grup_studiu`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS grupStudiu (
idGrupStudiu INT NOT NULL  PRIMARY KEY,
denumireMaterie VARCHAR(30) NULL,
mesaj TEXT NULL);

-- -----------------------------------------------------
-- Table 'grup_studiu_student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS grup_studiu_student (
idGrupStudiu INT NULL,
idStudent INT NULL );
alter table grup_studiu_student add constraint fk_grup_studiu_student_grupStudiu foreign key(idGrupStudiu) references grupStudiu(idGrupStudiu) ON DELETE CASCADE ON UPDATE CASCADE;
alter table grup_studiu_student add constraint fk_grup_studiu_student_student foreign key(idStudent) references student(idStudent) ON DELETE CASCADE ON UPDATE CASCADE;

-- -----------------------------------------------------
-- Table `student_activitati_grup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS student_activitati_grup (
idActivitate INT NULL,
idStudent INT NULL );
alter table student_activitati_grup add constraint fk_student_activitati_grup_grup_studiu_student foreign key(idStudent) references grup_studiu_student(idStudent)ON DELETE CASCADE ON UPDATE CASCADE;

-- -----------------------------------------------------
-- Table `activitati_grup_studiu_planificare`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS activitati_grup_studiu_planificare (
idActivitate int not null auto_increment PRIMARY KEY,
denumire varchar(30) null,
idGrupStudiu int null,
idProfesor int null,
numarMinimParticipanti int null,
dataDesfasurare int null,
durata time null
);
alter table activitati_grup_studiu_planificare add constraint fk_activitati_grup_studiu_planificare_grupStudiu foreign key(idGrupStudiu) references grupStudiu(idGrupStudiu)ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE activitati_grup_studiu_planificare
CHANGE COLUMN dataDesfasurare dataDesfasurare DATE;



