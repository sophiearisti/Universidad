--correcto
Create table Tipo_Cuenta
(
  ID_TipoCuenta NUMBER(10,0) NOT NULL,
  Nombre_tipo VARCHAR(25) NOT NULL,
  PRIMARY KEY (ID_TipoCuenta),
  unique (Nombre_tipo),
  CHECK (Nombre_tipo in ('cliente','dueño','trabajador'))
);

CREATE SEQUENCE TIPO_CUENTA_ID_SEQ;
CREATE OR REPLACE TRIGGER TIPO_CUENTA_ID_TRIG
BEFORE INSERT ON TIPO_CUENTA
FOR EACH ROW
BEGIN
    IF :new.ID_TIPOCUENTA is null then
        :new.ID_TIPOCUENTA := TIPO_CUENTA_ID_SEQ.NEXTVAL;
    end if;
END;

--correcto
Create table Tipo_Pago
(
  ID_Tipo NUMBER(10,0) NOT NULL,
  Tipo_Pago VARCHAR(25),
  PRIMARY KEY (ID_Tipo),
  unique (Tipo_Pago),
  CHECK(Tipo_Pago in ('efectivo','tarjeta','bono'))
);

--TRIG ID TIPO_PAGO
CREATE SEQUENCE TIPO_PAGO_ID_SEQ;
CREATE OR REPLACE TRIGGER TIPO_PAGO_ID_TRIG
BEFORE INSERT ON TIPO_PAGO
FOR EACH ROW
BEGIN
    IF :new.ID_TIPO is null then
        :new.ID_TIPO:= TIPO_PAGO_ID_SEQ.NEXTVAL;
    end if;
END;

--correcto
Create table Ubicacion
(
  ID_ubicacion NUMBER(10,0) NOT NULL,
  pais VARCHAR(25),
  PRIMARY KEY (ID_ubicacion),
  unique (pais)
);

--TRIG ID TIPO_PAGO
CREATE SEQUENCE Ubicacion_ID_SEQ;
CREATE OR REPLACE TRIGGER Ubicacion_ID_TRIG
BEFORE INSERT ON Ubicacion
FOR EACH ROW
BEGIN
    IF :new.ID_ubicacion is null then
        :new.ID_ubicacion:= Ubicacion_ID_SEQ.NEXTVAL;
    end if;
END;

--correcto
Create table Tipo_propiedades
(
  IDtipo NUMBER(10,0) NOT NULL,
  tipo VARCHAR(15) NOT NULL,
  PRIMARY KEY (IDtipo),
  UNIQUE (tipo)
);

--TRIG ID TIPO_PROPIEDADES
CREATE SEQUENCE TIPO_PROPIEDADES_ID_SEQ;
CREATE OR REPLACE TRIGGER TIPO_PROPIEDADES_ID_TRIG
BEFORE INSERT ON TIPO_PROPIEDADES
FOR EACH ROW
BEGIN
    IF :new.IDTIPO is null then
        :new.IDTIPO:= TIPO_PROPIEDADES_ID_SEQ.NEXTVAL;
    end if;
END;

--correcto
Create table Tipo_Servicio
(
  ID_TipoServicio NUMBER(10,0) NOT NULL,
  Servicio VARCHAR(25) NOT NULL,
  Costo_Servicio float(3),
  PRIMARY KEY (ID_TipoServicio),
  UNIQUE (Servicio)
);

--TRIG ID TIPO_SERVICIO
CREATE SEQUENCE TIPO_SERVICIO_ID_SEQ;
CREATE OR REPLACE TRIGGER TIPO_SERVICIO_ID_TRIG
BEFORE INSERT ON TIPO_SERVICIO
FOR EACH ROW
BEGIN
    IF :new.ID_TIPOSERVICIO is null then
        :new.ID_TIPOSERVICIO:= TIPO_SERVICIO_ID_SEQ.NEXTVAL;
    end if;
END;

--correcto






--TRIG ID SERVICIO
CREATE SEQUENCE SERVICIO_ID_SEQ;
CREATE OR REPLACE TRIGGER SERVICIO_ID_TRIG
BEFORE INSERT ON SERVICIO
FOR EACH ROW
BEGIN
    IF :new.ID_SERVICIO is null then
        :new.ID_SERVICIO:= SERVICIO_ID_SEQ.NEXTVAL;
    end if;
END;

