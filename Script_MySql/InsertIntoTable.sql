use proiect;
-- -----------------------------------------------------
-- Table `login`
-- -----------------------------------------------------
INSERT INTO login (idUser, username, password) VALUES 
(1, 'ionpopescu', 'parola123'),
(2, 'mariaionescu', 'parola321'),
(3, 'andreineagu', 'parola456'),
(4, 'elenadumitru', 'parola654'),
(5, 'vladdragomir', 'parola789'),
(6, 'cristinavoicu', 'parola987'),
(7, 'lauradinu', 'parola1010'),
(8, 'bogdanpop', 'parola0101'),
(9,  'raulblotor', '1234'),
(10, 'ciunseba','parola0101'),
(11, 'popabogdan','parola0101'),
(12, 'balancaius', 'parola0101'),
(13, 'trufasiuoana', 'parola0101'),
(14, 'danescumiruna', 'parola0101'),
(15, 'moldovanliana', 'parola0101'),
(16, 'senilacoco', 'parola0101'),
(17, 'stancumihai', 'parola0101'),
(18, 'tantituta', 'parola0101'),
(19, 'ionraul', 'parola0101');


-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
INSERT INTO users (tipUser, CNP, nume, prenume, adresa, numarTelefon, email, IBAN, numarContract, username, parola) VALUES 
('Student', '1234567890123', 'Ion', 'Popescu', 'Str. Libertatii, nr. 10, Bucuresti', '0722000111', 'ion.popescu@email.ro', 'RO49AAAA1B31007593840000', 'CNT12345', 'ionpopescu', 'parola123'),
('Profesor', '9876543210987', 'Maria', 'Ionescu', 'Str. Revolutiei, nr. 5, Cluj-Napoca', '0722111222', 'maria.ionescu@email.ro', 'RO49BBBB1B31007593840000', 'CNT98765', 'mariaionescu', 'parola321'),
('Student', '2567890123456', 'Andrei', 'Neagu', 'Str. Mihai Eminescu, nr. 22, Timisoara', '0722333444', 'andrei.neagu@email.ro', 'RO24CCCC1B31007593840000', 'CNT67891', 'andreineagu', 'parola456'),
('Profesor', '7890123456789', 'Elena', 'Dumitru', 'Str. Aviatorilor, nr. 3, Iasi', '0722444555', 'elena.dumitru@email.ro', 'RO24DDDD1B31007593840000', 'CNT45678', 'elenadumitru', 'parola654'),
('Student', '1945678901234', 'Vlad', 'Dragomir', 'Str. Unirii, nr. 44, Brasov', '0722555666', 'vlad.dragomir@email.ro', 'RO35EEEE1B31007593840000', 'CNT10101', 'vladdragomir', 'parola789'),
('Profesor', '1567890123456', 'Cristina', 'Voicu', 'Str. Pacii, nr. 7, Constanta', '0722666777', 'cristina.voicu@email.ro', 'RO35FFFF1B31007593840000', 'CNT20202', 'cristinavoicu', 'parola987'),
('Student', '1965432178901', 'Laura', 'Dinu', 'Str. Mihai Viteazul, nr. 33, Galati', '0723000444', 'laura.dinu@email.ro', 'RO38GGGG1B31007593840000', 'CNT30303', 'lauradinu', 'parola1010'),
('Profesor', '2567890432198', 'Bogdan', 'Pop', 'Str. Libertatii, nr. 9, Sibiu', '0723000555', 'bogdan.pop@email.ro', 'RO38HHHH1B31007593840000', 'CNT40404', 'bogdanpop', 'parola0101'),
('Administrator', '1960524964855', 'Blotor', 'Raul', '9442 Ohio Street', '0756872168', 'raulblotor@gmail.com', 'RO68RFRL5323647931533462', 'CNT45865','raulblotor','1234'),
('Student', '1880823391622', 'Ciun', 'Seba', 'STR. OZANA nr. 31NEAMŢ', '0785196237', 'email109@gmail.com', 'RO66PORL9643949451324852','CNT40402','ciunseba','parola0101'),
('Student', '6020111282061', 'Popa', 'Bogdan', 'STR. CUZA I. AL. nr. 712', '0795817931', 'email108@gmail.com', 'RO93RZBR6415277156241589','CNT40405','popabogdan','parola0101'),
('Student', '1961018297901', 'Balan', 'Caius', 'STR. ALECSANDRI VASILE nr. 1', '0762669644', 'email107@gmail.com', 'RO45RZBR9556181131258995','CNT40406','balancaius','parola0101'),
('Student', '1911227322219', 'Trufasiu', 'Oana', 'BD. VICTORIEI nr. 75', '0718606580', 'email106@gmail.com', 'RO88PORL6816134996846722','CNT40407','trufasiuoana','parola0101'),
('Student', '1980529220151', 'Danescu', 'Miruna', 'Strada Simion Barnutiu 12', '0792657618', 'email105@gmail.com', 'RO13RZBR9896277965434457','CNT40408','danescumiruna','parola0101'),
('Student', '1880704095202', 'Moldovan', 'Liana', 'STR. JIULUI nr. 1', '0773171239', 'email104@gmail.com', 'RO43RZBR7892235656367736','CNT40409','moldovanliana','parola0101'),
('Student', '2970510401889', 'Senila', 'Coco', 'DN 1A NR. 4', '0794388574', 'email103@gmail.com', 'RO90RZBR8661654928594455','CNT40410','senilacoco','parola0101'),
('Student', '2900219171030', 'Stancu', 'Mihai', 'Bulevardul Nicolae Grigorescu 41', '0735414752', 'email102@gmail.com', 'RO62RZBR5927758534654274','CNT40411','stancumihai','parola0101'),
('Student', '5010321345477', 'Tanti', 'Tuta', 'STR. LIPATTI DINU bl. 37A', '0716873326', 'email101@gmail.com', 'RO44RZBR1132719417663996','CNT40412','tantituta','parola0101'),
('Administrator', '1964524964855', 'Ion', 'Raul', '9442 Ohio Street', '0756872168', 'ionraul@gmail.com', 'RO68RFRL5323644931533462', 'CNT45825','ionraul','1234');

