# Создание БД и первый API

## Структура проекта
### Директория client:
```bash
./client
├──/node_modules
│    └── ...
├──/public
│    └── ...
├──/src
│   ├── App.jsx
│   ├── components
│   │   ├── Footer.jsx
│   │   ├── Header.jsx
│   │   └── ProductCard.jsx
│   ├── main.jsx
│   ├── data
│   │   └── products.js
│   ├── pages
│   │   ├── Home.jsx
│   │   ├── Contacts.jsx      
│   │   ├── Order.jsx        
│   │   └── Products.jsx
│   └── styles
│       ├── Footer.css
│       ├── global.css
│       ├── Header.css
│       ├── Order.css        
│       ├── Contacts.css     
│       ├── ProductCard.css
│       └── Home.css
├── package.json
├── index.html
├── vite.config.js
└── ...
```
### Директория server:
``` bash
./server
├──/api                         [+]
│    ├── db.php                 [+]
│    └── products.php           [+]
...
```

## Серверная часть
### Содание файла server/api/db.php
Прежде, чем создавать файл для подключения к базе данных, нужно создать базу данных с таблицей для продуктов. 
http://localhost/phpmyadmin -> вкладка SQL:
```sql
CREATE DATABASE IF NOT EXISTS winnie_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
 USE winnie_shop;
 
 CREATE TABLE IF NOT EXISTS products (
 id INT AUTO_INCREMENT PRIMARY KEY,
 title VARCHAR(255) NOT NULL,
 short TEXT,
 description TEXT,
 image VARCHAR(255),
 price INT NOT NULL,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
 );
 
 INSERT INTO products (title, short, description, image, price) VALUES
 ('Мёд луговой', 'Банка 250г', 'Вкуснейший мёд от пчёл Винни', 'images/honey.jpg', 250),
 ('Плюшевый мишка', 'Маленький плюш', 'Мягкая игрушка Винни', 'images/teddy.jpg', 800),
 ('Мёд с орешками', 'Банка 250г', 'Мёд с добавлением орехов', 'images/honey-nuts.jpg', 300);
 ```

#### ./api/db.php
```php
<?php

$host = '127.0.0.1';
$db   = 'winnie_shop';
$user = 'root';
$pass = '';
$charset = 'utf8mb4';

$dsn = "mysql:host=$host;dbname=$db;charset=$charset";
$options = [
    PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
];

try {
    $pdo = new PDO($dsn, $user, $pass, $options);
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(['error' => 'DB connection failed: ' . $e->getMessage()]);
    exit;
}
```
#### ./api/products.php
```php
<?php
header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Origin: http://localhost:5173');
header('Access-Control-Allow-Methods: GET, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type');

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(204);
    exit;
}

require_once __DIR__ . '/db.php';

try {
    $stmt = $pdo->query('SELECT id, title, short, description, image, price FROM products ORDER BY id ASC');
    $products = $stmt->fetchAll();
    echo json_encode($products, JSON_UNESCAPED_UNICODE);
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]);
}
```

## React приложение 
### Изменение файла ./src/pages/Products.jsx
```javascript
import { useEffect, useState } from 'react';
import ProductCard from '../components/ProductCard';
import '../styles/Products.css';

export default function Products(){
  const [products, setProducts] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch('http://localhost/api/products.php')
      .then(res => {
        if (!res.ok) throw new Error('Ошибка сети: ' + res.status);
        return res.json();
      })
      .then(data => setProducts(data))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <main className="products-page"><h1>Каталог товаров</h1><p>Загрузка...</p></main>;
  if (error) return <main className="products-page"><h1>Каталог товаров</h1><p>Ошибка: {error}</p></main>;

  return (
    <main className="products-page">
      <h1>Каталог товаров</h1>
      <section className="products-container" aria-live="polite">
        {products.map(p => <ProductCard key={p.id} product={p} />)}
      </section>
    </main>
  );
}
```