--correcto
Create table Impuestos
(
  ID_Impuesto NUMBER(10,0) NOT NULL,
  Tipo VARCHAR(25) NOT NULL,
  Porcentaje FLOAT(3) NOT NULL,
  PRIMARY KEY (ID_Impuesto),
  UNIQUE (Tipo)
);

--TRIG ID IMPUESTO
CREATE SEQUENCE IMPUESTO_ID_SEQ;
CREATE OR REPLACE TRIGGER IMPUESTOS_ID_TRIG
BEFORE INSERT ON IMPUESTOS
FOR EACH ROW
BEGIN
    IF :new.ID_IMPUESTO is null then
        :new.ID_IMPUESTO:= IMPUESTO_ID_SEQ.NEXTVAL;
    end if;
END;

--correcto
create table comisiones
(
 ID_Comision NUMBER(10,0) NOT NULL,
 tipo varchar(25) NOT NULL,
 porcentaje float(3) NOT NULL,
 primary key(ID_Comision),
 UNIQUE(tipo)
);

--TRIG ID COMISIONES
CREATE SEQUENCE COMISIONES_ID_SEQ;
CREATE OR REPLACE TRIGGER COMISIONES_ID_TRIG
BEFORE INSERT ON COMISIONES
FOR EACH ROW
BEGIN
    IF :new.ID_COMISION is null then
        :new.ID_COMISION:= COMISIONES_ID_SEQ.NEXTVAL;
    end if;
END;

--correcto
Create table Sucursal
(
  Nombre_Sucursal VARCHAR(1) NOT NULL,
  Ciudad VARCHAR(25) NOT NULL,
  PRIMARY KEY (Nombre_Sucursal)
);

--correcto
Create table Trabajadores
(
  ID_Trabajador NUMBER(10,0) NOT NULL,
  Nombre VARCHAR(25) NOT NULL,
  Apellido VARCHAR(25) NOT NULL,
  Tipo_Documento VARCHAR(25) NOT NULL,
  Documento VARCHAR(25) NOT NULL,
  Nombre_Sucursal VARCHAR(1) NOT NULL,
  contrasena VARCHAR(15) NOT NULL,
  Unique(Tipo_Documento,Documento),
  PRIMARY KEY (ID_Trabajador),
  FOREIGN KEY (Nombre_Sucursal) REFERENCES Sucursal(Nombre_Sucursal) on delete cascade
);

--TRIG ID TRABAJADORES
CREATE SEQUENCE TRABAJADORES_ID_SEQ;
CREATE OR REPLACE TRIGGER TRABAJADORES_ID_TRIG
BEFORE INSERT ON TRABAJADORES
FOR EACH ROW
BEGIN
    IF :new.ID_TRABAJADOR is null then
        :new.ID_TRABAJADOR:= TRABAJADORES_ID_SEQ.NEXTVAL;
    end if;
END;

Create table cuenta
(
  IDcuenta NUMBER(10,0) NOT NULL,
  Nombre_usuario VARCHAR(15) NOT NULL,
  contrasena VARCHAR(15) NOT NULL,
  nombre VARCHAR (15) NOT NULL,
  apellido VARCHAR (15) NOT NULL,
  correo VARCHAR (35) NOT NULL,
  fecha_creacion DATE DEFAULT SYSDATE NOT NULL,
  documento VARCHAR(15) NOT NULL,
  tipo_documento VARCHAR(15) NOT NULL,
  ID_TipoCuenta NUMBER(10,0) NOT NULL,
  ID_Trabajador NUMBER(10,0) NOT NULL,
  PRIMARY KEY (IDcuenta),
  FOREIGN KEY (ID_TipoCuenta) REFERENCES Tipo_cuenta(ID_TipoCuenta) on delete cascade ,
  FOREIGN KEY (ID_Trabajador) REFERENCES Trabajadores(ID_Trabajador) on delete set NULL ,
  UNIQUE (Nombre_usuario),
  UNIQUE (tipo_documento,documento,ID_TipoCuenta)
  --CHECK (Nombre_usuario like '%[A-Z0-9a-z]%'),
  --CHECK (correo like '%@%')
);

--TRIG ID CUENTA
CREATE SEQUENCE CUENTA_ID_SEQ;
CREATE OR REPLACE TRIGGER CUENTA_ID_TRIG
BEFORE INSERT ON CUENTA
FOR EACH ROW
BEGIN
    IF :new.IDCUENTA is null then
        :new.IDCUENTA := CUENTA_ID_SEQ.NEXTVAL;
    end if;
