INSERT INTO merchant_risk_profiles
    (merchant_id, merchant_name, risk_level, max_ticket_amount, allowed_currencies, updated_at)
VALUES
    ('merchant-001', 'Demo Retail Peru', 'LOW', 1500.00, 'PEN,USD', now()),
    ('merchant-002', 'Demo Travel LATAM', 'MEDIUM', 800.00, 'PEN,USD', now()),
    ('merchant-003', 'Demo High Risk Merchant', 'HIGH', 250.00, 'PEN', now())
ON CONFLICT (merchant_id) DO UPDATE SET
    merchant_name = EXCLUDED.merchant_name,
    risk_level = EXCLUDED.risk_level,
    max_ticket_amount = EXCLUDED.max_ticket_amount,
    allowed_currencies = EXCLUDED.allowed_currencies,
    updated_at = EXCLUDED.updated_at;
