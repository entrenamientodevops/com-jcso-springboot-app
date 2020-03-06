INSERT INTO users(username, password, name, lastname, email, status) VALUES('jcsolis','$2a$10$/bxihxdPOzXHklwgEly76emAbQJD4Q.N/ljqmNryjGVTmRBAFy8pC','Jhon','Solis','jcsolis78@gmail.com',true);
INSERT INTO users(username, password, name, lastname, email, status) VALUES('asolis','$2a$10$ojJ01uJYbsv1/2IxqCyRWOhpEUq8Ir.DDZSG7FOmpsDy8YJMffpgS','Andrea','Solis','andrea.solis@gmail.com',true);

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');


INSERT INTO users_roles(user_id, role_id) VALUES(1, 1);
INSERT INTO users_roles(user_id, role_id) VALUES(2, 2);
INSERT INTO users_roles(user_id, role_id) VALUES(2, 1);