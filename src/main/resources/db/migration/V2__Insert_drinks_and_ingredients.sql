INSERT INTO recipe (name) VALUES ('Эспрессо');
INSERT INTO recipe (name) VALUES ('Американо');
INSERT INTO recipe (name) VALUES ('Капучино');

INSERT INTO ingredient (name, stock) VALUES ('Молоко', 2000);
INSERT INTO ingredient (name, stock) VALUES ('Молотый кофе', 2000);
INSERT INTO ingredient (name, stock) VALUES ('Вода', 5000);

INSERT INTO recipe_step (recipe_id, action_type, ingredient_id, quantity) VALUES (1, 'ADD_INGREDIENT', 2, 10);  -- Молотый кофе
INSERT INTO recipe_step (recipe_id, action_type) VALUES (1, 'HEAT_WATER');
INSERT INTO recipe_step (recipe_id, action_type, ingredient_id, quantity) VALUES (1, 'ADD_INGREDIENT', 3, 30);  -- Вода
INSERT INTO recipe_step (recipe_id, action_type) VALUES (1, 'POUR_INTO_CUP');


INSERT INTO recipe_step (recipe_id, action_type, ingredient_id, quantity) VALUES (2, 'ADD_INGREDIENT', 2, 10);  -- Молотый кофе
INSERT INTO recipe_step (recipe_id, action_type) VALUES (2, 'HEAT_WATER');
INSERT INTO recipe_step (recipe_id, action_type, ingredient_id, quantity) VALUES (2, 'ADD_INGREDIENT', 3, 100);  -- Вода
INSERT INTO recipe_step (recipe_id, action_type) VALUES (2, 'POUR_INTO_CUP');


INSERT INTO recipe_step (recipe_id, action_type, ingredient_id, quantity) VALUES (3, 'ADD_INGREDIENT', 2, 10);  -- Молотый кофе
INSERT INTO recipe_step (recipe_id, action_type) VALUES (3, 'HEAT_WATER');
INSERT INTO recipe_step (recipe_id, action_type, ingredient_id, quantity) VALUES (3, 'ADD_INGREDIENT', 3, 50);  -- Вода
INSERT INTO recipe_step (recipe_id, action_type, ingredient_id, quantity) VALUES (3, 'FROTH_MILK', 1, 100);  -- Молоко
INSERT INTO recipe_step (recipe_id, action_type) VALUES (3, 'POUR_INTO_CUP');