END;

--correcto
create table localizacion
(
    ID_Localizacion NUMBER(10,0) NOT NULL,
    id_Ubicacion_Pais NUMBER(10,0) NOT NULL,
    Departamento varchar(25) NOT NULL,
    municipio varchar(25) NOT NULL,
    primary key (ID_Localizacion),
    UNIQUE (id_Ubicacion_Pais, Departamento, municipio),
    FOREIGN KEY (id_Ubicacion_Pais) REFERENCES ubicacion(ID_ubicacion) on delete cascade
);

--TRIG ID LOCALIZACION
CREATE SEQUENCE LOCALIZACION_ID_SEQ;
CREATE OR REPLACE TRIGGER LOCALIZACION_ID_TRIG
BEFORE INSERT ON LOCALIZACION
FOR EACH ROW
BEGIN
    IF :new.ID_LOCALIZACION is null then
        :new.ID_LOCALIZACION:= LOCALIZACION_ID_SEQ.NEXTVAL;
    end if;
END;

--correcto
Create table Interes_Cliente
(
  ID_Intereses NUMBER(10,0) NOT NULL,
  id_ubicacion NUMBER(10,0) NOT NULL,
  Max_Renta FLOAT(3) NOT NULL,
  Habitaciones NUMBER (10,0) NOT NULL,
  Tipo VARCHAR(25) NOT NULL,
  ID_Cuenta NUMBER(10,0),
  PRIMARY KEY (ID_Intereses),
  FOREIGN KEY (ID_Cuenta) REFERENCES Cuenta(IDcuenta) on delete cascade,
  FOREIGN KEY (id_ubicacion) REFERENCES ubicacion(ID_ubicacion) on delete cascade
);

CREATE SEQUENCE INTERESES_ID_SEQ;

CREATE OR REPLACE TRIGGER INTERESES_ID_TRIG
BEFORE INSERT ON INTERES_CLIENTE
FOR EACH ROW
BEGIN
    IF :new.ID_INTERESES is null then
        :new.ID_INTERESES := INTERESES_ID_SEQ.NEXTVAL;
    end if;
END;

--correcto
Create table Tarjeta
(
  Numero_Tarjeta NUMBER(10,0) NOT NULL,
  Tipo_tarjeta VARCHAR(25) NOT NULL,
  Nombre_Poseedor VARCHAR(25) NOT NULL,
  Año_Vencimiento NUMBER (10,0) NOT NULL,
  CHECK (Tipo_tarjeta in ('visa','mastercard')),
  PRIMARY KEY (Numero_Tarjeta)
);

CREATE SEQUENCE INTERESES_ID_SEQ;

CREATE OR REPLACE TRIGGER INTERESES_ID_TRIG
BEFORE INSERT ON INTERES_CLIENTE
FOR EACH ROW
BEGIN
    IF :new.ID_INTERESES is null then
        :new.ID_INTERESES := INTERESES_ID_SEQ.NEXTVAL;
    end if;
END;

--correcto
Create table Pago
(
  ID_Pago NUMBER(10,0) NOT NULL
  Correo VARCHAR(25) NOT NULL,
  Total FLOAT(3) NOT NULL,
  ID_Cuenta NUMBER(10,0),
  FECHA date DEFAULT SYSDATE NOT NULL,
  Numero_Tarjeta NUMBER(10,0),
  PRIMARY KEY (ID_Pago),
  FOREIGN KEY (ID_Cuenta) REFERENCES Cuenta (IDCuenta) on delete cascade ,
  FOREIGN KEY (Numero_Tarjeta) REFERENCES Tarjeta (Numero_Tarjeta) on delete cascade
);

--TRIG ID PAGO
CREATE SEQUENCE PAGO_ID_SEQ;
CREATE OR REPLACE TRIGGER PAGO_ID_TRIG
BEFORE INSERT ON PAGO
FOR EACH ROW
BEGIN
    IF :new.ID_PAGO is null then
        :new.ID_PAGO:= PAGO_ID_SEQ.NEXTVAL;
    end if;
END;

Create table Bono
(
  numero_bono NUMBER(10,0) NOT NULL,
  ID_Pago NUMBER(10,0) NOT NULL,
  cantidad float NOT NULL
  PRIMARY KEY (numero_bono),
  FOREIGN KEY (ID_Pago) REFERENCES Pago (ID_Pago) on delete cascade
);

