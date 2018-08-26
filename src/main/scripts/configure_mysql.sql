CREATE DATABASE dashboard_dev;

CREATE USER 'pgd_dev_user'@'localhost' IDENTIFIED BY 'G0d1n';
CREATE USER 'pgd_dev_user'@'%' IDENTIFIED BY 'G0d1n';

GRANT ALL PRIVILEGES ON dashboard_dev.* to 'pgd_dev_user'@'%';
GRANT ALL PRIVILEGES ON dashboard_dev.* to 'pgd_dev_user'@'localhost';

-- PRODUCCION

CREATE DATABASE dashboard_prod;

CREATE USER 'pgd_prod_user'@'localhost' IDENTIFIED BY 'G0d1n';
CREATE USER 'pgd_prod_user'@'%' IDENTIFIED BY 'G0d1n';


GRANT ALL PRIVILEGES ON dashboard_prod.* to 'pgd_prod_user'@'localhost';
GRANT ALL PRIVILEGES ON dashboard_prod.* to 'pgd_prod_user'@'%';


