DROP TABLE IF EXISTS storage;
CREATE TABLE IF NOT EXISTS storage (id MEDIUMINT NOT NULL AUTO_INCREMENT, cost DOUBLE NOT NULL, tails INTEGER NOT NULL, PRIMARY KEY (id),FOREIGN KEY (ingredients_id) REFERENCES ingredients(id));
INSERT INTO storage(cost, tails) VALUES (0.0, 0);
INSERT INTO storage(cost, tails) VALUES (0.0, 0);
INSERT INTO storage(cost, tails) VALUES (0.0, 0);
INSERT INTO storage(cost, tails) VALUES (0.0, 0);
INSERT INTO storage(cost, tails) VALUES (0.0, 0);
INSERT INTO storage(cost, tails) VALUES (0.0, 0);
INSERT INTO storage(cost, tails) VALUES (0.0, 0);
INSERT INTO storage(cost, tails) VALUES (0.0, 0);
INSERT INTO storage(cost, tails) VALUES (0.0, 0);