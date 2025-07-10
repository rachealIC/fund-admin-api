-- Create funds table
CREATE TABLE funds (
                       fund_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       fund_name VARCHAR(100) NOT NULL,
                       fund_description VARCHAR(500),
                       balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index on fund_name for faster searches
CREATE INDEX idx_funds_fund_name ON funds(fund_name);

-- Add constraint to ensure balance is not negative
ALTER TABLE funds ADD CONSTRAINT chk_balance_non_negative CHECK (balance >= 0);