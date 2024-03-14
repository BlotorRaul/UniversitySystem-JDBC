use proiectfinal;
#1) arata toti users
DELIMITER $$
create procedure get_users()
begin
	select * from users;
end$$
DELIMITER ;
#---------------------/\---------------------#
#inserare valori in tabelul fiecareSaptamana
DELIMITER $$

CREATE PROCEDURE InserareFiecareSaptamana(
    IN ziuaParam VARCHAR(30),
    IN oraInceputParam TIME,
    IN oraSfarsitParam TIME,
    IN numeMaterieParam VARCHAR(30),
    IN numeActivitateParam VARCHAR(30)
)
BEGIN
    INSERT INTO fiecareSaptamana ( ziua, oraInceput, oraSfarsit, numeMaterie, numeActivitate)
    VALUES (ziuaParam, oraInceputParam, oraSfarsitParam, numeMaterieParam, numeActivitateParam);
END $$

DELIMITER ;

call InserareFiecareSaptamana("Luni",'15:20','16:40',"Poo","Act1");
select * from fiecareSaptamana;
#---------------------/\---------------------#
DELIMITER $$
CREATE PROCEDURE getStudentIdByUserId(IN userId INT)
BEGIN
    SELECT idStudent FROM student WHERE idUser = userId;
END $$
DELIMITER ;
call getStudentIdByUserId(3);
#---------------------/\---------------------#
DELIMITER $$

CREATE PROCEDURE GetIdActivitateMaterie(IN materieId INT, IN profesorId INT, IN tipActi int)
BEGIN
    SELECT idActivitateMaterie 
    FROM activitati_materie
    WHERE idMaterie = materieId AND idProfesor = profesorId AND tipActivitate = tipActi;
END $$

DELIMITER ;
select * from profesor;

select * from fiecareSaptamana;
call GetIdActivitateMaterie(1,6,1);

#---------------------/\---------------------#
#2) sa se afiseze materia, numele activitatii(curs, seminar, laborator) si nota de la activitate
delimiter $$
create procedure get_materie_activitate_nota(idStud int)
begin
	select m.numeMaterie, a.numeActivitate,n.nota
    from student_materie sm,materie m, activitati_materie am, activitate a, note n
    where sm.idStudent = idStud and sm.idMaterie=m.idMaterie and m.idMaterie =am.idMaterie and am.tipActivitate = a.tipActivitate 
    and n.idActivitateMaterie = am.idActivitateMaterie and n.idStudent = idStud;
    
end$$
delimiter ;

call get_materie_activitate_nota(1);
#---------------------/\---------------------#
#3) Sa se afiseze materiile la care un student este inscris
delimiter $$
create procedure get_materie(idStud int)
begin
select m.numeMaterie
from student_materie sm
join materie m on
sm.idStudent = m.idMaterie 
where sm.idStudent = idStud;
end$$
delimiter ;

#---------------------/\---------------------#
#4)Sa se stearga studentul de la activitatea respectiva din tabelul note

delimiter $$
create procedure delete_student_tabel_note(idStud int, idActi int)
begin
	delete from note where idStudent = idStud and idActivitateMaterie = idActi;
end$$
delimiter ;

#---------------------/\---------------------#
#5)Sa se stearga studentul cu materia M din tabelul student_materie

delimiter $$
create procedure delete_student_materie(idStud int, idMat int)
begin
	#sters din tabela student_materie
	delete from student_materie  where idStudent = idStud and idMaterie=idMat;
end$$
delimiter ;

#---------------------/\---------------------#
#6) Sa se determine id-ul activitatilor de la materia idMat la care participa studentul idS
delimiter $$
create procedure get_idActivitate(idStud int, idMat int)
begin
	select am.idActivitateMaterie
    from activitati_materie am
    join note n on
    n.idActivitateMaterie = am.idActivitateMaterie
    where n.idStudent =idStud and am.idMaterie =idMat;
end$$
delimiter ;
call get_idActivitate(1,1);

#---------------------/\---------------------#
#7) Sa se afiseze activitatile din ziua curenta a unui profesor
delimiter $$
create procedure get_profesor_activitati_azi(idProf int)
begin
	set @astazi =(select dayname(current_date()));
    
    select m.numeMaterie, a.numeActivitate, fs.oraInceput, fs.oraSfarsit
    from materie m,activitati_materie am, activitate a, fiecaresaptamana fs
    where m.idMaterie = am.idMaterie and a.tipActivitate =am.tipActivitate and fs.idActivitateMaterie = am.idActivitateMaterie
    and fs.ziua = @astazi and am.idProfesor = idProf
    order by fs.oraInceput;
