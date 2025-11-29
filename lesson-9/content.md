# Создание фронтенда интернет-магазина: Оформление заказа и контакты
### Структура папки ./src/
```bash
./src
├── App.jsx
├── components
│   ├── Footer.jsx
│   ├── Header.jsx
│   └── ProductCard.jsx
├── main.jsx
├── data
│   └── products.js
├── pages
│   ├── Home.jsx
│   ├── Contacts.jsx      [+]
│   ├── Order.jsx         [+] 
│   └── Products.jsx
└── styles
    ├── Footer.css
    ├── global.css
    ├── Header.css
    ├── Order.css         [+]
    ├── Contacts.css      [+]
    ├── ProductCard.css
    └── Home.css
```

## Последовательное создание страницы оформления заказа (Order.jsx + Order.css)

### Файл Order.css
```css
.order-page {
  max-width: 500px;
  margin: 30px auto;
  padding: 20px;
  background: #fff8f0;
  border-radius: 16px;
  box-shadow: 0 12px 30px rgba(107,91,74,0.06);
}

.order-page h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #6b5b4a;
}

.order-form label {
  display: flex;
  flex-direction: column;
  margin-bottom: 12px;
  font-weight: 500;
  color: #6b5b4a;
}

.order-form input {
  padding: 8px 10px;
  border-radius: 8px;
  border: 1px solid #ccc;
  margin-top: 4px;
}

.order-form button {
  background: #f2b94a;
  color: white;
  border: none;
  padding: 10px 16px;
  border-radius: 10px;
  cursor: pointer;
  width: 100%;
  font-weight: 600;
  margin-top: 12px;
  transition: background 0.2s ease;
}

.order-form button:hover {
  background: #e0a632;
}

.order-success {
  text-align: center;
  font-size: 1.2em;
  color: #6b5b4a;
}
```

### Order.jsx
#### 1. Определение функционального компонента, а также импорт файла со стилями:
```javascript
import { useState } from "react";
import "../styles/Order.css";

function Order() {
  return (
    
  );
}
export default Order;
```
#### 2. Объявление начального состояния внутри компонента
```javascript
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    product: "",
    quantity: 1,
  });

   const [submitted, setSubmitted] = useState(false);
```
#### 3. Создание обработчика полей формы
```javascript
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };
```
#### 4. Обработчик события отправки формы
```javascript
  const handleSubmit = (e) => {
    e.preventDefault();
    // В будущем здесь можно отправить данные на сервер
    console.log("Данные заказа:", formData);
    setSubmitted(true);
  };
```
#### 5. JSX: корневой блок и условный рендер
```javascript
return (
    <div className="order-page">
      <h2>Оформить заказ</h2>
      {submitted ? (
        <div className="order-success">
          Спасибо, {formData.name}! Ваш заказ принят.
        </div>
      ) : (
        <form onSubmit={handleSubmit} className="order-form">

        </form>
      )}
    </div>
  );
```
#### 6. Содержимое тега form:
```javascript
<form onSubmit={handleSubmit} className="order-form">
  <label>
    Ваше имя:
    <input
      type="text"
      name="name"
      value={formData.name}
      onChange={handleChange}
      required
    />
  </label>
  <label>
    Email:
    <input
      type="email"
      name="email"
      value={formData.email}
      onChange={handleChange}
      required
    />
  </label>
  <label>
    Товар:
    <input
      type="text"
      name="product"
      value={formData.product}
      onChange={handleChange}
      required
    />
  </label>
  <label>
    Количество:
    <input
      type="number"
      name="quantity"
      value={formData.quantity}
      onChange={handleChange}
      min="1"
    />
  </label>
  <button type="submit">Отправить заказ</button>
</form>
```
#### Окончательный результат:
```javascript
import { useState } from "react";
import "../styles/Order.css";

export default function Order() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    product: "",
    quantity: 1,
  });

  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // В будущем здесь можно отправить данные на сервер через fetch/axios
    console.log("Данные заказа:", formData);
    setSubmitted(true);
  };

  return (
    <div className="order-page">
      <h2>Оформить заказ</h2>
      {submitted ? (
        <div className="order-success">
          Спасибо, {formData.name}! Ваш заказ принят.
        </div>
      ) : (
        <form onSubmit={handleSubmit} className="order-form">
          <label>
            Ваше имя:
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            Email:
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            Товар:
            <input
              type="text"
              name="product"
              value={formData.product}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            Количество:
            <input
              type="number"
              name="quantity"
              value={formData.quantity}
              onChange={handleChange}
              min="1"
            />
          </label>
          <button type="submit">Отправить заказ</button>
        </form>
      )}
    </div>
  );
}

```
## Последовательное создание страницы с контактами (Contacts.jsx + Contacts.css)

