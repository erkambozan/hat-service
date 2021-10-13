CREATE TABLE IF NOT EXISTS hat.user_account
(
    id           UUID PRIMARY KEY,
    reference_id UUID,
    first_name   TEXT,
    last_name    TEXT,
    email        VARCHAR(128),
    password     VARCHAR(60),
    secret       VARCHAR(128),
    active       BOOLEAN,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at   TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX IF NOT EXISTS user_account_email_uindex
    ON hat.user_account (email);
CREATE UNIQUE INDEX IF NOT EXISTS user_account_id_uindex
    ON hat.user_account (id);