end$$
delimiter ;

#---------------------/\---------------------#
#8) Sa se afiseze toate activitatile pe care le sustine un profesor pe toata durata unei saptamani

delimiter $$
create procedure get_profesor_activitati_toata_perioada(idProf int)
begin
	select m.numeMaterie, a.numeActivitate, fs.ziua, fs.oraInceput, fs.oraSfarsit
    from activitate a, fiecaresaptamana fs, activitati_materie am, materie m
    where a.tipActivitate = am.tipActivitate and fs.idActivitateMaterie = am.idActivitateMaterie and am.idMaterie = m.idMaterie
     and am.idProfesor = idProf
     order by fs.oraInceput;
end$$
delimiter ;
call get_profesor_activitati_toata_perioada(1);
#---------------------/\---------------------#
#9)Sa se afiseze toate activitatile pe care le sustine un profesor din calendar
delimiter $$
create procedure get_prof_activitati_toate_calendar(idProf int)
begin
	select m.numeMaterie,c.dataDorita
    from calendar c, materie m 
    where c.idProfesor = idProf and m.idMaterie = c.idMaterie and c.dataDorita>=curdate()
    order by c.dataDorita;
end$$
delimiter ;

call get_prof_activitati_toate_calendar(5);
#---------------------/\---------------------#
#10)Sa se afiseze toate activitatile pe care le sustine un profesor din calendar azi
delimiter $$
create procedure get_prof_activitati_azi_calendar(idProf int)
begin
	select m.numeMaterie,c.dataDorita, time(c.dataDorita)
    from calendar c, materie m 
    where c.idProfesor = idProf and m.idMaterie = c.idMaterie and date(c.dataDorita)=curdate();
end$$
delimiter ;

#---------------------/\---------------------#
#11) Sa se insereze in tabela activitati_materie
delimiter $$
create procedure populate_activitati_materie(denumireMaterie varchar(30),denumireActivitate varchar(30), pondere int,nrMax int,idProf int)
begin
	select a.tipActivitate into @param1
    from activitate a
    where a.numeActivitate = denumireActivitate
    group by(a.tipActivitate); #ptr a fi valori distincte ptr fiecare tip de activitate
    
    select m.idMaterie into @param2
    from materie m
    where m.denumire = denumireMaterie
    group by(m.idMaterie);
    
    set @param3 = idProf;
    set @param4= nrMax;
    set @param5 = pondere;
    
    insert into activitati_materie(tipActivitate, idMaterie, idProfesor,nrParticipiantiMax,pondereNota)
    values (@param1, @param2,@param3,@param4,@param5);
    
end$$
delimiter ;
#---------------------/\---------------------#
#12) Un profesor adauga nota studentului
delimiter $$
create procedure profesor_adauga_nota_student(idProf int, denumireMaterie varchar(30),denumireActivitate varchar(30), numeStudent varchar(30), notaNoua float)
begin
	select s.idStudent into @idStud
    from student s
    join users u
    on u.idUser = s.idUser
    where concat(u.nume, ' ', u.prenume) = numeStudent;
    
    select n.idActivitate into @activitate
    from note n
    join activitati_materie am
    on n.idActivitateMaterie = am.idActivitateMaterie
    join activitate a
    on a.tipActivitate = am.tipActivitate
    join materie m
    on m.idMaterie = am.idMaterie
    join student_materie sm
    on sm.idMaterie = m.idMaterie
    where am.idProfesor = idProf and n.idStudent =@idStud and a.numeActivitate = denumireActivitate and m.numeMaterie = denumireMaterie
    group by(n.idActivitate);
    
    update note
    set nota = notaNoua
    where idStudent =@idStud and idActivitate = @activitate;
