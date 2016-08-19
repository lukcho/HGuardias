insert into hg_parametros  values ('admin_correo', 'Correos de los administradores','lcorrea@yachay.gob.ec');
insert into hg_parametros  values ('envio_mail', 'Dirección de Servicio Web ','http://yachay-ws.yachay.gob.ec/email');
insert into hg_parametros  values ('find_personas', 'Buscar persona','http://yachay-ws.yachay.gob.ec/data/WSPersonaEntity/dni=');
insert into hg_parametros  values ('id_ws_mail', 'ID para envio del correo','sof_solutions');
insert into hg_parametros  values ('login_ws', 'Direccion de servicio para web login','http://10.1.0.115:8080/app-permisos/WSLogin/postPermisos');

insert into hg_lugares  values (default, 'CCTV',4,'Urcuquí','A',true,false);
insert into hg_lugares  values (default, 'San Eloy',3,'Urcuquí','A',false,false);
insert into hg_lugares  values (default, 'Centro de emprendimiento',3,'Urcuquí','A',true,false);
insert into hg_lugares  values (default, 'Tanques de Agua',3,'Urcuquí','A',false,false);
insert into hg_lugares  values (default, 'Instituto',3,'Urcuquí','A',false,false);
insert into hg_lugares  values (default, 'Control 1',3,'Urcuquí','A',false,false);
insert into hg_lugares  values (default, 'Control 2',1,'Urcuquí','A',false,false);
insert into hg_lugares  values (default, 'Rosario',3,'Urcuquí','A',false,false);
insert into hg_lugares  values (default, 'Casa Blanca',3,'Urcuquí','A',false,false);
insert into hg_lugares  values (default, 'Chalet',3,'Urcuquí','A',false,false);
insert into hg_lugares  values (default, 'Ingenio',3,'Urcuquí','A',false,false);
insert into hg_lugares  values (default, 'Casas Patrimoniales',3,'Urcuquí','A',false,false);
insert into hg_lugares  values (default, '40 Casas',3,'Urcuquí','A',false,false);
insert into hg_lugares  values (default, 'Bloques',3,'Urcuquí','A',false,true);
insert into hg_lugares  values (default, 'Centro de enrolamiento',3,'Urcuquí','A',false,true);


insert into hg_turno  values (default, 'Diurno','07:00:00','15:00:00','A');
insert into hg_turno  values (default, 'Vespertino','15:00:00','23:00:00','A');
insert into hg_turno  values (default, 'Nocturno','23:00:00','07:00:00','A');