Create table PagoXtipo_pago
(
  ID_tipo NUMBER(10,0) NOT NULL,
  ID_Pago NUMBER(10,0) NOT NULL,
  PRIMARY KEY (ID_tipo, ID_Pago),
  FOREIGN KEY (ID_tipo) REFERENCES tipo_pago (ID_tipo) on delete cascade ,
  FOREIGN KEY (ID_Pago) REFERENCES Pago (ID_Pago) on delete cascade
);

--correcto
create table servicio
(
 ID_Servicio NUMBER(10,0) NOT NULL,
 Cant_inquilinos NUMBER(10,0) NOT NULL,
 id_TipoServicio NUMBER(10,0) NOT NULL,
 primary key (ID_Servicio),
 foreign key (id_TipoServicio)references Tipo_Servicio(id_TipoServicio) on delete cascade
 
);


--correcto
Create table caracteristicas
(
  IDcaracteristicas NUMBER(10,0) NOT NULL,
  id_tipo number(10,0) NOT NULL,
  cant_habitaciones number(10,0) NOT NULL,
  fecha date DEFAULT SYSDATE NOT NULL,
  renta float(3) NOT NULL,
  PRIMARY KEY (IDcaracteristicas),
  FOREIGN KEY (id_tipo) REFERENCES Tipo_propiedades(idtipo) on delete cascade
);

--TRIG ID CARACTERISTICAS
CREATE SEQUENCE CARACTERISTICAS_ID_SEQ;
CREATE OR REPLACE TRIGGER CARACTERISTICAS_ID_TRIG
BEFORE INSERT ON CARACTERISTICAS
FOR EACH ROW
BEGIN
    IF :new.IDCARACTERISTICAS is null then
        :new.IDCARACTERISTICAS:= CARACTERISTICAS_ID_SEQ.NEXTVAL;
    end if;
END;

Create table Tipo_estado
(
  ID_Tipoestado NUMBER(10,0) NOT NULL,
  Nombre_estado VARCHAR(25) NOT NULL,
  PRIMARY KEY (ID_Tipoestado),
  unique (Nombre_estado)
);

CREATE SEQUENCE Tipo_estado_ID_SEQ;
CREATE OR REPLACE TRIGGER Tipo_estado_ID_TRIG
BEFORE INSERT ON Tipo_estado
FOR EACH ROW
BEGIN
    IF :new.ID_Tipoestado is null then
        :new.ID_Tipoestado:= Tipo_estado_ID_SEQ.NEXTVAL;
    end if;
END;

create table propiedades
(
    ID_Propiedades NUMBER(10,0) NOT NULL,
    ID_dueno NUMBER(10,0) NOT NULL,
    Descripcion varchar(150) NOT NULL,
    Estado NUMBER(10,0) NOT NULL,
    imagen blob null,
    ID_localizacion NUMBER(10,0) NOT NULL,
    ID_Caracteristicas NUMBER(10,0) NOT NULL,
    primary key (ID_Propiedades),
    foreign key (ID_localizacion) references localizacion (ID_localizacion)  on delete cascade ,
    foreign key (ID_Caracteristicas) references caracteristicas (IDCaracteristicas) on delete cascade,
    foreign key (Estado) references Tipo_estado (ID_Tipoestado) on delete cascade,
    foreign key (ID_dueno) references CUENTA(IDCuenta) on delete cascade
);

--TRIG ID PROPIEDADES
CREATE SEQUENCE PROPIEDADES_ID_SEQ;
CREATE OR REPLACE TRIGGER PROPIEDADES_ID_TRIG
BEFORE INSERT ON PROPIEDADES
FOR EACH ROW
BEGIN
    IF :new.ID_Propiedades is null then
        :new.ID_Propiedades:= PROPIEDADES_ID_SEQ.NEXTVAL;
    end if;
END;

--correcto
create table INQUILINOS
(
    ID_inquilinos NUMBER(10,0) NOT NULL,
    nombre varchar(25) NOT NULL,
    ID_propiedades NUMBER(10,0) NOT NULL,
    primary key (ID_inquilinos,ID_propiedades),
    FOREIGN KEY (ID_Propiedades) REFERENCES PROPIEDADES (ID_Propiedades) on delete cascade
);

