-- passwords: 123123
INSERT INTO USER(user_id, active, balance, blocked_amount, created, email, password, name)
values(1, true, 500000, 0, CURRENT_TIMESTAMP(), 'michal.lipski2@gmail.com', '$2a$10$cCoNpcHWstZqS4twmbVpxeCdVcLh.CLctAr/lO6vNX9/BuNDh8ZAa', 'Michał');
INSERT INTO USER(user_id, active, balance, blocked_amount, created, email, password, name)
values(2, true, 500000, 0, CURRENT_TIMESTAMP(), 't@gmail.com', '$2a$10$wVtdyQC4SpIxMhzn9CX9UOaZAMi0PwhUEwENTbt4NREEvjWpPBPCO', 'Tomek');
INSERT INTO USER(user_id, active, balance, blocked_amount, created, email, password, name)
values(3, true, 500000, 0, CURRENT_TIMESTAMP(), 'a@gmail.com', '$2a$10$SpDq4G1oZV/h.aJ4zNVjoOV24/pb6yv71bCVzgetKDM9qOIZ.mwWm', 'Admin');

INSERT INTO USER_ROLES(user_user_id, roles) values(1, 'USER');
INSERT INTO USER_ROLES(user_user_id, roles) values(2, 'USER');
INSERT INTO USER_ROLES(user_user_id, roles) values(3, 'ADMIN');
INSERT INTO USER_ROLES(user_user_id, roles) values(3, 'USER');