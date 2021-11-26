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

CREATE TABLE IF NOT EXISTS hat.stake_settings
(
    id                UUID PRIMARY KEY,
    expiry_stake_time INTEGER,
    stake_percentage  DOUBLE PRECISION,
    stake_type        TEXT,
    minimum_limit     DOUBLE PRECISION,
    created_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at        TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX IF NOT EXISTS stake_settings_stake_type_uindex
    ON hat.stake_settings (stake_type);
CREATE UNIQUE INDEX IF NOT EXISTS stake_settings_id_uindex
    ON hat.stake_settings (id);

CREATE TABLE IF NOT EXISTS hat.stake
(
    id                   UUID PRIMARY KEY,
    user_id              UUID,
    started_stake_amount DOUBLE PRECISION,
    expiry_stake_amount  DOUBLE PRECISION,
    expiry_stake_time    INTEGER,
    stake_percentage     DOUBLE PRECISION,
    start_date           TIMESTAMP WITH TIME ZONE,
    end_date             TIMESTAMP WITH TIME ZONE,
    created_at           TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at           TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX IF NOT EXISTS stake_id_uindex
    ON hat.stake (id);

CREATE TABLE IF NOT EXISTS hat.user_total_balance
(
    id                   UUID PRIMARY KEY,
    user_id              UUID,
    total_balance        DOUBLE PRECISION,
    withdrawable_balance DOUBLE PRECISION,
    locked_balance       DOUBLE PRECISION,
    created_at           TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at           TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX IF NOT EXISTS user_total_balance_id_uindex
    ON hat.user_total_balance (id);