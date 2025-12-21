# Оформление заказа и обратная связь

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
├──/api                         
│    ├── contacts.php    
│    ├── db.php
│    ├── orders.php             
│    └── products.php           
...
```

## Создание таблиц в базе данных
### Таблица contacts
```sql
CREATE TABLE IF NOT EXISTS contacts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  email VARCHAR(255) NOT NULL,
  message TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
### Таблица orders
```sql
CREATE TABLE IF NOT EXISTS orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  product VARCHAR(255) NOT NULL,
  quantity INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Файлы server/api/contacts.php и server/api/order.php
### contacts.php
```php
<?php
header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Origin: http://localhost:5173');
header('Access-Control-Allow-Methods: POST, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type');

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(204);
    exit;
}

require_once __DIR__ . '/db.php';

$data = json_decode(file_get_contents('php://input'), true);

if (!$data || empty($data['email']) || empty($data['message'])) {
    http_response_code(400);
    echo json_encode(['error' => 'Неверные данные']);
    exit;
}

try {
    $stmt = $pdo->prepare("INSERT INTO contacts (name, email, message) VALUES (?, ?, ?)");
    $stmt->execute([$data['name'] ?? 'Клиент', $data['email'], $data['message']]);
    echo json_encode(['success' => true, 'message' => 'Сообщение отправлено']);
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]);
}

```
### orders.php
```php
<?php
header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Origin: http://localhost:5173');
header('Access-Control-Allow-Methods: POST, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type');

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(204);
    exit;
}

require_once __DIR__ . '/db.php';

$data = json_decode(file_get_contents('php://input'), true);

if (!$data || empty($data['name']) || empty($data['email']) || empty($data['product']) || empty($data['quantity'])) {
    http_response_code(400);
    echo json_encode(['error' => 'Неверные данные']);
    exit;
}

try {
    $stmt = $pdo->prepare("INSERT INTO orders (name, email, product, quantity) VALUES (?, ?, ?, ?)");
    $stmt->execute([$data['name'], $data['email'], $data['product'], $data['quantity']]);
    echo json_encode(['success' => true, 'message' => 'Заказ успешно сохранён']);
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]);
}

```