INSERT INTO vacuna(nombre,descripcion ) VALUES ('Sputnik','Vacuna Sputnik');
INSERT INTO vacuna(nombre,descripcion ) VALUES ('AstraZeneca','Vacuna AstraZeneca');
INSERT INTO vacuna(nombre,descripcion ) VALUES ('Pfizer','Vacuna Pfizer');
INSERT INTO vacuna(nombre,descripcion ) VALUES ('Jhonson&Jhonson','Vacuna Jhonson&Jhonson');

INSERT INTO usuario(apellido, cedula, password, correo, nombre, username) VALUES ('TOAQUIZA','0504121344', '$2a$10$6esFrgZeRIRa63JxsZ2gOOYDYmShMoQs62MRizMmc00ZFc41SB18O', 'oscartoaquiza388@gmail.com', 'OSCAR', 'TOAQUIZA_OSCAR');
INSERT INTO usuario(apellido, cedula, password, correo, nombre, username, estado_vacunacion) VALUES ('MOLINA CASA','0504121345', '$2a$10$CAmUgUxp0c8jU1kwmTRDjObybjkTuz8uXOSM/c46W8w59cV8TWgR6', 'molinacasa@gmail.com', 'PEDRO RODRIGO','MOLINA_PEDRO',true);
INSERT INTO usuario(apellido, cedula, password, correo, nombre, username, estado_vacunacion) VALUES ('VILCA QUILUMBA','0504121346', '$2a$10$Okn3g0r4/gh8oUX1eJcQPuoEe6TJ8GDij3tbrN37psXhCUD4dx2J6', 'vilcaquilumba@gmail.com', 'JUAN FERNANDO','VILCA_JUAN',true);

INSERT INTO rol (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO rol (nombre) VALUES ('ROLE_EMPLEADO');

INSERT INTO usuario_rol (usuario_id,rol_id) VALUES (1,1);
INSERT INTO usuario_rol (usuario_id,rol_id) VALUES (2,2);
INSERT INTO usuario_rol (usuario_id,rol_id) VALUES (3,2);


INSERT INTO usuario_vacuna (fecha_vacunacion,numero_dosis,id_usuario,id_vacuna) VALUES ('2021-11-12',1,2,1);
INSERT INTO usuario_vacuna (fecha_vacunacion,numero_dosis,id_usuario,id_vacuna) VALUES ('2022-03-12',2,2,2);
INSERT INTO usuario_vacuna (fecha_vacunacion,numero_dosis,id_usuario,id_vacuna) VALUES ('2022-05-08',3,2,1);
INSERT INTO usuario_vacuna (fecha_vacunacion,numero_dosis,id_usuario,id_vacuna) VALUES ('2021-10-12',1,3,3);
INSERT INTO usuario_vacuna (fecha_vacunacion,numero_dosis,id_usuario,id_vacuna) VALUES ('2022-04-11',2,3,1);