end$$
delimiter ;
#---------------------/\---------------------#
# 13. sa se afiseze toti studentii( + activitate + nota) de la toate materiile pe care le preda un profesor
delimiter $$
create procedure get_studenti_dela_profesor(idProf int)
begin
	 select m.numeMaterie, concat(u.nume+ ' '+ u.prenume) as 'nume', a.numeActivitate, n.nota
     from profesor p
     join profesor_materie pm
     on p.idProfesor = pm.idProfesor
     join materie m
     on m.idMaterie = pm.idMaterie
     join activitati_materie am
     on m.idMaterie = am.idMaterie
     join activitate a
     on a.tipActivitate = am.tipActivitate
     join note n
     on n.idActivitateMaterie = am.idActivitateMaterie
     join student s 
     on s.idStudent = n.idStudent
     join users u
     on u.idUser = s.idUser
     where am.idProfesor = idProf and p.idProfesor = am.idProfesor
     order by m.numeMaterie, concat(u.nume+ ' '+ u.prenume);
     
end$$
delimiter ;
#---------------------/\---------------------#
#14. sa se afiseze toate grupurile de studiu la care studentul se poate inscrie (sa nu fie deja inscris)
delimiter $$
create procedure get_inscriere_in_grup(idStud int)
begin
	select gs.idGrupStudiu, gs.denumireMaterie
    from student s 
    join student_materie sm
	on s.idStudent = sm.idStudent
    join materie m 
    on m.idMaterie = sm.idMaterie
    join grupstudiu gs
    on gs.denumireMaterie = m.numeMaterie
    where s.idStudent = idStud and gs.idGrupStudiu not in (
    select gs.idGrupStudiu
    from grupstudiu gs
    join grup_studiu_student gss
    on gs.idGrupStudiu = gss.idGrupStudiu
    where gss.idStudent = idStud
    );
    
     
end$$
delimiter ;
#---------------------/\---------------------#
#15. sa se afiseze grupurile de studii in care este inscris
delimiter $$
create procedure get_grupuri_inscrisi_studenti(idStud int)
begin
	select gs.idGrupStudiu,gs.denumireMAterie
    from grupstudiu gs
    join grup_studiu_student gss
    on gs.idGrupStudiu = gss.idGrupStudiu
    where gss.idStudent = idStud;
     
end$$
delimiter ;

#---------------------/\---------------------#
#16. afizeaza toti membrii dintr-un grup de studiu
delimiter $$
create procedure get_membrii_unui_grup(idGrup int)
begin
    select gss.idStudent, concat(u.nume+ ' ' + u.prenume)
	from grup_studiu_student gss 
    join student s 
    on s.idStudent = gss.idStudent
    join users u 
    on u.idUser = s.idUser
    where gss.idGrupStudiu = idGrup;
end$$
delimiter ;
#---------------------/\---------------------#
#17. Sa se insereze student in grup
delimiter $$
create procedure insert_student_grup(idGrup int,idStud int)
begin
    insert into grup_studiu_student values(idGrup,idStud);
end$$
delimiter ;

#---------------------/\---------------------#
#18. procedura care afiseaza toate mesajele dintr-un grup
delimiter $$
create procedure get_mesaje_grup(idGrup int)
begin
    select mesaj
    from grupstudiu
    where idGrupStudiu = idGrup;
end$$
delimiter ;
#---------------------/\---------------------#
#19. procedura sa faca update la mesaj
delimiter $$
create procedure update_mesaje_grup(idGrup int, mesajNou text)
begin
    update grupstudiu set mesaj = mesajNou where idGrupStudiu = idGrup;
end$$
delimiter ;
#---------------------/\---------------------#
#20 sa se afiseze toate activitatile grupului care sunt valide
delimiter $$
create procedure get_activitati_grup_valide(idGrup int)
begin
    select ag.denumire, ag.dataDesfasurare
    from activitati_grup_studiu_planificare ag
    where ag.idGrupStudiu = idGrup and ag.dataDesfasurare> CURRENT_DATE();
end$$
delimiter ;
#---------------------/\---------------------#
#21. Sa se inscrie un student la o activitate de grup
delimiter $$
create procedure insert_student_activitate(idStud int, idActi int)
begin
    insert into student_activitati_grup values(idActi, idStud);
end$$
delimiter ;

