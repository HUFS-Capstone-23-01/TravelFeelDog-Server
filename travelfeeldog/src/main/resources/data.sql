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

-- update query for insert time

-- UPDATE Member
-- SET created_time = NOW(), updated_time = NOW()
-- WHERE created_time IS NULL OR updated_time IS NULL;