### Contacts.css:
```css
/* Contacts page styles */
.contact {
  padding: 40px 20px;
  text-align: center;
  background: #FFF8EC;
  color: #8B5E3C;
  min-height: 100vh;
  box-sizing: border-box;
  font-family: system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial;
}
.contact h1 {
  margin: 0 0 6px;
  font-size: 28px;
  color: #6e4326;
}
.contact .lead {
  margin: 0 0 18px;
  font-size: 15px;
  color: #A57952;
}
/* Grid */
.contact-grid {
  display: flex;
  gap: 20px;
  justify-content: center;
  align-items: flex-start;
  margin: 18px auto 28px;
  max-width: 1000px;
  padding: 0 12px;
  flex-wrap: wrap;
  box-sizing: border-box;
}
/* Cards */
.contact-card {
  background: #FFFDF8;
  border: 1px solid rgba(0,0,0,0.06);
  box-shadow: 0 8px 18px rgba(0,0,0,0.05);
  border-radius: 12px;
  padding: 18px;
  width: 420px;
  text-align: left;
  box-sizing: border-box;
}
.contact-card.contact-form-card {
  width: 420px;
}
/* Headings inside cards */
.contact-card h2 {
  margin: 0 0 10px;
  font-size: 20px;
  color: #6e4326;
}
/* Contact info */
.contact-card p {
  margin: 6px 0;
  color: #6e4326;
  font-size: 14px;
}
.contact-card a {
  color: #5A3D23;
  text-decoration: none;
}
.contact-card a:hover {
  text-decoration: underline;
}
/* Hours */
.contact-hours h3 {
  margin: 12px 0 6px;
  font-size: 15px;
  color: #6e4326;
}
.contact-hours p {
  margin: 4px 0;
  color: #A57952;
  font-size: 14px;
}
/* Form */
.contact-form label {
  display: block;
  margin-bottom: 10px;
  font-size: 14px;
  color: #6e4326;
}
.contact-form input,
.contact-form textarea {
  width: 100%;
  margin-top: 6px;
  padding: 8px 10px;
  border-radius: 10px;
  border: 1px solid rgba(0,0,0,0.08);
  font-size: 14px;
  background: #ffffff;
  color: #5A3D23;
  box-sizing: border-box;
}
.contact-form textarea {
  resize: vertical;
  min-height: 100px;
}
.contact-form input:focus,
.contact-form textarea:focus {
  outline: none;
  border-color: #E4AB41;
  box-shadow: 0 4px 10px rgba(228,171,65,0.08);
}
/* Actions: buttons & links */
.contact-actions {
  display: flex;
  gap: 10px;
  margin-top: 12px;
  align-items: center;
  flex-wrap: wrap;
}
.products-link.small,
.home-link.small {
  display: inline-block;
  padding: 8px 12px;
  background: #F2B94C;
  color: #5A3D23;
  text-decoration: none;
  border-radius: 10px;
  font-weight: 700;
  font-size: 14px;
  border: 1px solid rgba(0,0,0,0.05);
}
.products-link.small:hover,
.home-link.small:hover {
  background: #E4AB41;
}
/* Submit button uses simpler style like .pc-add */
.contact-actions .pc-add {
  border: none;
  padding: 10px 14px;
  border-radius: 12px;
  cursor: pointer;
  background: #F2B94C;
  color: #5A3D23;
  font-weight: 700;
  transition: background 0.18s ease, transform 0.12s ease;
}
.contact-actions .pc-add:hover {
  background: #E4AB41;
  transform: translateY(-2px);
}
/* Map placeholder */
.contact-map {
  max-width: 1000px;
  margin: 12px auto 40px;
  padding: 0 12px;
  box-sizing: border-box;
}
.map-placeholder {
  height: 180px;
  border-radius: 10px;
  background: linear-gradient(180deg, #FFF6E6, #FFFDF8);
  border: 1px solid rgba(0,0,0,0.05);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #A57952;
  font-size: 14px;
  margin-top: 10px;
}
/* Small helpers */
.small-note {
  font-size: 13px;
  color: #A57952;
  margin-top: 8px;
}
/* Responsive */
@media (max-width: 880px) {
  .contact-card,
  .contact-card.contact-form-card {
    width: 92%;
    max-width: 520px;
  }

  .contact {
    padding: 28px 12px;
  }

  .map-placeholder {
    height: 200px;
  }
}
@media (max-width: 480px) {
  .contact h1 { font-size: 22px; }
  .contact .lead { font-size: 14px; }
  .contact-card { padding: 14px; border-radius: 10px; }
  .contact-form textarea { min-height: 120px; }
}

```

