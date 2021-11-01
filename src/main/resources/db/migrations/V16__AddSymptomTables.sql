CREATE TABLE symptoms
(
    id      int PRIMARY KEY IDENTITY (1, 1),
    name    varchar(128)  NOT NULL,
    version varchar(16)    NOT NULL,
    text_de nvarchar(256) NOT NULL,
    text_fr nvarchar(256) NOT NULL,
    text_it nvarchar(256) NOT NULL,
    text_en nvarchar(256) NOT NULL
);

CREATE TABLE user_symptoms
(
    id      int PRIMARY KEY IDENTITY (1, 1),
    symptom_id  int    NOT NULL,
    screening_id bigint NOT NULL,
    CONSTRAINT fk_user_symptom_symptom_id
        FOREIGN KEY (symptom_id)
            REFERENCES symptoms (id)
            ON UPDATE NO ACTION
            ON DELETE NO ACTION,
    CONSTRAINT fk_user_symptom_screening_id
        FOREIGN KEY (screening_id)
            REFERENCES screenings (id)
            ON UPDATE CASCADE
            ON DELETE CASCADE
);