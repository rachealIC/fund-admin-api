-- Create funds table
CREATE TABLE transactions (
                       transaction_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       fund_id UUID NOT NULL,
                       type VARCHAR(50) NOT NULL CHECK (type IN ( 'DEPOSIT', 'WITHDRAWAL', 'FEE', 'INVESTMENT_GAIN', 'INVESTMENT_LOSS' )),
                       amount DECIMAL(19, 2) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT fk_fund
                        FOREIGN KEY (fund_id)
                            REFERENCES funds(fund_id)
                            ON DELETE CASCADE
);

-- Index for performance when querying by fund
CREATE INDEX  idx_transactions_fund_id ON transactions(fund_id);

CREATE INDEX idx_transactions_type ON transactions(type);

