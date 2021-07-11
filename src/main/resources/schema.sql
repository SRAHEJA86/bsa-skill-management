DROP TABLE IF EXISTS USER;

CREATE TABLE USER (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  userName VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  active BOOLEAN NOT NULL,
  roles VARCHAR(250) DEFAULT NULL
);



