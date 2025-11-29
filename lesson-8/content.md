## Структура папки ./src/
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
│   └── Products.jsx
└── styles
    ├── Footer.css
    ├── global.css
    ├── Header.css
    ├── ProductCard.css
    └── Home.css
```

# Products Page:
### файл Products.jsx
```javascript
import products from '../data/products';
import ProductCard from '../components/ProductCard';
import '../styles/Products.css';

export default function Products(){
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
### Products.css
```css
.products-page {
    flex-direction: column;
    display: flex;
    width: 100%;
}
.products-page h1 {
    text-align: center;
    margin-bottom: 50px;
}
.products-container {
    width: 100dvw;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 40px;
    justify-content: center;
}
```

# ProductCard component
### ProductCard.jsx
```javascript
import '../styles/ProductCard.css';

export default function ProductCard({ product }) {
  return (
    <article className="pc-card" aria-labelledby={`p-${product.id}`}>
      <div className="pc-media">
        <img src={product.image} alt={product.title} />
      </div>

      <div className="pc-info">
        <h3 id={`p-${product.id}`} className="pc-title">{product.title}</h3>
        <p className="pc-short">{product.short}</p>
        <div className="pc-bottom">
          <div className="pc-price">{product.price} ₽</div>
          <button className="pc-add" aria-label={`Добавить ${product.title} в корзину`}>
            В корзину
          </button>
        </div>
      </div>
    </article>
  );
}
```
### ProductCard.css
```css
.pc-card { 
    display: flex; 
    gap: 20px;
    align-items: center;
    justify-content: space-between;
    border-radius: 16px; 
    overflow: hidden; 
    background: #FFFDF8;
    max-width: 600px;
    width: 100%;
    padding: 16px;
    box-shadow: 0 8px 18px rgba(0,0,0,0.07);
    border: 1px solid rgba(0,0,0,0.06);
    transition: 0.25s ease;
}

.pc-card:hover { 
    transform: translateY(-6px);
    box-shadow: 0 14px 28px rgba(0,0,0,0.10);
}

.pc-media {
    flex: 0 0 200px;
    display: flex;
    justify-content: center;
    align-items: center;
}

.pc-media img { 
    width: 200px; 
    height: 200px; 
    object-fit: cover; 
    border-radius: 12px;
    box-shadow: 0 6px 14px rgba(0,0,0,0.08);
}

.pc-info {
    flex: 1;
    min-width: 0;
}

.pc-info h4 {
    margin: 0 0 6px;
    font-size: 20px;
    color: #8B5E3C;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.pc-info p {
    margin: 0 0 10px;
    font-size: 15px;
    color: #A57952;
    line-height: 1.4;
}

.pc-bottom { 
    display: flex; 
    justify-content: space-between; 
    align-items: center; 
    margin-top: 10px;
}

.pc-price {
    font-size: 18px;
    font-weight: 700;
    color: #8B5E3C;
    background: #FFE9BF;
    padding: 8px 12px;
    border-radius: 10px;
}

.pc-add {  
    border: none; 
    padding: 10px 14px; 
    border-radius: 12px; 
    cursor: pointer; 
    background: #F2B94C;
    color: #5A3D23;
    font-weight: 700;
    transition: 0.2s ease;
}

.pc-add:hover {
    background: #E4AB41;
    transform: translateY(-3px);
}
```

# Home Page:
### Home.jsx
```javascript
import "../styles/Home.css";
import { Link } from "react-router";
export default function Home() {
  return (
    <main className="home">
      <h1>Добро пожаловать в магазин Винни-Пуха!</h1>
      <p>Здесь вы найдёте мёд, игрушки, сувениры и многое другое.</p>

      <div className="welcome-card">
        <h2>Мы рады вам!</h2>
      </div>
      <section className="section">
    <h3>Популярные товары</h3>
    <p>Самый вкусный мёд и лучшие игрушки — всё в одном месте.</p>
    <Link to="/products" className="link">
      Перейти к товарам
    </Link>
  </section>

  <section className="section">
    <h3>О нас</h3>
    <p>Мы — небольшой магазин, вдохновлённый историями о Винни-Пухе.</p>
    <Link to="/contacts" className="link">
      Перейти
    </Link>
  </section>
    </main>
  );
}
```
### Home.css
```css
.home {
  padding: 50px;
  padding-top: 150px;
  text-align: center;
  background: #FFF8EC;
  color: #8B5E3C;
  height: fit-content;
}

.welcome-card {
  margin: 40px auto;
  max-width: 500px;
  padding: 24px;
  background: #F7D488;
  border-radius: 24px;
  box-shadow: 0 6px 12px rgba(0,0,0,0.08);
}

h1 {
  font-size: 32px;
  margin-bottom: 10px;
}

p {
  font-size: 18px;
}

.section {
  margin: 100px auto;
  max-width: 600px;
  min-height: 250px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background: #FFE9BF;
  border-radius: 16px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.05);
  color: #8B5E3C;
}

.section h3 {
  margin-bottom: 8px;
  font-size: 22px;
}

.section p {
  margin: 0;
  font-size: 16px;
}

.link {
  display: block;
  margin-top: 20px;
  padding: 12px 20px;
  background: #F2B94C;
  color: #8B5E3C;
  text-decoration: none;
  border-radius: 12px;
  font-size: 18px;
  font-weight: 600;
  box-shadow: 0 4px 8px rgba(0,0,0,0.07);
  transition: 0.2s ease;
  width: 250px;
}

.link:hover {
  background: #E0A843;
}
```

# Остальные файлы

## Header
### Header.jsx
```javascript
import { Link } from 'react-router';
import "../styles/Header.css";

export default function Header() {
  return (
    <header className="header">
      <div className="logo"><Link to="/">Винни-Пух & Co</Link></div>

      <nav className="nav">
        <Link to="/">Главная</Link>
        <Link to="/products">Товары</Link>
        <Link to="/contacts">Контакты</Link>
        <Link to="/order" className="order-link">Заказать</Link>
      </nav>
    </header>
  );
}
```
### Header.css
```css
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #FFF8EC;
  padding: 20px 40px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
}
.logo {
  font-size: 24px;
  font-weight: 700;
  color: #8B5E3C;
}
.nav a {
  margin-left: 24px;
  text-decoration: none;
  color: #8B5E3C;
  font-weight: 500;
  position: relative;
  transition: color 0.3s ease;
}
.nav a::after {
  content: "";
  position: absolute;
  left: 0;
  bottom: -2px;
  width: 0%;
  height: 2px;
  background-color: currentColor;
  transition: width 0.3s ease;
}
.nav a:hover {
  color: #ff8427;
}
.nav a:hover::after {
  width: 100%;
}
```

## Footer
### Footer.jsx
```javascript
import "../styles/Footer.css";

function Footer() {
  return (
    <footer className="footer">
      <p>© {new Date().getFullYear()} Винни-Пух и Компания</p>
    </footer>
  );
}

export default Footer;
```

### Footer.css
```css
.footer {
  margin-top: 40px;
  padding: 20px;
  text-align: center;
  background: #FFF8EC;
  color: #8B5E3C;
  font-size: 14px;
  border-top: 1px solid #E6CDAA;
}
```

## App.jsx
```javascript
import React from 'react';
import { Routes, Route } from 'react-router';
import Header from './components/Header';
import Footer from './components/Footer';
import Home from './pages/Home';
import Products from './pages/Products';
import './styles/global.css';
import Contacts from './pages/Contacts';
import Order from './pages/Order';

export default function App(){
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/products" element={<Products />} />
        <Route path="/contacts" element={<Contacts />} />
        /* <Route path="/order" element={<Order />}/>
      </Routes>
      <Footer />
    </>
  );
}
```

## main.jsx
```javascript
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </StrictMode>,
)
```