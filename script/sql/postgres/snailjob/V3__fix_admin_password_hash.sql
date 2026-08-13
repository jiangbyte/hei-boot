-- Fix console password: UI sends MD5(plain), server stores SHA256(that).
-- Correct hash for plain '123456' is SHA256(MD5('123456')).

UPDATE sj_system_user
SET password = 'cdf4a007e2b02a0c49fc9b7ccfbb8a10c644f635e1765dcf2a7ab794ddc7edac',
    update_dt = now()
WHERE username = 'admin';
