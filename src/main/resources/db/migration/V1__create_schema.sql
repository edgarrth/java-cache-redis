CREATE TABLE merchant_risk_profiles (
    merchant_id VARCHAR(80) PRIMARY KEY,
    merchant_name VARCHAR(160) NOT NULL,
    risk_level VARCHAR(20) NOT NULL,
    max_ticket_amount NUMERIC(19, 2) NOT NULL,
    allowed_currencies VARCHAR(80) NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE payments (
    payment_id VARCHAR(80) PRIMARY KEY,
    merchant_id VARCHAR(80) NOT NULL,
    customer_id VARCHAR(80) NOT NULL,
    idempotency_key VARCHAR(120) NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    payment_method_type VARCHAR(30) NOT NULL,
    payment_method_token VARCHAR(180) NOT NULL,
    status VARCHAR(30) NOT NULL,
    authorization_code VARCHAR(80),
    decline_reason VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_payments_merchant_idempotency UNIQUE (merchant_id, idempotency_key)
);

CREATE INDEX idx_payments_merchant_created_at ON payments (merchant_id, created_at DESC);
CREATE INDEX idx_payments_status ON payments (status);
