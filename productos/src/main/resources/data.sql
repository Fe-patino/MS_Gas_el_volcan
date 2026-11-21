INSERT INTO producto (id, nombre, categoria, precio, precio_comercial, stock, descripcion, descripcion_breve, descripcion_completa, imagen) VALUES
(1, 'Cilindro 5kg', 'Cilindros de Gas', 8990, 8490, 50, 'Cilindro de gas licuado de 5 kilogramos, ideal para uso doméstico básico.', 'Cilindro de gas licuado de 5 kg.', 'Cilindro de gas licuado de 5 kilogramos, ideal para uso doméstico básico.', '/img/gasde5k.png'),
(2, 'Cilindro 11kg', 'Cilindros de Gas', 14990, 14290, 40, 'Cilindro de gas licuado de 11 kilogramos, el más usado en hogares.', 'Cilindro de gas licuado de 11 kg.', 'Cilindro de gas licuado de 11 kilogramos, el más usado en hogares.', '/img/gasde11k.png'),
(3, 'Cilindro 15kg', 'Cilindros de Gas', 18990, 18190, 30, 'Cilindro de gas licuado de 15 kilogramos para mayor autonomía.', 'Cilindro de gas licuado de 15 kg.', 'Cilindro de gas licuado de 15 kilogramos para mayor autonomía.', '/img/gasde15k.png'),
(4, 'Regulador Doméstico', 'Reguladores', 4990, 4590, 100, 'Regulador doméstico de gas licuado, universal para cilindros estándar.', 'Regulador doméstico universal.', 'Regulador doméstico de gas licuado, universal para cilindros estándar.', '/img/reguladordegasdomestico.png'),
(5, 'Regulador de Presión', 'Reguladores', 7990, 7490, 60, 'Regulador de presión ajustable para instalaciones industriales.', 'Regulador de presión ajustable.', 'Regulador de presión ajustable para instalaciones industriales.', '/img/reguladordepresion.png'),
(6, 'Regulador Dual', 'Reguladores', 9990, 9490, 40, 'Regulador dual para dos salidas simultáneas.', 'Regulador dual de dos salidas.', 'Regulador dual para dos salidas simultáneas.', '/img/reguladordual.png'),
(7, 'Manguera 1.5m', 'Mangueras y Conexiones', 3990, 3690, 80, 'Manguera flexible para gas de 1.5 metros con certificación de seguridad.', 'Manguera flexible de 1,5 m.', 'Manguera flexible para gas de 1.5 metros con certificación de seguridad.', '/img/manguerade1,5.png'),
(8, 'Manguera 3m', 'Mangueras y Conexiones', 5990, 5590, 60, 'Manguera flexible para gas de 3 metros de largo.', 'Manguera flexible de 3 m.', 'Manguera flexible para gas de 3 metros de largo.', '/img/gas3metros.png'),
(9, 'Detector de Gas', 'Accesorios', 15990, 14990, 20, 'Detector electrónico de fuga de gas con alarma sonora.', 'Detector de fuga con alarma sonora.', 'Detector electrónico de fuga de gas con alarma sonora.', '/img/detectorgas.png')
ON DUPLICATE KEY UPDATE
  nombre = VALUES(nombre), categoria = VALUES(categoria), precio = VALUES(precio),
  precio_comercial = VALUES(precio_comercial), descripcion = VALUES(descripcion),
  descripcion_breve = VALUES(descripcion_breve), descripcion_completa = VALUES(descripcion_completa),
  imagen = VALUES(imagen);
