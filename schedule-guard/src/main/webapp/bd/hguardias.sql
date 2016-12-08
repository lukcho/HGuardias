CREATE SEQUENCE seq_hg_horario_det
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_hg_horario_det
  OWNER TO postgres;


/*==============================================================*/
/* Table: HG_HORARIO_CAB                                        */
/*==============================================================*/
create table HG_HORARIO_CAB (
   HCAB_ID              INT4                 not null,
   HCAB_NOMBRE          VARCHAR(255)         null,
   HCAB_USUARIO         VARCHAR(255)         null,
   HCAB_FECHA_REGISTRO  TIMESTAMP            null,
   HCAB_FECHA_INICIO    DATE                 null,
   HCAB_FECHA_FIN       DATE                 null,
   HCAB_NUMERO_REGISTROS_TOTAL INT4          null,
   HCAB_NUMERO_REGISTROS_CREADOS INT4        null,
   HCAB_NUMERO_LUGARES_VACIOS INT4           null,
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
   AUS_ID               INT4                 not null,
   GUA_CEDULA           VARCHAR(100)         null,
   TIP_AUS_ID           INT4                 null,
   AUS_FECHA_INICIO     DATE                 null,
   AUS_FECHA_FIN        DATE                 null,
   AUS_DESCRIPCION      VARCHAR(255)         null,
   constraint PK_HG_AUSENCIAS primary key (AUS_ID)
);

/*==============================================================*/
/* Table: HG_FALTOS                                             */
/*==============================================================*/
create table HG_FALTOS (
   FALTO_ID             INT4                 not null,
   HDET_ID              INT4                 null,
   GUA_CEDULA           VARCHAR(100)         null,
   FALTO_DESCRIPCION    VARCHAR(255)         null,
   FALTO_FECHA_FALTA     TIMESTAMP            null,
   constraint PK_HG_FALTOS primary key (FALTO_ID)
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
   GUA_TIPO_SANGRE      VARCHAR(100)         null,
   GUA_ESTADO_CIVIL     VARCHAR(100)         null,
   GUA_DIRECCION        VARCHAR(255)         null,
   GUA_ESTADO           CHAR(1)              null,
   GUA_CCTV             BOOL                 null,
   GUA_CENTRO_EMPRENDIMIENTO BOOL            null,
   GUA_MOTORIZADO       BOOL                 null,
   GUA_CHOFER           BOOL                 null,
   GUA_CONTROL_ACCESOS  BOOL                 null,
   GUA_CASO_TURNO       INT4                 null,
   GUA_CASO_ESTUDIO     BOOL                 null,
   GUA_CASO_NOCTURNO    BOOL                 null,
   GUA_TIPO_LICENCIA_CHOFER VARCHAR(2)       null,
   GUA_TIPO_LICENCIA_MOTORIZADO VARCHAR(2)   null,
   constraint PK_HG_GUARDIAS primary key (GUA_CEDULA)
);

/*==============================================================*/
/* Table: HG_GUARDIAS_DIAS_NO_TRABAJO                           */
/*==============================================================*/
create table HG_GUARDIAS_DIAS_NO_TRABAJO (
   GUA_DIA_TRABAJO_ID   INT4                 not null,
   GUA_CEDULA           VARCHAR(100)         null,
   GUA_DIA_TRABAJO_LUNES BOOL                null,
   GUA_DIA_TRABAJO_MARTES BOOL               null,
   GUA_DIA_TRABAJO_MIERCOLES BOOL            null,
   GUA_DIA_TRABAJO_JUEVES BOOL               null,
   GUA_DIA_TRABAJO_VIERNES BOOL              null,
   GUA_DIA_TRABAJO_SABADO BOOL               null,
   GUA_DIA_TRABAJO_DOMINGO BOOL              null,
   constraint PK_HG_GUARDIAS_DIAS_NO_TRABAJO primary key (GUA_DIA_TRABAJO_ID)
);

/*==============================================================*/
/* Table: HG_GUARDIAS_PENDIENTE                                 */
/*==============================================================*/
create table HG_GUARDIAS_PENDIENTE (
   GUAPEN_ID            INT4                 not null,
   GUA_CEDULA           VARCHAR(100)         null,
   GUAPEN_FECHA         DATE                 null,
   constraint PK_HG_GUARDIAS_PENDIENTE primary key (GUAPEN_ID)
);

/*==============================================================*/
/* Table: HG_HISTORIAL_MOVIMIENTOS                              */
/*==============================================================*/
create table HG_HISTORIAL_MOVIMIENTOS (
   HISTO_ID             INT4                 not null,
   HCAB_ID              INT4                 null,
   HDET_FECHA           DATE                 null,
   GUA_CEDULA_DESDE     VARCHAR(100)         null,
   GUA_CEDULA_HACIA     VARCHAR(100)         null,
   LUG_ID_DESDE         INT4                 null,
   LUG_ID_HACIA         INT4                 null,
   HISTO_FECHA_MOVIMIENTO TIMESTAMP          null,
   constraint PK_HG_HISTORIAL_MOVIMIENTOS primary key (HISTO_ID)
);

