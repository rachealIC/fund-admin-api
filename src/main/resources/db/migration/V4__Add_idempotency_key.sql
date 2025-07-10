
ALTER TABLE transactions ADD COLUMN idempotency_key VARCHAR(100) UNIQUE;