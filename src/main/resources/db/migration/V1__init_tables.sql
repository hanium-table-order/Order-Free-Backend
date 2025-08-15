CREATE TABLE category (
                          id VARCHAR(64) PRIMARY KEY,
                          name JSON NOT NULL
);

CREATE TABLE menu (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      category_id VARCHAR(64) NOT NULL,
                      name JSON NOT NULL,
                      price INT NOT NULL,
                      description JSON NOT NULL,
                      options JSON,
                      image VARCHAR(255),
                      sold_out BOOLEAN NOT NULL,
                      quantity INT NOT NULL,
                      enable_inventory BOOLEAN NOT NULL,
                      CONSTRAINT fk_menu_category FOREIGN KEY (category_id) REFERENCES category(id)
);