#---------------------/\---------------------#
#22. sa se afiseze toate activitatile grupului la care participa studentul idStudent
delimiter $$
create procedure get_toate_activitati_studentului(idStud int, idGrupa int)
begin
    select agsp.denumire, agsp.dataDesfasurare
    from grup_studiu_student gss 
    join  student_activitati_grup sag
    on gss.idStudent = sap.idStudent
    join activitati_grup_studiu_planificare agsp 
    on sap.idActivitate = agsp.idActivitate
    where gss.idGrupStudiu = idGrupa and gss.idStudent = idStud and agsp.dataDesfasurare > current_timestamp()
    group by sag.idActivitate;
end$$
delimiter ;
#---------------------/\---------------------#
#23. Sa se insereze o activitate de grup 

delimiter $$
create procedure insert_activitate_grup(numeActi varchar(30),idGrup int, nrMinPartic int, dataDesf datetime, durataActi time, idStud int, numeProf varchar(30))
begin
    select p.idProfesor into @idProf
    from utilizator u
    join profesor p
    on p.idUser = u.idUser
    where concat(u.nume +' '+ u.prenume) = numeProf;
    
    insert into activitati_grup_studiu_planificare(denumire,idGrupStudiu,idProfesor,numarMinimParticipanti,dataDesfasurare,durata)
    values (numeActi,idGrup,@idProf,nrMinPartic,dataDesf,durataActi);
    
    select activitati_grup_studiu_planificare.idActivitate into @idActi
    from activitati_grup_studiu_planificare
    where denumire = numeActi and idGrupStudiu = idGrup and numarMinimParticipanti = nrMinPartic and dataDesfasurare = dataDesf and durata = durataActi;
    
    insert into student_activitati_grup(idActivitate,idStudent) values (@idActiv,idStud);
    
end$$
delimiter ;
#---------------------/\---------------------#
#24. Sa se afiseze toate activitatile la care participa un student
delimiter $$
create procedure get_activitati_student_toata_saptamana(idStud int)
begin
    select a.numeActivitate, m.numeMaterie, fs.ziua, fs.oraInceput, fs.oraSfarsit
    from student_materie sm, materie m, activitati_materie am, activitate a, fiecaresaptamana fs, note n
    where sm.idStudent = idStud and sm.idMaterie = m.idMaterie and m.idMaterie = am.idMaterie and a.tipActivitate = am.tipActivitate and fs.idActivitateMAterie = am.idActivitateMAterie
    and n.idActivitateMaterie = am.idActivitateMaterie;
    
end$$
delimiter ;

#---------------------/\---------------------#
#25. Sa se afiseze materiile la care studentul nu este inscris
delimiter $$
create procedure get_materii_neinscrise_student_(idStud int)
begin
    select count(*) into @ok
    from student_materie
    where idStudent=idStud;
    
    if @ok = 0 then
		select numeMaterie
		from materie
        group by(denumire);
	else
		select m.numeMaterie
        from student_materie sm, materie m
        where sm.idStudent = idStud and m.idMateire not in(
		select m.idMaterie
        from materie m, student_materie sm
        where m.idMaterie = sm.idMAterie and sm.idStudent = idStud
        group by (m.idMaterie)
        )
        group by(m.numeMaterie);
    end if;
end$$
delimiter ;
#---------------------/\---------------------#
#26.determinare profesori cu activitatiile lor de la o anumita materie
delimiter $$
create procedure get_profesor_activitati_bymaterie(idMat int)
begin
    select am.idProfesor, concat(u.nume,' ', u.prenume), a.numeActivitate
    from activitati_materie am 
    join profesor p
    on am.idProfesor = p.idProfesor
    join users u
    on u.idUser = p.idUser
    join activitate a
    on a.tipActivitate = am.tipACtivitate
    where am.idMaterie = idMat
    group by am.idProfesor, a.tipActivitate;
end$$
delimiter ;
#---------------------/\---------------------#
#27. sa se afiseze id-urile activitatilor ale unui profesor de la o materie
delimiter $$
create procedure get_idactivitate_profesor_materie(idProf int, idMat int)
begin
    select idActivitate
    from activitati_materie
    where idMaterie = idMat and idProfesor = idProf;
end$$
delimiter ;
#---------------------/\---------------------#
#28.determinare id prof cu cei mai putini studenti pentru asignare la student cand se inscrie la curs
delimiter $$
create procedure get_idprof_ceimaiputini_studenti(materie varchar(30))
begin
    select p.idProfesor, count(sm.idStudent)
    from profesor p
    join profesor_materie pm
    on pm.idProfesor = p.idProfesor
    join materie m
    on m.idMaterie = pm.idMaterie
    join student_materie sm
    on sm.idMaterie = m.idMaterie
    where m.numeMaterie = materie and p.idProfesor = sm.idProfesor
    group by p.idProfesor
    order by count(sm.idStudent) ASC;
