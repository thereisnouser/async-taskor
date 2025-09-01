CREATE TABLE IF NOT EXISTS jobs (
    id UUID PRIMARY KEY,

    type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,

    src_key TEXT NOT NULL,
    dst_key TEXT,
    error TEXT,

    created_at TIMESTAMPTZ NOT NULL,
    completed_at TIMESTAMPTZ
);

CREATE INDEX IF NOT EXISTS idx_jobs_status_created_at ON jobs (status, created_at);