--TRIG ID inquilinos
CREATE SEQUENCE inquilinos_ID_SEQ;
CREATE OR REPLACE TRIGGER inquilinos_ID_TRIG
BEFORE INSERT ON inquilinos
FOR EACH ROW
BEGIN
    IF :new.ID_Inquilinos is null then
        :new.ID_Inquilinos:= inquilinos_ID_SEQ.NEXTVAL;
    end if;
END;


Create table visitas
(
  ID_Visitas NUMBER(10,0) NOT NULL,
  FECHA DATE DEFAULT SYSDATE NOT NULL,
  ID_Propiedad NUMBER(10,0),
  ID_Cuenta NUMBER(10,0) NOT NULL,
  PRIMARY KEY (ID_Visitas),
  FOREIGN KEY (ID_Propiedad) REFERENCES propiedades(ID_Propiedades),
  FOREIGN KEY (ID_Cuenta) REFERENCES cuenta(IDCuenta)
);

--TRIG ID VISITAS
CREATE SEQUENCE VISITAS_ID_SEQ;
CREATE OR REPLACE TRIGGER VISITAS_ID_TRIG
BEFORE INSERT ON VISITAS
FOR EACH ROW
BEGIN
    IF :new.ID_Visitas is null then
        :new.ID_Visitas:= VISITAS_ID_SEQ.NEXTVAL;
    end if;
END;



create table solicitudes
(
 ID_solicitud NUMBER(10,0) NOT NULL,
 id_propiedad NUMBER(10,0) NOT NULL,
 fecha timestamp DEFAULT SYSTIMESTAMP NOT NULL,
 id_cliente NUMBER(10,0) NOT NULL,
 primary key (ID_solicitud),
 foreign key (id_cliente)references cuenta(IDCUENTA) on delete cascade
 foreign key (id_propiedad)references propiedades(id_propiedades) on delete cascade
);

create table aceptado
(
 ID_aceptado NUMBER(10,0) NOT NULL,
 id_propiedad NUMBER(10,0) NOT NULL,
 id_cliente NUMBER(10,0) NOT NULL,
 primary key (ID_aceptado),
 foreign key (id_cliente) references cuenta(IDCUENTA) on delete cascade,
 foreign key (id_propiedad) references propiedades(id_propiedades) on delete cascade
)

--TRIG ID solicitudes
CREATE SEQUENCE solicitudes_ID_SEQ;
CREATE OR REPLACE TRIGGER solicitudes_ID_TRIG
BEFORE INSERT ON solicitudes
FOR EACH ROW
BEGIN
    IF :new.ID_solicitud is null then
        :new.ID_solicitud:= solicitudes_ID_SEQ.NEXTVAL;
    end if;
END;

--trigger para borrar solicitudes y aceptado si se arrienda
CREATE OR REPLACE TRIGGER Borrar_solicitudes
BEFORE UPDATE of estado ON PROPIEDADES
REFERENCING new AS new old AS old
FOR EACH ROW
BEGIN
  IF :new.estado= 2 then
      delete from solicitudes WHERE :old.ID_PROPIEDADES= ID_PROPIEDAD;
      delete from aceptado WHERE :old.ID_PROPIEDADES= id_propiedad;
  end if;
END;

--trigger para borrar solicitudes si se borra

CREATE OR REPLACE TRIGGER Borrar_para_eliminacion
BEFORE UPDATE of estado ON PROPIEDADES
REFERENCING new AS new old AS old
FOR EACH ROW
BEGIN
  IF :new.estado= 3 then
      delete from solicitudes WHERE :old.ID_PROPIEDADES= ID_PROPIEDAD;
      delete from ACEPTADO WHERE :old.ID_PROPIEDADes= ID_PROPIEDAD;
      delete from INQUILINOS WHERE :old.ID_PROPIEDADES= ID_PROPIEDADES;
      delete from SERVICIO where :old.ID_PROPIEDADES=ID_PROPIEDAD;
      delete from VISITAS where :old.ID_PROPIEDADES=ID_PROPIEDAD;
  end if;
END;


CREATE SEQUENCE aceptado_ID_SEQ;
CREATE OR REPLACE TRIGGER aceptado_ID_TRIG
BEFORE INSERT ON aceptado
FOR EACH ROW
BEGIN
    IF :new.ID_aceptado is null then
        :new.ID_aceptado := aceptado_ID_SEQ.NEXTVAL;
    end if;
END;