end$$
delimiter ;
#---------------------/\---------------------#
#29.determinare nota si pondere de la toate activitatile de la o materie ale unui student
delimiter $$
create procedure get_media(idStud int, idMat int)
begin
    select n.nota, am.pondereNota
    from note n
    join activitati_materie am
    on am.idActivitateMaterie = n.idActivitate
    where n.idStudent = idStud and am.idMaterie = idMat;
end$$
delimiter ;
#---------------------/\---------------------#
#30. seteaza nota in student_materie
delimiter $$
create procedure set_nota(idStud int, idMat int, notaNou float)
begin
    update note n
    join  activitati_materie am
    on am.idActivitateMaterie = n.idActivitateMaterie
    set n.nota = notaNou
    where n.idStudent = idStud and am.idMaterie = idMat;
end$$
delimiter ;
#---------------------/\---------------------#
#31. inserare in tabelul fiecaresaptamana
delimiter $$
create procedure insert_fiecaresaptamana(idProf int, materie varchar(30),activitate varchar(30),zi varchar(30), inc time, sf time)
begin
    set @idMat = (select idMaterie from materie where numeMAterie = materie);
    set @idActi = (select tipActivitate from activitate where numeActivitate = activitate);
    set @rezultat = (select idActivitate from activitate_materie where idMaterie = @idMat and idProfesor = idProf and tipActivitate = @idActi);
    
    insert into fiecaresaptamana values(@rezultat, zi, inc, sf);
end$$
delimiter ;
#---------------------/\---------------------#
#32. sa se determine numarul de studenti de la materia "materie" care participa la activitatile profesorului cu id-ul "idProf"
delimiter $$
create procedure numar_studenti_materie(idProf int, materie varchar(30))
begin
	select idMaterie into @idMat from materie where numeMaterie = materie;

    select count(1)
    from note n
    join activitati_materie am
    on am.idActivitateMaterie = n.idActivitateMaterie
    where am.idProfesor = idProf and am.idMAterie = @idMat
    group by am.tipActivitate;
    
end$$
delimiter ;
#---------------------/\---------------------#
#33 numar maxim de studenti care se poate inscrie la o materie predata de un anumit profesor
delimiter $$
create procedure numar_max_studenti_materie(idProf int, materie varchar(30))
begin
    select idMaterie into @idMat from materie where numeMaterie = materie;
    select min(nrParticipantiMax)
    from activitati_materie
    where idMaterie =@idMat and idProfesor = idProf;
end$$
delimiter ;
#---------------------/\---------------------#
#34. determinare profesor care preda o anumita materie
delimiter $$
create procedure get_profesor_preda_materie(materie varchar(30))
begin
    select idMaterie into @idMat from materie where numeMaterie = materie;
    select concat(u.nume,' ', u.prenume)
    from profesor p
    join users u
    on p.idUser = u.idUser
    join profesor_materie pm
    on pm.idProfesor = p.idProfesor
    where pm.idMaterie = @idMat;
end$$
delimiter ;
#---------------------/\---------------------#
#35. determinare nr studenti care participa la o activitate dintr-un grup de studiu
delimiter $$
create procedure inscriere_student_activitate(idGrup int, activitate varchar(30))
begin
    select count(gss.idStudent)
    from grup_studiu_student gss, grupstudiu gs, activitati_grup_studiu_planificare agsp
    where gss.idGrupStudiu = gs.idGrupStudiu and gs.idGrupStudiu=agsp.idGrupStudiu and
    gss.idGrupStudiu = idGrup and agsp.denumire = activitate;
end$$
delimiter ;
#---------------------/\---------------------#
#36. procedura care determina toate datele activitatilor la care vrea sa se inscrie
delimiter $$
create procedure get_date_inscriere_student_activitati(numeMat varchar(30), numeProf varchar(30))
begin
    select idMaterie into @idMat from materie where numeMaterie = numeMat;
    
    select p.idProfesor into @idProf
    from profesor p
    join users u
    on u.idUser=p.idUser
    where concat(u.nume,' ',u.prenume) = numeProf;
    
    select s.ziua, s.oraInceput, s.oraSfarsit
    from activitati_materie am, fiecaresaptamana fs, activitate a
    where am.idActivitateMaterie = fs.idActivitateMaterie and a.tipActivitate =am.tipActivitate
    and am.idMaterie = @idMat and am.idProfesor = @idProf
    group by am.idActivitateMaterie
    order by s.ziua, s.oraInceput, s.oraSfarsit;
