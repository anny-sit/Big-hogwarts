ALTER TABLE "public".student ADD CONSTRAINT age_constraint CHECK (age > 16);

ALTER TABLE "public".student ALTER COLUMN name SET NOT NULL,
ADD CONSTRAINT name_unique UNIQUE (name);

ALTER TABLE "public".faculty ADD CONSTRAINT name_colour_unique UNIQUE (name, colour);

ALTER TABLE "public".student ALTER COLUMN age SET DEFAULT 20;