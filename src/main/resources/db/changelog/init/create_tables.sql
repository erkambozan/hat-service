CREATE TABLE IF NOT EXISTS hat.user_account
(
    id           UUID PRIMARY KEY,
    reference_id UUID,
    first_name   TEXT,
    last_name    TEXT,
    email        VARCHAR(128),
    role         TEXT,
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
    maximum_limit     DOUBLE PRECISION,
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
    stake_type           TEXT,
    stake_status         BOOLEAN,
    start_date           DATE,
    end_date             DATE,
    created_at           TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at           TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX IF NOT EXISTS stake_id_uindex
    ON hat.stake (id);

CREATE TABLE IF NOT EXISTS hat.user_total_balance
(
    id                   UUID PRIMARY KEY,
    user_id              UUID,
    withdrawable_balance DOUBLE PRECISION,
    locked_balance       DOUBLE PRECISION,
    earn_balance         DOUBLE PRECISION,
    created_at           TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at           TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX IF NOT EXISTS user_total_balance_id_uindex
    ON hat.user_total_balance (id);

CREATE TABLE IF NOT EXISTS hat.permission
(
    id              UUID PRIMARY KEY,
    permission_name TEXT,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at      TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX IF NOT EXISTS permission_id_uindex
    ON hat.permission (id);

CREATE TABLE IF NOT EXISTS hat.user_permission
(
    id            UUID PRIMARY KEY,
    user_id       UUID,
    permission_id UUID,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at    TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX IF NOT EXISTS user_permission_id_uindex
    ON hat.user_permission (id);

CREATE TABLE IF NOT EXISTS hat.transactions
(
    id          UUID PRIMARY KEY,
    user_id     UUID,
    withdraw_id UUID,
    amount      DOUBLE PRECISION,
    title       TEXT,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX IF NOT EXISTS transactions_id_uindex
    ON hat.transactions (id);

CREATE TABLE IF NOT EXISTS hat.withdrawal
(
    id              UUID PRIMARY KEY,
    user_id         UUID,
    withdraw_amount DOUBLE PRECISION,
    wallet_address  TEXT,
    status          TEXT,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at      TIMESTAMP WITH TIME ZONE
);
CREATE UNIQUE INDEX IF NOT EXISTS withdrawal_id_uindex
    ON hat.withdrawal (id);

CREATE TABLE IF NOT EXISTS hat.earn_withdraw
(
    id               UUID PRIMARY KEY,
    user_id          UUID,
    coin_type        TEXT,
    coin_price       DOUBLE PRECISION,
    withdraw_address TEXT,
    withdraw_amount  DOUBLE PRECISION,
    status           TEXT,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at       TIMESTAMP WITH TIME ZONE
);
CREATE UNIQUE INDEX IF NOT EXISTS earn_withdraw_id_uindex
    ON hat.earn_withdraw (id);

CREATE TABLE IF NOT EXISTS hat.payment
(
    id              UUID PRIMARY KEY,
    user_id         UUID,
    user_email      TEXT,
    transaction_id  TEXT,
    usd_amount      DOUBLE PRECISION,
    token_amount    DOUBLE PRECISION,
    currency        TEXT,
    currency_amount DOUBLE PRECISION,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at      TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX IF NOT EXISTS payment_id_uindex
    ON hat.payment (id);

CREATE UNIQUE INDEX IF NOT EXISTS payment_transaction_id_uindex
    ON hat.payment (transaction_id);

CREATE TABLE IF NOT EXISTS hat.email_verification
(
    id                UUID PRIMARY KEY,
    user_id           UUID,
    verification_code INTEGER,
    end_time          DATE,
    created_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at        TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX IF NOT EXISTS payment_id_uindex
    ON hat.payment (id);

CREATE UNIQUE INDEX IF NOT EXISTS payment_transaction_id_uindex
    ON hat.payment (transaction_id);