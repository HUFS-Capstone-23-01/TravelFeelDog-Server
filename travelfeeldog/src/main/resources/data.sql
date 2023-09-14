INSERT INTO member
(member_nickname, member_email, member_level, member_exp, member_token, member_image_url, member_delete, member_block, created_time, updated_time)
VALUES
    (SUBSTRING(MD5(RAND()), 1, 10),
     CONCAT(SUBSTRING(MD5(RAND()), 1, 10), '@naver.com'),
     FLOOR(RAND() * 10),
     FLOOR(RAND() * 100),
     MD5(RAND()),
     '/base/baseLogo.png',
     FALSE,
     FALSE,
     NOW(),
     NOW()
    );
INSERT INTO member
(member_nickname, member_email, member_level, member_exp, member_token, member_image_url, member_delete, member_block, created_time, updated_time)
VALUES
    (SUBSTRING(MD5(RAND()), 1, 10),
     CONCAT(SUBSTRING(MD5(RAND()), 1, 10), '@gmail.com'),
     FLOOR(RAND() * 10),
     FLOOR(RAND() * 100),
     MD5(RAND()),
     '/base/baseLogo.png',
     FALSE,
     FALSE,
     NOW(),
     NOW()
    );

INSERT INTO member
(member_nickname, member_email, member_level, member_exp, member_token, member_image_url, member_delete, member_block, created_time, updated_time)
VALUES
    (SUBSTRING(MD5(RAND()), 1, 10),
     CONCAT(SUBSTRING(MD5(RAND()), 1, 10), '@gmail.com'),
     FLOOR(RAND() * 10),
     FLOOR(RAND() * 100),
     MD5(RAND()),
     '/base/baseLogo.png',
     FALSE,
     FALSE,
     NOW(),
     NOW()
    );


-- create index feed__index_member_id
--     on feed (member_id);
--
-- create index feed__index_created_time
--     on feed (created_time);
--
-- create index feed__index_member_id_created_time
--     on feed (member_id,created_time);

