INSERT INTO Member (member_nickname, member_email, member_level, member_exp, member_token, member_image_url, member_delete, member_block)
VALUES
    (SUBSTRING(MD5(RAND()), 1, 10), CONCAT(SUBSTRING(MD5(RAND()), 1, 10), '@naver.com'), FLOOR(RAND() * 10), FLOOR(RAND() * 100), MD5(RAND()), '/base/baseLogo.png', FALSE, FALSE),
    (SUBSTRING(MD5(RAND()), 1, 10), CONCAT(SUBSTRING(MD5(RAND()), 1, 10), '@google.com'), FLOOR(RAND() * 10), FLOOR(RAND() * 100), MD5(RAND()), '/base/baseLogo.png', FALSE, FALSE),
    (SUBSTRING(MD5(RAND()), 1, 10), CONCAT(SUBSTRING(MD5(RAND()), 1, 10), '@naver.com'), FLOOR(RAND() * 10), FLOOR(RAND() * 100), MD5(RAND()), '/base/baseLogo.png', FALSE, FALSE);