-- -----------------------------------------------------
-- Table `administrator`
-- -----------------------------------------------------
INSERT INTO administrator (idUser, superAdministrator) VALUES 
(9, 1),  -- Presupunem că Maria Ionescu este un administrator și un superadministrator
(19, 0);  



-- -----------------------------------------------------
-- Table `profesor`
-- -----------------------------------------------------
INSERT INTO profesor (idUser, minimOre, maximOre, departament) VALUES 
('2','20', '40','calculatoare'),
('4','20', '40','calculatoare'),
('6','20', '40','calculatoare'),
('8','20', '40','automatica');

-- -----------------------------------------------------
-- Table `student`
-- -----------------------------------------------------
INSERT INTO student (idUser, oreSustinute, anInvatamant) VALUES 
('1', '33', '1'),
('3', '33', '1'),
('5', '33', '1'),
('7', '31', '1'),
('10', '31', '1'),
('11', '30', '1'),
('12', '33', '1'),
('13', '33', '1'),
('14', '32', '2'),
('15', '32', '2'),
('16', '32', '2'),
('17', '31', '2'),
('18', '31', '2');

-- -----------------------------------------------------
-- Table `calendar`
-- -----------------------------------------------------
INSERT INTO calendar (idProfesor, idMaterie, numeMaterie, nrMaxStudenti, dataDorita) VALUES 
(1, 1, 'POO', 30, '2023-03-15 10:00:00'),
(1, 2, 'BD', 30, '2023-03-16 10:00:00'),
(2, 3, 'AF', 25, '2023-03-20 10:00:00'),
(2, 4, 'MES', 25, '2023-03-21 10:00:00'),
(3, 5, 'CAN', 20, '2023-04-05 10:00:00'),
(3, 6, 'PSN', 20, '2023-04-06 10:00:00'),
(4, 1, 'POO', 35, '2023-04-10 10:00:00'),
(4, 2, 'BD', 35, '2023-04-11 10:00:00');


-- -----------------------------------------------------
-- Table `profesor_materie`
-- -----------------------------------------------------
INSERT INTO profesor_materie (idProfesor, idMaterie) VALUES 
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 5),
(3, 6),
(4, 7),
(4, 8);

-- -----------------------------------------------------
-- Table `materie`
-- -----------------------------------------------------
INSERT INTO materie (descriere, numeMaterie, dataIncepere, dataSfarsire) VALUES 
('programare orientata obiect', 'POO', '2023-02-20', '2023-06-10'),
('baze de date', 'BD', '2023-02-20', '2023-06-10'),
('algoritmi fundamentali', 'AF', '2023-02-20', '2023-06-10'),
('masuratori', 'MES', '2023-02-20', '2023-06-10'),
('circuite analogice', 'CAN', '2023-02-20', '2023-06-10'),
('sisteme numerice', 'PSN', '2023-02-20', '2023-06-10');


