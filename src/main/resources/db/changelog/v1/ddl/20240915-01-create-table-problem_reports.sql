--liquibase formatted sql
--changeset Dudochkin:20240915-01-create-table-problem_reports

create table problem_reports
(
    id            uuid,
    created_at    TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id       UUID      NOT NULL,
    description   TEXT      NOT NULL,
    workplaces_id UUID      NOT NULL,
    is_completed  BOOLEAN   NOT NULL DEFAULT FALSE,
    is_canceled   BOOLEAN   NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_sender
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_workplace
        FOREIGN KEY (workplaces_id)
            REFERENCES workplaces (id)
            ON DELETE CASCADE
)