end$$
delimiter ;
#---------------------/\---------------------#
#37. determina daca se suprapun 2 intervale de timp
delimiter $$
create procedure verificare_intervale_suprapuse(z1 varchar(45), o1 time, o2 time, z2 varchar(45), a1 time, a2 time)
begin
    select if(z1=z2 and ( (o1<a1 and a1<o2) or(o1<a2 and a2<o2) or (o1=a1 and a2=o2) ),false,true);
end$$
delimiter ;
#---------------------/\---------------------#
#38. administrator: cautare utilizator dupa nume si tip
delimiter $$
create procedure admin_cauta_user(numeUtilizator varchar(30), tipUtilizator varchar(30))
begin
    select concat(nume,' ',prenume),numarTelefon,CNP,IBAN,adresa,email,nrContract
    from users
    where username = numeUtilizator and tipUser = tipUtilizator;
end$$
delimiter ;
#---------------------/\---------------------#
#39. administrator: cautare profesor dupa numele cursului
delimiter $$
create procedure admin_cauta_profesor_dupa_curs(numeCurs varchar(30))
begin
    select concat(u.nume, ' ', u.prenume)
    from users u, profesor p, profesor_materie pm, materie m
    where u.idUser = p.idUser and p.idProfesor = pm.idProfesor and pm.idMaterie = m.idMaterie and
    m.numeMaterie= numeCurs and u.tipUser = 'Profesor';
end$$
delimiter ;
#---------------------/\---------------------#
#40 administrator: vizualizarea tuturor studentilor inscrisi la un curs
delimiter $$
create procedure admin_vede_studenti_inscrisi_curs(numeCurs varchar(30))
begin
    select concat(u.nume, ' ', u.prenume)
    from users u, student s, student_materie sm, materie m
    where u.idUser = s.idUser and s.idStudent = sm.idStudent and m.idMaterie= sm.idMaterie
    and u.tipUser ='Student' and m.numeMaterie = numeCurs;
end$$
delimiter ;
#---------------------/\---------------------#
#41. administrator: asignare profesori la cursuri
delimiter $$
create procedure admin_inscrie_profesori(numeProf varchar(30), numeCurs varchar(30))
begin
    select p.idProfesor into @idProf
    from profesor p, users u 
    where p.idUser = u.idUser and concat(u.nume, ' ', u.prenume) = numeProfesor 
    and u.tipUser ='Profesor';
    
    select m.idMaterie into @idMat
    from materie m
    where m.numeMaterie = numeCurs;
    
    insert into profesor_materie(idProfesor, idMaterie) values (@idProf, @idMat);
end$$
delimiter ;
#---------------------/\---------------------#
#42 determinare materii predate de un profesor
delimiter $$
create procedure get_materii_predate_profesor(numeProf varchar(30))
begin
    select p.idProfesor into @idProf
    from profesor p
    join user u
    on p.idUser =u.idUser
    where concat(u.nume + ' ' + u.prenume) = numeProf;
    
    select m.numeMaterie
    from materie m
    where m.idMaterie not in (
    select m.idMaterie
    from materie m
    join profesor_materie pm
    on m.idMaterie = pm.idMaterie
    where pm.idProfesor =@idProf)
    group by(m.numeMaterie);
end$$
delimiter ;
#---------------------/\---------------------#
#43. modifica utilizatorii
delimiter $$
create procedure update_user(cnp_ varchar(45), adrs text, nrTel varchar(45), mail varchar(45), iban_ varchar(45), nrC int, numeUtilizator varchar(45), tipU varchar(45))
begin
    UPDATE utilizator SET CNP=cnp_, adresa=adrs, numarTelefon=nrTel, email=mail, IBAN=iban_, nrContract=nrC WHERE concat(nume,' ',prenume)=numeUtilizator AND tipUtilizator=tipU;
end$$
delimiter ;