### Contacts.jsx
#### 1. Определение функционального компонента, а также импорт файла со стилями:
```javascript
import React from "react";
import { Link } from "react-router";
import "../styles/Contacts.css";

function Contacts() {
  return (
    
  );
}
export default Contacts;
```
#### 2. Функция обработчика отправки формы (handleSubmit)
```javascript
function handleSubmit(e) {
    e.preventDefault();
    // простая имитация отправки — заменить на реальный запрос позже
    const name = e.target.name.value || "Клиент";
    alert(`Спасибо, ${name}! Ваше сообщение отправлено.`);
    e.target.reset();
  }
```
#### 3.1 JSX - структура разметки 
```javascript
return (
    <main className="contact">
      <h1>Контакты — Магазин Винни-Пуха</h1>
      <p className="lead">Напишите нам или приходите в гости — будем рады!</p>

      <div className="contact-grid">
        
      </div>

    </main>
  );
```
#### 3.2 JSX - структура разметки: продолжение
```javascript
<main className="contact">
  <h1>Контакты — Магазин Винни-Пуха</h1>
  <p className="lead">Напишите нам или приходите в гости — будем рады!</p>

  <div className="contact-grid">
    <div className="contact-card">
      
    </div>
    
    <aside className="contact-card contact-form-card">
      <h2>Написать нам</h2>
      <form className="contact-form" onSubmit={handleSubmit}>
        
      </form>
    </aside>
  </div>
</main>
```
#### 3.3 JSX - структура разметки: продолжение
```javascript
<main className="contact">
  <h1>Контакты — Магазин Винни-Пуха</h1>
  <p className="lead">Напишите нам или приходите в гости — будем рады!</p>

  <div className="contact-grid">
    <div className="contact-card">
      <h2>Контактная информация</h2>
      <p><strong>Адрес:</strong> Медовый переулок, 7, г. Сказочный</p>
      <p><strong>Телефон:</strong> <a href="tel:+74950000000">+7 (495) 000-00-00</a></p>
      <p><strong>Email:</strong> <a href="mailto:info@winnie-shop.example">info@winnie-shop.example</a></p>

      <div className="contact-hours">
        <h3>Часы работы</h3>
        <p>Пн–Пт: 10:00 — 19:00</p>
        <p>Сб: 11:00 — 17:00</p>
        <p>Вс: выходной</p>
      </div>

      <div className="contact-actions">
        <Link to="/products" className="products-link small">Перейти к товарам</Link>
        <Link to="/" className="home-link small">На главную</Link>
      </div>
    </div>
    
    <aside className="contact-card contact-form-card">
      <h2>Написать нам</h2>
      <form className="contact-form" onSubmit={handleSubmit}>
        
      </form>
    </aside>
  </div>
</main>
```
#### 3.4 JSX - структура разметки: финиш
```javascript
<main className="contact">
  <h1>Контакты — Магазин Винни-Пуха</h1>
  <p className="lead">Напишите нам или приходите в гости — будем рады!</p>

  <div className="contact-grid">
    <div className="contact-card">
      <h2>Контактная информация</h2>
      <p><strong>Адрес:</strong> Медовый переулок, 7, г. Сказочный</p>
      <p><strong>Телефон:</strong> <a href="tel:+74950000000">+7 (495) 000-00-00</a></p>
      <p><strong>Email:</strong> <a href="mailto:info@winnie-shop.example">info@winnie-shop.example</a></p>

      <div className="contact-hours">
        <h3>Часы работы</h3>
        <p>Пн–Пт: 10:00 — 19:00</p>
        <p>Сб: 11:00 — 17:00</p>
        <p>Вс: выходной</p>
      </div>

      <div className="contact-actions">
        <Link to="/products" className="products-link small">Перейти к товарам</Link>
        <Link to="/" className="home-link small">На главную</Link>
      </div>
    </div>
    
    <aside className="contact-card contact-form-card">
      <h2>Написать нам</h2>
      <form className="contact-form" onSubmit={handleSubmit}>
        <div className="contact-fields">
          <label>
            Имя
            <input name="name" type="text" placeholder="Ваше имя" />
          </label>

          <label>
            Email
            <input name="email" type="email" placeholder="you@example.com" required />
          </label>

          <label>
            Сообщение
            <textarea name="message" rows="4" placeholder="Текст сообщения" required />
          </label>
        </div>

        <div className="contact-actions">
          <button type="submit" className="pc-add">Отправить</button>
        </div>
      </form>
    </aside>
  </div>

</main>
```
### Окончательный результат
```javascript
import React from "react";
import { Link } from "react-router";
import '../styles/Contacts.css'

export default function Contacts() {
  function handleSubmit(e) {
    e.preventDefault();
    const name = e.target.name.value || "Клиент";
    alert(`Спасибо, ${name}! Ваше сообщение отправлено.`);
    e.target.reset();
  }

  return (
    <main className="contact">
      <h1>Контакты — Магазин Винни-Пуха</h1>
      <p className="lead">Напишите нам или приходите в гости — будем рады!</p>

      <div className="contact-grid">
        <div className="contact-card">
          <h2>Контактная информация</h2>
          <p><strong>Адрес:</strong> Медовый переулок, 7, г. Сказочный</p>
          <p><strong>Телефон:</strong> <a href="tel:+74950000000">+7 (495) 000-00-00</a></p>
          <p><strong>Email:</strong> <a href="mailto:info@winnie-shop.example">info@winnie-shop.example</a></p>

          <div className="contact-hours">
            <h3>Часы работы</h3>
            <p>Пн–Пт: 10:00 — 19:00</p>
            <p>Сб: 11:00 — 17:00</p>
            <p>Вс: выходной</p>
          </div>

          <div className="contact-actions">
            <Link to="/products" className="products-link small">Перейти к товарам</Link>
            <Link to="/" className="home-link small">На главную</Link>
          </div>
        </div>

        <aside className="contact-card contact-form-card">
          <h2>Написать нам</h2>
          <form className="contact-form" onSubmit={handleSubmit}>
            <div className="contact-fields">
              <label>
                Имя
                <input name="name" type="text" placeholder="Ваше имя" />
              </label>

              <label>
                Email
                <input name="email" type="email" placeholder="you@example.com" required />
              </label>

              <label>
                Сообщение
                <textarea name="message" rows="4" placeholder="Текст сообщения" required />
              </label>
            </div>

            <div className="contact-actions">
              <button type="submit" className="pc-add">Отправить</button>
            </div>
          </form>
        </aside>
      </div>

    </main>
  );
}

```
