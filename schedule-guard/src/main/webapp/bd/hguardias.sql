CREATE SEQUENCE seq_hg_lugares
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_hg_lugares
  OWNER TO postgres;


CREATE SEQUENCE seq_hg_turno
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_hg_turno
  OWNER TO postgres;


CREATE SEQUENCE seq_hg_ausencias
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_hg_ausencias
  OWNER TO postgres;


CREATE SEQUENCE seq_hg_horario_det
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_hg_horario_det
  OWNER TO postgres;

CREATE SEQUENCE seq_hg_horario_cab
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_hg_horario_cab
  OWNER TO postgres;



/*==============================================================*/
/* Table: HG_HORARIO_CAB                                        */
/*==============================================================*/
create table HG_HORARIO_CAB (
   HCAB_ID              INT4                 not null DEFAULT nextval('seq_hg_horario_cab'::regclass),
   HCAB_NOMBRE          VARCHAR(255)         null,
   HCAB_USUARIO         VARCHAR(255)         null,
   HCAB_FECHA_REGISTRO  TIMESTAMP            null,
   HCAB_FECHA_INICIO    DATE                 null,
   HCAB_FECHA_FIN       DATE                 null,
   constraint PK_HG_HORARIO_CAB primary key (HCAB_ID)
);

/*==============================================================*/
/* Table: HG_HORARIO_DET                                        */
/*==============================================================*/
create table HG_HORARIO_DET (
   HDET_ID              INT4                 not null DEFAULT nextval('seq_hg_horario_det'::regclass),
   TUR_ID               INT4                 null,
   LUG_ID               INT4                 null,
   GUA_CEDULA           VARCHAR(100)         null,
   HCAB_ID              INT4                 null,
   HDET_FECHA_INICIO    DATE                 null,
   HDET_FECHA_FIN       DATE                 null,
   HDET_HORA_INICIO     TIME                 null,
   HDET_HORA_FIN        TIME                 null,
   HDET_ESTADO          CHAR(1)              null,
   constraint PK_HG_HORARIO_DET primary key (HDET_ID)
);

/*==============================================================*/
/* Table: HG_AUSENCIAS                                          */
/*==============================================================*/
create table HG_AUSENCIAS (
   AUS_ID               INT4                 not null DEFAULT nextval('seq_hg_ausencias'::regclass),
   GUA_CEDULA           VARCHAR(100)         null,
   AUS_FECHA_INICIO     DATE                 null,
   AUS_FECHA_FIN        DATE                 null,
   AUS_DESCRIPCION      VARCHAR(255)         null,
   constraint PK_HG_AUSENCIAS primary key (AUS_ID)
);

/*==============================================================*/
/* Table: HG_GUARDIAS                                           */
/*==============================================================*/
create table HG_GUARDIAS (
   GUA_CEDULA           VARCHAR(100)         not null,
   GUA_NOMBRE           VARCHAR(100)         null,
   GUA_APELLIDO         VARCHAR(100)         null,
   GUA_FECHANAC         DATE                 null,
   GUA_CIUDAD           VARCHAR(100)         null,
   GUA_SEXO             CHAR(1)              null,
   GUA_TELEFONO         VARCHAR(10)          null,
   GUA_CELULAR          VARCHAR(10)          null,
   GUA_CORREO           VARCHAR(255)         null,
   GUA_DIRECCION        VARCHAR(255)         null,
   GUA_ESTADO           CHAR(1)              null,
   GUA_CCTV             BOOL                 null,
   GUA_MOTORIZADO       BOOL                 null,
   GUA_CHOFER           BOOL                 null,
   GUA_CONTROL_ACCESOS  BOOL                 null,
   GUA_CASO_TURNO       VARCHAR(50)          null,
   GUA_CASO_ESTUDIO     BOOL                 null,
   GUA_CASO_NOCTURNO    BOOL                 null,
   constraint PK_HG_GUARDIAS primary key (GUA_CEDULA)
);

/*==============================================================*/
/* Table: HG_LUGARES                                            */
/*==============================================================*/
create table HG_LUGARES (
   LUG_ID               INT4                 not null DEFAULT nextval('seq_hg_lugares'::regclass),
   LUG_NOMBRE           VARCHAR(50)          null,
   LUG_NRO_GUARDIAS     INT4                 null,
   LUG_CIUDAD           VARCHAR(50)          null,
   LUG_ESTADO           CHAR(1)              null,
   LUG_CCTV             BOOL                 null,
   LUG_CONTROL_ACCESOS  BOOL                 null,
   constraint PK_HG_LUGARES primary key (LUG_ID)
);

/*==============================================================*/
/* Table: HG_PARAMETROS                                         */
/*==============================================================*/
create table HG_PARAMETROS (
   PAR_ID               VARCHAR(30)          not null,
   PAR_NOMBRE           VARCHAR(50)          null,
   PAR_VALOR            TEXT                 null,
   constraint PK_HG_PARAMETROS primary key (PAR_ID)
);

/*==============================================================*/
/* Table: HG_TURNO                                              */
/*==============================================================*/
create table HG_TURNO (
   TUR_ID               INT4                 not null DEFAULT nextval('seq_hg_turno'::regclass),
   TUR_DESCRIPCION      VARCHAR(50)          null,
   TUR_HORA_INICIO      TIME                 null,
   TUR_HORA_FIN         TIME                 null,
   TUR_ESTADO           CHAR(1)              null,
   constraint PK_HG_TURNO primary key (TUR_ID)
);

alter table HG_AUSENCIAS
   add constraint FK_HG_AUSEN_REFERENCE_HG_GUARD foreign key (GUA_CEDULA)
      references HG_GUARDIAS (GUA_CEDULA)
      on delete restrict on update restrict;

alter table HG_HORARIO_DET
   add constraint FK_HG_HORAR_REFERENCE_HG_LUGAR foreign key (LUG_ID)
      references HG_LUGARES (LUG_ID)
      on delete restrict on update restrict;

alter table HG_HORARIO_DET
   add constraint FK_HG_HORAR_REFERENCE_HG_GUARD foreign key (GUA_CEDULA)
      references HG_GUARDIAS (GUA_CEDULA)
      on delete restrict on update restrict;

alter table HG_HORARIO_DET
   add constraint FK_HG_HORAR_REFERENCE_HG_TURNO foreign key (TUR_ID)
      references HG_TURNO (TUR_ID)
      on delete restrict on update restrict;

alter table HG_HORARIO_DET
   add constraint FK_HG_HORAR_REFERENCE_HG_HORAR foreign key (HCAB_ID)
      references HG_HORARIO_CAB (HCAB_ID)
      on delete restrict on update restrict;