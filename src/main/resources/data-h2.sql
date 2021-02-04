INSERT INTO project (name) VALUES ('Todo');
INSERT INTO project (name) VALUES ('NewPrj');
INSERT INTO todo (headline, description, status, project_id) VALUES ('Test Task 1', 'We have to do this task right now 1', 'FINISHED', 1);
INSERT INTO todo (headline, description, status, project_id) VALUES ('Test Task 2', 'We have to do this task right now 2', 'WAITING', 1);
INSERT INTO todo (headline, description, status, project_id) VALUES ('Initial Task', 'We have to find new project', 'WAITING', 2);

INSERT INTO authority (name, id) VALUES ('ADMIN', 1);
INSERT INTO authority (name, id) VALUES ('USER', 2);
INSERT INTO user (id, username, password, email) VALUES (1,'admin','$2a$10$fC5DugP7AHdhI7yxvQcZnOrpZLu.fYv1gIl0qNid3aXG4Z7porzrm', 'asd@asd.com');
INSERT INTO user (id, username, password, email) VALUES (2,'user','$2a$10$RE2ifF325M8iTVdjdxn1RebS3VmUqmyoyNz0DDMrsE4cHJrGXkOCe', 'asd@gg.com');
INSERT INTO user_authority (authority_id, user_id) VALUES (1, 1);
INSERT INTO user_authority (authority_id, user_id) VALUES (2, 2);