-- -----------------------------------------------------
-- Table `activitate`
-- -----------------------------------------------------
INSERT INTO activitate (numeActivitate) VALUES 
('curs'),
('seminar'),
('laborator');

-- -----------------------------------------------------
-- Table `activitati_materie`
-- -----------------------------------------------------
INSERT INTO activitati_materie (tipActivitate, idMaterie, idProfesor, nrParticipantiMax, pondereNota) VALUES 
(1, 1, 1, 30, 50),
(2, 1, 1, 30, 30),
(3, 1, 1, 30, 20),
(1, 3, 2, 25, 50),
(2, 4, 2, 25, 30),
(3, 3, 2, 25, 20),
(1, 5, 3, 20, 25),
(2, 6, 3, 20, 25),
(1, 4, 4, 35, 40),
(2, 5, 4, 35, 35),
(3, 4, 4, 35, 25);

-- -----------------------------------------------------
-- Table `note`
-- -----------------------------------------------------
INSERT INTO note (idActivitateMaterie, idStudent, nota) VALUES 
(3, 1, 8.5),
(4, 1, 9.0),
(5, 2, 7.5),
(5, 2, 8.0),
(6, 3, 7.0),
(7, 3, 7.5),
(8, 4, 9.0),
(9, 4, 8.5);


-- -----------------------------------------------------
-- Table `fiecareSaptamana`
-- -----------------------------------------------------
INSERT INTO fiecareSaptamana (idActivitateMaterie, ziua, oraInceput, oraSfarsit) VALUES 
(10, 'Luni', '08:00:00', '10:00:00'),
(11, 'Miercuri', '12:00:00', '14:00:00'),
(4, 'Marti', '08:00:00', '10:00:00'),
(5, 'Joi', '12:00:00', '14:00:00'),
(6, 'Vineri', '10:00:00', '12:00:00'),
(7, 'Sambata', '14:00:00', '16:00:00'),
(8, 'Luni', '14:00:00', '16:00:00'),
(9, 'Marti', '16:00:00', '18:00:00');
-- -----------------------------------------------------
-- Table `student_materie`
-- -----------------------------------------------------
INSERT INTO student_materie (idStudent, idMaterie, idProfesor, dataInscriere, nota) VALUES 
(1,1,1,curdate(),null),
(2,1,2,curdate(),null),
(3,1,3,curdate(),null),
(4,1,1,curdate(),null),
(5,1,2,curdate(),null),
(6,2,4,curdate(),null),
(7,2,2,curdate(),null),
(8,2,4,curdate(),null),
(9,3,3,curdate(),null),
(10,3,4,curdate(),null),
(8,5,4,curdate(),null),
(4,5,1,curdate(),null),
(3,5,1,curdate(),null),
(4,5,5,curdate(),null),
(5,6,4,curdate(),null),
(6,6,1,curdate(),null),
(8,6,1,curdate(),null);

-- -----------------------------------------------------
-- Table `grup_studiu`
-- -----------------------------------------------------
INSERT INTO grupStudiu (idGrupStudiu, denumireMaterie, mesaj) VALUES 
(1, 'POO', 'Bine ati venit in grupul de studiu '),
('2', 'BD', 'Bine ati venit in grupul de studiu '),
('3', 'AF', 'Bine ati venit in grupul de studiu '),
('4', 'MES', 'Bine ati venit in grupul de studiu '),
('5', 'CAN', 'Bine ati venit in grupul de studiu '),
('6', 'PSN', 'Bine ati venit in grupul de studiu ');

-- -----------------------------------------------------
-- Table 'grup_studiu_student`
-- -----------------------------------------------------
INSERT INTO grup_studiu_student (idGrupStudiu, idStudent) VALUES 
(1, 1);  -- Presupunem că studentul Ion Popescu este membru în grupul de studiu de Programare
-- -----------------------------------------------------
-- Table `student_activitati_grup`
-- -----------------------------------------------------
INSERT INTO student_activitati_grup (idActivitate, idStudent) VALUES 
(1, 1);
-- -----------------------------------------------------
-- Table `activitati_grup_studiu_planificare`
-- -----------------------------------------------------
INSERT INTO activitati_grup_studiu_planificare (denumire, idGrupStudiu, idProfesor, numarMinimParticipanti, dataDesfasurare, durata) VALUES 
('Sesiune Q&A', 1, 1, 5, '2023-04-15', '01:30:00');
select * from activitati_grup_studiu_planificare;