/*==============================================================*/
/* Table: HG_LUGARES                                            */
/*==============================================================*/
create table HG_LUGARES (
   LUG_ID               INT4                 not null,
   LUG_NOMBRE           VARCHAR(50)          null,
   LUG_NRO_GUARDIAS     INT4                 null,
   LUG_CIUDAD           VARCHAR(50)          null,
   LUG_CCTV             BOOL                 null,
   LUG_CENTRO_EMPRENDIMIENTO BOOL            null,
   LUG_CONTROL_ACCESOS  BOOL                 null,
   LUG_LUNES            BOOL                 null,
   LUG_MARTES           BOOL                 null,
   LUG_MIERCOLES        BOOL                 null,
   LUG_JUEVES           BOOL                 null,
   LUG_VIERNES          BOOL                 null,
   LUG_SABADO           BOOL                 null,
   LUG_DOMINGO          BOOL                 null,
   LUG_ESTADO           CHAR(1)              null,
   LUG_PRIORIDAD        INT4                 null,
   constraint PK_HG_LUGARES primary key (LUG_ID)
);

/*==============================================================*/
/* Table: HG_LUGARES_TURNOS_VACIOS                              */
/*==============================================================*/
create table HG_LUGARES_TURNOS_VACIOS (
   HGLUGTUR_ID          INT4                 not null,
   HCAB_ID              INT4                 null,
   LUG_ID               INT4                 null,
   TUR_ID               INT4                 null,
   HGLUGTUR_FECHA_INICIO DATE                null,
   constraint PK_HG_LUGARES_TURNOS_VACIOS primary key (HGLUGTUR_ID)
);

/*==============================================================*/
/* Table: HG_TIPO_AUSENCIA                                      */
/*==============================================================*/
create table HG_TIPO_AUSENCIA (
   TIP_AUS_ID           INT4                 not null,
   TIP_AUS_NOMBRE       VARCHAR(100)         null,
   TIP_AUS_DESCRIPCION  VARCHAR(100)         null,
   constraint PK_HG_TIPO_AUSENCIA primary key (TIP_AUS_ID)
);

/*==============================================================*/
/* Table: HG_LUGAR_TURNO                                        */
/*==============================================================*/
create table HG_LUGAR_TURNO (
   LUG_TUR              INT4                 not null,
   LUG_ID               INT4                 null,
   TUR_ID               INT4                 null,
   LUG_TUR_NUMERO_GUARDIAS INT4              null,
   constraint PK_HG_LUGAR_TURNO primary key (LUG_TUR)
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
   TUR_ID               INT4                 not null,
   TUR_DESCRIPCION      VARCHAR(50)          null,
   TUR_HORA_INICIO      TIME                 null,
   TUR_HORA_FIN         TIME                 null,
   TUR_CODIGO_COLOR     VARCHAR(50)          null,
   TUR_ESTADO           CHAR(1)              null,
   constraint PK_HG_TURNO primary key (TUR_ID)
);

alter table HG_AUSENCIAS
   add constraint FK_HG_AUSEN_REFERENCE_HG_TIPO_ foreign key (TIP_AUS_ID)
      references HG_TIPO_AUSENCIA (TIP_AUS_ID)
      on delete restrict on update restrict;

alter table HG_AUSENCIAS
   add constraint FK_HG_AUSEN_REFERENCE_HG_GUARD foreign key (GUA_CEDULA)
      references HG_GUARDIAS (GUA_CEDULA)
      on delete restrict on update restrict;
      
alter table HG_FALTOS
   add constraint FK_HG_FALTO_REFERENCE_HG_GUARD foreign key (GUA_CEDULA)
      references HG_GUARDIAS (GUA_CEDULA)
      on delete restrict on update restrict;

alter table HG_FALTOS
   add constraint FK_HG_FALTO_REFERENCE_HG_HORAR foreign key (HDET_ID)
      references HG_HORARIO_DET (HDET_ID)
      on delete restrict on update restrict;
      
alter table HG_GUARDIAS_DIAS_NO_TRABAJO
   add constraint FK_HG_GUARD_REFERENCE_HG_GUARD foreign key (GUA_CEDULA)
      references HG_GUARDIAS (GUA_CEDULA)
      on delete restrict on update restrict;
      
alter table HG_GUARDIAS_PENDIENTE
   add constraint FK_HG_GUARD_REFERENCE_HG_GUARD foreign key (GUA_CEDULA)
      references HG_GUARDIAS (GUA_CEDULA)
      on delete restrict on update restrict;      
      
alter table HG_HISTORIAL_MOVIMIENTOS
   add constraint FK_HG_HISTO_REFERENCE_HG_HORAR foreign key (HCAB_ID)
      references HG_HORARIO_CAB (HCAB_ID)
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

alter table HG_LUGARES_TURNOS_VACIOS
   add constraint FK_HG_LUGAR_REFERENCE_HG_HORAR foreign key (HCAB_ID)
      references HG_HORARIO_CAB (HCAB_ID)
      on delete restrict on update restrict;

alter table HG_LUGARES_TURNOS_VACIOS
   add constraint FK_HG_LUGAR_REFERENCE_HG_TURNO foreign key (TUR_ID)
      references HG_TURNO (TUR_ID)
      on delete restrict on update restrict;

alter table HG_LUGARES_TURNOS_VACIOS
   add constraint FK_HG_LUGAR_REFERENCE_HG_LUGAR foreign key (LUG_ID)
      references HG_LUGARES (LUG_ID)
      on delete restrict on update restrict;

alter table HG_LUGAR_TURNO
   add constraint FK_HG_LUGAR_REFERENCE_HG_TURNO foreign key (TUR_ID)
      references HG_TURNO (TUR_ID)
      on delete restrict on update restrict;

alter table HG_LUGAR_TURNO
   add constraint FK_HG_LUGAR_REFERENCE_HG_LUGAR foreign key (LUG_ID)
      references HG_LUGARES (LUG_ID)
      on delete restrict on update restrict;
      
      
      
      